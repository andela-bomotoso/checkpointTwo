package checkpoint.andela.db;

import checkpoint.andela.parser.AttributeValue;

import java.util.ArrayList;
import java.util.List;

public class DbWriter {

    DatabaseManager databaseManager;

    public DbWriter( DatabaseManager databaseManager) {

        this.databaseManager = databaseManager;
    }

    public void writeBufferToDatabase(List<AttributeValue<String,String>> bufferedFileContent,String databaseName, String tableName, List<String> tableFields,String recordMaker) {

        databaseManager.establishConnection();
        List<String> recordKeys = new ArrayList<>();
        List<String> recordValues = new ArrayList<>();

        for (AttributeValue<String, String> pair : bufferedFileContent) {

            if (!pair.attribute.equals(recordMaker)) {

                if ((!recordKeys.contains(pair.attribute)) && tableFields.contains(pair.attribute)) {

                    recordKeys.add(pair.attribute);
                    recordValues.add(pair.value);
                } else if (tableFields.contains(pair.attribute)) {
                    modifyExistingField(recordKeys, recordValues, pair.attribute, pair.value);
                }
            } else if (!recordKeys.isEmpty()) {

                String sql = "INSERT INTO " + databaseName + "." + tableName + databaseManager.generateInsertStatement(recordKeys, recordValues);
                databaseManager.runQuery(sql);
                recordKeys.clear();
                recordValues.clear();
            }
        }
        databaseManager.closeConnection();
    }

    public void modifyExistingField(List<String> keys, List<String> values, String currentKey, String currentValue) {
        int index = 0;
        for (int i = 0; i < keys.size(); i++) {
            index = i;
            if (keys.get(index).equals(currentKey)) {
                break;
            }
        }
        values.set(index, values.get(index) + "," + currentValue);
    }

}

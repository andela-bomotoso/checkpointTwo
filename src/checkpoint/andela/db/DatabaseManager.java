package checkpoint.andela.db;

import java.sql.*;
import java.util.List;
import java.util.Properties;

public class DatabaseManager {

    Properties connectionProperties = new Properties();
    private String databaseUrl;
    private String username;
    private String password;
    private String databaseName;
    Statement statement;
    Connection connection;

    public DatabaseManager(String databaseUrl, String username, String password) {

        setDatabaseUrl(databaseUrl);
        setUsername(username);
        setPassword(password);

        connectionProperties.put("user",this.username);
        connectionProperties.put("password",this.password);
    }

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public void setDatabaseUrl(String databaseUrl) {
        this.databaseUrl = databaseUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public Connection establishConnection() {

        try {
            connection = DriverManager.getConnection(databaseUrl, connectionProperties);
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return connection;
    }

    public void closeConnection() {

        if(connection != null){
            try {
                connection.close();
            }
            catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }

    public boolean databaseAlreadyExist(String databaseName){

        try{
            ResultSet resultSet = establishConnection().getMetaData().getCatalogs();
            while (resultSet.next()) {

                String dbName = resultSet.getString(1);
                if(dbName.equals(databaseName)){
                    return true;
                }
            }
            resultSet.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private void dropTableIfExists(String databaseName, String tableName){

        establishConnection();
        String query = "DROP TABLE IF EXISTS "+ databaseName+"."+tableName;
        runQuery(query);
    }

    public void createDatabase(String databaseName) {

        establishConnection();
        if( !databaseAlreadyExist(databaseName)) {
            String createDatabaseQuery = "CREATE DATABASE " + databaseName;
            runQuery(createDatabaseQuery);
        }
    }

    public void createTable( String databaseName, String tableName,List<String> fieldNames) {

        dropTableIfExists(databaseName,tableName);

        String query = "CREATE TABLE "+databaseName+"."+tableName+" (";
        String createTableQuery;

        for(String field:fieldNames) {
            query+="`"+field+"` text,";
        }
        createTableQuery = removeLastCharacter(query);
        String createTableSql =createTableQuery+")";
        runQuery(createTableSql);
    }

    public void runQuery(String query) {

        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public String removeLastCharacter(String str) {

        return str.substring(0,str.length()-1);
    }

    public String generateInsertStatement(List<String> recordKeys, List<String> recordValues) {
        String fields = "";
        String values = "";
        String querySubstring = "";

        for (int i = 0; i < recordKeys.size(); i++) {

            fields += "`" + recordKeys.get(i) + "`,";
            values += "\"" + escapeDoubleQuotes(recordValues.get(i)) + "\",";
        }

        querySubstring = "(" + removeLastCharacter(fields) + ") " +
                "VALUES " + "(" + removeLastCharacter(values) + ")";

        return querySubstring;
    }

    public String escapeDoubleQuotes(String str) {
        return str.replace("\"", "\\\"");
    }

}

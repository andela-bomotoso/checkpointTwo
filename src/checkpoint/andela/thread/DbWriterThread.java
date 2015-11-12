package checkpoint.andela.thread;

import checkpoint.andela.buffer.DataBuffer;
import checkpoint.andela.db.DatabaseManager;
import checkpoint.andela.db.DbWriter;
import checkpoint.andela.parser.AttributeValue;
import checkpoint.andela.parser.FileParser;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

public class DbWriterThread implements Runnable{

    private String databaseName;
    private String tableName;
    private List<String>tableFields;
    private String recordMaker;

    DatabaseManager databaseManager;
    DbWriter dbWriter = new DbWriter(databaseManager);

    public static boolean runState = true;

    public DbWriterThread(DbWriter dbWriter, String databaseName, String tableName,
                          List<String> tableFields, String recordMaker) {

        this.dbWriter = dbWriter;
        this.databaseName = databaseName;
        this.tableName = tableName;
        this.tableFields = tableFields;
        this.recordMaker = recordMaker;
    }

    public void run() {

        List<AttributeValue<String,String>> bufferedRecord = new ArrayList<AttributeValue<String, String>>();

        while (runState) {

            AttributeValue bufferRead = DataBuffer.getBuffer();
            bufferedRecord.add(bufferRead);
            ThreadLogger.logReadActivity( bufferRead);

            if (bufferRead.attribute.equals(recordMaker)) {
                writeRecordToDatabase(bufferedRecord);
            }
            runState = FileParserThread.runState;
        }

        runState = false;
    }

    public void writeRecordToDatabase(List<AttributeValue<String,String>> bufferedRecord) {

        dbWriter.writeBufferToDatabase(bufferedRecord,databaseName,tableName,tableFields,recordMaker);
        bufferedRecord.clear();
    }
}
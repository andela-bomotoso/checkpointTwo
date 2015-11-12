package checkpoint.andela.thread;


import checkpoint.andela.db.DatabaseManager;
import checkpoint.andela.db.DbWriter;
import Util.Config;
import checkpoint.andela.parser.FileParser;
import checkpoint.andela.parser.ReactantFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[]args){

        ReactantFile reactantFile = new ReactantFile(Config.reactantFilePath," - ","//","#");

        DatabaseManager databaseManager = new DatabaseManager(Config.databaseURL, Config.databaseUsername, Config.databasePassword);
        DbWriter dbWriter = new DbWriter(databaseManager);

        FileParser fileParser = new FileParser(reactantFile);

        FileParserThread fileParserThread =  new FileParserThread(fileParser,reactantFile);

        List<String> tableFields = new ArrayList<String>(Arrays.asList("UNIQUE-ID", "TYPES", "COMMON-NAME", "ATOM-MAPPINGS"));

        DbWriterThread dbWriterThread = new DbWriterThread(dbWriter,"reactiondb","reactions",tableFields,"//");

        LogWriterThread logWriterThread = new LogWriterThread(Config.logFilePath);

        ExecutorService executorService = Executors.newCachedThreadPool();

        fileParser.writeFileToBuffer(reactantFile);

        executorService.execute(fileParserThread);
        executorService.execute(dbWriterThread);
        executorService.execute(logWriterThread);
        executorService.shutdown();

    }
}

package checkpoint.andela.parser;

import checkpoint.andela.db.DatabaseManager;
import checkpoint.andela.db.DbWriter;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main {

    public static void main(String[]args) {

        DatabaseManager databaseManager = new DatabaseManager(Config.databaseURL, Config.databaseUsername, Config.databasePassword);
        //String filePath = "C:\\Users\\GRACE\\.IdeaIC14\\Checkpoints\\checkpoint2\\reactions.DAT";
        ReactantFile  reactantFile = new ReactantFile(Config.reactantFilePath," - ","//","#");
        FileParser fileParser = new FileParser(reactantFile);
        fileParser.writeFileToBuffer(reactantFile);
        List<String> tableFields = new ArrayList<String>(Arrays.asList("UNIQUE-ID", "TYPES", "COMMON-NAME", "ATOM-MAPPINGS"));
        List<AttributeValue<String, String>> bufferedFileContent;
        bufferedFileContent = fileParser.writeFileToBuffer(reactantFile);
        DbWriter dbWriter = new DbWriter( databaseManager);
        dbWriter.writeBufferToDatabase(bufferedFileContent,"reactiondb","reactions",tableFields,"//");

    }
}

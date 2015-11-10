package checkpoint.andela.db;

import checkpoint.andela.parser.*;
import junit.framework.TestCase;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DbWriterTest extends TestCase {

    ReactantFile reactantFile;
    DatabaseManager databaseManager;
    FileParser fileParser;
    DbWriter dbWriter;
    List<String> tableFields;
    List<AttributeValue<String, String>> bufferedFileContent;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        databaseManager = new DatabaseManager("jdbc:mysql://localhost/", "root", "admin");
        String testFilePath = "C:\\Users\\GRACE\\.IdeaIC14\\Checkpoints\\checkpointTwo\\reactions.DAT";

        reactantFile = new ReactantFile(testFilePath, " - ", "//", "#");

        tableFields = new ArrayList<String>(Arrays.asList("UNIQUE-ID", "TYPES", "COMMON-NAME", "ATOM-MAPPINGS"));

        fileParser = new FileParser(reactantFile);

        fileParser.writeFileToBuffer(reactantFile);

        dbWriter = new DbWriter(databaseManager);
    }

    @Test
    public void testWriteBufferToDatabaseWhenDatabaseHasAValidRecord() throws Exception {

        dbWriter.writeBufferToDatabase(fileParser.getReactantFileBuffer(), "reactiondb", "reactions", tableFields, "//");

        String queryCheck = "SELECT * from reactiondb.reactions WHERE `unique-id` = " + "\"RIBOFLAVINSYNDEAM-RXN\"";
        Statement statement = databaseManager.establishConnection().createStatement();
        ResultSet rs = statement.executeQuery(queryCheck);
        assertTrue(rs.absolute(1));

    }

    @Test
    public void testWriteBufferToDatabaseWhenDatabaseHasNoValidRecord() throws Exception {

        dbWriter.writeBufferToDatabase(fileParser.getReactantFileBuffer(), "reactiondb", "reactions", tableFields, "//");

        String queryCheck = "SELECT * from reactiondb.reactions WHERE `unique-id` = " + "\"C10-L1-0119\"";
        Statement statement = databaseManager.establishConnection().createStatement();
        ResultSet rs = statement.executeQuery(queryCheck);
        assertFalse(rs.absolute(1));

    }

    @Test
    public void testModifyExistingField() throws Exception {

        List<String> recordKeys = new ArrayList<>(Arrays.asList("RIGHT", "^COEFFICIENT"));
        List<String> recordValues = new ArrayList<>(Arrays.asList("PROTON,CPD-12230,D-ALANINE", "4"));

        String currentKey = "RIGHT";
        String currentValue = "UNDECAPRENYL-DIPHOSPHATE";

        List<String> recordValuesAfterModification = new ArrayList<>(Arrays.asList("PROTON,CPD-12230,D-ALANINE,UNDECAPRENYL-DIPHOSPHATE", "4"));
        dbWriter.modifyExistingField(recordKeys, recordValues, currentKey, currentValue);

        assertEquals(recordValues.size(), recordKeys.size());
        assertEquals(recordValuesAfterModification.get(0), recordValues.get(0));
    }
}

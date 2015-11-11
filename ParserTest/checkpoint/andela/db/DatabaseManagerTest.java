package checkpoint.andela.db;

import checkpoint.andela.parser.Config;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseManagerTest extends TestCase {
    DatabaseManager databaseManager;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        databaseManager =  new DatabaseManager(Config.databaseURL,Config.databaseUsername,Config.databasePassword);
        databaseManager.establishConnection();
    }

    @Test
    public void testEstablishConnection() throws Exception {
        Connection connection =  databaseManager.establishConnection();
        assertNotNull(connection);
    }

    @Test
    public void testDatabaseAlreadyExistWhenThereIsAnExistingDatabase() throws Exception {
        databaseManager.createDatabase("reactiondb");
        assertTrue(databaseManager.databaseAlreadyExist("reactiondb"));
    }

    @Test
    public void testDatabaseAlreadyExistWhenThereIsNoExistingDatabase() throws Exception {
        assertFalse(databaseManager.databaseAlreadyExist("ANDELA"));
    }

    @Test
    public void testCreateTable() throws Exception {
        List<String> tableFields = new ArrayList<String>(Arrays.asList("UNIQUE-ID", "TYPES", "COMMON-NAME", "ATOM-MAPPINGS"));
        databaseManager.createTable("reactiondb","reactions",tableFields);
    }

    @Test
    public void testRunQuery() throws Exception {

        String dropDbIfExists = "DROP DATABASE IF EXISTS ANDELA";
        databaseManager.runQuery(dropDbIfExists);
        String createDatabaseQuery = "CREATE DATABASE ANDELA";
        databaseManager.runQuery(createDatabaseQuery);
        assertTrue(databaseManager.databaseAlreadyExist("andela"));
        databaseManager.runQuery(dropDbIfExists);
        assertFalse(databaseManager.databaseAlreadyExist("andela"));
    }

    @Test
    public void testRemoveLastCharacter() throws Exception {
        String str1 = "CREATE TABLE ANDELA (Name,Age,Class,";
        String str2 = "CREATE TABLE ANDELA (Name,Age,Class";
        String str3 = databaseManager.removeLastCharacter(str1);
        assertEquals(str2,str3);
    }

    @Test
    public void testGenerateInsertStatement() throws Exception {
        List<String>recordKeys = new ArrayList<>(Arrays.asList("UNIQUE-ID", "TYPES", "COMMON-NAME"));
        List<String>recordValues = new ArrayList<>(Arrays.asList("RXN-8748","Small-Molecule-Reactions","6-phospho-lactosidase"));

        String insertQuery = "(`UNIQUE-ID`,`TYPES`,`COMMON-NAME`) VALUES (\"RXN-8748\""+","+"\"Small-Molecule-Reactions\""+","+"\"6-phospho-lactosidase\")";
        String insertQueryGenerated = databaseManager.generateInsertStatement(recordKeys, recordValues);

        assertEquals(insertQuery, insertQueryGenerated);
    }

    @Test
    public void testEscapeDoubleQuotes() throws Exception {
        String str1 = "CobA [ambiguous - see <a href=\\\"query.php?ec=2.5.1.17\\\" target=\\\"new\\\">EC 2.5.1.17</a>] SUMT";
        String str2 = databaseManager.escapeDoubleQuotes("CobA [ambiguous - see <a href=\"query.php?ec=2.5.1.17\" target=\"new\">EC 2.5.1.17</a>] SUMT");
        assertEquals(str1, str2);
    }
}
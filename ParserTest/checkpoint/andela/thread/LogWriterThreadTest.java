package checkpoint.andela.thread;

import Util.Config;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.File;


public class LogWriterThreadTest extends TestCase {

    LogWriterThread logWriterThread;
    String filePath1;
    String filePath2;


    public void setUp() throws Exception {

        super.setUp();
    }

    @Test
    public void testCheckIfFileExistWhenFileDoesNotExist() throws Exception {

        logWriterThread = new LogWriterThread(Config.nonExistentFilePath);
        File file1 = new File(Config.nonExistentFilePath);
        assertFalse(file1.exists());
        logWriterThread.checkIfFileExist();
        assertTrue(file1.exists());
        file1.delete();
    }

    @Test
    public void testCheckFileExistWhenFileExists() throws Exception {

        logWriterThread = new LogWriterThread(Config.logFilePath);
        File file2 = new File(Config.logFilePath);
        assertTrue(file2.exists());
        logWriterThread.checkIfFileExist();
        assertTrue(file2.exists());

    }
}
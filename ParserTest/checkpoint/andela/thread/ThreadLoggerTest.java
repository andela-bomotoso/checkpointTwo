package checkpoint.andela.thread;

import checkpoint.andela.buffer.LogBuffer;
import junit.framework.TestCase;
import org.junit.Test;

public class ThreadLoggerTest extends TestCase {

    @Test
    public void testLogWriteActivity() throws Exception {

        String value = "RXN-8748";
        String activityTime = "2015-11-11 22:43:15";
        String currentLog = "FileParser Thread " +"(" +activityTime+ ")----wrote UNIQUE ID " + value + " to buffer" ;
        ThreadLogger.logWriteActivity(activityTime,value);
        assertEquals(LogBuffer.getBuffer(), currentLog);
    }

    public void testLogReadActivity() throws Exception {

        String value = "RXN-8748";
        String activityTime = "2015-11-11 22:43:15";
        String currentLog = "DbWriter Thread " +"(" +activityTime+ ")----collected UNIQUE ID " + value + " from buffer" ;
        ThreadLogger.logReadActivity(activityTime,value);
        assertEquals(LogBuffer.getBuffer(),currentLog);

    }
}
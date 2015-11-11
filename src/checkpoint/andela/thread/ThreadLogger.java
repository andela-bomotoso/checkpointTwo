package checkpoint.andela.thread;
import checkpoint.andela.buffer.LogBuffer;
import checkpoint.andela.parser.AttributeValue;

public class ThreadLogger {

    public static void logWriteActivity(String activityTime, AttributeValue currentLine) {

        String value = currentLine.value.toString();
        String currentLog = "FileParser Thread " +"(" +activityTime+ ")----wrote UNIQUE ID " + value + " to buffer" ;
        LogBuffer.setBuffer(currentLog);
    }

    public static void logReadActivity(String activityTime,AttributeValue currentLine)
    {
        String value = currentLine.value.toString();
        String currentLog = "DbWriter Thread " +"(" +activityTime+ ")----collected UNIQUE ID " + value + " from buffer" ;
        LogBuffer.setBuffer(currentLog);
    }
}

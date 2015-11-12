package checkpoint.andela.thread;
import Util.Config;
import checkpoint.andela.buffer.LogBuffer;
import checkpoint.andela.parser.AttributeValue;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class ThreadLogger {

    static String activityTime = DateTime.now().toString("yyyy-MM-dd HH:mm:ss");

    public static void logWriteActivity( AttributeValue currentLine) {

        String attribute = currentLine.attribute.toString();
        String value = currentLine.value.toString();
        if(attribute.equals(Config.reactantFilePrimaryKey)) {
            String currentLog = "FileParser Thread " + "(" + activityTime + ")----wrote " + Config.reactantFilePrimaryKey +" "+ value + " to buffer";
            LogBuffer.setBuffer(currentLog);
        }
    }

    public static void logReadActivity(AttributeValue currentLine)
    {
        String attribute = currentLine.attribute.toString();
        String value = currentLine.value.toString();
        if(attribute.equals(Config.reactantFilePrimaryKey)) {
            String currentLog = "DbWriter Thread " + "(" + activityTime + ")----collected " + Config.reactantFilePrimaryKey + " "+value + " from buffer";
            LogBuffer.setBuffer(currentLog);
        }
    }
}

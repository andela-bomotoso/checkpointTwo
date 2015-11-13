package checkpoint.andela.thread;
import Util.Config;
import checkpoint.andela.buffer.LogBuffer;
import checkpoint.andela.parser.AttributeValue;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class ThreadLogger {

    public enum LogType {
        write,
        read
    }

    static String activityTime;
    static String attribute;
    static String value;

    public static void logActivity(AttributeValue currentLine, LogType logType) {

        attribute = currentLine.attribute.toString();
        value = currentLine.value.toString();

        if(attribute.equals(Config.reactantFilePrimaryKey)) {

            String currentLog = getLogMessage(logType);
            LogBuffer.setBuffer(currentLog);
        }
    }

    private static String getLogMessage(LogType logType) {

        String currentLog;
        activityTime = DateTime.now().toString("yyyy-MM-dd HH:mm:ss");

        if (logType == LogType.write) {
            currentLog = "FileParser Thread " + "(" + activityTime + ")----wrote " + Config.reactantFilePrimaryKey +" "+ value + " to buffer";
        }
        else {
            currentLog = "DbWriter Thread " + "(" + activityTime + ")----collected " + Config.reactantFilePrimaryKey +" "+ value + " from buffer";
        }
        return currentLog;
    }
}

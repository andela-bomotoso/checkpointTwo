package checkpoint.andela.buffer;

import checkpoint.andela.parser.AttributeValue;

import java.io.BufferedReader;
import java.util.concurrent.ArrayBlockingQueue;

public class LogBuffer  implements Buffer{

    private static ArrayBlockingQueue<String> logBuffer = new ArrayBlockingQueue<String>(1);
    public static boolean inUse = false;

    public static void setBuffer(String currentLine) {

        try {

            logBuffer.put(currentLine);
            inUse = true;
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public static String getBuffer() {

        String currentLog = null;

        try {
            currentLog = logBuffer.take();
            inUse = false;
        } catch(InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }

        return currentLog;
    }

}

package checkpoint.andela.buffer;

import checkpoint.andela.parser.AttributeValue;

import java.util.concurrent.ArrayBlockingQueue;

public class DataBuffer implements Buffer {

    private static ArrayBlockingQueue<AttributeValue> sharedBuffer = new ArrayBlockingQueue<AttributeValue>(1);
    public static boolean inUse = false;

    public static void setBuffer(AttributeValue currentLine) {

        try {

            sharedBuffer.put(currentLine);
            inUse = true;
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public static AttributeValue getBuffer() {

        AttributeValue currentLog = null;

        try {
            currentLog = sharedBuffer.take();
            inUse = false;
        } catch(InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
        return currentLog;
    }

}

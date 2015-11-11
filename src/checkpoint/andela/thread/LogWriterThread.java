package checkpoint.andela.thread;

import checkpoint.andela.buffer.LogBuffer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogWriterThread implements Runnable{

    private File fileToWrite;
    private BufferedWriter bufferedWriter;
    private FileWriter fileWriter;
    private boolean runState;
    String logKey;

    public LogWriterThread(String filePath) {

        fileToWrite = new File(filePath);
    }

    public void checkIfFileExist() {
        try {
            if (!fileToWrite.exists()) {
                fileToWrite.createNewFile();
            }
        }

        catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void writeBufferToFile(String currentLog) {
        checkIfFileExist();

        try {

            fileWriter = new FileWriter(fileToWrite, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(currentLog + "\n");
            bufferedWriter.close();

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void run() {
        runState = true;

        while (runState){

            String currentLog = LogBuffer.getBuffer();
            writeBufferToFile(currentLog);
            runState = DbWriterThread.runState;
        }
    }
}

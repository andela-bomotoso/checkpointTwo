package checkpoint.andela.thread;


import checkpoint.andela.buffer.DataBuffer;
import checkpoint.andela.parser.AttributeValue;
import checkpoint.andela.parser.FileParser;
import checkpoint.andela.parser.ReactantFile;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

public class FileParserThread implements Runnable{

    List<AttributeValue<String,String>> reactantFileBuffer;
    ReactantFile reactantFile;

    FileParser fileParser = new FileParser(reactantFile);

    public static boolean runState = false;


    public FileParserThread(FileParser fileParser,ReactantFile reactantFile) {

        this.fileParser = fileParser;
        this.reactantFile = reactantFile;
    }

    public void run() {

        reactantFileBuffer = fileParser.writeFileToBuffer();
            for (AttributeValue currentLine : reactantFileBuffer) {

                runState = true;
                DataBuffer.setBuffer(currentLine);
                    ThreadLogger.logWriteActivity(currentLine);
            }

        runState = false;
    }
}

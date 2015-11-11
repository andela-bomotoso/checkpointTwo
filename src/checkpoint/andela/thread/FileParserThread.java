package checkpoint.andela.thread;


import checkpoint.andela.buffer.DataBuffer;
import checkpoint.andela.parser.AttributeValue;
import checkpoint.andela.parser.FileParser;
import checkpoint.andela.parser.ReactantFile;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.BufferedReader;
import java.io.File;
import java.util.List;

public class FileParserThread implements Runnable{

    List<AttributeValue<String,String>> reactantFileBuffer;
    ReactantFile reactantFile;

    FileParser fileParser = new FileParser(reactantFile);

    DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    public static boolean runState = true;


    public FileParserThread(FileParser fileParser,ReactantFile reactantFile) {

        this.fileParser = fileParser;
        this.reactantFile = reactantFile;
    }

    public void run() {

        reactantFileBuffer = fileParser.writeFileToBuffer(reactantFile);

        for(AttributeValue currentLine:reactantFileBuffer) {
            DataBuffer.setBuffer(currentLine);

            if(currentLine.attribute.equals("UNIQUE-ID") && runState == true) {
                String activityTime = dateTimeFormatter.print(DateTime.now());
                ThreadLogger.logWriteActivity(activityTime, currentLine);
            }
        }
        runState = false;

    }
}

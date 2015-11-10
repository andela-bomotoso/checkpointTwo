package checkpoint.andela.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileParser {

    ReactantFile fileToParse;
    List<AttributeValue<String,String>> reactantFileBuffer = new ArrayList<AttributeValue<String, String>>();
    BufferedReader bufferedReader;
    File file;

    public FileParser(ReactantFile fileToParse) {

         this.fileToParse = fileToParse;
    }

    public List<AttributeValue<String, String>> getReactantFileBuffer() {
        return reactantFileBuffer;
    }

    public void setReactantFileBuffer(List<AttributeValue<String, String>> reactantFileBuffer) {
        this.reactantFileBuffer = reactantFileBuffer;
    }

    public void readFile(ReactantFile fileToParse) {
        file = new File (fileToParse.getFilePath());

        try {
             bufferedReader = new BufferedReader(new FileReader(file));

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void writeFileToBuffer(ReactantFile fileToParse) {
        readFile(fileToParse);

        String line = "";

        try {
            while ((line = bufferedReader.readLine()) != null) {

                if (!lineToBeSkipped(line)) {
                    updateBuffer(parseLine(line));
                }

                else if (line.startsWith(fileToParse.getRecordMarker())) {
                   updateBuffer(parseRecordMarker());
                }
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public AttributeValue parseLine(String line) {

        String[] currentLine = line.trim().split(fileToParse.getKeyValueSeparator(), 2);
        AttributeValue currentAttributeValueLine = new AttributeValue(currentLine[0], currentLine[1]);
        return currentAttributeValueLine;
    }

    public AttributeValue parseRecordMarker() {

        AttributeValue currentRecordMarker = new AttributeValue(fileToParse.getRecordMarker(), "");
        return currentRecordMarker;
    }

    public void updateBuffer(AttributeValue currentLine) {
        reactantFileBuffer.add(currentLine);
    }

    public boolean lineToBeSkipped(String line) {
        return (line.startsWith("/") && (line.trim() != fileToParse.getRecordMarker()))
                ||  line.startsWith(fileToParse.getCommentDelimiter()) || line.isEmpty();
    }
}

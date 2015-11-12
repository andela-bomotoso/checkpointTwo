package checkpoint.andela.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileParser {

    ReactantFile fileToParse;
    BufferedReader bufferedReader;
    File file;

    public FileParser(ReactantFile fileToParse) {

        this.fileToParse = fileToParse;
    }


    public BufferedReader readFile() {

        file = new File (fileToParse.getFilePath());

        try {
            bufferedReader = new BufferedReader(new FileReader(file));

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return bufferedReader;
    }

    public List<AttributeValue<String,String>> writeFileToBuffer() {
        List<AttributeValue<String,String>> reactantFileBuffer = new ArrayList<AttributeValue<String, String>>();

        readFile();

        String line;

        try {
            while ((line = bufferedReader.readLine()) != null) {

                if (!lineToBeSkipped(line)) {
                    reactantFileBuffer.add(parseLine(line));
                }

                else if (line.startsWith(fileToParse.getRecordMarker())) {
                    reactantFileBuffer.add(parseRecordMarker());
                }
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return reactantFileBuffer;
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

    public boolean lineToBeSkipped(String line) {

        return (line.startsWith("/") && (line.trim() != fileToParse.getRecordMarker()))
                ||  line.startsWith(fileToParse.getCommentDelimiter()) || line.isEmpty();
    }

}
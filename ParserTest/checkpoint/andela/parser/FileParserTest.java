package checkpoint.andela.parser;

import Util.Config;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class FileParserTest extends TestCase {

    ReactantFile fileToParse;

    FileParser fileParser;
    List<AttributeValue<String,String>> reactantFileBuffer = new ArrayList<AttributeValue<String, String>>();

    @Before
    public void setUp() throws Exception {
        super.setUp();

        fileToParse = new ReactantFile(Config.reactantFilePath," - ","//","#");
        fileParser = new FileParser(fileToParse);
    }

    @Test
    public void testWriteFileToBuffer() throws Exception {
        fileParser.writeFileToBuffer(fileToParse);
    }
    @Test
    public void testParseLine() throws Exception {
        String line = "UNIQUE-ID - ALKAPHOSPHA-RXN";
        String[] currentLine = line.trim().split(fileToParse.getKeyValueSeparator(), 2);

        AttributeValue currentAttributeValueLine = new AttributeValue(currentLine[0], currentLine[1]);

        assertEquals(currentAttributeValueLine.attribute,fileParser.parseLine(line).attribute);
        assertEquals(currentAttributeValueLine.value,fileParser.parseLine(line).value);
    }

    @Test
    public void testParseRecordMarker() throws Exception {
        assertEquals("//",fileParser.parseRecordMarker().attribute);
        assertEquals("",fileParser.parseRecordMarker().value);
    }

    @Test
    public void testLineToBeSkippedWhenLineOfTextfileStartswithCommentDelimiterHash() {
        String line = "# The format of this file is defined at http://bioinformatics.ai.sri.com/ptools/flatfile-format.html.";
        assertTrue(fileParser.lineToBeSkipped(line));
    }

    @Test
    public void testLineToBeSkippedWhenLineStartswithRecordMarkerDoubleSlash() {
        String line = "// ";
        assertTrue(fileParser.lineToBeSkipped(line));
    }

    @Test
    public void testLineToBeSkippedWhenlineOfTextfileIsEmpty() {
        String line = "";
        assertTrue(fileParser.lineToBeSkipped(line));
    }

    @Test
    public void testLineToBeSkippedWhenlineOfTextfileDoesNotStartWithSpecialCharacters() {
        String line = "(:NO-HYDROGEN-ENCODING (0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56) (((NADH 0 43) (|Pyruvate-dehydrogenase-lipoate| 44 56)) ((NAD 0 43) (|Pyruvate-dehydrogenase-dihydrolipoate| 44 56))))";
        assertFalse(fileParser.lineToBeSkipped(line));
    }
}
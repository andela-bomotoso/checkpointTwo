package checkpoint.andela.buffer;

import checkpoint.andela.parser.AttributeValue;

import java.io.IOException;

public interface Buffer {

    public static void setBuffer(AttributeValue curentLine) throws InterruptedException {}
    public static AttributeValue getBuffer() throws InterruptedException {return null;}
}

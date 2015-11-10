package checkpoint.andela.parser;


public class ReactantFile  {

    private String filePath;
    private String keyValueSeparator;
    private String recordMarker;
    private String commentDelimiter;

    public ReactantFile(String filePath, String keyValueSeparator, String recordMarker, String commentDelimiter) {
        this.filePath = filePath;
        this.keyValueSeparator = keyValueSeparator;
        this.recordMarker = recordMarker;
        this.commentDelimiter = commentDelimiter;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getCommentDelimiter() {
        return commentDelimiter;
    }

    public void setCommentDelimiter(String commentDelimiter) {
        this.commentDelimiter = commentDelimiter;
    }

    public String getRecordMarker() {
        return recordMarker;
    }

    public void setRecordMarker(String recordMarker) {
        this.recordMarker = recordMarker;
    }

    public String getKeyValueSeparator() {
        return keyValueSeparator;
    }

    public void setKeyValueSeparator(String keyValueSeparator) {
        this.keyValueSeparator = keyValueSeparator;
    }
}

package m.Exceptions;

public class MException extends Exception {
    public int line;
    public int col;

    public MException(String message, int line, int col) {
        super(message);
        this.line = line;
        this.col = col;
    }
}

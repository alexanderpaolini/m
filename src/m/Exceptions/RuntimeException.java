package m.Exceptions;

public class RuntimeException extends MException {
    public RuntimeException(String message, int line, int col) {
        super(message, line, col);
    }
}

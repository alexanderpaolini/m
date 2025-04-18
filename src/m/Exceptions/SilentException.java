package m.Exceptions;

public class SilentException extends RuntimeException {
    public SilentException() {
        super("", 0, 0);
    }
}

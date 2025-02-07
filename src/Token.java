public class Token {
    TokenKind kind;
    String value;
    int line;
    int column;

    Token(TokenKind kind, int line, int column) {
        this(kind, "N/A", line, column);
    }

    Token(TokenKind kind, String value, int line, int column) {
        this.kind = kind;
        this.value = value;
        this.line = line;
        this.column = column;
    }

    @Override
    public String toString() {
        return String.format("Token(%s, %s, %d, %d)", this.kind, this.value, this.line, this.column);
    }
}

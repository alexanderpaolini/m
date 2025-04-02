package m.Scanner;

public class Token {
    public final TokenType type;
    public final String lex;
    public final Object literal;
    public final int line;

    Token(TokenType type, String lex, Object literal, int line) {
        this.type = type;
        this.lex = lex;
        this.literal = literal;
        this.line = line;
    }

    public String toString() {
        return type + " " + lex + " " + literal;
    }
}

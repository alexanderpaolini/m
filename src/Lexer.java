import java.lang.reflect.Array;
import java.util.ArrayList;

public class Lexer {
    String input;
    ArrayList<Token> tokens = new ArrayList<>();
    Lexer(String input) {
        this.input = input;
    }

    ArrayList<Token> lex () {
        return this.tokens;
    }
}

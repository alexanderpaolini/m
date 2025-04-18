package m.Exceptions;

import m.Scanner.Token;
import m.Scanner.TokenType;

import java.util.Arrays;

public class UnexpectedTokenException extends Exception {
    public UnexpectedTokenException(Token foundToken) {
        super("Unexpected Token: \"" + foundToken.lex + "\"");
    }

    public UnexpectedTokenException(Token foundToken, TokenType expectedTokenType) {
        super("Unexpected Token: \"" + foundToken.lex + "\" Expected: " + expectedTokenType);
    }

    public UnexpectedTokenException(Token foundToken, TokenType[] expectedTokenTypes) {
        super("Unexpected Token: \"" + foundToken.lex + "\" Expected: " + Arrays.toString(expectedTokenTypes));
    }
}

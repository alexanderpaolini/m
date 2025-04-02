package m.Exceptions;

import m.Scanner.Token;
import m.Scanner.TokenType;

import java.util.Arrays;

public class UnexpectedTokenException extends RuntimeException {
    public UnexpectedTokenException(Token foundToken) {
        super("[line " + foundToken.line + "] " + "Unexpected token: " + foundToken.type);
    }

    public UnexpectedTokenException(Token foundToken, TokenType expectedTokenType) {
        super("[line " + foundToken.line + "] " + "Unexpected token: " + foundToken.type + " Expected: " + expectedTokenType);
    }

    public UnexpectedTokenException(Token foundToken, TokenType[] expectedTokenTypes) {
        super("[line " + foundToken.line + "] " + "Unexpected token: " + foundToken.type + " Expected: " + Arrays.toString(expectedTokenTypes));
    }
}

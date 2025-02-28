package m;

import java.util.Arrays;

public class UnexpectedTokenException extends RuntimeException {
    UnexpectedTokenException(Token foundToken) {
        super("[line " + foundToken.line + "] " + "Unexpected token: " + foundToken.type);
    }

    UnexpectedTokenException(Token foundToken, TokenType expectedTokenType) {
        super("[line " + foundToken.line + "] " + "Unexpected token: " + foundToken.type + " Expected: " + expectedTokenType);
    }

    UnexpectedTokenException(Token foundToken, TokenType[] expectedTokenTypes) {
        super("[line " + foundToken.line + "] " + "Unexpected token: " + foundToken.type + " Expected: " + Arrays.toString(expectedTokenTypes));
    }
}

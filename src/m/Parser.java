package m;

import java.math.BigDecimal;
import java.util.*;

public class Parser {
    private List<Token> tokens;
    int current = 0;
    private final Program program = new Program();

    Parser (List<Token> tokens) {
        this.tokens = tokens;
    }

    Token peek() {
        return tokens.get(current);
    }

    Token consume() {
        return tokens.get(current++);
    }

    Number parseNumber() {
        Token cur = consume();
        return new Number((BigDecimal) cur.literal);
    }

    Expression parseFactor() {
        Token cur = peek();
        if (cur.type == TokenType.NUMBER) return parseNumber();
        if (cur.type == TokenType.LEFT_PAREN) {
            consume(); // (
            Expression node = parseExpression();
            consume(); // )
            return node;
        }
        return parseIdentifier();
    }

    Expression parseTerm() {
        Expression node = parseFactor();

        while (peek().type == TokenType.STAR || peek().type == TokenType.SLASH) {
            Token tok = consume();
            Expression right = parseFactor();
            node = new BinaryExpression(node, right, tok.type == TokenType.STAR ? BinaryOperator.MULTIPLY : BinaryOperator.DIVIDE);
        }

        return node;
    }

    Expression parseExpression() {
        Expression node = parseTerm();

        while (peek().type == TokenType.PLUS || peek().type == TokenType.MINUS) {
            Token tok = consume();
            Expression right = parseTerm();
            ;node = new BinaryExpression(node, right, tok.type == TokenType.PLUS ? BinaryOperator.PLUS : BinaryOperator.MINUS);
        }

        return node;
    }

    Identifier parseIdentifier() {
        Token cur = consume();
        return new Identifier((String) cur.lex);
    }

    PrintStatement parsePrintStatement() {
        Token _ = consume(); // consume print
        Expression expression = parseExpression();
        return new PrintStatement(expression);
    }

    Program parseTokens () {
        while (!atEOF()) {
            Statement stmt = null;
            switch (peek().type) {
                case TokenType.PRINT:
                    stmt = parsePrintStatement();
                    break;
                default:
                    M.error(1, "ERROR!!");
                    break;
            }
            if (stmt != null) {
                program.addStatement(stmt);
            }
        }
        return program;
    }

    private boolean atEOF() {
        return this.tokens.get(this.current).type.equals(TokenType.EOF);
    }
}

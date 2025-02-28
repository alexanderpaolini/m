package m;

import java.math.BigDecimal;
import java.util.*;

public class Parser {
    private final List<Token> tokens;
    private final Program program = new Program();

    int current = 0;

    Parser (List<Token> tokens) {
        this.tokens = tokens;
    }

    Token peek() {
        return tokens.get(current);
    }

    Token peekNext() {
        return tokens.get(current + 1);
    }

    Token consume() {
        return tokens.get(current++);
    }

    Token consume(TokenType[] types) throws Exception {
        match(types);

        return consume();
    }

    void match(TokenType[] types) throws Exception {
        Token tok = peek();
        for (TokenType type : types) {
            if (tok.type == type) {
                return;
            }
        }

        if (types.length == 1) {
            throw new UnexpectedTokenException(tok, types[0]);
        } else {
            throw new UnexpectedTokenException(tok, types);
        }
    }

    Number parseNumber() throws Exception {
        Token cur = consume(new TokenType[]{TokenType.NUMBER});
        return new Number((BigDecimal) cur.literal);
    }

    Expression parseUnaryExpression() throws Exception {
        Token cur = consume(new TokenType[]{TokenType.BANG, TokenType.MINUS});
        Expression node = parseFactor();
        return switch (cur.type) {
            case BANG -> new UnaryExpression(node, UnaryOperator.NOT);
            case MINUS -> new UnaryExpression(node, UnaryOperator.NEGATE);
            default -> throw new Exception("Unexpected unsupported unary operator...");
        };
    }

    Expression parseFactor() throws Exception {
        Token cur = peek();
        switch (cur.type) {
            case TokenType.NUMBER:
                return parseNumber();
            case TokenType.BANG:
                case TokenType.MINUS:
                    return parseUnaryExpression();
            case TokenType.LEFT_PAREN:{
                consume(new TokenType[]{TokenType.LEFT_PAREN});
                Expression node = parseExpression();
                consume(new TokenType[]{TokenType.RIGHT_PAREN});
                return node;
            }
            default:
                return parseIdentifier();
        }
    }

    Expression parseTerm() throws Exception {
        Expression node = parseFactor();

        while (peek().type == TokenType.STAR || peek().type == TokenType.SLASH) {
            Token tok = consume(); // * or /
            Expression right = parseFactor();
            node = new BinaryExpression(node, right, tok.type == TokenType.STAR ? BinaryOperator.MULTIPLY : BinaryOperator.DIVIDE);
        }

        return node;
    }

    Expression parseExpression() throws Exception {
        Expression node = parseTerm();

        while (peek().type == TokenType.PLUS || peek().type == TokenType.MINUS) {
            Token tok = consume(); // + or -
            Expression right = parseTerm();
            node = new BinaryExpression(node, right, tok.type == TokenType.PLUS ? BinaryOperator.PLUS : BinaryOperator.MINUS);
        }

        return node;
    }

    Identifier parseIdentifier() throws Exception {
        Token cur = consume(new TokenType[]{TokenType.IDENTIFIER});
        return new Identifier((String) cur.lex);
    }

    PrintStatement parsePrintStatement() throws Exception {
        Token _ = consume(new TokenType[]{TokenType.PRINT}); // consume print
        Expression expression = parseExpression();
        return new PrintStatement(expression);
    }

    AssignmentStatement parseAssignment() throws Exception {
        Identifier id = parseIdentifier();
        Token _ = consume(new TokenType[]{TokenType.EQUAL});
        Expression left = parseExpression();
        return new AssignmentStatement(id, left);
    }

    Program parseTokens () {
        while (!atEOF()) {
            Statement stmt = null;
            try {
                switch (peek().type) {
                    case TokenType.PRINT:
                        stmt = parsePrintStatement();
                        break;
                    case TokenType.IDENTIFIER:
                        switch (peekNext().type) {
                            case TokenType.EQUAL:
                                stmt = parseAssignment();
                                break;
                            default:
                                throw new UnexpectedTokenException(peekNext(), new TokenType[]{TokenType.EQUAL});
                        }
                        break;
                    default:
                        throw new UnexpectedTokenException(peek());
                }
                if (stmt != null) {
                    program.addStatement(stmt);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());

                // pattern here: skip until one of [PRINT]
                // A pretty POOR solution, but technically it kind of works
                boolean foundPossible = false;
                TokenType[] possibleTokens = new TokenType[]{TokenType.PRINT, TokenType.IDENTIFIER};
                while (!foundPossible && !atEOF()) {
                    consume();
                    for (TokenType tok : possibleTokens) {
                        if (peek().type == tok) {
                            foundPossible = true;
                            break;
                        }
                    }
                }
            }
        }
        return program;
    }

    private boolean atEOF() {
        return this.tokens.get(this.current).type.equals(TokenType.EOF);
    }
}

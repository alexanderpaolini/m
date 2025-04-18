package m.Parser;

import m.Scanner.Token;
import m.Scanner.TokenType;
import m.Exceptions.UnexpectedTokenException;

import java.math.BigDecimal;
import java.util.*;

public class Parser {
    private final List<Token> tokens;
    private final Program program = new Program();

    int current = 0;

    public Parser(List<Token> tokens) {
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

    m.Parser.Number parseNumber() throws Exception {
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
            case TokenType.LEFT_PAREN: {
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
        if (peek().type == TokenType.LEFT_BRACKET)
            return parseArray();

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

    Array parseArray() throws Exception {
        consume(new TokenType[]{TokenType.LEFT_BRACKET}); // [

        Array arr = new Array();
        while (peek().type != TokenType.RIGHT_BRACKET) {
            arr.addElement(parseExpression());

            if (peek().type == TokenType.COMMA)
                consume(new TokenType[]{TokenType.COMMA}); // ,
        }

        consume(new TokenType[]{TokenType.RIGHT_BRACKET}); // ]

        return arr;
    }

    ValueProducingStatement parseValueProducingStatement() throws Exception {
        switch (peek().type) {
            case TokenType.LEFT_BRACE:
                consume(new TokenType[]{TokenType.LEFT_BRACE}); // {
                BlockStatement blockStmt = new BlockStatement();
                // Code here
                while (peek().type != TokenType.RIGHT_BRACE) {
                    Statement s = parseStatement(true);
                    blockStmt.addStatement(s);
                }
                consume(new TokenType[]{TokenType.RIGHT_BRACE}); // }
                return blockStmt;
            case TokenType.IDENTIFIER:
                if (peekNext().type == TokenType.EQUAL) {
                    return parseAssignment();
                } else if (peekNext().type == TokenType.LEFT_PAREN) {
                    return parseFunctionCall();
                }
                return parseIdentifier();
            default:
                return parseExpression();
        }
    }

    PrintStatement parsePrintStatement() throws Exception {
        Token _ = consume(new TokenType[]{TokenType.PRINT}); // consume print
        ValueProducingStatement expression = parseValueProducingStatement();
        return new PrintStatement(expression);
    }

    AssignmentStatement parseAssignment() throws Exception {
        Identifier id = parseIdentifier();
        Token _ = consume(new TokenType[]{TokenType.EQUAL});
        ValueProducingStatement right = parseValueProducingStatement();
        return new AssignmentStatement(id, right);
    }

    FunctionCall parseFunctionCall() throws Exception {
        Identifier name = parseIdentifier();
        consume(new TokenType[]{TokenType.LEFT_PAREN}); // (

        ArrayList<Expression> parameters = new ArrayList<>();
        if (peek().type != TokenType.RIGHT_PAREN) {
            parameters.add(parseExpression());
        }
        while (peek().type == TokenType.COMMA) {
            consume(new TokenType[]{TokenType.COMMA}); // ,
            parameters.add(parseExpression());
        }
        consume(new TokenType[]{TokenType.RIGHT_PAREN}); // )

        return new FunctionCall(name, parameters);
    }

    Function parseFunction() throws Exception {
        consume(new TokenType[]{TokenType.FN}); // fn

        Identifier name = parseIdentifier();
        consume(new TokenType[]{TokenType.LEFT_PAREN}); // (

        ArrayList<Identifier> arguments = new ArrayList<>();
        if (peek().type == TokenType.IDENTIFIER) {
            arguments.add(parseIdentifier());
        }
        while (peek().type == TokenType.COMMA) {
            consume(new TokenType[]{TokenType.COMMA}); // ,
            arguments.add(parseIdentifier());
        }

        consume(new TokenType[]{TokenType.RIGHT_PAREN}); // )

        consume(new TokenType[]{TokenType.COLON}); // :

        return new Function(
                name,
                arguments,
                parseValueProducingStatement()
        );
    }

    void parseImportStatement() {
        // parse import statement
        // imports the functions from the file
        consume(); // !
        consume(); // includes
        consume(); // "file"
    }

    Statement parseStatement(boolean vp) throws Exception {
            switch (peek().type) {
                case FN:
                    return parseFunction();
                case TokenType.BANG:
                    if (peekNext().type == TokenType.IDENTIFIER && peekNext().lex.equals("include")) {
                        parseImportStatement();
                        return null;
                    }
                case TokenType.PRINT:
                    return parsePrintStatement();
                case TokenType.IDENTIFIER:
                    if (peekNext().type == TokenType.LEFT_PAREN) {
                        return parseFunctionCall();
                    }
                default:
                    return parseValueProducingStatement();
            }
    }

    public Program parseTokens(){
        try {
            while (!atEOF()) {
                Statement s = parseStatement(false);
                if (s != null) {
                    program.addStatement(s);
                }
            }
            return program;
        } catch (Exception e) {
            System.out.println(this.program);
            throw new RuntimeException(e);
        }
    }

    private boolean atEOF() {
        return this.tokens.get(this.current).type == TokenType.EOF;
    }
}

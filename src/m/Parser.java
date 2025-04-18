package m;

import m.AST.*;
import m.AST.Number;
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

    Token consume(TokenType[] types) throws UnexpectedTokenException {
        match(types);

        return consume();
    }

    void match(TokenType[] types) throws UnexpectedTokenException {
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

    m.AST.Number parseNumber() throws UnexpectedTokenException {
        Token cur = consume(new TokenType[]{TokenType.NUMBER});
        return new Number((BigDecimal) cur.literal);
    }

    Expression parseFactor() throws UnexpectedTokenException {
        Expression node = parseExponent();

        while (peek().type == TokenType.STAR || peek().type == TokenType.SLASH) {
            Token tok = consume(); // * or /
            Expression right = parseExponent();
            node = new BinaryExpression(node, right,
                    tok.type == TokenType.STAR ? BinaryOperator.MULTIPLY : BinaryOperator.DIVIDE);
        }

        return node;
    }

    Expression parseExponent() throws UnexpectedTokenException {
        Expression node = parseUnaryExpression();

        while (peek().type == TokenType.POWER) {
            Token tok = consume(); // **
            Expression right = parseUnaryExpression();
            node = new BinaryExpression(node, right, BinaryOperator.POWER);
        }

        return node;
    }

    Expression parseUnaryExpression() throws UnexpectedTokenException {
        if (peek().type == TokenType.BANG || peek().type == TokenType.MINUS) {
            Token cur = consume(new TokenType[]{TokenType.BANG, TokenType.MINUS});
            Expression right = parseUnaryExpression(); // recurse for chains like !!x or -(-x)
            return switch (cur.type) {
                case BANG -> new UnaryExpression(right, UnaryOperator.NOT);
                case MINUS -> new UnaryExpression(right, UnaryOperator.NEGATE);
                default -> throw new UnexpectedTokenException(cur, new TokenType[]{TokenType.BANG, TokenType.MINUS});
            };
        }

        return parsePrimary();
    }


    Expression parsePrimary() throws UnexpectedTokenException {
        Token cur = peek();

        switch (cur.type) {
            case NUMBER:
                return parseNumber();

            case LEFT_PAREN: {
                consume(new TokenType[]{TokenType.LEFT_PAREN});
                Expression expr = parseExpression();
                consume(new TokenType[]{TokenType.RIGHT_PAREN});
                return expr;
            }

            case IDENTIFIER: {
                if (peekNext().type == TokenType.LEFT_PAREN) {
                    return parseFunctionCall();
                }
                return parseIdentifier();
            }

            default:
                throw new UnexpectedTokenException(cur);
        }
    }


    Expression parseTerm() throws UnexpectedTokenException {
        Expression node = parseFactor();

        while (peek().type == TokenType.STAR || peek().type == TokenType.SLASH) {
            Token tok = consume(); // * or /
            Expression right = parseFactor();
            node = new BinaryExpression(node, right, tok.type == TokenType.STAR ? BinaryOperator.MULTIPLY : BinaryOperator.DIVIDE);
        }

        return node;
    }

    Expression parseAdditive() throws UnexpectedTokenException {
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

    Expression parseLogicalOr() throws UnexpectedTokenException {
        Expression expr = parseLogicalAnd();
        while (peek().type == TokenType.OR) {
            Token tok = consume();
            Expression right = parseLogicalAnd();
            expr = new BinaryExpression(expr, right, BinaryOperator.OR);
        }
        return expr;
    }

    Expression parseExpression() throws UnexpectedTokenException {
        return parseLogicalOr();
    }

    Expression parseLogicalAnd() throws UnexpectedTokenException {
        Expression expr = parseEquality();
        while (peek().type == TokenType.AND) {
            Token tok = consume();
            Expression right = parseEquality();
            expr = new BinaryExpression(expr, right, BinaryOperator.AND);
        }
        return expr;
    }

    Expression parseEquality() throws UnexpectedTokenException {
        Expression expr = parseComparison();
        while (peek().type == TokenType.EQUAL_EQUAL || peek().type == TokenType.BANG_EQUAL) {
            Token tok = consume();
            Expression right = parseComparison();
            BinaryOperator op = (tok.type == TokenType.EQUAL_EQUAL) ? BinaryOperator.EQUAL : BinaryOperator.NOT_EQUAL;
            expr = new BinaryExpression(expr, right, op);
        }
        return expr;
    }

    Expression parseComparison() throws UnexpectedTokenException {
        Expression expr = parseAdditive();
        while (Set.of(TokenType.LESS, TokenType.LESS_EQUAL, TokenType.GREATER, TokenType.GREATER_EQUAL).contains(peek().type)) {
            Token tok = consume();
            BinaryOperator op;
            switch (tok.type) {
                case LESS -> op = BinaryOperator.LESS;
                case LESS_EQUAL -> op = BinaryOperator.LESS_EQUAL;
                case GREATER -> op = BinaryOperator.GREATER;
                case GREATER_EQUAL -> op = BinaryOperator.GREATER_EQUAL;
                default -> throw new UnexpectedTokenException(tok, new TokenType[]{TokenType.LESS, TokenType.LESS_EQUAL, TokenType.GREATER, TokenType.GREATER_EQUAL});
            }
            Expression right = parseAdditive();
            expr = new BinaryExpression(expr, right, op);
        }
        return expr;
    }

    Identifier parseIdentifier() throws UnexpectedTokenException {
        Token cur = consume(new TokenType[]{TokenType.IDENTIFIER});
        return new Identifier((String) cur.lex);
    }

    Array parseArray() throws UnexpectedTokenException {
        consume(new TokenType[]{TokenType.LEFT_BRACKET}); // [

        Array arr = new Array();
        while (peek().type != TokenType.RIGHT_BRACKET) {
            arr.addElement(parseAdditive());

            if (peek().type == TokenType.COMMA)
                consume(new TokenType[]{TokenType.COMMA}); // ,
        }

        consume(new TokenType[]{TokenType.RIGHT_BRACKET}); // ]

        return arr;
    }

    ValueProducingStatement parseConditional() throws UnexpectedTokenException {
        consume(new TokenType[]{TokenType.IF}); // if
        consume(new TokenType[]{TokenType.LEFT_PAREN}); // (
        Expression cond = parseExpression(); // conditional
        consume(new TokenType[]{TokenType.RIGHT_PAREN}); // )

        ValueProducingStatement posCond = parseValueProducingStatement();
        ValueProducingStatement negCond = null;

        if (peek().type == TokenType.ELSE) {
            consume(new TokenType[]{TokenType.ELSE}); // else
            negCond = parseValueProducingStatement();
        }

        return new Conditional(cond, posCond, negCond);
    }

    ValueProducingStatement parseValueProducingStatement() throws UnexpectedTokenException {
        switch (peek().type) {
            case TokenType.LEFT_BRACE:
                consume(new TokenType[]{TokenType.LEFT_BRACE}); // {
                BlockStatement blockStmt = new BlockStatement();
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
                return parseExpression();
            case TokenType.IF:
                return parseConditional();
            default:
                return parseExpression();
        }
    }

    PrintStatement parsePrintStatement() throws UnexpectedTokenException {
        Token _ = consume(new TokenType[]{TokenType.PRINT}); // consume print
        ValueProducingStatement expression = parseValueProducingStatement();
        return new PrintStatement(expression);
    }

    AssignmentStatement parseAssignment() throws UnexpectedTokenException {
        Identifier id = parseIdentifier();
        Token _ = consume(new TokenType[]{TokenType.EQUAL});
        ValueProducingStatement right = parseValueProducingStatement();
        return new AssignmentStatement(id, right);
    }

    FunctionCall parseFunctionCall() throws UnexpectedTokenException {
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

    Function parseFunction() throws UnexpectedTokenException {
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

    Statement parseStatement(boolean vp) throws UnexpectedTokenException {
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

    public Program parseTokens() throws UnexpectedTokenException {
        while (!atEOF()) {
            Statement s = parseStatement(false);
            if (s != null) {
                program.addStatement(s);
            }
        }
        return program;
    }

    private boolean atEOF() {
        return this.tokens.get(this.current).type == TokenType.EOF;
    }
}

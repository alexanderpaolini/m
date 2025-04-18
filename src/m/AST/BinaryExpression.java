package m.AST;

import m.Interpreter.Environment;

import java.math.BigDecimal;

public class BinaryExpression extends Expression {
    Expression left;
    Expression right;
    BinaryOperator operator;

    public BinaryExpression(Expression left, Expression right, BinaryOperator operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public String toString() {
        return "BinaryExpression{" + left.toString() + " " + operator.toString() + " " + right.toString() +"}";
    }

    public void execute(Environment env) {
        left.execute(env);
        right.execute(env);

        Object leftVal = left.getValue();
        Object rightVal = right.getValue();

        switch (operator) {
            case OR:
                value = left.isTruthy() || right.isTruthy();
                return;
            case AND:
                value = left.isTruthy() && right.isTruthy();
                return;
            case EQUAL:
                value = leftVal.equals(rightVal);
                return;
        }

        if (!(leftVal instanceof BigDecimal) || !(rightVal instanceof BigDecimal)) {
            return;
        }

        BigDecimal a = (BigDecimal) leftVal;
        BigDecimal b = (BigDecimal) rightVal;

        switch (operator) {
            case LESS:
                value = a.compareTo(b) < 0;
                break;
            case LESS_EQUAL:
                value = a.compareTo(b) <= 0;
                break;
            case GREATER:
                value = a.compareTo(b) > 0;
                break;
            case GREATER_EQUAL:
                value = a.compareTo(b) >= 0;
                break;
            case PLUS:
                value = a.add(b);
                break;
            case MINUS:
                value = a.subtract(b);
                break;
            case MULTIPLY:
                value = a.multiply(b);
                break;
            case DIVIDE:
                if (b.equals(BigDecimal.ZERO)) {
                    value = null;
                } else {
                    value = a.divide(b);
                }
                break;
        }
    }
}

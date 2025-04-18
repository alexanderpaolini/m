package m.Parser;

public class UnaryExpression extends Expression {
    Expression right;
    UnaryOperator operator;

    public UnaryExpression(Expression right, UnaryOperator operator) {
        this.right = right;
        this.operator = operator;
    }

    @Override
    public String toString() {
        return "UnaryExpression{" + operator.toString() + ", " + right.toString() + "}";
    }
}

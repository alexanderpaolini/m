package m;

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
        return "BinaryOperator{" + left.toString() + " " + operator.toString() + " " + right.toString() +"}";
    }
}

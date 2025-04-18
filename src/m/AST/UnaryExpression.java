package m.AST;

import m.Interpreter.Environment;

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

    public void execute(Environment env) {
        Object val = right.getValue();

        switch (operator) {
            case NOT:
                if (val instanceof Boolean) {
                    value = !(Boolean) val;
                }
                break;

            case NEGATE:
                if (val instanceof Integer) {
                    value = -((Integer) val);
                }
                break;
        }
    }
}

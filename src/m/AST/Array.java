package m.AST;

import m.Interpreter.Environment;

import java.util.ArrayList;

public class Array extends Expression {
    ArrayList<Expression> elements = new ArrayList<>();

    public Array() {}

    public void addElement(Expression e) {
        this.elements.add(e);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');

        if (!this.elements.isEmpty()) {
            sb.append(elements.getFirst().toString());
        }

        for (int i = 1; i < this.elements.size(); i++) {
            sb.append(',');
            sb.append(elements.get(i).toString());
        }

        sb.append(']');

        return sb.toString();
    }

    @Override
    public void execute(Environment env) {
        value = new ArrayList<Object>();

        for (Expression e : elements) {
            e.execute(env);
            ((ArrayList) value).add(e.getValue());
        }
    }
}

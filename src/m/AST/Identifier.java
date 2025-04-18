package m.AST;

import m.Interpreter.Environment;

public class Identifier extends Expression {
    String name;

    public Identifier(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Identifier{" + this.name + "}";
    }

    public void execute(Environment env) {
        value = env.get(name);
    }
}

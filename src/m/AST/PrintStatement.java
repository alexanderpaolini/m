package m.AST;

import m.Interpreter.Environment;

public class PrintStatement extends Statement {
    private final ValueProducingStatement value;

    public PrintStatement(ValueProducingStatement value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "PrintStatement{" + value + "}";
    }

    @Override
    public void execute(Environment env) {
        value.execute(env);
        System.out.println(value.getValue());
    }
}

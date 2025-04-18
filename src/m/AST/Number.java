package m.AST;

import m.Interpreter.Environment;

import java.math.BigDecimal;

public class Number extends Expression {
    public Number(BigDecimal value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    @Override
    public void execute(Environment env) {/* Not Necessary */}
}

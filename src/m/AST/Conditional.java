package m.AST;

import m.Interpreter.Environment;

import java.math.BigDecimal;

public class Conditional extends ValueProducingStatement {
    Expression condition;
    ValueProducingStatement posCond;
    ValueProducingStatement negCond;

    public Conditional (Expression condition, ValueProducingStatement posCond, ValueProducingStatement negCond) {
        this.condition = condition;
        this.posCond = posCond;
        this.negCond = negCond;
    }

    @Override
    public void execute(Environment env) {
        condition.execute(env);

        if (condition.isTruthy()) {
            posCond.execute(env);
            value = posCond.getValue();
        } else {
            negCond.execute(env);
            value = negCond.getValue();
        }
    }
}

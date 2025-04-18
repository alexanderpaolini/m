package m.AST;

public abstract class ValueProducingStatement extends Statement {
    Object value;

    Object getValue() {
        return value;
    }
}

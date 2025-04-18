package m.AST;

import java.math.BigDecimal;

public abstract class Expression extends ValueProducingStatement {
    boolean isTruthy() {
        if (value instanceof Boolean) {
            return (boolean) value;
        } else if (value instanceof BigDecimal) {
            return !value.equals(BigDecimal.ZERO);
        } else if (value instanceof Array) {
            return !((Array) value).elements.isEmpty();
        }
        return false;
    }
}

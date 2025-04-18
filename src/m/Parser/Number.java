package m.Parser;

import java.math.BigDecimal;

public class Number extends Expression {
    BigDecimal value;

    public Number(BigDecimal value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}

package m.Parser;

public class PrintStatement extends Statement {
    private final ValueProducingStatement value;

    public PrintStatement(ValueProducingStatement value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "PrintStatement{" + value + "}";
    }
}

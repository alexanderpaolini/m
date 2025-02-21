package m;

public class PrintStatement extends Statement {
    ValueProducingStatement value;

    PrintStatement(ValueProducingStatement value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "PrintStatement{" + value + "}";
    }
}

package m.Parser;

public class Identifier extends Expression {
    String name;

    public Identifier(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Identifier{" + this.name + "}";
    }
}

package m;

public class Identifier extends Expression {
    String name;

    Identifier (String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Identifier{" + this.name + "}";
    }
}

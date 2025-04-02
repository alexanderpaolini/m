package m.Parser;

public class AssignmentStatement extends ValueProducingStatement {
    private final ValueProducingStatement right;
    private final Identifier identifier;

    public AssignmentStatement(Identifier identifier, ValueProducingStatement right) {
        this.right = right;
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return "AssignmentStatement{" + identifier + ", " + right + "}";
    }
}

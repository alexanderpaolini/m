package m;

public class AssignmentStatement extends Statement {
    private final Expression right;
    private final Identifier identifier;

    AssignmentStatement(Identifier identifier, Expression right) {
        this.right = right;
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return "AssignmentStatement{" + identifier + ", " + right + "}";
    }
}

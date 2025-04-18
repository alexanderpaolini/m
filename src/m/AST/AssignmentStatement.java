package m.AST;

import m.Interpreter.Environment;

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

    public void execute(Environment env) {
        this.right.execute(env);

        env.set(
                this.identifier.name,
                this.right.getValue()
        );
    }
}

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
        StringBuilder sb = new StringBuilder();
        sb.append("AssignmentStatement{\n");
        sb.append(identifier.toString().indent(2));
        sb.append(right.toString().indent(2));
        sb.append("}");
        return sb.toString();
    }

    @Override
    public void execute(Environment env) {
        this.right.execute(env);

        env.set(
                this.identifier.name,
                this.right.getValue()
        );
    }
}

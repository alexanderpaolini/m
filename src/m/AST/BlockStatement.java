package m.AST;

import com.sun.jdi.Value;
import m.Interpreter.Environment;

import java.util.ArrayList;
import java.util.List;

public class BlockStatement extends ValueProducingStatement {
    List<Statement> statements = new ArrayList<>();

    public void addStatement(Statement s) {
        this.statements.add(s);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("BlockStatement{");
        for (Statement s : statements) {
            sb.append("\n");
            sb.append("  ").append(s);
        }
        sb.append("\n");
        sb.append("}");
        return sb.toString();
    }

    public void execute(Environment env) {
        env = new Environment(env);

        for (Statement s : statements) {
            s.execute(env);
        }

        if (statements.getLast() instanceof ValueProducingStatement) {
            value = ((ValueProducingStatement) statements.getLast()).getValue();
            return;
        }

        value = null;
    }
}

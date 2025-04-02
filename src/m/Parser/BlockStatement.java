package m.Parser;

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
}

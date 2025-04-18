package m.AST;

import java.util.*;

/**
 * Program class. Holds list of Nodes
 */
public class Program {
    ArrayList<Statement> statements = new ArrayList<>();

    public void addStatement (Statement s) {
        statements.add(s);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Program{\n");
        for (Statement s : statements) {
            sb.append(s.toString().indent(2));
        }
        sb.append("}");
        return sb.toString();
    }

    public ArrayList<Statement> getStatements() {
        return statements;
    }
}

package m.Parser;

import java.util.ArrayList;

public class FunctionCall extends ValueProducingStatement {
    Identifier name;
    ArrayList<Expression> parameters;

    public FunctionCall(Identifier name, ArrayList<Expression> parameters) {
        this.name = name;
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FunctionCall{");
        sb.append(name);
        sb.append("\n");
        for (Expression p : parameters) {
            sb.append(p);
        }
        sb.append("}");
        return sb.toString();
    }
}

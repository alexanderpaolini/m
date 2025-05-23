package m.AST;

import m.Interpreter.Environment;

import java.util.ArrayList;

public class FunctionCall extends Expression {
    Identifier name;
    ArrayList<Expression> parameters;

    public FunctionCall(Identifier name, ArrayList<Expression> parameters) {
        this.name = name;
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FunctionCall{\n");
        sb.append(name.toString().indent(2));
        sb.append("  Parameters{\n");
        for (Expression param : parameters) {
            sb.append(param.toString().indent(4));
        }
        sb.append("  }\n");
        sb.append("}\n");
        return sb.toString();
    }

    @Override
    public void execute(Environment env) {
        Environment env2 = new Environment(env);
        Function f = env.getFunction(this.name.name);

        for (int i = 0; i < parameters.size(); i++) {
            String name = f.arguments.get(i).name;

            Expression param = parameters.get(i);
            param.execute(env);

            env2.set(name, param.getValue());
        }

        f.statement.execute(env2);

        value = f.statement.getValue();
    }
}

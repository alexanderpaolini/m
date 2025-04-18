package m.AST;

import m.Interpreter.Environment;

import java.util.ArrayList;

public class Function extends Statement {
    Identifier name;
    ArrayList<Identifier> arguments;
    ValueProducingStatement statement;

    public Function(Identifier name, ArrayList<Identifier> arguments, ValueProducingStatement statement) {
        this.name = name;
        this.arguments = arguments;
        this.statement = statement;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Function{\n\t");
        sb.append(name);
        sb.append("\n\tArguments{");
        for (Identifier argument : arguments) {
            sb.append(argument);
        }
        sb.append("}\n\t");
        sb.append(statement);
        sb.append("\n");
        sb.append("}\n");
        return sb.toString();
    }

    @Override
    public void execute(Environment env) {
        env.addFunction(this.name.name, this);
    }
}

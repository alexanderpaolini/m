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
        sb.append("Function{\n");
        sb.append(name.toString().indent(2));
        sb.append("Arguments{".indent(2));
        for (Identifier argument : arguments) {
            sb.append(argument.toString().indent(4));
        }
        sb.append("}".indent(2));
        sb.append(statement.toString().indent(2));
        sb.append("}");
        return sb.toString();
    }


    @Override
    public void execute(Environment env) {
        env.addFunction(this.name.name, this);
    }
}

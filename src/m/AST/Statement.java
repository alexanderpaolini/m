package m.AST;

import m.Interpreter.Environment;

public abstract class Statement {
    public abstract void execute(Environment env);
}

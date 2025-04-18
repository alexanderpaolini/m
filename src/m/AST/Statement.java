package m.AST;

import m.Interpreter.Environment;

public abstract class Statement {
    abstract public void execute(Environment env);
}

package m.Interpreter;

import m.AST.Program;
import m.AST.Statement;
import m.Exceptions.RuntimeException;
import m.Exceptions.SilentException;

import java.util.ArrayList;
import java.util.Objects;

public class Interpreter {
    Program program;
    Environment environment;
    int pc = 0;

    public Interpreter(Program prog)  {
        this.program = prog;
    }

    public void execute(Environment environment) throws RuntimeException {
        this.environment = Objects.requireNonNullElseGet(environment, Environment::new);
        run();
    }

    private void run() throws RuntimeException {
        ArrayList<Statement> statements = program.getStatements();
        while (pc < program.getStatements().size())
            statements.get(pc++).execute(environment);
    }
}

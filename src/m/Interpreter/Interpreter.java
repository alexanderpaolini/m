package m.Interpreter;

import m.AST.Program;
import m.AST.Statement;

import java.util.ArrayList;

public class Interpreter {
    Program program;
    Environment environment;
    int pc = 0;

    public Interpreter(Program prog)  {
        this.program = prog;
    }

    public void execute() {
        this.environment = new Environment();
        run();
    }

    public void execute(Environment environment) {
        this.environment = environment;
        run();
    }

    private void run() {
        ArrayList<Statement> statements = program.getStatements();
        while (pc < program.getStatements().size()) {
            Statement cur = statements.get(pc);

            cur.execute(environment);

            pc++;
        }
    }
}

package m;

import m.Exceptions.SilentException;
import m.Exceptions.UnexpectedTokenException;
import m.Interpreter.Environment;
import m.Interpreter.Interpreter;
import m.AST.Program;
import m.Scanner.Token;

import java.io.*; //BufferedReader, IOException, InputStreamReader
import java.util.*; //List
import java.nio.charset.*; //Charset
import java.nio.file.*; //Files, Paths

public class M {
    static boolean hadError = false;

    static boolean SHOW_TOKENS;
    static boolean SHOW_AST;

    public static void main(String[] args) throws Exception {
        ArgParser argp = new ArgParser();

        args = argp.parse(args);

        SHOW_TOKENS = argp.get("t") != null || argp.get("tokens") != null;
        SHOW_AST = argp.get("a") != null || argp.get("ast") != null;

        if (args.length > 1) {
            System.out.println("Usage: M [script]");
            System.exit(64);
        } else if (args.length == 1) {
            runFile(args[0]);
        } else {
            runPrompt();
        }
    }

    private static void runFile(String path) throws Exception {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()), null);

        if (hadError) System.exit(65);
    }

    private static void runPrompt() throws Exception {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        Environment env = new Environment();
        for (; ; ) {
            System.out.print("| ");
            String line = reader.readLine();
            if (line == null) break;
            run(line, env);
            hadError = false;
        }
    }

    private static void run(String source, Environment env) {
        m.Scanner.Scanner scanner = new m.Scanner.Scanner(source);
        List<Token> tokens = null;

        try {
            tokens = scanner.scanTokens();

            if (SHOW_TOKENS) {
                for (Token token : tokens) {
                    System.out.println(token);
                }
            }
        } catch (Exception e) {
            System.err.println("Scanner Error: " + e.getMessage());
            return;
        }

        Parser parser = new Parser(tokens);
        Program prog = null;
        try {
            prog = parser.parseTokens();
        } catch (UnexpectedTokenException e) {
            hadError = true;
            error(parser.peek().line, e.getMessage());
            return;
        }

        if (SHOW_AST) {
            System.out.println(prog);
        }
        Interpreter interpreter = new Interpreter(prog);
        try {
            interpreter.execute(env);
        } catch (SilentException e) {
            // Silent -- ignore
        } catch (m.Exceptions.RuntimeException e) {
            error(e.line, e.getMessage());
        } catch (Error e) {
            error(e.toString());
        }
    }

    public static void error(String message) {
        report(null, message);
    }

    public static void error(int line, String message) {
        report(line, "", message);
    }

    public static void report(int line, String where, String message) {
        System.err.println("[line " + line + "] Error" + where + ": " + message);
        hadError = true;
    }

    public static void report(String where, String message) {
        System.err.println((where == null ? "" : where + " " ) + message);
        hadError = true;
    }
}
package m;

import m.Interpreter.Interpreter;
import m.AST.Program;
import m.Scanner.Token;

import java.io.*; //BufferedReader, IOException, InputStreamReader
import java.util.*; //List
import java.nio.charset.*; //Charset
import java.nio.file.*; //Files, Paths

public class M {
    static boolean hadError = false;

    static boolean SHOW_TOKENS = true;
    static boolean SHOW_AST = true;

    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Usage: M [script]");
            System.exit(64);
        } else if (args.length == 1) {
            runFile(args[0]);
        } else {
            runPrompt();
        }
    }

    private static void runFile(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));

        if (hadError) System.exit(65);
    }

    private static void runPrompt() throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for (; ; ) {
            System.out.print("> ");
            String line = reader.readLine();
            if (line == null) break;
            run(line);
            hadError = false;
        }
    }

    private static void run(String source) {
        m.Scanner.Scanner scanner = new m.Scanner.Scanner(source);
        List<Token> tokens = scanner.scanTokens();

        if (SHOW_TOKENS)
            for (Token token : tokens) {
                System.out.println(token);
            }

        Parser parser = new Parser(tokens);
        Program prog = parser.parseTokens();

        if (SHOW_AST)
            System.out.println(prog);

        Interpreter interpreter = new Interpreter(prog);
        interpreter.execute();
    }

    public static void error(int line, String message) {
        report(line, "", message);
    }

    public static void report(int line, String where, String message) {
        System.err.println("[line " + line + "] Error" + where + ": " + message);
        hadError = true;
    }
}
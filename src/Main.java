import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws CompilerException {
        boolean OPTIONS_PRINT_TOKENS = false;
        boolean OPTIONS_PRINT_AST = false;
        int i = 0;
        while (i < args.length) {
            String arg = args[i];
            switch (arg) {
                case "--help":
                case "-h":
                    showHelp();
                    return;
                case "--print-tokens":
                case "-t":
                    OPTIONS_PRINT_TOKENS = true;
                    break;
                case "--print-ast":
                case "-a":
                    OPTIONS_PRINT_AST = true;
                    break;
            }
            i++;
        }

        if (args.length == 0) {
            showUsage();
            return;
        }

        String inputPath = args[0];

        String fileContent = null;
        try {
            fileContent = Files.readString(Paths.get(inputPath));
        } catch (IOException e) {
            System.err.println("Error reading file " + inputPath);
            return;
        }

        Lexer lexer = new Lexer(fileContent);

        ArrayList<Token> tokens = new ArrayList<>();

        if (OPTIONS_PRINT_TOKENS) {
            for (Token tok : tokens) {
                System.out.println(tok.toString());
            }
        }
    }

    static void showUsage() {
        System.out.println("The m Language Interpreter");
        System.out.println("Usage: m [input] [options]");
    }

    static void showHelp() {
        showUsage();
        System.out.println("Options:");
        System.out.println("    --help            (-h)    Show this help message");
        System.out.println("    --print-tokens    (-t)    Print the lexed tokens");
        System.out.println("    --print-ast       (-a)    Print the lexed AST");
    }
}

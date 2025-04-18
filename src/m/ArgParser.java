package m;

import java.util.*;

public class ArgParser {
    private final Map<String, Object> args = new HashMap<>();

    public String[] parse(String[] argv) {
        List<String> leftovers = new ArrayList<>();

        for (int i = 0; i < argv.length; i++) {
            String arg = argv[i];

            if (arg.startsWith("--")) {
                String name = arg.substring(2);
                Object value = true;

                if (i + 1 < argv.length && !argv[i + 1].startsWith("--")) {
                    value = argv[++i]; // take the next arg as value
                }

                args.put(name, value);
            } else {
                leftovers.add(arg);
            }
        }

        return leftovers.toArray(new String[0]);
    }

    public Object get(String name) {
        return args.get(name);
    }
}

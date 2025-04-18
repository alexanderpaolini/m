package m.Interpreter;

import m.AST.Function;

import java.util.HashMap;

public class Environment {
    Environment outer = null;
    HashMap<String, Object> data = new HashMap<>();
    HashMap<String, Function> functions = new HashMap<>();

    public Environment(Environment outer) {
        this.outer = outer;
    }

    public Environment() {}

    public Object get(String name) {
        Object d = data.get(name);

        if (d != null)
            return d;

        if (outer != null)
            return outer.get(name);

        return null;
    }

    public void set(String name, Object value) {
        if (outer != null && outer.get(name) != null) {
            outer.set(name, value);
            return;
        }

        data.put(name, value);
    }

    public void addFunction(String name, Function func) {
        if (getFunction(name) != null) {
            throw new RuntimeException("Duplicate function: " + name);
        }

        this.functions.put(name, func);
    }

    public Function getFunction(String name) {
        Function f = functions.get(name);

        if (f != null)
            return f;

        if (outer != null)
            return outer.getFunction(name);

        return null;
    }
}

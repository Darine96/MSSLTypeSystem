package fr.univorleans.mssl.DynamicSyntax;

import fr.univorleans.mssl.DynamicSyntax.Syntax.Expression;

public class InvokeFunction implements Expression {

    final static String spawn = "spawn";
    private final String name;
    private final Expression[] arguments;

    public InvokeFunction(String name, Expression[] expressions) {

        this.name = name;
        this.arguments = expressions;
    }

    public String getSpawn() {
        return spawn;
    }
    public String getName() {
        return name;
    }

    public Expression[] getArguments() {
        return arguments;
    }

    @Override
    public int getOpcode() {
        return Syntax.Invoke_Expression;
    }

    @Override
    public String toString() {
        String r = spawn+"("+ name + "(";
        for (int i = 0; i != arguments.length; ++i) {
            if (i != 0) {
                r += ",";
            }
            r += arguments[i];
        }
        return r + "))";
    }
}


package fr.univorleans.mssl.DynamicSyntax;

import fr.univorleans.mssl.DynamicSyntax.Syntax.Expression;

public class InvokeFunction implements Expression {

    final static String spawn = "spawn";
    private final String name;
    private final Expression[] arguments;

    private final String[] signals;

    public InvokeFunction(String name, Expression[] expressions, String[] signals) {

        this.name = name;
        this.arguments = expressions;
        this.signals = signals;
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

    public String[] getSignals() {
        return signals;
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
        if (signals.length!=0){
            r+=";";
        }
        for (int i = 0; i != signals.length; ++i) {
            if (i != 0) {
                r += ",";
            }
            r += signals[i];
        }
        return r + "))";
    }
}


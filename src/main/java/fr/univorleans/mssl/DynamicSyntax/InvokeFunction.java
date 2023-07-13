package fr.univorleans.mssl.DynamicSyntax;

import fr.univorleans.mssl.DynamicSyntax.Syntax.Expression;

public class InvokeFunction implements Expression {

    final static String spawn = "spawn";
    private final String name;
    private final Expression[] arguments;

    private final String[] signals;

    private final Boolean check;

    public InvokeFunction(String name, Expression[] expressions, String[] signals, Boolean check) {

        this.name = name;
        this.arguments = expressions;
        this.signals = signals;
        this.check = check;
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

    /**
     * check = true if the function call is inside the spawn;
     * otherwise it is outside the spawn
     * @return
     */
    public Boolean getCheck() {
        return check;
    }

    @Override
    public String toString() {
        String r ="";
        if(check){
            r = spawn+"("+ name + "(";}
        else {
            r = name + "(";}
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


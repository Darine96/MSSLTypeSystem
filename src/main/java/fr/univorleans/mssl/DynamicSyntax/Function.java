package fr.univorleans.mssl.DynamicSyntax;

import fr.univorleans.mssl.SOS.Pair;

/**
 * fn name (params) -> ret {block}
 */
public class Function implements Syntax.Expression{
    private final String name;
    private final Pair<String, Signature>[] params;

    private final String[] signals;
    private final Signature ret;
    private final Block body;

    private final int k;

    public int getK() {
        return k;
    }

    public Boolean containsWhenWatch = false;

    public Function(String name, Pair<String, Signature>[] params,
            String[] signals,Signature ret, Block body, int k) {
        this.name = name;
        this.params = params;
        this.signals=signals;
        this.ret = ret;
        this.body = body;
        this.k = k;
    }

    public String getName() {
        return name;
    }

    public String[] getSignals() {
        return signals;
    }

    /**
     * the signatures of the function parameters
     * @return
     */
    public Pair<String, Signature>[] getParams() {
        return params;
    }

    /**
     * the return signature of the function
     * @return
     */
    public Signature getRet() {
        return ret;
    }

    /**
     * the body of the function
     * @return
     */
    public Block getBody() {
        return body;
    }

    public String toString_params(){

        if (this.getParams().length == 0){
            return "";
        }
        else if (this.getParams().length == 1){
            return " mut "+this.getParams()[0].first()+" : "+this.getParams()[0].second();
        }
        else {
            String params = " mut "+this.getParams()[0].first()+" : "+this.getParams()[0].second();
            for (int i = 1; i < this.getParams().length; ++i) {
                params = params+ ", mut "+this.getParams()[i].first()+" : "+this.getParams()[i].second();
            }
           // params = params+" mut "+this.getParams()[0].first()+" : "+this.getParams()[0].second() +", ";
            return params;
        }
    }

    public String toString_signals(){

        if (this.getSignals().length == 0){
            return "";
        }
        else if (this.getSignals().length == 1){
            return "; "+this.getSignals()[0];
        }
        else {
            String signals = "; "+this.getSignals()[0];
            for (int i = 1; i < this.getSignals().length; ++i) {
                signals = signals+ ", "+this.getSignals()[i];
            }
            // params = params+" mut "+this.getParams()[0].first()+" : "+this.getParams()[0].second() +", ";
            return signals;
        }
    }

    @Override
    public int getOpcode() {
        return Syntax.Invoke_Expression;
    }
    @Override
    public String toString() {
        return "fn "+this.getName()+" ( "+toString_params()+toString_signals()+" ) -> "+this.k+" "+this.ret.toString() +" "+this.getBody().toString()
                ;
    }
}

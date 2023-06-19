package fr.univorleans.mssl.DynamicSyntax;

import fr.univorleans.mssl.SOS.Pair;

/**
 * fn name (params) -> ret {block}
 */
public class Function implements Syntax.Expression{
    private final String name;
    private final Pair<String, Signature>[] params;
    private final Signature ret;
    private final Block body;


    public Function(String name, Pair<String, Signature>[] params, Signature ret, Block body) {
        this.name = name;
        this.params = params;
        this.ret = ret;
        this.body = body;
    }

    public String getName() {
        return name;
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

    @Override
    public int getOpcode() {
        return Syntax.Invoke_Expression;
    }
    @Override
    public String toString() {
        return "fn "+this.getName()+" ( "+toString_params()+" ) -> "+this.ret.toString() +" "+this.getBody().toString()
                ;
    }
}

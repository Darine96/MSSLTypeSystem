package fr.univorleans.mssl.TypeSystem;

import fr.univorleans.mssl.DynamicSyntax.Function;
import fr.univorleans.mssl.DynamicSyntax.Lifetime;
import fr.univorleans.mssl.DynamicSyntax.Signature;
import fr.univorleans.mssl.DynamicSyntax.Type;
import fr.univorleans.mssl.Exception.ExceptionsMSG;
import fr.univorleans.mssl.SOS.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BorrowCheckerFunction extends BorrowChecker{
   public HashMap<String, Environment> envFunctions = new HashMap<>();

   public HashMap<String, Environment> getEnvFunctions() {
        return envFunctions;
    }

    public HashMap<String, Type> map = new HashMap<>();

    public BorrowCheckerFunction(boolean copyInference, String expression, List<Function> decls) {
        super(copyInference, expression, decls);
    }

    /********************************************** FunctionDeclaration *****************************************************/
/*********************************************************************************************************************/

    /**
     *  T-Program and T-Function
     *  engine of apply list of functions
     *  @return
     */

    public Pair<Environment, Type> apply(Environment gam, Lifetime lifetime, ArrayList<Function> functions) throws ExceptionsMSG {
        for (int i=0;i!=functions.size();++i){
            HashMap<String, Type> _map = new HashMap<>();
            int k = functions.get(i).getK();
            apply(lifetime,functions.get(i), k);
            _map.putAll(map);
            functions.get(i).getBody().put(_map);
            map.clear();
        }
        return new Pair<>(gam, Type.Unit);
    }


    protected void apply(Lifetime lifetime, Function function, int k) throws ExceptionsMSG {
        //(1) récuperer les parametres
        Pair<String, Signature>[] params = function.getParams();
        String[] signals = function.getSignals();
        /**
         * check the shape of the signatures
         */
        /** no need when we add the extension! **/
        //check(!shapeSignature(params), "Incompatible Signatures!");
        //(2) Create a global lifetime
        Lifetime newLft = new Lifetime(lifetime);
        //(3) create a new empty environment
        Environment gam = BorrowChecker.EMPTY_ENVIRONMENT;
        //(4) to compute a suitable type
        HashMap<String, Lifetime> suitable = new HashMap<>();
        /**
         * to compiletoC
         */
        Environment envf = BorrowChecker.EMPTY_ENVIRONMENT;
        //(4) Suitable Types, specially for borrow and inactive Trc
        for (int i = 0; i != params.length; ++i) {
            String p = params[i].first();
            Signature s = params[i].second();
            // return a suitable type
            Pair<Environment, Type> r = s.lower(gam, suitable, newLft);
            gam = r.first();
            // put the resulting type into gam
            gam = gam.put(p, new Location(r.second(), newLft));
            /*** necessary to compileToC **/
            envf = envf.put(p, new Location(r.second(), newLft));
           // System.out.printf("\n\n\n environment "+envf);

            /** needed for free to compiletoC ***/
            map.put(p,r.second());
            }
        //(5) put signals into gam
        for (int i = 0; i != signals.length; ++i) {
            String s = signals[i];
            // Bind signal to resulting type
            gam = gam.put(s, new Location(Type.Sig, newLft));
            /*** necessary to compileToC **/
            envf = envf.put(s, new Location(Type.Sig, newLft));
            // System.out.printf("\n\n\n environment "+envf);

            /** needed for free to compiletoC ***/
            map.put(s,Type.Sig);
        }
            /** put map into block of the function **/
            function.getBody().put(map);
        //(5) // Type method body
        Pair<Environment, Type> p = super.apply(gam, newLft, function.getBody(), k);
        //BorrowChecker.getGlobal().getMap().forEach((key, value) -> thirdMap.merge(key, value, String::concat));
        envf = envf.putALL(BorrowChecker.getGlobal());
        //reinitialise
        BorrowChecker.global=EMPTY_ENVIRONMENT;
        envFunctions.put(function.getName(), envf);
        //(6) Check type compatibility ( in our case we have not a return function)
        if(!function.getRet().isSubtype(p.first(),p.second(), suitable)){
            System.out.printf("The type of the function is incompatible with the type of its body!");
        }
    }


    protected boolean shapeSignature(Pair<String,Signature>[] signatures){
        for (int i=0;i!=signatures.length;++i){
            if(signatures[i].second() instanceof Signature.Clone ){
                return false;
            }
        }
        return true;
    }

    protected void check(Boolean check, String msg) throws ExceptionsMSG {
        if(check){
            throw new ExceptionsMSG("\n "+msg+" \n");
        }
    }
}

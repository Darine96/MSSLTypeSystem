package fr.univorleans.mssl.CompiletoC;

import com.google.common.base.CharMatcher;
import fr.univorleans.mssl.DynamicSyntax.*;
import fr.univorleans.mssl.DynamicSyntax.Syntax.Expression;
import fr.univorleans.mssl.MSSL.Main;
import fr.univorleans.mssl.MSSL.ExtensionMain;
import fr.univorleans.mssl.SOS.Pair;
import fr.univorleans.mssl.TypeSystem.Environment;
import fr.univorleans.mssl.TypeSystem.Location;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fr.univorleans.mssl.DynamicSyntax.Value.Unit;

public class CompileToC extends ToCRules<ToCRules.ExtensionToC>{

    /*** Fresh variables needed ***/
    /**
     * recuperer l'environnement
     */
    private Environment env;
    private Environment envBlock=null;

    //private Environment envFunction;
    public HashMap<String, Environment> envFunctions = new HashMap<>();
    private int NBthreads;

    private static int CopyNBthreads = 0;
    private static String filename = getFilename();

    private String current_fresh="";

    private String current_variable="";
    private Lval currentLval=null;
    private boolean check_box=false;
    private boolean check_assign = false;
    private boolean check_ifelse =false;
    private boolean check_function = false;

    private Lifetime lftMain = null;

    private int fresh =0;

    public boolean lasteexpression = false;
    public HashMap<String, Type> blockvariables = new HashMap<>();
    private int nodeNumbers =0;
    private final List<Function> functions;

    public static String getFilename(){
        if(Main.getSource()!=""){
            return Main.getSource();
        }
        return ExtensionMain.getSource();
    }
    public void setNBthreads() {
        NBthreads--;
    }

    public int getFresh() {
        return fresh;
    }
    public int incFresh() {
        this.fresh +=1;
        return fresh;
    }

    public String getCurrent_fresh() {
        return current_fresh;
    }
    public void setCurrent_fresh(String s) {
        this.current_fresh = s+getFresh();

    }

    public Function get_Function(String name){
        for (int i = 0;i!=functions.size(); ++i){
            if(functions.get(i).getName().equals(name)){
                return functions.get(i);
            }
        }
        return null;
    }

    /** constructor **/
    public CompileToC(List<Function> functions, HashMap<String, Environment> envFunctions , Environment envBlock, int NBthreads, ExtensionToC... extensions) {
        super(extensions);
        this.functions = functions;
        this.envFunctions=envFunctions;
        this.envBlock=envBlock;
        this.NBthreads=NBthreads;


        // Bind self in extensions
        for(ExtensionToC e : extensions) {
            e.self = this;
        }
    }

    public String returnSig(Signature signature){
        String type = null;
        if (signature instanceof Signature.Int){
            type="int ";
        }else  if (signature instanceof Signature.Bool){
            type="bool ";
        } else if (signature instanceof Signature.Box || signature instanceof Signature.Borrow) {
            Signature sig;
            if(signature instanceof Signature.Box){
               sig = ((Signature.Box) signature).getOperand();
            }else {
                sig = ((Signature.Borrow) signature).getSignature();
            }
            /**
             * Sig: box<int> or box<box<int>>
             * compute how much of ref contain this reference
             */
            /**
             * Sig: box<trc<int>> or box<box<trc<int>>>
             * verify if this signature contain a trc sig
             *
             */
            //int refcount = sig.refcount();
            /** the number of references
             *  box<&'a int>, box< clone int>, etc
             * **/
            int refcount = signature.refcountBorrow();
            boolean containsTrc = (sig.containsTrcBorrow());
            if(containsTrc){
                int posTrc = signature.posTrc();
                type = "Trc "+"*".repeat(posTrc-1);
            }else {
                type = "int "+"*".repeat(refcount);
            }
        }else if(signature instanceof Signature.Trc || signature instanceof Signature.Clone){
            type = "Trc ";
        }
        return type;
    }

    /******************* engine to apply *****************************/

    public final Expression execute(Expression expression) {

        Expression exp = null;
        /**
         * create all threads global
         * create the scheduler global
         */
        if(NBthreads != 0){
            CopyNBthreads = NBthreads;
            try {
                PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
                writer.println("ft_scheduler_t sched;");
                for(int i=1;i<=NBthreads; ++i){
                    writer.println("ft_thread_t _th"+i+";");
                    writer.println("struct node *__th"+i+";");
                }
                writer.close();
            } catch (IOException ie) {
                System.out.println("An error occurred.");
                ie.printStackTrace();
            }
        }
        /**
         * Convert all declaration functions
         */
        for (int i = 0; i != functions.size(); ++i) {
            check_function=true;
            env=envFunctions.get(functions.get(i).getName());
            Signature ret = functions.get(i).getRet();
            try{
                PrintWriter writer = new PrintWriter(new FileWriter(filename,true));
                if(ret instanceof Signature.Unit) {
                    writer.println("\nvoid " + functions.get(i).getName() + "(void* args){\t");
                }else{
                    String retType = returnSig(ret);
                    writer.print("\n"+retType+" "+ functions.get(i).getName() + "(");
                }
                int counter = functions.get(i).getParams().length;
                for (int j=0; j!=functions.get(i).getParams().length;++j){
                    Pair<String, Signature> jth = functions.get(i).getParams()[j];
                    String type= returnSig(jth.second());
                    if(ret instanceof Signature.Unit) {
                        writer.println("\t" + type + jth.first() + " = (" + type + ") get_value(args," + j + ");\t");
                    }else {
                        if((j == functions.get(i).getParams().length-1) && (functions.get(i).getSignals().length ==0)) {
                            writer.println(type + jth.first() +"){");
                        }else {
                            writer.print(type + jth.first() + ", ");
                        }
                    }
                   /* if(type.contains("*")){
                        writer.println("\t"+type+jth.first()+" = ("+type.substring(0, jth.second().toString().indexOf("*"))+") get_value(args,"+j+");\t");
                    }else{
                        writer.println("\t"+type+jth.first()+" = ("+type+") get_value(args,"+j+");\t");
                    }*/

                    /*if(j!= (functions.get(i).getParams().length -1)){
                        writer.print(type+jth.first()+", ");
                    }else{
                        writer.print(type+jth.first()+")");
                    }*/
                }
                for (int j=0; j!=functions.get(i).getSignals().length;++j){
                    String jth = functions.get(i).getSignals()[j];
                    String type="ft_event_t ";
                    if(ret instanceof Signature.Unit) {
                        writer.println("\t" + type + jth + " = (" + type + ") get_value(args," + counter + ");\t");
                    }else{
                        if(j == functions.get(i).getSignals().length-1){
                            writer.print(type+ jth+"){");
                        }else {
                            writer.print(type+ jth+", ");
                        }
                    }
                    counter+=1;

                }
                if(functions.get(i).containsWhenWatch) {
                    writer.println("\tstatic void* arr["+functions.get(i).getSignals().length +"]= {};\n" +
                            "    int taille = 0;\n" +
                            "    char str[20];\n" +
                            "    strcpy(str, __func__);");
                }
                writer.close();
            }
            catch (IOException e){
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            apply(functions.get(i).getBody());

            check_function=true;
            /**
             * free all variable declared into function
             * and remove all variables into environment
             */
            for (String name : env.bindings()) {

                env = env.remove(name);
            }

        }
        try{
            PrintWriter writer = new PrintWriter(new FileWriter(filename,true));
            check_box=true;
                writer.println("\n\nint main(int argc, char const *argv[]){\t");
            if(CopyNBthreads>0) {
                writer.println("\tvoid *retval;");
                writer.println("\tsched = ft_scheduler_create ();");
            }
            writer.close();
        }
        catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        check_function=false;
        env=envBlock;
        /**
         * lft Main of the Block
         */
        lftMain = ((Expression.Block) expression).getLifetime();
        exp = apply(expression);

        return exp;
    }
    /***************************************************************/
    @Override
    public Syntax.Expression apply(Syntax.Expression expression) {
        Syntax.Expression e = super.apply(expression);
        return e;
    }

    @Override
    protected Syntax.Expression apply(Syntax.Expression.Assignment expression) {
        Lval lval = expression.getLval();
        int count = lval.path().size();
        Location _l = env.get(lval.name());
        Type type = _l.getType().returnType(env, count);
        boolean containsTrc = _l.getType().ContainsTrcType(env);
      //  Pair<Type,Lifetime> p = lval.typeOf(env);
        //boolean containsTrc = type.ContainsTrc(env);
        /**
         * x = 0; or *x= 0; etc...
         * let mut x = trc(0); *x = 1;
         */
        String deref = null;
        /**
         * if this type contains a Trc
         * let mut x = box(trc(0));
         * **x = 1; // il faut traduire vers *((int *)_get_value_(*x))
         */
        // compute the position of the trc
        int pos = _l.getType().positionTrc(env);
        if(containsTrc){
            // if the type contient un trc
            // then, if this lval is a dereference; make sure that if we travers a trc
            // make sure if we travers a trc
            if(pos == 1 && !(_l.getType() instanceof Type.Trc)){
                containsTrc = false;
            }else {
                if (count != 0 && pos > 0 && pos <= count) {
                    containsTrc = false;
                    deref = "*".repeat(count - (pos - 1)) + "((int " + "*".repeat(_l.getType().refcount(env) - (pos - 1)) + ")_get_value(" + "*".repeat(pos - 1) + lval.name() + "))";
                }
            }
        }

        /**
         * put free before assignment, for ex:
         * let mut x = box(0);
         * free(x);
         * x = box(1);
         */
        if(_l.getType() instanceof Type.Clone){ type = _l.getType();}
        String free = type.free(lval.name(),count,"", _l.getType().positionTrc(env));
        /**
         * put free into the file.c
         */
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
            writer.println("\t"+free);
            writer.close();
        } catch (IOException ie) {
            System.out.println("An error occurred.");
            ie.printStackTrace();
        }
        /************************************************************************************************************/
        if(expression.getExpr() instanceof Value.Integer || expression.getExpr() instanceof Syntax.Expression.Borrow || expression.getExpr() instanceof Syntax.Expression.Access){
            try{
                PrintWriter writer = new PrintWriter(new FileWriter(filename,true));
                String borrow = expression.getExpr().toString();
                if(borrow.contains("mut")) {borrow=borrow.replace("mut", "");}
                if(deref!=null){
                    writer.print("\t"+deref+ "=" + borrow);
                }else {
                    writer.print("\t" + "*".repeat(count) + lval.name() + "=" + borrow);
                }
                writer.close();
            }
            catch (IOException e){
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }else if(expression.getExpr() instanceof Syntax.Expression.Box){
            //(1) free the content of lval

            //(2) realloc a new fresh then assign it to the variable
            Value.Integer counter = (Value.Integer) apply(expression.getExpr());
            String _var ="_"+incFresh();
            int _count = type.refcount(env);
            if(containsTrc){
                _count = type.positionTrc(env);
            }
            _create_box(_count,containsTrc, counter, _var);
            try{
                PrintWriter writer = new PrintWriter(new FileWriter(filename,true));
                if(deref!=null){
                    writer.println(";");
                    writer.print("\t"+deref+ "=" + _var);
                }else {
                    writer.println(";");
                    writer.print("\t" + "*".repeat(count) + lval.name() + "=" + _var);
                }
                writer.close();
            }
            catch (IOException e){
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }else if(expression.getExpr() instanceof Syntax.Expression.Trc){
            Syntax.Expression.Trc e = (Syntax.Expression.Trc) expression.getExpr();
            //(1) free the content of trc

            //(2) realloc
            if ((e.getOperand() instanceof Value.Integer)) {
                apply(e);
                try {
                    PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
                    if(deref!=null){
                        writer.print("\t"+deref + "= _create_trc (" + getCurrent_fresh() + ")");
                    }else {
                        writer.print("\t" + "*".repeat(count) + lval.name() + "= _create_trc (" + getCurrent_fresh() + ")");
                    }
                    writer.close();
                } catch (IOException ie) {
                    System.out.println("An error occurred.");
                    ie.printStackTrace();
                }

            }else if((e.getOperand() instanceof Syntax.Expression.Access) || (e.getOperand() instanceof Syntax.Expression.Borrow)){
                apply(e);
                try {
                    PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
                    if(deref!=null){
                        writer.print("\t"+deref + "= _create_trc (" + getCurrent_fresh() + ")");
                    }else {
                        writer.print("\t" +"*".repeat(count)+ lval.name() + "= _create_trc (" + getCurrent_fresh() + ")");
                    }
                    writer.close();
                } catch (IOException ie) {
                    System.out.println("An error occurred.");
                    ie.printStackTrace();
                }
            }else if(e.getOperand() instanceof Syntax.Expression.Box){
                // create a fresh box then bind it to a trc
                Value.Integer counter = (Value.Integer) apply(e.getOperand());
                Type.Trc Trctype = (Type.Trc) env.get(lval.name()).getType();
                Type _type = Trctype.getType();
                boolean _b = _type.ContainsTrc(env);
                int _count = _type.refcount(env);
                if(_b){_count = _type.positionTrc(env); }
                String fresh = "_"+incFresh();
                _create_box(_count, _b, counter, fresh);
                try {
                    PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
                    writer.println(";");
                    if(deref!=null){
                        writer.print("\t"+deref + "= _create_trc (&" + fresh + ")");
                    }else {
                        writer.print("\t " + lval.name() + "= _create_trc (&" + fresh + ")");
                    }
                    writer.close();
                } catch (IOException ie) {
                    System.out.println("An error occurred.");
                    ie.printStackTrace();
                }
            }
        }else if(expression.getExpr() instanceof Syntax.Expression.Clone){
            //(1) free(this clone)

            //(2) realloue
            Lval _lval =((Syntax.Expression.Clone) expression.getExpr()).getOperand();
            try {
                PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
                writer.print("\t"+"*".repeat(count)+ lval.name() + "= _clone_trc (" + _lval.name() + ")");
                writer.close();
            } catch (IOException ie) {
                System.out.println("An error occurred.");
                ie.printStackTrace();
            }
        }else if(expression.getExpr() instanceof InvokeFunction){
            String name = ((InvokeFunction) expression.getExpr()).getName();
            int function = 0;
            for (int k = 0; k!= functions.size();++k){
                if(functions.get(k).getName() == name){
                    function = k;
                }
            }
            Signature ret = functions.get(function).getRet();
            Pair<String, Signature>[] signatures = functions.get(function).getParams();
            String retType = returnSig(ret);
            try {
                PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
                writer.print("\t"+lval.name()+ " = "+name+"(" );
                String[] args = argumentsInvokefunction(((InvokeFunction) expression.getExpr()).getArguments(),signatures);
                for (int i = 0;i!=args.length -1;++i){
                    writer.print(args[i]+", " );
                }
                writer.print(args[args.length -1]+")" );
                writer.close();
            } catch (IOException ie) {
                System.out.println("An error occurred.");
                ie.printStackTrace();
            }
            //apply(expression.getExpr());
        }
        return null;
    }

    @Override
    protected Syntax.Expression apply(Syntax.Expression.Block expression) {
        final int n = expression.getExprs().length;
        Syntax.Expression e = null;
        if(!check_function) {
            if(check_box && !check_ifelse){
                check_box=false;
                }else{
                try {
                    PrintWriter writer = new PrintWriter(new FileWriter(filename, true));

                    writer.print("{\n");

                    writer.close();
                } catch (IOException ie) {
                    System.out.println("An error occurred.");
                    ie.printStackTrace();
                }
            }
        }
        check_function=false;
        for(int i = 0; i!= n;++i){
            if(i == n-1 && expression.getExprs()[i] instanceof Expression.Access){
                // needed to write return
                blockvariables= expression.variablesType();
                lasteexpression = true;
            }
            e = apply(expression.getExprs()[i]);

            try {
                PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
                writer.println(";");
                writer.close();
            } catch (IOException ie) {
                System.out.println("An error occurred.");
                ie.printStackTrace();
            }
        }
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
            if(lftMain != expression.getLifetime()) {
                /**
                 * free at the end of each block
                 */
                if(lasteexpression){
                    lasteexpression = false;
                }else {
                    drop(expression.variablesType());
                }
                writer.print("\n}");
            }
            else{ if(CopyNBthreads>0) {
                writer.println("\tft_scheduler_start (sched);");
                for (int i = 1; i <= CopyNBthreads; ++i) {
                    writer.println("\tpthread_join (ft_pthread(_th" + i + "),&retval);");
                   // writer.println("\tft_free(_th\" + i + \");");
                }
              //  writer.println("\tft_scheduler_free(sched);");
            }
                /**
                 * free at the end of each block
                 */
               /** (1) free nodes **/
                while(nodeNumbers>0){
                    writer.println("\tfree_node(__th"+nodeNumbers+");");
                    nodeNumbers-=1;
                }

                /** (2) free variables **/


                    for (Map.Entry mapentry : expression.variablesType().entrySet()) {
                        String name = (String) mapentry.getKey();
                        Type type = (Type) mapentry.getValue();
                        String free = type.free(name, 0, "", 0);
                        /**
                         * put free into the file.c
                         */
                        writer.println("\t" + free);
                    }

                writer.println("\treturn 0;");
                writer.print("}");

            }
            writer.close();
        } catch (IOException ie) {
            System.out.println("An error occurred.");
            ie.printStackTrace();
        }
        /**
         * remove all variables into map
         */
        expression.remove();
        return Unit;
    }

    @Override
    protected Syntax.Expression apply(Syntax.Expression.Box expression) {
        Syntax.Expression e = expression.getOperand();
        Value.Integer counter = null;
        if(e instanceof Value.Integer){
            current_fresh = String.valueOf(((Value.Integer) e).value());
            return new Value.Integer(((Value.Integer) e).value(),1);
        }else if ( e instanceof Syntax.Expression.Box) {
            counter = (Value.Integer) apply(((Syntax.Expression.Box) e));
            counter.setCounter(1);
        }else if(e instanceof Syntax.Expression.Borrow ){
            check_box=true;
            Lval lval = ((Syntax.Expression.Borrow) e).getOperand();
            this.current_fresh="&"+lval.toString();
            currentLval = lval;
            return new Value.Integer(0,1);
        } else if (e instanceof Syntax.Expression.Access) {
            check_box=true;
            Lval lval = ((Syntax.Expression.Access) e).operand();
            this.current_fresh=lval.toString();
            currentLval = lval;
            return new Value.Integer(0,1);
        } else if (e instanceof Syntax.Expression.Trc) {
            check_box=true;
            apply(e);
            if(check_box) {
            //create a fresh trc
            String before = getCurrent_fresh();
            String _fresh = "_"+incFresh();
            setCurrent_fresh("_");

                try {
                    PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
                    writer.println("\tTrc " + _fresh + "= _create_trc (" + before + ");");
                    writer.close();
                } catch (IOException ie) {
                    System.out.println("An error occurred.");
                    ie.printStackTrace();
                }
            }
            return new Value.Integer(0,1);
        }else if (e instanceof Syntax.Expression.Clone) {
            Syntax.Expression.Clone _clone =(Syntax.Expression.Clone) e;
            //create a fresh trc
            String before = _clone.getOperand().name();
            String _fresh = "_"+incFresh();
            setCurrent_fresh("_");
            try {
                PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
                writer.println("\tTrc " + _fresh + "= _clone_trc(" + before + ");");
                writer.close();
            } catch (IOException ie) {
                System.out.println("An error occurred.");
                ie.printStackTrace();
            }
            return new Value.Integer(0,1);
        }
        return counter;
    }

    @Override
    protected Syntax.Expression apply(Syntax.Expression.Trc expression) {
        /**
         * we use the implementation of trc.c and trc.h
         */
        /**
         * case (1): let mut x = trc(0):
         * case(2) let mut x = trc(box(0));
         * case (3): 1. let mut x = trc(y);
         *           2. let mut y = 0;//int
         *           3. let mut y = box(e);
         *           4. let mut y = &a; // where a is anything
         *           5. *y, **y, etc...
         */

        //case 1
        if(expression.operand instanceof Value.Integer){
            String fresh = "_"+incFresh();
            String _fresh = "_"+incFresh();
            setCurrent_fresh("_");
            try {
                PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
                writer.println("\tint "+fresh+"="+expression.operand+";");
                writer.println("\tint * "+_fresh+" = "+"&"+fresh+";");
                // writer.print("_create_trc ("+_fresh+")");
                writer.close();
            } catch (IOException ie) {
                System.out.println("An error occurred.");
                ie.printStackTrace();
            }
        }else if(expression.operand instanceof Syntax.Expression.Access){
            /**
             * let mut x = trc(x) or let mut x = trc(*x);
             */
            // recuperer path
            Lval lval =((Syntax.Expression.Access) expression.operand).operand();
            Location l = env.get(lval.name());
            //int count = l.getType().refcount(env);
            int count = lval.path().size();
            if(count !=0){
                current_fresh = "&"+"*".repeat(count)+lval.name();
            }else{
                current_fresh = "&"+lval.name();
            }
        }else if(expression.operand instanceof Syntax.Expression.Borrow){
            /**
             * let mut x = trc(&x) or let mut x = trc(&*x);
             * x ne peut pas être de type trc ou clone
             */
            //create a fresh variable pointer to x
            Lval lval = (Lval) ((Syntax.Expression.Borrow) expression.operand).getOperand();
            Location l = env.get(lval.name());
            int count = l.getType().refcount(env)+1 - lval.path().size();
            String fresh = "_"+incFresh();
            setCurrent_fresh("_");
            current_fresh="&"+fresh;
            try {
                PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
                writer.println("\tint"+"*".repeat(count)+" "+fresh+"="+expression.operand.toString().replace("mut", "")+";");
                // writer.print("_create_trc ("+_fresh+")");
                writer.close();
            } catch (IOException ie) {
                System.out.println("An error occurred.");
                ie.printStackTrace();
            }
        }
        else if(expression.operand instanceof Expression.Box){
            /**
             * trc(box(0)); or trc(box(x), etc ...
             */
            if(check_box) {
                Value.Integer counter = (Value.Integer) apply((expression).getOperand());
                Type Trctype = env.get(current_variable).getType();
                String fresh = "_" + incFresh();
                Type _type = Trctype.returnType(env, Trctype.positionTrc(env));
                int _count = _type.refcount(env);
                boolean _b = _type.ContainsTrc(env);
                if (_b) {
                    _count = _type.positionTrc(env);
                }
                _create_box(_count, _b, counter, fresh);
                String _fresh = "_" + incFresh();
                current_fresh= _fresh;
                try {
                    PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
                    writer.println(";");
                    writer.println("\tTrc " +_fresh+ "= _create_trc (&" + fresh + ");");
                    writer.close();
                } catch (IOException ie) {
                    System.out.println("An error occurred.");
                    ie.printStackTrace();
                }
            }

        }
        return null;
    }

    @Override
    protected Expression apply(Expression.Conditional expression) {
        apply(expression.lftoperand);
        Expression e = expression.lftoperand;
        String deref = condition(e);
        /*************************************************************/
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
            writer.println(deref+" "+expression.operator);
            writer.close();
        } catch (IOException ie) {
            System.out.println("An error occurred.");
            ie.printStackTrace();
        }
        apply(expression.rghtoperand);
        return null;
    }

    @Override
    protected Expression apply(Expression.IfElse expression) {
        check_ifelse = true;
        Expression cond = expression.getConditions();
        //String s = expression.getConditions().toString();
        String s = null;
        /*********************************************/
        if(cond instanceof Expression.Conditional){
            String lft = condition(((Expression.Conditional) cond).lftoperand);
            String rght = condition(((Expression.Conditional) cond).rghtoperand);
            s = lft+((Expression.Conditional) cond).operator.toString()+rght;
        }else {
            s = expression.getConditions().toString();
        }
        /***********************************************/
        String _s = s.replace("^", "");
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
            writer.println("\tif("+_s+")");
            writer.close();
        } catch (IOException ie) {
            System.out.println("An error occurred.");
            ie.printStackTrace();
        }
        apply(expression.getIfblock());
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
            writer.print("\telse");
            writer.close();
        } catch (IOException ie) {
            System.out.println("An error occurred.");
            ie.printStackTrace();
        }
        apply(expression.getElseblock());
        check_ifelse = false;
        return null;
    }

    @Override
    protected Syntax.Expression apply(InvokeFunction expression) {
        //(0) get the function
        String name= expression.getName();
        Syntax.Expression[] arguments = expression.getArguments();
        String[] signals = expression.getSignals();
            // (0.1) create a fresh variable is necessary
        // check if the function inside spawn
        if(expression.getCheck()) {
            String[] args = fresh_argments(name, arguments);
            //(1) create a struct
           // String node = "__" + name;
            String node = "__th" + NBthreads;
            nodeNumbers+=1;
            if(arguments.length!=0 || signals.length!=0) {
                try {
                    PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
                    //writer.println("\tstruct node *" + node + ";");
                    //(2) add the arguments to the struct
                    writer.print("\t" + node + " = add_value_indetermine(" + (arguments.length + signals.length - 1));
                    int j=0;
                    for (int i = 0; i != args.length; ++i) {
                        writer.print(", " + args[i]);
                    }

                    for (int i = 0; i!=signals.length; ++i) {
                        writer.print(", " + signals[i]);
                    }
                    writer.println(");");
                    writer.close();
                } catch (IOException ie) {
                    System.out.println("An error occurred.");
                    ie.printStackTrace();
                }
            }

            //(3)create a thread by decrement the nombre and bind it to the scheduler
            String th = "_th" + NBthreads;
            setNBthreads();
            try {
                PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
                if (arguments.length != 0 || signals.length != 0) {
                    writer.print("\t" + th + " = ft_thread_create(sched," + name + ",NULL," + node + ")");
                } else {
                    writer.print("\t" + th + " = ft_thread_create(sched," + name + ",NULL,NULL)");
                }
                writer.close();
            } catch (IOException ie) {
                System.out.println("An error occurred.");
                ie.printStackTrace();
            }
        }else{
            int function = 0;
            for (int k = 0; k!= functions.size();++k){
                if(functions.get(k).getName() == name){
                    function = k;
                }
            }
            Pair<String, Signature>[] signatures = functions.get(function).getParams();
            try {
                PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
                writer.print("\t"+name+"(");
                String[] args = argumentsInvokefunction(((expression).getArguments()),signatures);
                if(args.length!=0) {
                    for (int i = 0; i != args.length - 1; ++i) {
                        writer.print(args[i] + ", ");
                    }
                    if (signals.length == 0) {
                        writer.print(args[args.length - 1] + ")");
                    } else {
                        writer.print(args[args.length - 1] + ", ");
                    }
                }
                    /*** signals ***/
                if(signals.length!=0) {
                    for (int i = 0; i != signals.length - 1; ++i) {
                        writer.print(signals[i] + ", ");
                    }
                    writer.print(signals[signals.length - 1] + ")");
                }
                writer.close();
            } catch (IOException ie) {
                System.out.println("An error occurred.");
                ie.printStackTrace();
            }

        }
        return null;
    }

    @Override
    protected Syntax.Expression apply(Syntax.Expression.Clone expression) {
        return null;
    }

    @Override
    protected Syntax.Expression apply(Syntax.Expression.Borrow expression) {
        return null;
    }

    @Override
    protected Syntax.Expression apply(Syntax.Expression.Declaration expression) {
        Syntax.Expression e = expression.getInitialiser();
        Location l = env.get(expression.getVariable());
        current_variable = expression.getVariable();
        /**
         * let mut x = 0;//int
         */
        if(e instanceof Value.Integer){
            try {
                PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
                writer.print("\tint "+expression.getVariable()+"="+expression.getInitialiser());
                writer.close();
            } catch (IOException ie) {
                System.out.println("An error occurred.");
                ie.printStackTrace();
            }
        }else if(e instanceof Syntax.Expression.Box){
            Value.Integer counter = (Value.Integer) apply(expression.getInitialiser());
            /**
             * borrow or access. For example,
             * let mut x = box(&y);
             * let mut x = box(*y); etc...
             */
            Type type = l.getType();
            boolean b = (type.ContainsTrcType(env) || type.ContainsTrc(env));
            int c = type.refcount(env);
            if(b){
                c = type.positionTrc(env);}
            //if(check_box){ c = c- CharMatcher.is('*').countIn(current_fresh);}
            _create_box(c, b, counter, expression.getVariable().toString());

        }else if(e instanceof Syntax.Expression.Trc){
            //let mut x = trc(0);
            if ((((Syntax.Expression.Trc) e).getOperand() instanceof Value.Integer)) {
                apply(e);
                try {
                    PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
                    writer.print("\tTrc " + expression.getVariable() + "= _create_trc (" + getCurrent_fresh() + ")");
                    writer.close();
                } catch (IOException ie) {
                    System.out.println("An error occurred.");
                    ie.printStackTrace();
                }

            }else if((((Syntax.Expression.Trc) e).getOperand() instanceof Syntax.Expression.Access) || (((Syntax.Expression.Trc) e).getOperand() instanceof Syntax.Expression.Borrow)){
                apply(e);
                try {
                    PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
                    writer.print("\tTrc " + expression.getVariable() + "= _create_trc (" + getCurrent_fresh() + ")");
                    writer.close();
                } catch (IOException ie) {
                    System.out.println("An error occurred.");
                    ie.printStackTrace();
                }
            }else if(((Syntax.Expression.Trc) e).getOperand() instanceof Syntax.Expression.Box){
                // create a fresh box then bind it to a trc
                Value.Integer counter = (Value.Integer) apply(((Syntax.Expression.Trc) e).getOperand());
                Type.Trc Trctype = (Type.Trc) env.get(expression.getVariable()).getType();
                String fresh = "_"+incFresh();
                Type _type = Trctype.getType();
                int _count = _type.refcount(env);
                boolean _b = _type.ContainsTrc(env);
                if(_b){ _count = _type.positionTrc(env);}
                _create_box(_count, _b, counter, fresh);
                try {
                    PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
                    writer.println(";");
                    writer.print("\tTrc " + expression.getVariable() + "= _create_trc (&" + fresh + ")");
                    writer.close();
                } catch (IOException ie) {
                    System.out.println("An error occurred.");
                    ie.printStackTrace();
                }
            }
        }else if(e instanceof Syntax.Expression.Clone){
            Lval lval = ((Syntax.Expression.Clone) e).getOperand();
            try {
                PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
                writer.print("\tTrc " + expression.getVariable() + "= _clone_trc (" + "*".repeat(lval.path().size())+lval.name() + ")");
                writer.close();
            } catch (IOException ie) {
                System.out.println("An error occurred.");
                ie.printStackTrace();
            }
        }else if( e instanceof Syntax.Expression.Borrow){
            //create a fresh variable pointer to x
            Lval lval = ((Syntax.Expression.Borrow) e).getOperand();
            Location _l = env.get(lval.name());
            int count = _l.getType().refcount(env)+1- lval.path().size();
            String deref = "*".repeat(lval.path().size())+lval.name();
            try {
                PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
            if(_l.getType().ContainsTrcType(env)){
                // if the type contient un trc
                // then, if this lval is a dereference; make sure that if we travers a trc
                // compute the position of the trc
                int pos = _l.getType().positionTrc(env);
                int _deref = lval.path().size();
                // make sure if we travers a trc
                if(_deref !=0 && pos <= _deref) {
                    deref = "*".repeat(_deref - (pos -1)) + "((int " + "*".repeat(_l.getType().refcount(env) -(pos -1)) + ")_get_value(" +"*".repeat(pos-1)+lval.name() + "))";
                    writer.print("\tint" + "*".repeat(count) + " " + expression.getVariable() + "=& " + deref);
                }else{
                    writer.print("\tTrc"+"*".repeat((pos -_deref))+" "+expression.getVariable()+"=& "+deref);
                     }

            }else {

                writer.print("\tint" + "*".repeat(count) + " " + expression.getVariable() + "=& " + deref);
            }
                    //writer.print("\tint"+"*".repeat(count)+" "+expression.getVariable()+"="+expression.getInitialiser().toString().replace("mut", ""));
                writer.close();
            } catch (IOException ie) {
                System.out.println("An error occurred.");
                ie.printStackTrace();
            }

        }else if( e instanceof Syntax.Expression.Access){
            //create a fresh variable pointer to x
            Lval lval = ((Syntax.Expression.Access) e).operand();
            Location _l = env.get(lval.name());
            int count = _l.getType().refcount(env) - lval.path().size();
            String deref = "*".repeat(lval.path().size())+lval.name();
            int _deref = lval.path().size();
            if(_l.getType().ContainsTrc(env)){
                // if the type contient un trc
                // then, if this lval is a dereference; make sure that if we travers a trc
                // compute the position of the trc
                int pos = _l.getType().positionTrc(env);

                // make sure if we travers a trc
                if(_deref !=0 && pos <= _deref) {
                    deref = "*".repeat(_deref - (pos -1)) + "((int " + "*".repeat(_l.getType().refcount(env) -(pos -1)) + ")_get_value(" +"*".repeat(pos-1)+lval.name() + "))";
                }
            }
            try {
                PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
                if(_l.getType().returnType(env,_deref).ContainsTrcType(env)){
                    count = _l.getType().returnType(env,_deref).positionTrc(env)-1;
                    writer.print("\t Trc" + "*".repeat(count) + " " + expression.getVariable() + "=" + deref);
                }else {
                    if (expression.getInitialiser().toString().contains("^")) {
                        writer.print("\t int" + "*".repeat(count) + " " + expression.getVariable() + "=" + deref);
                    } else {
                        writer.print("\t int" + "*".repeat(count) + " " + expression.getVariable() + "=" + deref);
                    }
                }
                writer.close();
            } catch (IOException ie) {
                System.out.println("An error occurred.");
                ie.printStackTrace();
            }
        }else if(e instanceof InvokeFunction){
            String name = ((InvokeFunction) e).getName();
            int function = 0;
            for (int k = 0; k!= functions.size();++k){
                if(functions.get(k).getName() == name){
                    function = k;
                }
            }
            Signature ret = functions.get(function).getRet();
            String retType = returnSig(ret);
            Pair<String, Signature>[] signatures = functions.get(function).getParams();
                try {
                    PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
                    writer.print("\t" + retType+ " "+expression.getVariable()+"  = "+name+"(" );
                    String[] args = argumentsInvokefunction(((InvokeFunction) expression.getInitialiser()).getArguments(),signatures);
                    for (int i = 0;i!=args.length -1;++i){
                        writer.print(args[i]+", " );
                    }
                    writer.print(args[args.length -1]+")" );
                    writer.close();
                } catch (IOException ie) {
                    System.out.println("An error occurred.");
                    ie.printStackTrace();
                }

        }

        return null;
    }

    @Override
    protected Syntax.Expression apply(Syntax.Expression.Cooperate expression) {

        try{
            PrintWriter writer = new PrintWriter(new FileWriter(filename,true));
            writer.print("\t ft_thread_cooperate()");
            writer.close();
        }
        catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return Unit;
    }
    @Override
    protected Syntax.Expression apply(Syntax.Expression.Access expression) {
        Lval v = expression.operand();
        Pair<Type,Lifetime> typing_omega = v.typeOf(env);
        String lval = expression.operand().toString();
        String s = lval;

        if(lasteexpression) {
            if(lval.contains("*")){
                    // is a dereference into box
                s= "_i";
                    if(typing_omega.first() instanceof Type.Trc || typing_omega.first() instanceof Type.Clone){
                        int ref = typing_omega.first().positionTrc(env);
                        try {
                            PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
                            writer.println("\t Trc" + "*".repeat(ref-1) + " _i = " + lval + ";");
                            writer.close();
                        }
                              catch (IOException e){
                                System.out.println("An error occurred.");
                                e.printStackTrace();
                            }

                        }else {
                    int ref = typing_omega.first().refcount(env);
                        try {
                            PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
                            writer.println("\t int"+ "*".repeat(ref)+" _i = "+lval+";");
                            writer.close();
                        }
                        catch (IOException e){
                            System.out.println("An error occurred.");
                            e.printStackTrace();
                        }

                   }
            }


            drop(blockvariables);
            try {
                PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
                writer.print("\treturn "+s);
                writer.close();
            }
            catch (IOException e){
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

        }else{
            try {
                PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
                writer.print("\t"+lval);
                writer.close();
            }
            catch (IOException e){
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    protected Expression apply(Expression.Sig expression) {
        String s = expression.getVariable();
        try{
            PrintWriter writer = new PrintWriter(new FileWriter(filename,true));
            writer.print("\t ft_event_t "+s+" = ft_event_create (sched)");
            writer.close();
        }
        catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected Expression apply(Expression.When expression) {
        String s = expression.getVariable();
        try{
            PrintWriter writer = new PrintWriter(new FileWriter(filename,true));
            writer.print("\t { ft_thread_when_event("+s+");\n\t");
            writer.print("if(ft_thread_get_case_watch()){\n" +
                    "             if(ft_thread_compare_function(str)){\n" +
                    "                int index = ft_thread_goto(taille);\n" +
                    "               ft_thread_reset_event("+s+");\n" +
                    "                goto *arr[index];\n" +
                    "            }\n" +
                    "             else{\n" +
                    "                ft_thread_return_function(taille);\n" +
                    "                ft_thread_reset_event("+s+");\n" +
                    "                return; }\n" +
                    "         }");
            writer.close();
        }
        catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        apply(expression.getOperand());
        try{
            PrintWriter writer = new PrintWriter(new FileWriter(filename,true));
            writer.print("\tft_thread_reset_event("+s+");}");
            writer.close();
        }
        catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected Expression apply(Expression.Watch expression) {
        String s = expression.getVariable();
        try{
            PrintWriter writer = new PrintWriter(new FileWriter(filename,true));
            writer.print("{\n" +
                    "      ft_thread_set_event_watch("+s+", str);\n" +
                    "      arr[taille]= &&"+s+";\n" +
                    "      taille++;\n" +
                    "      \n" +
                    "   if(ft_thread_get_case_watch()){\n" +
                    "      //case 1: function event same current function:\n" +
                    "      if(ft_thread_compare_function(str)){\n" +
                    "         //case 2: case_function is true\n" +
                    "         if(ft_thread_get_case_function()){\n" +
                    "            int index = ft_thread_goto_case_function(taille);// faire attention\n" +
                    "            //printf(\"the index is %d\\n\", index);\n" +
                    "            taille = index + 1;\n" +
                    "            goto *arr[index];\n" +
                    "         }\n" +
                    "         else{\n" +
                    "            int index = ft_thread_goto(taille);\n" +
                    "\n" +
                    "            //printf(\"the index is %d\\n\", index);\n" +
                    "            taille = index + 1;\n" +
                    "            goto *arr[index];\n" +
                    "         }\n" +
                    "      \n" +
                    "   }\n" +
                    "   else{\n" +
                    "      // the watch event generate not in the same current function\n" +
                    "      ft_thread_return_function(taille);\n" +
                    "      return;\n" +
                    "   }\n" +
                    "   }");
            writer.close();
        }
        catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        apply(expression.getOperand());
        try{
            PrintWriter writer = new PrintWriter(new FileWriter(filename,true));
            writer.print("//NOM DU SIGNAL\n" +
                    "   "+s+":{ \n" +
                    "      ft_thread_watch_list_not_done();// attention\n" +
                    "      taille--;\n" +
                    "   }}");
            writer.close();
        }
        catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected Expression apply(Expression.Emit expression) {
        String s = expression.getVariable();
        try{
            PrintWriter writer = new PrintWriter(new FileWriter(filename,true));
            writer.print("\t ft_thread_generate("+s+")");
            writer.close();
        }
        catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected Syntax.Expression apply(Tuples.TuplesExpression expression) {
        return null;
    }

    @Override
    protected Syntax.Expression apply(Unit value) {
        return null;
    }

    @Override
    protected Syntax.Expression apply(Value.Integer value) {
        return null;
    }

    @Override
    protected Syntax.Expression apply(Value.Reference value) {
        return null;
    }

    @Override
    protected Expression apply(Expression.Print expression) {
        Lval lval = expression.lval();
        int counter =  CharMatcher.is('*').countIn(lval.name());
        String name = lval.name().substring(counter);
        Location _l = env.get(name);
        Type type = _l.getType();
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
            if(type.ContainsTrcType(env)){
                int pos = type.positionTrc(env)-1;
                writer.print("printf(\""+lval.name()+" = %i\","+"*".repeat(counter-pos)+"((int"+
                        "*".repeat(counter-pos)+")_get_value("+"*".repeat(pos)+name+")))");
            }else {
                writer.print("printf(\"" + lval.name() + " = %i\"," + lval.name() + ")");
            }
            writer.close();
        } catch (IOException ie) {
            System.out.println("An error occurred.");
            ie.printStackTrace();
        }
        return null;
    }


    /*******************************************************************
     FUNCTIONS
     /*******************************************************************/
    public static void debut_code(){
        String encoding = "UTF-8";
        try{
            PrintWriter writer = new PrintWriter(filename, encoding);
            writer.println("#include\"fthread.h\"");
            writer.println("#include\"trc.h\"");
            writer.println("#include<stdlib.h>");
            writer.println("#include<stdbool.h>");
            writer.println("#include<stdio.h>");
            writer.println("#include<string.h>");
            // writer.println("int main(argc, char *argv[])\n" +
            //       "{");
            writer.close();
        }
        catch (IOException e){
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /*******************create a box **********************************/
    public void _create_box(int count, boolean type, Value.Integer counter, String var){
        /**
         * count contain already the correct number of reference
         * counter contains the number of box ( needed ofr malloc)
         * type boolean: takes true if this type contain a Trc
         */
            //deref or lval or borrow
            //let mut x = box(*y): (Deref)
        /**
         * case if type contain a trc
         * for example: y = box(trc(&y))
         */
        if(type){
                count = count -1;
                try {
                    PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
                    writer.println("\tTrc " + "*".repeat(count) + " " + var + " = malloc(sizeof(Trc" + "*".repeat(count - 1) + "));");
                    int _n = counter.getCounter() - 1;
                    int n = count - 1;
                    int j = 1;
                    while (_n > 0) {
                        writer.println("\t" + "*".repeat(j) + var + " = malloc(sizeof(Trc" + "*".repeat(n - 1) + "));");
                        n--;
                        _n--;
                        j = j + 1;
                    }
                    writer.print("\t" + "*".repeat(counter.getCounter()) + var + " = " + current_fresh);
                    writer.close();
                } catch (IOException ie) {
                    System.out.println("An error occurred.");
                    ie.printStackTrace();
                }
            }/**
             * case : box(box(&y)) ot box(*x) or box(y), etc..
             **/
            else if(check_box) {
                try {
                    PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
                    //writer.println("\tint " + "*".repeat(counter.getCounter()) + " " + var + " = (int " + "*".repeat(counter.getCounter()) + ") malloc(sizeof(int" + "*".repeat(counter.getCounter() - 1) + "));");
                    writer.println("\tint " + "*".repeat(count) + " " + var + " = (int " + "*".repeat(count) + ") malloc(sizeof(int" + "*".repeat(count - 1) + "));");
                    int _n = counter.getCounter() - 1;
                    int n = count - 1;
                    int j = 1;
                    while (_n > 0) {
                        writer.println("\t" + "*".repeat(j) + var + " = malloc(sizeof(int" + "*".repeat(n - 1) + "));");
                        _n--;
                        n--;
                        j = j + 1;
                    }
                    writer.print("\t" + "*".repeat(counter.getCounter()) + var + " = " + current_fresh);
                    writer.close();
                } catch (IOException ie) {
                    System.out.println("An error occurred.");
                    ie.printStackTrace();
                }
                check_box=false;
            }/** case where the content of box is a value **/
            else{
            try {
                PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
                writer.println("\tint " + "*".repeat(counter.getCounter()) + " " + var + " = (int " + "*".repeat(counter.getCounter()) + ") malloc(sizeof(int" + "*".repeat(counter.getCounter() - 1) + "));");
                int n = counter.getCounter()-1;
                int j=1;
                while(n>0){
                    writer.println("\t"+"*".repeat(j)+var+" = (int "+"*".repeat(n)+") malloc(sizeof(int"+"*".repeat(n-1)+"));");
                    n = n-1;
                    j=j+1;
                }
                writer.print("\t"+"*".repeat(counter.getCounter())+var+" = "+counter.value());
                writer.close();
            } catch (IOException ie) {
                System.out.println("An error occurred.");
                ie.printStackTrace();
            }
        }

    }


    public String[] fresh_argments(String name, Expression[] arguments){
        String[] args= new String[arguments.length];
        String fresh=null;
        Function function = get_Function(name);
        Pair<String, Signature>[] params =function.getParams();
        for (int i = 0; i!= arguments.length;++i){
            Expression e = arguments[i];
            try {
                PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
                if(e instanceof Value.Integer || e instanceof Value.Boolean){
                    fresh="_"+incFresh();
                    writer.println("int "+fresh+" = "+e+";");
                    args[i]="&"+fresh;
                }else if(e instanceof Syntax.Expression.Box){
                    // create a fresh Box
                    Value.Integer counter = (Value.Integer) apply(e);
                    int count = counter.getCounter();
                    fresh="_"+incFresh();
                    Signature sig = params[i].second();
                    Boolean check = sig.containsTrc();
                    count = sig.refcount();
                    _create_box(count, check, counter, fresh);
                    try {
                        PrintWriter writer1 = new PrintWriter(new FileWriter(filename, true));
                        writer1.println(";");
                        writer1.close();
                    } catch (IOException ie) {
                        System.out.println("An error occurred.");
                        ie.printStackTrace();
                    }
                    // add the fresh argument into the args
                    args[i]=fresh;
                }else if(e instanceof Syntax.Expression.Trc){
                    //add va fresh argument
                    fresh="_"+incFresh();
                    if ((((Syntax.Expression.Trc) e).getOperand() instanceof Value.Integer)) {
                        apply(e);
                        writer.print("\tTrc " + fresh+ "= _create_trc (" + getCurrent_fresh() + ")");
                        // writer.close();
                    }else if((((Syntax.Expression.Trc) e).getOperand() instanceof Syntax.Expression.Access) || (((Syntax.Expression.Trc) e).getOperand() instanceof Syntax.Expression.Borrow)){
                        apply(e);
                        writer.print("\tTrc " + fresh + "= _create_trc (" + getCurrent_fresh() + ")");
                    }else if(((Syntax.Expression.Trc) e).getOperand() instanceof Syntax.Expression.Box){
                        // create a fresh box then bind it to a trc
                        Value.Integer counter = (Value.Integer) apply(((Syntax.Expression.Trc) e).getOperand());
                        Signature sig = params[i].second();
                        Boolean check = sig.containsTrc();
                        String fresh1 = "_"+incFresh();
                        _create_box(sig.refcount(), check, counter, fresh1);
                        //_create_box(counter.getCounter(), check, counter, fresh1);
                        writer.println(";");
                        writer.print("\tTrc " + fresh + "= _create_trc (&" + fresh1 + ")");
                        // add the fresh into the args
                        args[i]=fresh;
                    }
                }else if(e instanceof Syntax.Expression.Clone){
                    // create a fresh argument
                    fresh="_"+incFresh();

                    Lval lval = ((Syntax.Expression.Clone) e).getOperand();
                    writer.println("\tTrc " + fresh + "= _clone_trc (" + "*".repeat(lval.path().size())+lval.name() + ");");
                    // add this fresh into the args
                    writer.close();
                    args[i]=fresh;
                }else if(e instanceof Syntax.Expression.Access){
                    // create a fresh arguments and make sur that this acces does contain a reference
                    Lval lval = ((Syntax.Expression.Access) e).operand();
                    Type type = env.get(lval.name()).getType();
                    int c = lval.path().size();
                    //if(type.refcount(env) == 0){
                    /**
                     * let mut x = box(1);
                     * spawn(f(*x)) // type int
                     */
                    if(type.returnType(env, c) instanceof Type.Int){
                        args[i]="& "+"*".repeat(c)+lval.name();
                    }else{
                        args[i]="*".repeat(c)+lval.name();
                    }
                }
            } catch (IOException ie) {
                System.out.println("An error occurred.");
                ie.printStackTrace();
            }

        }

        return args;
    }
    /***************************************************************/
    public String[] argumentsInvokefunction(Expression[] arguments, Pair<String, Signature>[] signatures){
        String[] args=new String[arguments.length];
        for(int i =0; i!= arguments.length;++i){
            Expression e = arguments[i];
            if(e instanceof Value.Integer || e instanceof Value.Boolean || e instanceof Syntax.Expression.Borrow || e instanceof Syntax.Expression.Access){
                String arg = e.toString();
                if(arg.contains("mut")) {arg=arg.replace("mut", "");}
                args[i]=arg;
            }else if(e instanceof Syntax.Expression.Box ){
                //(1) free the content of lval

                //(2) realloc a new fresh then assign it to the variable
                Value.Integer counter = (Value.Integer) apply(e);
                String _var ="_"+incFresh();
                int _count = signatures[i].second().refcountBorrow();
                Boolean containsTrc = (signatures[i].second().containsTrc() && signatures[i].second().containsTrcBorrow());
                if(containsTrc){
                    _count = signatures[i].second().posTrc();
                }
                        _create_box(_count,containsTrc, counter, _var);
                try {
                    PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
                    writer.print(";\n");
                    writer.close();
                } catch (IOException ie) {
                    System.out.println("An error occurred.");
                    ie.printStackTrace();
                }
                args[i]=_var;
            }else if(e instanceof Syntax.Expression.Trc || e instanceof  Syntax.Expression.Clone){
                //f(trc(0));
                String _var = "_"+incFresh();
                if(e instanceof Expression.Trc) {
                    if ((((Syntax.Expression.Trc) e).getOperand() instanceof Value.Integer)) {
                        apply(e);

                        try {
                            PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
                            writer.print("\tTrc " + _var + "= _create_trc (" + getCurrent_fresh() + ")");
                            writer.print(";\n");
                            writer.close();
                        } catch (IOException ie) {
                            System.out.println("An error occurred.");
                            ie.printStackTrace();
                        }

                    } else if ((((Syntax.Expression.Trc) e).getOperand() instanceof Syntax.Expression.Access) || (((Syntax.Expression.Trc) e).getOperand() instanceof Syntax.Expression.Borrow)) {
                        apply(e);
                        try {
                            PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
                            writer.print("\tTrc " + _var + "= _create_trc (" + getCurrent_fresh() + ")");
                            writer.print(";\n");
                            writer.close();
                        } catch (IOException ie) {
                            System.out.println("An error occurred.");
                            ie.printStackTrace();
                        }
                    } else if (((Syntax.Expression.Trc) e).getOperand() instanceof Syntax.Expression.Box) {
                        // create a fresh box then bind it to a trc
                        Value.Integer counter = (Value.Integer) apply(((Syntax.Expression.Trc) e).getOperand());
                        int _count = signatures[i].second().refcountBorrow();
                        Boolean containsTrc = (signatures[i].second().containsTrc() && signatures[i].second().containsTrcBorrow());
                   /* if(containsTrc){
                        _count = signatures[i].second().posTrc();
                    }*/
                        String fresh = "_" + incFresh();
                        _create_box(_count, false, counter, fresh);
                        try {
                            PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
                            writer.print(";\n");
                            writer.print("\tTrc " + _var + "= _create_trc (&" + fresh + ");\n");
                            writer.close();
                        } catch (IOException ie) {
                            System.out.println("An error occurred.");
                            ie.printStackTrace();
                        }
                    }
                }else{
                    Lval lval = ((Expression.Clone) e).getOperand();
                    try {
                        PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
                        writer.print(";\n");
                        writer.print("\tTrc " + _var + "=  _clone_trc (" + lval.name().toString() + ");\n");
                        writer.close();
                    } catch (IOException ie) {
                        System.out.println("An error occurred.");
                        ie.printStackTrace();
                    }


                }
            args[i]=_var;
            }
        }
        return args;
    }
    /****************************************************************/
    public String condition(Expression e){
        String deref = null;
        Lval lval=null;
        /*************************************************************/
        if(e instanceof Expression.Borrow){
             lval = ((Expression.Borrow) e).getOperand();
            int count = lval.path().size();
            Location _l = env.get(lval.name());
            Type type = _l.getType().returnType(env, count);
            boolean containsTrc = _l.getType().ContainsTrcType(env);
            // compute the position of the trc
            int pos = _l.getType().positionTrc(env);
            if(containsTrc){
                // if the type contient un trc
                // then, if this lval is a dereference; make sure that if we travers a trc
                // make sure if we travers a trc
                if(pos == 1 && !(_l.getType() instanceof Type.Trc)){
                    deref ="*".repeat(count) + lval.name();

                }else {
                    if (count != 0 && pos > 0 && pos <= count) {
                        containsTrc = false;
                        deref ="*".repeat(count - (pos - 1)) + "((int " + "*".repeat(_l.getType().refcount(env) - (pos - 1)) + ")_get_value(" + "*".repeat(pos - 1) + lval.name() + "))";
                    }
                }
            }else{
                deref ="*".repeat(count) + lval.name();
            }

        }else {
            deref = e.toString();
        }
        return deref;
    }
    /***************************************************************/
    public abstract static class ExtensionToC implements ToCRules.ExtensionToC {
        protected CompileToC self;
    }


    public void _create_box_hold(int count, boolean type, Value.Integer counter, String var){
        if(check_box){
            //deref or lval or borrow
            //let mut x = box(*y): (Deref)
            //count = type.refcount(env) - CharMatcher.is('*').countIn(current_fresh);
            count = count - CharMatcher.is('*').countIn(current_fresh);
            if(type){
                //count = count -1;
                try {
                    PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
                    if(!check_assign) {
                        writer.println("\tTrc " + "*".repeat(count) + " " + var + " = malloc(sizeof(Trc" + "*".repeat(count - 1) + "));");
                    }else{
                        writer.println("\t"+var + " = malloc(sizeof(Trc" + "*".repeat(count - 1) + "));");
                    }
                    int n = counter.getCounter() - 1;
                    //int n = count - 1;
                    int j = 1;
                    while (n > 0) {
                        writer.println("\t" + "*".repeat(j) + var + " = malloc(sizeof(Trc" + "*".repeat(n - 1) + "));");
                        n = n - 1;
                        j = j + 1;
                    }
                    writer.print("\t" + "*".repeat(counter.getCounter()) + var + " = " + current_fresh);
                    writer.close();
                } catch (IOException ie) {
                    System.out.println("An error occurred.");
                    ie.printStackTrace();
                }
            }else {
                try {
                    PrintWriter writer = new PrintWriter(new FileWriter(filename, true));

                    writer.println("\tint " + "*".repeat(count) + " " + var + " = malloc(sizeof(int" + "*".repeat(count - 1) + "));");

                    /*else{
                        writer.println("\t"+var + " = malloc(sizeof(int" + "*".repeat(count - 1) + "));");
                    }*/
                    int _n = counter.getCounter() - 1;
                    int n = count - 1;
                    int j = 1;
                    while (_n > 0) {
                        writer.println("\t" + "*".repeat(j) + var + " = malloc(sizeof(int" + "*".repeat(n - 1) + "));");
                        _n--;
                        n--;
                        j = j + 1;
                    }
                    writer.print("\t" + "*".repeat(counter.getCounter()) + var + " = " + current_fresh);
                    writer.close();
                } catch (IOException ie) {
                    System.out.println("An error occurred.");
                    ie.printStackTrace();
                }
            }
            // reinitialisé check_box
            check_box = false;

        }//let mut x = box(trc..)

        else if(type){
            try {
                PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
                writer.println("\tTrc " + "*".repeat(count) + " " + var + " = malloc(sizeof(Trc" + "*".repeat(count - 1) + "));");
                //writer.println(var + " = malloc(sizeof(Trc" + "*".repeat(count - 1) + "));");

                int n = counter.getCounter() - 1;
                //int n = count - 1;
                int j = 1;
                while (n > 0) {
                    writer.println("\t" + "*".repeat(j) + var + " = malloc(sizeof(Trc" + "*".repeat(n - 1) + "));");
                    n = n - 1;
                    j = j + 1;
                }
                writer.print("\t" + "*".repeat(counter.getCounter()) + var + " = " + current_fresh);
                writer.close();
            } catch (IOException ie) {
                System.out.println("An error occurred.");
                ie.printStackTrace();
            }
        }
        /**
         *let mut x = box(0);
         * let mut x = box(box(0));
         **/
        else{
            try {
                PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
                writer.println("\tint " + "*".repeat(counter.getCounter()) + " " + var + " = (int " + "*".repeat(counter.getCounter()) + ") malloc(sizeof(int" + "*".repeat(counter.getCounter() - 1) + "));");
                int n = counter.getCounter()-1;
                int j=1;
                while(n>0){
                    writer.println("\t"+"*".repeat(j)+var+" = (int "+"*".repeat(n)+") malloc(sizeof(int"+"*".repeat(n-1)+"));");
                    n = n-1;
                    j=j+1;
                }
                writer.print("\t"+"*".repeat(counter.getCounter())+var+" = "+counter.value());
                writer.close();
            } catch (IOException ie) {
                System.out.println("An error occurred.");
                ie.printStackTrace();
            }
        }
    }

    public void drop(HashMap<String,Type> map){
        for(Map.Entry mapentry: map.entrySet()){
            String name = (String) mapentry.getKey();
            Type type = (Type) mapentry.getValue();
            String free = type.free(name,0,"", 0);
            /**
             * put free into the file.c
             */
            try {
                PrintWriter writer = new PrintWriter(new FileWriter(filename, true));
                writer.println("\t"+free);
                writer.close();
            } catch (IOException ie) {
                System.out.println("An error occurred.");
                ie.printStackTrace();
            }
        }
    }
}

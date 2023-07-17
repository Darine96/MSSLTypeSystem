package fr.univorleans.mssl.DynamicParser;

import com.google.common.base.CharMatcher;
//import com.sun.java.swing.action.NewAction;
import fr.univorleans.mssl.DynamicSyntax.*;
import fr.univorleans.mssl.DynamicSyntax.Syntax.*;
import fr.univorleans.mssl.DynamicSyntax.Syntax.Expression.*;
import fr.univorleans.mssl.DynamicSyntax.Syntax.Expression.Clone;
import fr.univorleans.mssl.SOS.Pair;
import fr.univorleans.mssl.Parser.msslBaseVisitor;
import fr.univorleans.mssl.Parser.msslParser;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyvisitorBlock extends msslBaseVisitor<Object> {

    /** Defined a block for return Parser **/
    public Syntax.Expression.Block block;
    public ArrayList<Syntax.Expression> expressions = new ArrayList<>();
    public ArrayList<Function> declarations = new ArrayList<>();
    private Lifetime globalLifetime;
    public Lifetime current_lifetime = null;

    public Boolean WhenWatch = false;
    /**
     * environment for the semantics (copy, move)
     */
    private KindVariable kindVariable = new KindVariable(new HashMap<>());

    HashMap<String, ArrayList<Boolean>> kindTupleIndex = new HashMap<>();
    public MyvisitorBlock(Lifetime globalLifetime) {
        this.globalLifetime = globalLifetime.freshWithin();
    }


    /**
     * impelement methodes
     */
    @Override public Pair< ArrayList<Function>, Syntax.Expression.Block> visitProg(msslParser.ProgContext ctx) {
        /**
         * récuperer tous les fonctions déclarées
         */
        for (int i = 0; i< ctx.declaration().size(); i++){
            current_lifetime = new Lifetime();
            Function function= (Function) this.visit(ctx.declaration(i));
            declarations.add(function);
        }
        /**
         * the main block
         */
        //set the current lifetime

        current_lifetime = globalLifetime;
        Syntax.Expression expression = (Syntax.Expression) this.visit(ctx.block());
        block = (Syntax.Expression.Block) expression;
        block.setLifetime(globalLifetime);
        return new Pair<>(declarations,block);
    }
    /** Declaration functions */
    @Override public Function visitDeclaration_function(msslParser.Declaration_functionContext ctx) {
        String nameFunc = ctx.IDENTIFIER().getText();
        ArrayList<Pair<String, Signature>> params = new ArrayList<>();
        if ((ctx.params()) !=null){
            params = (ArrayList<Pair<String, Signature>>) this.visit(ctx.params());
        }
        //get the return signature
        Signature ret = (Signature) this.visit(ctx.signature());
        //get the function body
        Syntax.Expression.Block body = (Syntax.Expression.Block) this.visit(ctx.block());

        String[] signals = new String[0];
        if(ctx.signals()!=null){
            String s1 = ctx.signals().getText();

            signals = s1.replace(";","").split(",");
        }
        //clear the environnment
        kindVariable.clear();
        Object value = this.visit(ctx.value());
        int k = Integer.parseInt(value.toString());
        Function f = new Function(nameFunc, params.toArray(new Pair[params.size()]), signals,ret, body, k);
        if(WhenWatch){ f.containsWhenWatch=true; WhenWatch=false;  }
        return f; }

    @Override public ArrayList<String> visitSignalsFunc(msslParser.SignalsFuncContext ctx) {
        ArrayList<String> signals = new ArrayList<>();
        for (int i =0; i<ctx.IDENTIFIER().size(); i++){
               signals.add(ctx.IDENTIFIER(i).toString());
        }

        return signals;  }


    @Override public ArrayList<Pair<String, Signature>> visitParamsFunc(msslParser.ParamsFuncContext ctx) {
        //get the parameters function
        ArrayList<Pair<String, Signature>> params = new ArrayList<>();
        for (int i =0; i<ctx.IDENTIFIER().size(); i++){
            Signature sig = (Signature) this.visit(ctx.signature(i));
            //put the semantic of each parameter
            kindVariable.put(ctx.IDENTIFIER(i).toString(), deduceKind(sig));
            // System.out.printf(" \nsignature of parameter i "+ ctx.signature(i).toString());
            params.add(new Pair<>(ctx.IDENTIFIER(i).toString(), sig));
            //System.out.printf(" \nname of parameter i "+ ctx.IDENTIFIER(i).toString());
        }

        return params; }

    @Override public Signature.Unit visitSigUnit(msslParser.SigUnitContext ctx) { return new Signature.Unit(); }
    @Override public Signature.Int visitSigInt(msslParser.SigIntContext ctx) { return new Signature.Int(); }
    @Override public Signature.Bool visitSigBool(msslParser.SigBoolContext ctx) { return new Signature.Bool(); }
    @Override public Signature.Box visitSigBox(msslParser.SigBoxContext ctx) {
        Signature sig = (Signature) this.visit(ctx.signature());
        return new Signature.Box(sig); }
    @Override public Signature.Trc visitSigTrc(msslParser.SigTrcContext ctx) {
        Signature sig = (Signature) this.visit(ctx.signature());

        return new Signature.Trc(sig); }
    @Override public Signature.Clone visitSigClone(msslParser.SigCloneContext ctx) {
        Signature sig = (Signature) this.visit(ctx.signature());
        return new Signature.Clone(sig); }

    @Override public Signature visitSigRef(msslParser.SigRefContext ctx) {
        Signature sig = (Signature) this.visit(ctx.signature());
        Boolean mut = false;
        if(ctx.MUT()!=null){ mut = true;}
        String lft = (String) this.visit(ctx.lif());
        return new Signature.Borrow(lft, mut,sig); }

    @Override public String visitLifetime(msslParser.LifetimeContext ctx) {
        String lft = ctx.IDENTIFIER().toString();
        return lft; }


    /************************************************************************************************************/
    @Override public Value visitExpVal(msslParser.ExpValContext ctx) {
        Object value = this.visit(ctx.value());
        return (Value) value;
    }

    @Override public Syntax.Expression visitExpTuple(msslParser.ExpTupleContext ctx) {
        ArrayList<Syntax.Expression> expressions = new ArrayList<>();
        for (int i = 0; i!= ctx.expr().size();++i){
            Syntax.Expression expression = (Syntax.Expression) this.visit(ctx.expr(i));
            expressions.add(expression);
        }
        Syntax.Expression[] e = expressions.toArray(new Syntax.Expression[expressions.size()]);
        Tuples.TuplesExpression tuplesExpression = new Tuples.TuplesExpression(e);
        tuplesExpression.toString();
        return new Tuples.TuplesExpression(e); }

    @Override public Syntax.Expression visitExpPrint(msslParser.ExpPrintContext ctx) {
        String deref = ctx.Mul().toString();
        int counter =  CharMatcher.is('*').countIn(deref);;
        String name = ctx.IDENTIFIER(0).toString();
       // System.out.println("the name in print is "+name);
        Lval lval = new Lval("*".repeat(counter)+name, new Path());
        return new Print(lval); }

    @Override public Syntax.Expression visitExpIndex(msslParser.ExpIndexContext ctx) {
        ArrayList<Path.Element> elements = new ArrayList<>();
        int index = Integer.parseInt(ctx.INTEGER().getText());
        elements.add(new Tuples.TuplesIndex(index));
        Path.Element[] es = elements.toArray(new Path.Element[elements.size()]);

        Syntax.Expression name = (Syntax.Expression) this.visit(ctx.expr());
        Lval lval = ((Syntax.Expression.Access) name).operand();
        Lval lval2 = new Lval(lval.name(), new Path(es));
        ///// ajouter la semantic of this element
        /**
         * true: move semantics
         * false: copy semantics
         */
        Boolean semantics = kindTupleIndex.get(lval2.name()).get(index);
        return Syntax.Expression.Access.construct(lval2, semantics);
    }

     @Override public Syntax.Expression visitExpDeref(msslParser.ExpDerefContext ctx) {
        Lval lval;
        Syntax.Expression.Access access;
        //if(ctx.Mul()!= null){
            /** (*exp)
             * the number of star e.g: *x or **x or ***x, etc
             * Access
             */
            access = (Syntax.Expression.Access) this.visit(ctx.expr());
            lval = access.operand();
            Lval lval1 = new Lval(lval.name(), append(lval.path(), Path.DEREF_ELEMENT));
            return Syntax.Expression.Access.construct(lval1, kindVariable.contains(lval1.name()));

       // }
       /* else {
            // identifier
            String identifier = ctx.getText();
            // creat a path to the lval
            lval = createLval(identifier);
            return Syntax.Expression.Access.construct(lval, kindVariable.contains(lval.name())); }*/
    }

    @Override public Syntax.Expression visitExpIdentifier(msslParser.ExpIdentifierContext ctx) {
        // identifier
        String identifier = ctx.getText();
        // creat a path to the lval
        Lval lval = createLval(identifier);
        return Syntax.Expression.Access.construct(lval, kindVariable.contains(lval.name()));
    }
    @Override public Syntax.Expression.Cooperate visitExpCooperate(msslParser.ExpCooperateContext ctx) {
        this.visit(ctx.Cooperate());
        return new Syntax.Expression.Cooperate(); }

    @Override public Syntax.Expression.Borrow visitExpSharedRef(msslParser.ExpSharedRefContext ctx) {
        Syntax.Expression.Access access = (Syntax.Expression.Access) this.visit(ctx.expr());
        /**
         * &w: &x or &*x
         * case of dereference e.g &*x
         */
        Syntax.Expression.Borrow b = new Syntax.Expression.Borrow(access.operand(), false);
        return new Syntax.Expression.Borrow(access.operand(), false);
    }

    @Override public Syntax.Expression.Borrow visitExpMutableRef(msslParser.ExpMutableRefContext ctx) {
        Syntax.Expression.Access access = (Syntax.Expression.Access) this.visit(ctx.expr());
        /**
         * &w: &mut x or &mut *x
         * case of dereference e.g &mut *x
         */
        return new Syntax.Expression.Borrow(access.operand(), true);
    }

    @Override public Expression.Conditional visitExpConditionals(msslParser.ExpConditionalsContext ctx) {
        Expression lft = (Expression) this.visit(ctx.expr(0));
        Expression rht = (Expression) this.visit(ctx.expr(1));
        String operator = ctx.OPERATOR().getText();
        return new Conditional(lft, rht, operator); }

    @Override public Syntax.Expression.Block visitExpBlock(msslParser.ExpBlockContext ctx) {
        this.visit(ctx);
        return new Syntax.Expression.Block(); }

    @Override public Expression.IfElse visitExpIF(msslParser.ExpIFContext ctx) {
        Expression cond = (Expression) this.visit(ctx.expr());
        current_lifetime= current_lifetime.freshWithin();
        Block ifblock = (Block) this.visit(ctx.block(0));
        current_lifetime = current_lifetime.get();
        current_lifetime= current_lifetime.freshWithin();
        Block elseblock = (Block) this.visit(ctx.block(1));
        current_lifetime = current_lifetime.get();
        return new IfElse(cond,ifblock,elseblock); }

    @Override public Object visitTypInt(msslParser.TypIntContext ctx) { return visitChildren(ctx); }

    @Override public Object visitTypBoolean(msslParser.TypBooleanContext ctx) { return visitChildren(ctx); }

    @Override public Syntax.Expression.Block visitStmtBlock(msslParser.StmtBlockContext ctx) {
        /**
         * create a fresh lifetime into global lifetime
         */
        Syntax.Expression expression = (Syntax.Expression) this.visit(ctx.instructions());
        return (Syntax.Expression.Block) expression;
    }
    @Override public Syntax.Expression.Block visitInstSequence(msslParser.InstSequenceContext ctx) {
        /**
         * List of instructions
         */
        List<msslParser.InstructionContext> instructions = ctx.instruction();
        ArrayList<Syntax.Expression> expressions = new ArrayList<>();
        Syntax.Expression expression = null;
        for (msslParser.InstructionContext instruction: instructions){

            expression = (Syntax.Expression) this.visit(instruction);

            /**
             * add the resutlt into array expression
             */
            expressions.add(expression);
        }
        return new Syntax.Expression.Block(current_lifetime, expressions.toArray(new Syntax.Expression[expressions.size()])); }

    @Override public Syntax.Expression.Declaration visitInstLet(msslParser.InstLetContext ctx) {
        Syntax.Expression.Declaration declaration = (Syntax.Expression.Declaration) this.visit(ctx.declVar());
        /**
         * deduce the semantics of a given variable
         * let mut x = 1/ &
         * let mut x = box/trc/&mut
         * let mut x = y/*y;
         */
        Syntax.Expression e = declaration.getInitialiser();
        String var = declaration.getVariable();
        Boolean btuple = false; // copy semantic by default
        if(e instanceof Value.Integer || e instanceof Value.Boolean || e instanceof Syntax.Expression.Borrow){
            // copy semantics
            if(e instanceof Syntax.Expression.Borrow && ((Syntax.Expression.Borrow) e).isMutable()==true){
                // mutable borrow
                kindVariable.put(var,true);
            }else {
                kindVariable.put(var,false);
            }
        }
        else if (e instanceof Syntax.Expression.Box || e instanceof Syntax.Expression.Trc || e instanceof Clone){
            // move semantics: Box, trc, clone
            kindVariable.put(var,true);}
        else if(e instanceof Tuples.TuplesExpression){
            Tuples.TuplesExpression e1 = (Tuples.TuplesExpression) e;
            ArrayList<Boolean> indexkind = new ArrayList<>();
            for (Syntax.Expression expression: e1.getExpressions()){
                if(expression instanceof Syntax.Expression.Box || expression instanceof Syntax.Expression.Trc || (expression instanceof Syntax.Expression.Borrow && ((Syntax.Expression.Borrow) expression).isMutable()==true)){
                    btuple = true;
                    indexkind.add(true);
                }else{
                    indexkind.add(false);
                }
            }
            kindVariable.put(var,btuple);
            kindTupleIndex.put(var,indexkind);
        }
        else {
            // let mut x = y...
            Access access = (Access) e;
            Lval lval = access.operand();
            kindVariable.put(var, kindVariable.contains(lval.name()));
        }

        return declaration; }

    @Override public Syntax.Expression.Assignment visitInstAssignment(msslParser.InstAssignmentContext ctx) {
        //left operand
        Syntax.Expression.Access access = (Syntax.Expression.Access) this.visit(ctx.expr(0));

        //right operand
        Syntax.Expression rightExpression = (Syntax.Expression) this.visit(ctx.expr(1));
        Syntax.Expression.Assignment s = new Syntax.Expression.Assignment(access.operand(), rightExpression);
        return new Syntax.Expression.Assignment(access.operand(), rightExpression);
    }
    @Override public Syntax.Expression visitInstExpr(msslParser.InstExprContext ctx) {
        Syntax.Expression expression = (Syntax.Expression) this.visit(ctx.expr());
        return expression; }
    @Override public Syntax.Expression.Box visitExpBox(msslParser.ExpBoxContext ctx) {
        Syntax.Expression expression = (Syntax.Expression) this.visit(ctx.expr());
        return new Syntax.Expression.Box(expression); }
    @Override public Syntax.Expression.Trc visitExpTrc(msslParser.ExpTrcContext ctx) {
        Syntax.Expression expression = (Syntax.Expression) this.visit(ctx.expr());
        return new Syntax.Expression.Trc(expression); }
    /**
     * Invoke Function: spawn(f(v,v,v..));
     */
    @Override public InvokeFunction visitExpInvoke(msslParser.ExpInvokeContext ctx) {

        String name = ctx.IDENTIFIER().getText();
        ArrayList<Syntax.Expression> arguments = new ArrayList<>();
        for (int i=0; i<ctx.expr().size(); i++){
            Syntax.Expression expression = (Syntax.Expression) this.visit(ctx.expr(i));
            if(expression instanceof Syntax.Expression.Variable){
                arguments.add(createLval(((Syntax.Expression.Variable) expression).getVariable()));
            }
            else {
                arguments.add(expression);
            }
        }
        ArrayList<String> signals = new ArrayList<>();
        if(ctx.signals()!=null){
            signals = (ArrayList<String>) this.visit(ctx.signals());
        }
        Syntax.Expression[] expressions = arguments.toArray(new Syntax.Expression[arguments.size()]);
        return new InvokeFunction(name, expressions,signals.toArray(new String[signals.size()]), true); }

    @Override public InvokeFunction visitExpInvokeOutSpawn(msslParser.ExpInvokeOutSpawnContext ctx) {
        String name = ctx.IDENTIFIER().getText();
        ArrayList<Syntax.Expression> arguments = new ArrayList<>();
        for (int i=0; i<ctx.expr().size(); i++){
            Syntax.Expression expression = (Syntax.Expression) this.visit(ctx.expr(i));
            if(expression instanceof Syntax.Expression.Variable){
                arguments.add(createLval(((Syntax.Expression.Variable) expression).getVariable()));
            }
            else {
                arguments.add(expression);
            }
        }
        ArrayList<String> signals = new ArrayList<>();
        if(ctx.signals()!=null){
            signals = (ArrayList<String>) this.visit(ctx.signals());
        }
        Syntax.Expression[] expressions = arguments.toArray(new Syntax.Expression[arguments.size()]);
        return new InvokeFunction(name, expressions,signals.toArray(new String[signals.size()]), false); }



    /**
     * x =trc(0); y = x.clone; or x = box(trc(x)); y=*x.clone;
     * @param ctx the parse tree
     * @return
     */
    @Override public Clone visitExpClone(msslParser.ExpCloneContext ctx) {
          if(ctx.IDENTIFIER()!=null){
            // identifier
            String identifier = String.valueOf(ctx.IDENTIFIER());
            // creat a path to the lval
            Lval lval = createLval(identifier);
            return new Clone(lval);
        }else {
            Syntax.Expression expression = (Expression) this.visit(ctx.expr());
            if (expression instanceof Access) {
                Lval lval = ((Access) expression).operand();
                if (ctx.Mul() != null) {
                    Lval lval1 = new Lval(lval.name(), append(lval.path(), Path.DEREF_ELEMENT));
                    return new Clone(lval1);
                }
                return new Clone(lval);
            }
        }
        return null; }

    @Override public Syntax.Expression.Block visitInstBlock(msslParser.InstBlockContext ctx) {
        //create a fresh lifetime into the current lifetime
        current_lifetime= current_lifetime.freshWithin();
        Syntax.Expression.Block block = (Syntax.Expression.Block) this.visit(ctx.block());
        current_lifetime = current_lifetime.get();
        //  System.out.printf("\ncurrent lifetime be after after"+current_lifetime.get());
        return block; }

    @Override public Syntax.Expression.Sig visitInstSig(msslParser.InstSigContext ctx) {
        String name = ctx.IDENTIFIER().getText();
        //System.out.println(name);
        return new Sig(name); }

    @Override public Syntax.Expression.Emit visitInstEmit(msslParser.InstEmitContext ctx) {
        String name = ctx.IDENTIFIER().getText();
        return new Emit(name); }

    @Override public Syntax.Expression.When visitInstWhen(msslParser.InstWhenContext ctx) {
        String name = ctx.IDENTIFIER().getText();
        current_lifetime= current_lifetime.freshWithin();
        Syntax.Expression expression = (Syntax.Expression) this.visit(ctx.block());
        current_lifetime = current_lifetime.get();
       // System.out.println(expression.toString());
        WhenWatch = true;
        return new When(name,expression); }

    @Override public Syntax.Expression.Watch visitInstWatch(msslParser.InstWatchContext ctx) {
        String name = ctx.IDENTIFIER().getText();
        current_lifetime= current_lifetime.freshWithin();
        Syntax.Expression expression = (Syntax.Expression) this.visit(ctx.block());
        current_lifetime = current_lifetime.get();
        WhenWatch = true;
        return new Watch(name,expression); }


    @Override public Syntax.Expression.Declaration visitDeclVar(msslParser.DeclVarContext ctx) {
        /**
         * let mut x = e;
         * récuperer le nom du variable
         */
        String name = ctx.IDENTIFIER().toString();

        /**
         * ajouter la variable dans un environnement:
         * le context du current Parser
         */
        //récuperer l'expression
        Syntax.Expression expression = (Syntax.Expression) this.visit(ctx.expr());
        return new Syntax.Expression.Declaration(name, expression); }
    @Override public Value.Integer visitInt(msslParser.IntContext ctx) {
        return new Value.Integer(Integer.parseInt(ctx.getText().toString()));
    }

    @Override public Value.Boolean visitBOOL(msslParser.BOOLContext ctx) {
        return new Value.Boolean(Boolean.parseBoolean(ctx.getText().toString()));
    }
    /********** Return a block Expression ********/
    /**
     * create an lval for a given variable name
     */
    public Lval createLval(String identifier){

        /**
         * verifier si la variable traversée est un déréference :e.g. *x
         */
        /***
         * compute the path of this lval x or *x
         */
        Path path = new Path();

        /**
         * now, we can creat an object Lval as follows
         */
        Lval lval = new Lval(identifier, path);
        return lval;
    }

    private static Path append(Path lhs, Path.Element rhs) {
        final int n = lhs.size();
        Path.Element[] es = new Path.Element[n + 1];
        for (int i = 0; i != n; ++i) {
            es[i] = lhs.get(i);
            //System.out.printf("\n append "+ rhs.toString());
        }
        es[n] = rhs;
        return new Path(es);
    }

    private Boolean deduceKind(Signature signature){
        if(signature instanceof Signature.Box || signature instanceof Signature.Clone || signature instanceof Signature.Trc){
          return true;

        }else if(signature instanceof Signature.Borrow){
            Signature.Borrow sig = (Signature.Borrow) signature;
            if(sig.isMutable()){
                return true;
            }
        }
        return false;
    }

}

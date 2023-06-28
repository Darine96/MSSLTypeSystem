package fr.univorleans.mssl.SOS;


import fr.univorleans.mssl.DynamicSyntax.*;
import fr.univorleans.mssl.DynamicSyntax.Syntax.Expression;
import fr.univorleans.mssl.DynamicSyntax.Syntax.Expression.*;
import fr.univorleans.mssl.SOS.StoreProgram.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static fr.univorleans.mssl.DynamicSyntax.Value.Unit;

/**
 * Encodes the core operational semantics of MSSL. That is, the
 * individual reduction rules without committing to small-step or big-step.
 */
public class OperationalSemantics extends ReductionRule<State, Expression, OperationalSemantics.Extension> {

    private static final boolean DEBUG = false;
    private Map<String, Function> functions = new HashMap<>();
    Lifetime globalLifetime;

    public OperationalSemantics(Lifetime lifetime,Map<String, Function> functions, Extension... extensions) {
        super(extensions);
        this.globalLifetime = lifetime;
        this.functions = functions;
        // Bind self in extensions
        for(Extension e : extensions) {
            e.self = this;
        }
    }
    public OperationalSemantics(Lifetime lifetime, Extension... extensions) {
        super(extensions);
        this.globalLifetime = lifetime;
        this.functions = functions;
        // Bind self in extensions
        for(Extension e : extensions) {
            e.self = this;
        }
    }


    /**
     * Execute the outer block
     * @param
     * @param
     * @param expression
     * @return
     */
    public final Expression execute(Lifetime l, Expression expression) {
       // Execute block in outermost lifetime "*" that have a global lifetime)
        /**
         * selon les règle de reduction un état est composé de S et expression e
         */
        Pair<State, Expression> state = new Pair<>(StoreProgram.EMPTY_STATE, expression);



        /**
         * R-BlockA et R-Block B
         * Execute continually until all reductions complete
         */

        Expression e = null;
        do {
         //   System.out.printf("expression "+ expression.toString());
            state = apply(state.first(), l, state.second());
            e = state.second();
        }while (e != null && !(e instanceof Value));
        /**
         * R-BlockB apply drop, then after the execution of the
         * outer block we have all values in the heap are removed
         */
        System.out.printf("\n Are all values dropped into the heap? : "+state.first().isHeapEmpty()+"\n");
        if(!state.first().isHeapEmpty()) {
            throw new RuntimeException("Memory leak? : " + state.first());
        }
        return e;
    }

    @Override
    public Pair<State, Expression> apply(State S, Lifetime l, Expression expression) {
        Pair<State,Expression> state = super.apply(S,l,expression);
        return state;
    }

    /** Reduction Rules */

    /**
     * Rule R-Copy.
     */
    protected Pair<State, Expression> reduceCopy(State state,  Lifetime lifetime, Lval w) {
        /** Read contents of cell at given location */
        Value value = state.read(w, lifetime);
        return new Pair<>(state, value);
    }

    /**
     * Rule R-Move.
     */
    protected Pair<State, Expression> reduceMove(State S1,  Lifetime lifetime, Lval w) {
        /** Read contents of slot at given location for a given lifetime of the enclosing block*/
       // System.out.printf("\n R-copy tuplesIndex "+w.name()+"\n");
        Value value = S1.read(w, lifetime);
        /** Apply destructive update with the value is null */
        State S2 = S1.write(w, null, lifetime);
        /** Return value read */
        return new Pair<>(S2, value);
    }
    /**
     * Rule R-Declare.
     * stack frame contient (x, lx): c'est comme un block et une lifetime: parent, lifetime and locations
     * store contient (lx, value and lifetime)
     */
    protected Pair<State, Expression> reducedDeclare(State S1, Lifetime l, String x, Value v) {
        /** Allocate new location <v>^l **/
       //System.out.printf("\n tuplesValue "+v.toString()+"\n");
        Pair<State, Value.Reference> pl = S1.allocate(l, v);
        State S2 = pl.first();
        Value.Reference lx = pl.second();
        /** Bind variable to location
         * and bind variable to the lifetime
        */
        State S3 = S2.bind(x, lx, l);

        return new Pair<>(S3, Unit);
    }

    /**
     * Rule R-Assign.
     */
    protected Pair<State, Expression> reduceAssignment(State S1, Lifetime l, Lval w, Value v2) {
        /** Extract value being overwritten for a given lifetime*/
        Value v1 = S1.read(w, l);
        /** update the value **/
        State S2 = S1.write(w, v2, l);
        /** Drop overwritten value (and any owned boxes or trc) */
        State S3 = S2.drop(v1);
        return new Pair<>(S3, Unit);
    }



    /**
     * Rule R-BlockB.
     */
    protected Pair<State, Expression> reduceBlock(State S1, Lifetime l, Value v, Lifetime m) {
        /** drop all values (locations) that have the lifetime m */
        State S2 = S1.drop(m,v);

        return new Pair<>(S2, v);
    }

    /**
     * Rule R-Seq
     */
    protected Pair<State, Expression> reduceSeq(State state, Lifetime lifetime, Value v, Expression[] expressions, Lifetime m) {
        /** Drop value (and any owned boxes or trc) */
        State S2 = state.drop(v);

        return new Pair<>(S2, new Block(m, expressions));
    }

    /**
     * Rule R-SeqTerm for cooperation
     */

    /**
     * Rule R-Borrow.
     */
    protected Pair<State, Expression> reduceBorrow(State State, Lifetime l, Lval lval) {
         /**
         * test avec lifetime
         */
       /** Locate operand */
        Value.Reference lx = lval.locate(State, l);
        //System.out.printf("ref "+lx);
        /**
         * toBorrowed: précise si lval est un owner or not,
         * if yes then create a reference otherwise return the same reference
         */
        return new Pair<>(State, lx.toBorrowed());
    }

    /**
     * Rule R-Box.
     */
    protected Pair<State, Expression> reduceBox(State state, Lifetime lifetime, Value value) {
        /** get the global lifetime */
        Lifetime global = lifetime.getRoot();
        /** Allocate new location with the global lifetime*/
        Pair<State, Value.Reference> pl = state.allocate(global, value);

        State S2 = pl.first();
        Value.Reference ln = pl.second();

        return new Pair<>(S2, ln);
    }

    /**
     * Rule R-Trc.
     */
    protected Pair<State, Expression> reduceTrc(State state, Lifetime lifetime, Value value) {
        /** get the global lifetime */
        Lifetime global = lifetime.getRoot();
        /** Allocate new location with the global lifetime*/
        /**
         * initialise the counter of trc to 1
         */
        Pair<State, Value.Reference> pl = state.allocate(global, value, 1);

        State S2 = pl.first();
        // System.out.printf("trc "+ S2.isHeapEmpty());
        Value.Reference ln = pl.second();
        return new Pair<>(S2, ln);
    }

    /**
     * Rule R-Clone.
     */
    protected Pair<State, Expression> reduceClone(State state, Lifetime l, Lval lval) {
        // Read the value of the lval update qith lifetime
        Value lx = state.read(lval, l);
        /**
         * verifier si la valeur obtenu est une référence TRC
         */
        Pair<State, Value.Reference> S2 = state.increment_counter(lval, l);
        /**
         * toBorrowed: précise si lval est un owner or not,
         * if yes then create a reference otherwise return the same reference
         */
        return new Pair<>(S2.first(), S2.second().toCloned());
    }


    @Override
    protected Pair<State, Expression> apply(State state, Lifetime lifetime, Assignment expression) {
            if (expression.getExpr() instanceof Value) {
                /**
                 * x = 5;
                 */
                /** Statement can be completely reduced */
                return reduceAssignment(state, lifetime, expression.getLval(), (Value) expression.getExpr());
            } else {
                /**
                 * x = 5+1;
                 */
                /** Statement not ready to be reduced yet */
                Pair<State, Expression> rhs = apply(state, lifetime, expression.getExpr());
                /** Construct reduce statement */
                expression = new Assignment(expression.getLval(), rhs.second());

                return new Pair<>(rhs.first(), expression);
            }
        }

    @Override
    protected Pair<State, Expression> apply(State state, Lifetime lifetime, Block expression) {
        final int n = expression.getExprs().length;

        if (n > 0) {
            /**
             * reduce the first expression
             */
            Pair<State, Expression> p = apply(state, expression.getLifetime(), expression.getExprs()[0]);
            Expression e = p.second();
            state = p.first();
            if (e instanceof Value) {
                /**
                 * { 5;}
                 */
                if (n == 1) {
                    /**
                     *  R-BlockB
                     */
                    return reduceBlock(state, lifetime, (Value) e, expression.getLifetime());
                }
                /**
                 * { 5; let mut x = 5;}
                 */
                else {
                    // Slice off head
                    return reduceSeq(state,lifetime,(Value) e, slice(expression,1),expression.getLifetime());
                }
            }
            /**
             * { let mut x = 5+y; y = 0;}
             */
            else {
                /** Statement hasn't completed */
                Expression[] expressions = Arrays.copyOf(expression.getExprs(), n);
                /** Replace with partially reduced statement */
                expressions[0] = e;

                return new Pair<>(state, new Block(expression.getLifetime(), expressions));
            }
        }
        else {
            /** the block is empty */
            return reduceBlock(state, lifetime, Unit, expression.getLifetime());
        }
    }

    /**
     * cooperate
     * @param state
     * @param lifetime
     * @param expression
     * @return
     */
    @Override
    protected Pair<State, Expression> apply(State state, Lifetime lifetime, Cooperate expression) {
        return new Pair<>(state, Unit);
    }

    @Override
    protected Pair<State, Expression> apply(State state, Lifetime lifetime, Box expression) {
        if (expression.getOperand() instanceof Value) {
            /** Statement can be completely reduced */
            return reduceBox(state, lifetime, (Value) expression.getOperand());
        } else {
            /** Statement not ready to be reduced yet */
            Pair<State, Expression> rhs = apply(state, lifetime, expression.getOperand());
            /** Construct reduce statement */
            expression = new Box(rhs.second());

            return new Pair<>(rhs.first(), expression);
        }
    }

    /**
     * R-Sig
     * @param state
     * @param lifetime
     * @param expression
     * @return
     */
    @Override
    protected Pair<State, Expression> apply(State state, Lifetime lifetime, Sig expression) {
        /** get the global lifetime */
        Lifetime global = lifetime.getRoot();
       /**
         * initialise the counter of signal to 1 and its value to 0 ( absent by default)
         */
        Value.Integer v = new Value.Integer(0);
        Pair<State, Value.Reference> pl = state.allocate(global, v, 1);

        State S2 = pl.first();
        Value.Reference ls = pl.second();

        /** Bind Signal to location
         * and bind signal to the lifetime
         */
        String s = expression.getVariable();
        return reducedDeclare(S2,lifetime,s,ls);
    }

    /**
     * trc(e)
     * @param state
     * @param lifetime
     * @param expression
     * @return
     */
    @Override
    protected Pair<State, Expression> apply(State state, Lifetime lifetime, Trc expression) {
        if (expression.getOperand() instanceof Value) {
            /** Statement can be completely reduced */
            return reduceTrc(state, lifetime, (Value) expression.getOperand());
        } else {
            /** Statement not ready to be reduced yet */
            Pair<State, Expression> rhs = apply(state, lifetime, expression.getOperand());
            /** Construct reduce statement */
            expression = new Trc(rhs.second());

            return new Pair<>(rhs.first(), expression);
        }
    }

    /**
     * R-Emit
     * @param state
     * @param lifetime
     * @param expression
     * @return
     */
    @Override
    protected Pair<State, Expression> apply(State state, Lifetime lifetime, Emit expression) {
        /**
         * (1) get the value of signal
         */
        // since reduce expresion takes an lval , so we need to create an lval
        Lval w = new Lval(expression.getVariable(), new Path());
       /* Pair<State, Expression> p = reduceCopy(state, lifetime, w);*/
        /** read the value of signal **/
        /** determine if w existe **/
        Value v1 = state.read(w, lifetime);
        /**
         * upates its value in the store (i.e. make the signal present)
         */
        /*********************************************************/
        /** get the global lifetime */
        Lifetime global = lifetime.getRoot();
        /**
         * initialise the counter of signal to 1 and its value to 0 ( absent by default)
         */
        Value.Integer v = new Value.Integer(1);
        Pair<State, Value.Reference> pl = state.allocate(global, v, 1);

        State S2 = pl.first();
        Value.Reference ls = pl.second();
        /*********************************************************/
        State S = S2.write(w, ls, lifetime);

        /** drop v ***/
        State S3 = S.drop(v1);
        Path.Element[] es = new Path.Element[1];
        es[0]=Path.DEREF_ELEMENT;
        Lval lvam = new Lval(expression.getVariable(), new Path(es));
        Value v2 = S3.read(lvam, lifetime);
        //System.out.println("\n\n v2"+v2.toString()+"\n");
        return new Pair<>(S3, Unit);
    }

    @Override
    protected Pair<State, Expression> apply(State state, Lifetime lifetime, When expression) {
        /** read the value of s **/
        String s = expression.getVariable();
        Path.Element[] es = new Path.Element[1];
        es[0]=Path.DEREF_ELEMENT;
        Lval w = new Lval(s, new Path(es));
        //System.out.println("\n\n"+expression.getOperand().toString()+"\n\n");
        /** determine if w existe **/
        Value v = state.read(w, lifetime);
       /** R-whenFalse **/
        if(Integer.valueOf(v.toString()) == 0){
            return new Pair<>(state, expression);
        }
       /** R-whenTrue **/
       else{
           Pair<State,Expression> S = apply(state,lifetime,expression.getOperand());
           return new Pair<>(S.first(), S.second());
        }

    }

    /**
     * R-Conditional
     * (1) v1 == v2
     * (2) v1 == e1
     * (3) e1 == v1
     * @param S
     * @param lifetime
     * @param expression
     * @return
     */
    @Override
    protected Pair<State, Expression> apply(State S, Lifetime lifetime, Conditional expression) {
        Expression lft = expression.getLftoperand();
        Expression rht = expression.getLftoperand();
        String operator = expression.getOperator();
        if(lft instanceof Value && rht instanceof Value){
                Boolean compare = compare((Value) lft,(Value) rht,operator);
            return new Pair<>(S, new Value.Boolean(compare));
        } else if(lft instanceof Value){
            Pair<State, Expression> result = apply(S, lifetime, rht);
            return new Pair<>(result.first(), new Conditional(lft, result.second(), operator));
        }else {
            Pair<State, Expression> result = apply(S, lifetime, lft);
            return new Pair<>(result.first(), new Conditional(result.second(), rht, operator));
        }
    }

    /**
     * R-Ifelse
     * @param S
     * @param lifetime
     * @param expression
     * @return
     */
    @Override
    protected Pair<State, Expression> apply(State S, Lifetime lifetime, IfElse expression) {
        Expression cond = expression.getConditions();
        if(cond instanceof Value){
            if(cond instanceof Value.Boolean){
            Boolean b =  ((Value.Boolean) cond).value();
                if(b){
                    return new Pair<>(S, expression.getIfblock());
                }else {
                    return new Pair<>(S, expression.getElseblock());
                }
            }else {
                //exception
            }
        }
        else {
            Pair<State, Expression> r1 = apply(S, lifetime, expression.getConditions());
            return new Pair<>(r1.first(), new IfElse(r1.second(),expression.getIfblock(),expression.getElseblock()));
        }
        return  new Pair<>(S, expression);
    }

    /**
     * R-Watch
     * @param state
     * @param lifetime
     * @param expression
     * @return
     */
    @Override
    protected Pair<State, Expression> apply(State state, Lifetime lifetime, Watch expression) {
        /** determine if the signal given in parameter existe **/
        Pair<State,Expression> S = apply(state,lifetime,expression.getOperand());
        return new Pair<>(S.first(), S.second());
    }


    /**
     * R-Spawn
     * @param state
     * @param lifetime
     * @param expression
     * @return
     */
    @Override
    protected Pair<State, Expression> apply(State state, Lifetime lifetime, InvokeFunction expression) {
        final Expression[] arguments = expression.getArguments();
        final String[] signals = expression.getSignals();

        int i = ExprNonValue(arguments);
        /**
         * all arguments are values
         * All operands fully reduced, so perform invocation
         */
        if(i == -1){
            return invoke(state, lifetime, expression.getName(), expression.getArguments(),signals);
        }
        else {
            Expression ith = arguments[i];
            /** Reduce the expression */
            Pair<State, Expression> reduce = this.apply(state, lifetime, ith);

            Expression[] nelements = Arrays.copyOf(arguments, arguments.length);
            nelements[i] = reduce.second();
            return new Pair<>(reduce.first(), new InvokeFunction(expression.getName(), nelements,signals));
        }
    }

    /**
     * Clone
     * @param state
     * @param lifetime
     * @param expression
     * @return
     */
    @Override
    protected Pair<State, Expression> apply(State state, Lifetime lifetime, Clone expression) {
        return reduceClone(state, lifetime, expression.getOperand());
    }
    /**
     * Let x = e or Let x = v
     */
    @Override
    protected Pair<State, Expression> apply(State state, Lifetime lifetime, Declaration expression) {
        /** check if the Declaration instruction is completely reduced: let x = v; */
        if(expression.getInitialiser() instanceof Value){
            Value v = (Value) expression.getInitialiser();
            return reducedDeclare(state, lifetime, expression.getVariable(), v);
        }
        else {
            /** if the Declaration instruction is not completely reduced; let x = e; */
            Pair<State, Expression> pair = apply(state, lifetime, expression.getInitialiser());

            expression = new Declaration(expression.getVariable(), pair.second());
            return new Pair<>(pair.first(), expression);
        }


    }

    @Override
    protected Pair<State, Expression> apply(State state, Lifetime lifetime, Borrow expression) {
        return reduceBorrow(state, lifetime, expression.getOperand());
    }

    @Override
    protected Pair<State, Expression> apply(State state, Lifetime lifetime, Access expression) {
        if(expression.copy()) {
            return reduceCopy(state, lifetime, expression.operand());
        } else {
            return reduceMove(state, lifetime, expression.operand());
        }

    }


    /**
     * R-Tuples (Expression or Values)
     * e.g (box(1), trc(0)) ou (1,0)
     * @param state
     * @param lifetime
     * @param expression
     * @return
     */
    @Override
    protected Pair<State, Expression> apply(State state, Lifetime lifetime, Tuples.TuplesExpression expression) {
        final Expression[] expressions = expression.getExpressions();
        /**
         * determine if all expressions in this tuple are reduced
         */
        int i =  ExprNonValue(expressions);
        /**
         * i < 0 then all is totaly reduced. So the form of the tuple is TuplesValue
         */

        if(i < 0) {
            // All operands fully reduced
            return new Pair<>(state, new Tuples.TuplesValue(expressions));
        } else {
            Expression ith = expressions[i];
            // lhs is fully reduced
            Pair<State, Expression> p = apply(state, lifetime, ith);
            //
            Expression[] nelements = Arrays.copyOf(expressions, expressions.length);
            nelements[i] = p.second();
            return new Pair<>(p.first(), new Tuples.TuplesExpression(nelements));
        }
    }

    @Override
    protected Pair<State, Expression> apply(State state, Lifetime lifetime, Unit value) {
        return null;
    }

    @Override
    protected Pair<State, Expression> apply(State state, Lifetime lifetime, Value.Integer value) {
        return null;
    }

    @Override
    protected Pair<State, Expression> apply(State state, Lifetime lifetime, Value.Reference value) {
        return null;
    }

    @Override
    protected Pair<State, Expression> apply(State state, Lifetime lifetime, Print expression) {
        return new Pair<>(state, Unit);
    }

    /**
     * Return a new block with only a given number of its statements.
     *
     * @param b
     * @param n
     * @return
     */
    private static Expression[] slice(Block b, int n) {
        Expression[] expressions = new Expression[b.getExprs().length- n];
        for (int i = n; i < b.getExprs().length; ++i) {
            expressions[i - n] = b.get(i);
        }
        return expressions;
    }
    public abstract static class Extension implements ReductionRule.Extension<State, Expression> {
        protected OperationalSemantics self;
    }


    /**
     * invoke function
     */
    private Pair<State, Expression> invoke(State state, Lifetime lifetime, String name, Expression[] arguments, String[] signals) {
        /**
         * recuperer la function
         */
       Function declaration = functions.get(name);
        /**  Extract parameters */
        Pair<String, Signature>[] params = declaration.getParams();
        /**
         * instantiated lifetime through global lifetime ( fresh lifetime into the global lifetime
         */
        Lifetime global = new Lifetime();
        Block body =  instantiate(global, declaration.getBody());

        /**
         * add a new stack frame into the State specific for the new thread
         */
        state = state.push(body.getLifetime());

        /**
         * allouer tous les parameters avec la durée de vie calculé
         */
        for (int i = 0; i != arguments.length; ++i) {
            Value ith = (Value) arguments[i];
            Pair<State, Value.Reference> p = state.allocate(body.getLifetime(), ith);
            state = p.first().bind(params[i].first(), p.second(), body.getLifetime());
        }

        /**
         * allocate signal and incremente the content
         */
        for(int i = 0; i<signals.length; ++i){
            // Read the value of the lval update qith lifetime
            Lval w = new Lval(signals[i],new Path());
            //Value lx = state.read(w, lifetime);
            /**
             * increment the counter
             */
            Pair<State, Value.Reference> S2 = state.increment_counter(w, lifetime);
            Pair<State, Value.Reference> p = state.allocate(body.getLifetime(), S2.second());
            state = p.first().bind(w.name(), p.second().toCloned(), body.getLifetime());
        }


        // Done
        return new Pair<>(state, body);
    }

    /**
     * Instantiate a given block with fresh lifetimes such that they respect the
     * existing (static) nesting and are all within the given lifetime.
     * e.g. f1(x) =W {  {}^m }^l : the rule is l>= m;
     * @param lifetime
     * @param block
     * @return
     */
    private Block instantiate(Lifetime lifetime, Block block) {
        Lifetime m = lifetime.freshWithin();
        Expression[] expressions = new Expression[block.length()];
        for (int i = 0; i != expressions.length; ++i) {
            Expression ith = block.get(i);
            if (ith instanceof Block) {
                expressions[i] = instantiate(m, (Block) ith);
            } else {
                expressions[i] = ith;
            }
        }
        return new Block(m, expressions);
    }


    /**
     * Identify fist index in array which is not a value.
     *
     * @param items
     * @return
     */
    public static int ExprNonValue(Expression[] items) {
        for(int i=0;i!=items.length;++i) {
            if(!(items[i] instanceof Value)) {
                return i;
            }
        }
        return -1;
    }

    /*************** COMPZRE *************************/
    public static Boolean compare(Value v1, Value v2, String op){
        return v1==v2;
    }

}



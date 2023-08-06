package fr.univorleans.mssl.TypeSystem;

import fr.univorleans.mssl.DynamicSyntax.*;
import fr.univorleans.mssl.Exception.ExceptionsMSG;
import fr.univorleans.mssl.MSSL.Main;
import fr.univorleans.mssl.SOS.Pair;
import fr.univorleans.mssl.SOS.ReductionRule;
import fr.univorleans.mssl.SOS.StoreProgram;

import java.util.*;

public class BorrowChecker extends ReductionRule<Environment, Type, BorrowChecker.Extension> {

    /**
     * Enable or disable debugging output.
     */
    private static final boolean DEBUG = false;

    /**
     * list of declaration functions
     */
    public Map<String, Function> functions = new HashMap<>();

    /**
     * indice de fresh variable
     */
    private static int freshVar = 0;
    /**
     * Constant to reduce unnecessary environment instances.
     */
    public final static Environment EMPTY_ENVIRONMENT = new Environment(){};


    /**
     * Indicates whether or not to apply copy inference.
     */
    protected final boolean copyInference;
    protected final String expression;

    public static Environment global = EMPTY_ENVIRONMENT;
    public int NBthreads=0;

    public static Environment getGlobal() {
        return global;
    }

    public int getNBthreads() {
        return NBthreads;
    }

    public BorrowChecker(boolean copyInference, String expression, List<Function> decls, Extension... extensions) {
        super(extensions);
        this.copyInference = copyInference;
        this.expression = expression;
        for (int i = 0; i != decls.size(); ++i) {
            Function ith = decls.get(i);
            functions.put(ith.getName(), ith);
        }
        // Bind self in extensions
        for (Extension e : extensions) {
            e.self = this;
        }
    }

/*********************************************************************************************************************/
/*********************************************************************************************************************/

    /**
     * engine of apply program body
     * @return
     */
    @Override
    public Pair<Environment, Type> apply(Environment R1, Lifetime l, Syntax.Expression expression, int k) {
        Pair<Environment, Type> p = super.apply(R1, l, expression, k);
        if (DEBUG) {
            Environment R2 = p.first();
            Type type = p.second();
            System.err.println(type.toString());
        }
        return p;
    }


    /**
     * T-Const
     * @param state
     * @param lifetime
     * @param value
     * @return
     */


    @Override
    protected Pair<Environment, Type> apply(Environment state, Lifetime lifetime, Value.Unit value, int k) {
        // skip
        throw new UnsupportedOperationException();
    }

    @Override
    protected Pair<Environment, Type> apply(Environment environment, Lifetime lifetime, Value.Integer value, int k) {
        return new Pair<>(environment, Type.Int);
    }

    @Override
    protected Pair<Environment, Type> apply(Environment state, Lifetime lifetime, Value.Reference value, int k) {
        // skip
        throw new UnsupportedOperationException();
    }

    /**
     * T-Copy & T-Move
     * @param gam1
     * @param lifetime
     * @param expression
     * @return
     */
    @Override
    protected Pair<Environment, Type> apply(Environment gam1, Lifetime lifetime, Syntax.Expression.Access expression, int k){
       Lval omega = expression.operand();
        /**
         * env |- omega : <type> tau </type>^m
         */
        Pair<Type, Lifetime> location = omega.typeOf(gam1);
        Type type = location.first();
        Location _l = gam1.get(omega.name());
        Type _type = _l.getType();
        /**
         * message errors
         */
        // vérifier si omega est bien typé: i.e. n'est pas mové undefiend type!
            try {
                check(!type.defined(), omega.name()+" is moved");
            } catch (ExceptionsMSG e) {
                throw new RuntimeException(e);
            }
        /**
         * decide if omega have a copy or move semantics
         */
        if(isCopy(expression, type)){
            /**
             * T-Copy
             */
            // vérifier si l'information est correcte: copy(T)
            try {
                check(!type.copyable(), "the type of this lval doesn't implement a copy semantic");
            } catch (ExceptionsMSG e) {
                throw new RuntimeException(e);
            }
            // vérifier si il est interdit en lecture :readProhibited
            try {
                check(readProhibited(gam1, omega), "lval cannot be read (e.g. is moved in part or whole)");
            } catch (ExceptionsMSG e) {
                throw new RuntimeException(e);
            }

            /**
             * verify if: let mut x = trc(0);
             *            let mut y = &x;
             *            let mut a = *y; //error
             */
            try {
                check(((_type instanceof Type.Borrow) && omega.path().size()!=0), "cannot move out of "+omega.name()+" which is behind a shared reference");
            } catch (ExceptionsMSG e) {
                throw new RuntimeException(e);
            }

            //Done
            return new Pair<>(gam1, type);
        }
        else {
            /**
             * T-Move
             */
            // vérifier si il est interdit en écriture :readProhibited
            try {
                check(writeProhibited(gam1, omega), "lval cannot be written (e.g. is moved in part or whole)");
            } catch (ExceptionsMSG e) {
                throw new RuntimeException(e);
            }
            //premise vérifier si omega is cloned
            try {
                check(( TrcMoveProhibited(gam1, omega)), "lval is cloned so it can not be moved");
            } catch (ExceptionsMSG e) {
                throw new RuntimeException(e);
            }

            /**
             * verify if: let mut x = trc(0);
             *            let mut y = *x; event if x has the type reference of cloned
             */
            try {
                check(((_type instanceof Type.Trc || _type instanceof Type.Clone || _type instanceof Type.Borrow) && omega.path().size()!=0), "we cannot move out of "+omega.name());
            } catch (ExceptionsMSG e) {
                throw new RuntimeException(e);
            }
            //Done
            // Apply destructive update
            Environment gam2 = move(gam1,omega);
            return new Pair<>(gam2, type);
        }
    }

    /**
     * T-TuplesExpression
     * @param gam
     * @param lifetime
     * @param expression
     * @return
     */
    @Override
    protected Pair<Environment, Type> apply(Environment gam, Lifetime lifetime, Tuples.TuplesExpression expression, int k) {
        // Apply "carry typing" to type each operand
        Pair<Environment,Type[]> p = carry(gam, lifetime, expression.getExpressions(), k);
        // Done
        return new Pair<>(p.first(), new Tuples.TuplesType(p.second()));
    }

    /**
     * T-Mutable and T-IMMUTABLE
     */
    @Override
    protected Pair<Environment, Type> apply(Environment gam, Lifetime lifetime, Syntax.Expression.Borrow expression, int k) {
        // premise (1)
        Lval omega = expression.getOperand();
        // Determine type being read
        Pair<Type,Lifetime> p = omega.typeOf(gam);
        if(p==null){
            try {
                check(true, "Invalid Lval ");
            } catch (ExceptionsMSG e) {
                throw new RuntimeException(e);
            }
        }
        Type T1 = p.first();
        //premise 1: check iflval is well type, is not moved
        try {
            check(!T1.defined(), "Moved Lval ");
        } catch (ExceptionsMSG e) {
            throw new RuntimeException(e);
        }
        // vérifier si this borrow is mut or immut
        if(expression.isMutable()){
            /**
             * T-Mutable
             */
            //premise 1: mut
            try {
                check(!mut(gam, omega), " lval is not mutable");
            } catch (ExceptionsMSG e) {
                throw new RuntimeException(e);
            }
            //premise 2:
            try {
                check(writeProhibited(gam, omega), "lval is borrowed ");
            } catch (ExceptionsMSG e) {
                throw new RuntimeException(e);
            }
        }else {
            /**
             * T-Immutable
             */
            //premise 1:
            try {
                check(readProhibited(gam, omega), "Lval is borowed");
            } catch (ExceptionsMSG e) {
                throw new RuntimeException(e);
            }
        }
        //Done
        return new Pair<>(gam, new Type.Borrow(expression.isMutable(), omega));
    }

    /**
     * T-Declare
     * let mut x = e; ( x:tau)
     * @param gam1
     * @param lifetime
     * @param expression
     * @return
     */
    @Override
    protected Pair<Environment, Type> apply(Environment gam1, Lifetime lifetime, Syntax.Expression.Declaration expression, int k) {
            // récuperer la variable
            String x = expression.getVariable();
            Location lx = gam1.get(x);
            //exception if  x is already declared
            if(lx!=null){
                try {
                    check(true, "variable already declared ");
                } catch (ExceptionsMSG e) {
                    throw new RuntimeException(e);
                }
            }
            //Type operand: expression
            Pair<Environment, Type> operand = apply(gam1, lifetime, expression.getInitialiser(), k);
            //recuperer le type then update gam1
            Environment gam2 = operand.first();
            Type type = operand.second();
            //update
            Environment gam3 = gam2.put(x,type,lifetime);
            /** necessary to CompiletoC **/
            global = global.put(x,type,lifetime);
            //done
           return new Pair<>(gam3, Type.Unit);
    }

    /**
     * T-Block
     * @param gam1
     * @param lifetime
     * @param expression
     * @return
     */
    @Override
    protected Pair<Environment, Type> apply(Environment gam1, Lifetime lifetime, Syntax.Expression.Block expression, int k) {
        //typed the expression of block (1): first premise
        Pair<Environment, Type> typing = apply(gam1, expression.getLifetime(), expression.getExprs(), k);
        Environment gam2 = typing.first();
        Type type = typing.second();
        //vérifier the second premise of subtyping
        try {
            check(!type.within(this, gam2,lifetime), "lifetime not within");
        } catch (ExceptionsMSG e) {
            throw new RuntimeException(e);
        }

        //drop all variable have the lifetime's block
        Pair<HashMap<String, Type>, Environment> pair = drop(gam2, expression.getLifetime());
        Environment gam3 = pair.second();
        expression.put(pair.first());
        return new Pair<>(gam3, type);
    }


    /**
     * T-Seq
     */
    protected Pair<Environment, Type> apply(Environment gam1, Lifetime lifetime, Syntax.Expression[] expressions, int k) {
        Environment gamn = gam1;
        Type typen = Type.Unit;
        for (int i = 0; i != expressions.length; ++i) {
            // Type statement
            Pair<Environment, Type> typing = apply(gamn, lifetime, expressions[i], k);
            // Update environment and discard type (as unused for statements)
            gamn = typing.first();
            typen = typing.second();
        }
        //
        return new Pair<>(gamn, typen);
    }

    /**
     * T-Assign: w=e:unit
     * @param gam1
     * @param lifetime
     * @param expression
     * @return
     */
    @Override
    protected Pair<Environment, Type> apply(Environment gam1, Lifetime lifetime, Syntax.Expression.Assignment expression, int k) {
        Lval omega = expression.getLval();
        //premise 1: omega est bien typé dans gam1, existe dans gam1
        Pair<Type,Lifetime> typing_omega = omega.typeOf(gam1);
        if(typing_omega==null){
            try {
                check(true, "lval is invalid (e.g. incorrectly typed)");
            } catch (ExceptionsMSG e) {
                throw new RuntimeException(e);
            }
            }
        Type type1 = typing_omega.first();
       // System.out.printf("\n  type "+type1.toString());
        Lifetime omega_lifetime = typing_omega.second();
        //premise 2: typed operand
        Pair<Environment, Type> typing_operand = apply(gam1, lifetime, expression.getExpr(), k);
        Environment gam2 = typing_operand.first();
        Type type2 = typing_operand.second();
        //premise 3: ensure if type and omega type have the same shape
        try {
            check(!compatibleShape(gam2, type1, type2), "Incompatible Type");
        } catch (ExceptionsMSG e) {
            throw new RuntimeException(e);
        }
        //premise 4: the lifetime constraint
        //System.out.println("\n\n lifetime1 "+type2);
        try {
            check(!type2.within(this, gam2, omega_lifetime), "lifetime not within");
        } catch (ExceptionsMSG e) {
            throw new RuntimeException(e);
        }
        //premise 5: update the type in the environment
        // the boolean: true signifies strong updates
        Environment gam3 =  write(gam2, omega, type2, true);
        //premise 6: ensure that omega is not borrowed as shared or mutable in its environment
        try {
            check(writeProhibited(gam1, omega), "lval cannot be written");
        } catch (ExceptionsMSG e) {
            throw new RuntimeException(e);
        }
        //premise vérifier si omega is cloned
        //premise 7: ensure that omega is not cloned in its environment
        try {
            check((type1 instanceof Type.Trc && TrcMoveProhibited(gam3, omega)), " lval is cloned so it can not be moved");
        } catch (ExceptionsMSG e) {
            throw new RuntimeException(e);
        }
        //Done
        return new Pair<>(gam3, Type.Unit);
    }

    /**
     * T-Box
     * @param gam1
     * @param lifetime
     * @param expression
     * @return
     */
    @Override
    protected Pair<Environment, Type> apply(Environment gam1, Lifetime lifetime, Syntax.Expression.Box expression, int k) {
        // one premise: type its operand
        Pair<Environment, Type> typing = apply(gam1, lifetime, expression.getOperand(), k);
        Environment gam2 = typing.first();
        Type type = typing.second();
        //
        return new Pair<>(gam2, new Type.Box(type));
    }


    /**
     * T-Sig
     * @param gam1
     * @param lifetime
     * @param expression
     * @return
     */
    @Override
    protected Pair<Environment, Type> apply(Environment gam1, Lifetime lifetime, Syntax.Expression.Sig expression, int k) {
        // récuperer la variable
        String s = expression.getVariable();
        Location ls = gam1.get(s);
        //exception if  the signal is already existe
        if(ls!=null){
            try {
                check(true, "Signal already declared ");
            } catch (ExceptionsMSG e) {
                throw new RuntimeException(e);
            }
        }
        //update
        Environment gam2 = gam1.put(s,Type.Sig,lifetime);
        /** necessary to CompiletoC **/
        global = global.put(s,Type.Sig,lifetime);
        //done
        return new Pair<>(gam2, Type.Unit);
    }


    /**
     * T-Trc
     * @param gam1
     * @param lifetime
     * @param expression
     * @return
     */
    @Override
    protected Pair<Environment, Type> apply(Environment gam1, Lifetime lifetime, Syntax.Expression.Trc expression, int k) {
        // first premise: type its operand
        Pair<Environment, Type> typing = apply(gam1, lifetime, expression.getOperand(), k);
        Environment gam2 = typing.first();
        Type type = typing.second();
        // second premise
        // verified that the type type doesn't contains a trc type
        try {
            check(containsTrc(gam2, type), "this type contains a Trc type ");
        } catch (ExceptionsMSG e) {
            throw new RuntimeException(e);
        }
       return new Pair<>(gam2, new Type.Trc(type));
    }

    /**
     * T-Emit
     * @param gam
     * @param lifetime
     * @param expression
     * @return
     */
    @Override
    protected Pair<Environment, Type> apply(Environment gam, Lifetime lifetime, Syntax.Expression.Emit expression, int k) {
        // récuperer la variable
        String s = expression.getVariable();
        Location ls = gam.get(s);
        //exception if  the signal is already existe
        if(ls==null){
            try {
                check(true, "The signal "+ s+" does not exist ");
            } catch (ExceptionsMSG e) {
                throw new RuntimeException(e);
            }
        }
        //done
        return new Pair<>(gam, Type.Unit);
    }

    /**
     * T-When
     * @param gam
     * @param lifetime
     * @param expression
     * @return
     */
    @Override
    protected Pair<Environment, Type> apply(Environment gam, Lifetime lifetime, Syntax.Expression.When expression, int k) {
        // récuperer la variable
        String s = expression.getVariable();
        Location ls = gam.get(s);
        //exception if  the signal is already existe
        if(ls==null){
            try {
                check(true, "The signal "+ s+" does not exist ");
            } catch (ExceptionsMSG e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("\n\n hello \n\n");
        // safeTrc
        if(k == 1) {
            try {

                check(!SafeTrc(gam), "Borrowed shared data exists, it's not safe to cooperate");
            } catch (ExceptionsMSG e) {
                throw new RuntimeException(e);
            }
        }else{
            try {
                check(true, "the k effect is different from 1, so it is not safe to cooperate");
            } catch (ExceptionsMSG e) {
                throw new RuntimeException(e);
            }
        }
        //Type operand: expression
        Pair<Environment, Type> operand = apply(gam, lifetime, expression.getOperand(), k);
        //recuperer le type then update gam1
        Environment gam1 = operand.first();
        Type type = operand.second();
        //done
        return new Pair<>(gam1, type);
    }

    /**
     * T-Conditionnal
     * @param gam
     * @param lifetime
     * @param expression
     * @return
     */
    @Override
    protected Pair<Environment, Type> apply(Environment gam, Lifetime lifetime, Syntax.Expression.Conditional expression, int k) {
        // typed the left operand
        Pair<Environment,Type> r1 = apply(gam, lifetime, expression.getLftoperand(), k);
            Environment gam1 = r1.first();
            Type _t1 = r1.second();
        // typed the right operand
        // add the type _t1 as fresh variable
        String fresh = BorrowChecker.fresh();
        Pair<Environment,Type> r2 = apply(gam1.put(fresh, _t1,lifetime), lifetime, expression.getRghtoperand(), k);
            Environment gam2 = r2.first();
            Type _t2 = r2.second();
            //drop the fresh variable
            Environment gam3= gam2.remove(fresh);
        //(1) compare the compatibilty of _t1 and _t2
        try {
            check(!compatibleShape(gam2, _t1, _t2), "Incompatible Type");
        } catch (ExceptionsMSG e) {
            throw new RuntimeException(e);
        }
        //(2) verify if _t1 and _t2 applies copy semantics
        try {
            check(!_t1.copyable(), "the type of this lval doesn't implement a copy semantic");
        } catch (ExceptionsMSG e) {
            throw new RuntimeException(e);
        }
        try {
            check(!_t2.copyable(), "the type of this lval doesn't implement a copy semantic");
        } catch (ExceptionsMSG e) {
            throw new RuntimeException(e);
        }
        return new Pair<>(gam1, Type.Bool);
    }

    /**
     * T-IfElse
     * @param gam
     * @param lifetime
     * @param expression
     * @return
     */
    @Override
    protected Pair<Environment, Type> apply(Environment gam, Lifetime lifetime, Syntax.Expression.IfElse expression, int k) {
        //type the condition
        Pair<Environment, Type> r1 = apply(gam, lifetime, expression.getConditions(), k);
            Environment gam1 = r1.first();
            Type _t = r1.second();
        // verify if condition is of type boolean

        //type the if block
        Pair<Environment, Type> r2 = apply(gam1, lifetime, expression.getIfblock(), k);
            Environment gam2 = r2.first();
            Type _t1 = r2.second();
        //type the else block
        Pair<Environment, Type> r3 = apply(gam1, lifetime, expression.getElseblock(), k);
            Environment gam3 = r3.first();
            Type _t2 = r3.second();

        // ensure the compatibilities if _t1 and _t2
        try {
            check(!compatibleShape(gam2, _t1, _t2), "Incompatible Type");
        } catch (ExceptionsMSG e) {
            throw new RuntimeException(e);
        }
        //join the environment
        Environment gam4 = join(gam2, gam3, expression, k);

        System.out.println("\n\n gam2 "+gam4.toString());
        //join the type
        return new Pair<>(gam4, _t1.union(_t2));
    }

    /**
     * T-watch
     * @param gam
     * @param lifetime
     * @param expression
     * @return
     */
    @Override
    protected Pair<Environment, Type> apply(Environment gam, Lifetime lifetime, Syntax.Expression.Watch expression, int k) {
        // récuperer la variable
        String s = expression.getVariable();
        Location ls = gam.get(s);
        //exception if  the signal is already existe
        if(ls==null){
            try {
                check(true, "The signal "+ s+" does not exist ");
            } catch (ExceptionsMSG e) {
                throw new RuntimeException(e);
            }
        }
        //Type operand: expression
        Pair<Environment, Type> operand = apply(gam, lifetime, expression.getOperand(), k);
        //recuperer le type then update gam1
        Environment gam1 = operand.first();
        Type type = operand.second();
        //done
        return new Pair<>(gam1, type);
    }


    /**
     * T-Clone
     * @param gam
     * @param lifetime
     * @param expression
     * @return
     */
    @Override
    protected Pair<Environment, Type> apply(Environment gam, Lifetime lifetime, Syntax.Expression.Clone expression, int k) {
        //recuperer omega and be sure that omega have the t(rc type)
        Lval omega = expression.getOperand();
        // Determine type being read
        Pair<Type,Lifetime> p = omega.typeOf(gam);
        // Sanity check type
        if(p == null){
            try {
                check(true, omega.name()+" is invalid");
            } catch (ExceptionsMSG e) {
                throw new RuntimeException(e);
            }
            }
       // Extract lval's type
        Type T = p.first();
        // Sanity check type is moveable
        // omega is well-typed?
        try {
            check(!T.defined(), omega.name()+" is moved");
        } catch (ExceptionsMSG e) {
            throw new RuntimeException(e);
        }
        //omega has the type Trc?
        try {
            check(!(T instanceof Type.Trc), omega.name()+"  does not have the Trc type");
        } catch (ExceptionsMSG e) {
            throw new RuntimeException(e);
        }
        System.out.println("\n clone "+omega.name());
        //Done
        return new Pair<>(gam, new Type.Clone(omega));
    }

    /**
     * T-Cooperate
     * @param gam
     * @param lifetime
     * @param expression
     * @return
     */
    @Override
    protected Pair<Environment, Type> apply(Environment gam, Lifetime lifetime, Syntax.Expression.Cooperate expression, int k) {
        if(k==1) {
            try {
                check(!SafeTrc(gam), "Borrowed shared data exists, it's not safe to cooperate");
            } catch (ExceptionsMSG e) {
                throw new RuntimeException(e);
            }
        }else{
            try {
                check(true, "the k effect is different from 1, so it is not safe to cooperate");
            } catch (ExceptionsMSG e) {
                throw new RuntimeException(e);
            }
        }
        return new Pair<>(gam, new Type.Unit());
    }

    /**
     * T-Spawn and T-Function
     * we have the mechanism that ensure: the compatibilities between type and signature/ the invariant of the Trc
     * @param gam
     * @param lifetime
     * @param expression
     * @return
     */

    @Override
    protected Pair<Environment, Type> apply(Environment gam, Lifetime lifetime, InvokeFunction expression, int k) {
        //premise 1: determine the function being invoked
        Function declaration = functions.get(expression.getName());
        /**
         * true if the function call into spawn and false otherwise
         */
        Boolean check = expression.getCheck();
        if(declaration == null){
            try {
                check(true, " unknown function");
            } catch (ExceptionsMSG e) {
                throw new RuntimeException(e);
            }
            }
        //NBthreads+=1;
        Main.setNBthreads();
        //compare the arguments given by the parameters of the function
        Pair<String, Signature>[] parameters = declaration.getParams();
        Syntax.Expression[] arguments = expression.getArguments();
        try {
            check((parameters.length != arguments.length), "The number of arguments is incompatible with the number of parameters!");
        } catch (ExceptionsMSG e) {
            throw new RuntimeException(e);
        }

        //compare the signals arguments given by the signals parameters of the function
       String[] signals = declaration.getSignals();
        String[] signalsarguments = expression.getSignals();
      /*  System.out.println("\n\n signals parameters "+signals.length);
        System.out.println("\n\n signals arguments "+signalsarguments.length);*/
        try {
            check((signals.length != signalsarguments.length), "The number of signal's arguments is incompatible with the number of signal's parameters!");
        } catch (ExceptionsMSG e) {
            throw new RuntimeException(e);
        }

        //premise (2) typed the arguments
        Pair<Environment, Type[]> typedarguments = typedArguments(gam, lifetime, expression.getArguments(), k);

        /**
         *   premise (3) : launch the mechanism when check is true (i.e. within the spawn)
         */

        Environment gam2 = typedarguments.first();
        Type[] args = typedarguments.second();

        //premise (3.1): we have already sure that each lval is well typed in gam2
        /** into the spawn **/
        if(check) {
            //premise (3.2): verify if there exists two inactive trc pointing to the same memory location
            try {
                check(!invariantTrcSpawn(args, gam2), "Having two inactive Trcs pointing to the same memory location!");
            } catch (ExceptionsMSG e) {
                throw new RuntimeException(e);
            }

            //premise (3.3): verify if there exists an active Trc or reference type
            try {
                check(!invariantTrcSpawn(args, gam2), "Having two inactive Trcs pointing to the same memory location!");
            } catch (ExceptionsMSG e) {
                throw new RuntimeException(e);
            }
            //premise (3.4): verify if there exists two inactive trc pointing to the same memory location
            try {
                check(!containsType(args, gam2), "It is forbidden to have an active Trc or a reference in the arguments!");
            } catch (ExceptionsMSG e) {
                throw new RuntimeException(e);
            }

            //premise (3.2): the compatibilities between types and signatures
            /**
             * in our case, we have the form of the signatures: unit, int, bool, box, active trc, inactive trc and borrow
             * verify the compatibility between signatures and types
             */
            try {
                check(!compatibleSigType(parameters, args, gam2), " Incompatible Argument(s)!");
            } catch (ExceptionsMSG e) {
                throw new RuntimeException(e);
            }
            return new Pair<>(gam2, Type.Unit);
        }else {

            /******************** outiside the spawn *****************/
            try {
                check(!compatibleSigType(parameters, args, gam2), " Incompatible Argument(s)!");
            } catch (ExceptionsMSG e) {
                throw new RuntimeException(e);
            }


            // verify if we have an active Trc and an inactive Trc in the arguments
            try {
                check(!activeAndinactiveTrc(args, gam2), " it is forbidden to have an active Trc and an inactive Trc in the same location!");
            } catch (ExceptionsMSG e) {
                throw new RuntimeException(e);
            }
               //T-invokeB
            if(k==1 && declaration.getK() == 1){
                  try {
                        check(!SafeTrc(gam), "Borrowed shared data exists, it's not safe to cooperate!");
                    } catch (ExceptionsMSG e) {
                        throw new RuntimeException(e);
                    }
            }else if((k==0 && declaration.getK() == 1)){
                    try {

                        check(true, "the effect k must be 1!");
                    } catch (ExceptionsMSG e) {
                        throw new RuntimeException(e);
                    }
            }

            //premise: verify if there exists two inactive trc pointing to the same memory location
            try {
                check(!invariantTrcSpawn(args, gam2), "Having two inactive Trcs pointing to the same memory location!");
            } catch (ExceptionsMSG e) {
                throw new RuntimeException(e);
            }

            //verify if we have active and inactive Trc type to the same location
            // we no need to a function, it is already check when we type the arguments


            /** Apply lifting **/
            Type returnType = ReturnType(declaration,gam2,lifetime,args);
            /** Apply side effects ***/
             Environment gam3 = liftSideEffects(declaration, gam2, lifetime, args);
             System.out.println("\n\n gam3 liftSideEffects "+gam3.toString());
            return new Pair<>(gam3, returnType);
        }

    }

    @Override
    protected Pair<Environment, Type> apply(Environment gam, Lifetime lifetime, Syntax.Expression.Print expression, int k) {
        //nothing to do
        return new Pair<>(gam, new Type.Unit());
    }

/** ================================================================================
                                 FUNCTIONS NEEDED
    ================================================================================
 **/
    /***** COPY SEMANTICS ***/
    public boolean isCopy(Syntax.Expression.Access expression, Type type) {
        if (copyInference) {
            // int, bool and &w have the copy semantics
            boolean r = type.copyable();
            expression.infer(r ? Syntax.Expression.Access.Kind.COPY : Syntax.Expression.Access.Kind.MOVE);
            return r;
        } else {
            return expression.isCopy();
        }
    }
    /********************************************************************************************/

    /***** Read Prohibited ***/

    /**
     * Check whether a given LVal is prohibited from being read by some other type
     * in the environment (e.g. a mutable borrow). For example, consider the
     * following:
     *
     * <pre>
     * let mut x = 1;
     * // x->int
     * let mut y = &mut x;
     * // x->int, y->&mut x
     * </pre>
     *
     * After the second statement, the lval <code>x</code> is prohibited from being
     * read by the mutable borrow type <code>&mut x</code> stored in the environment
     * for <code>y</code>.
     *
     * @param environment  The environment in which we are checking for readability.
     * @param lv The lval being checked for readability
     * @return
     */
    protected boolean readProhibited(Environment environment, Lval lv) {
        // Look through all types to whether any prohibit reading this lval
        for (Location cell : environment.cells()) {
            Type type = cell.getType();
            if (type.prohibitsReading(lv)) {
                return true;
            }
        }
        return false;
    }

    /***          Write Prohibited **/
    /**
     * Check whether a given LVal is prohibited from being written by some other
     * type in the environment (e.g. a borrow)
     *
     * @param R  The environment in which we are checking for writability.
     * @param lv The lval being checked for writability
     * @return
     */
    protected boolean writeProhibited(Environment R, Lval lv) {
        // Check whether any type prohibits this being written
        System.out.println("\n\n Environment "+R.toString());
        for (Location cell : R.cells()) {
            Type type = cell.getType();
            if (type.prohibitsWriting(lv)) {
                return true;
            }
        }
        // Check whether writing through mutable references
        return false;
    }

    /**
     * be safe to move an lval that have the Trc type when it is not cloned in the current environment
     * @param R
     * @param lv
     * @return
     */
    protected boolean TrcMoveProhibited(Environment R, Lval lv) {
        // Check whether any type prohibits this being written
        for (Location cell : R.cells()) {
            Type type = cell.getType();
            if (type.prohibitsTrcMoving(lv)) {
                return true;
            }
        }
        // Check whether writing through mutable references
        return false;
    }

    /***   MOVE & strike     */

    /**
     * Update the environment after a given lval is moved somewhere else.
     *
     * @param R
     * @param w
     * @return
     */
    protected Environment move(Environment R, Lval w) {
        String x = w.name();
        Location Sw = R.get(x);
        Type T1 = Sw.getType();
        Lifetime l = Sw.getLifetime();
        Type T2 = strike(T1, w.path(), 0);
        return R.put(x, new Location(T2, l));
    }

    /** strike just for Box: we can move out just in Box **/
    /**
     * include the type of tuple
     * @param T
     * @param p
     * @param i
     * @return
     */
    protected Type strike(Type T, Path p, int i) {
        if (p.size() == i) {
            return T.undefine();
        } else if (T instanceof Type.Box) {
            // In core calculus, dereferences are only valid path elements.
            Type.Box box = (Type.Box) T;
            return new Type.Box(strike(box.getType(), p, i + 1));
        }else if (i < p.size() && T instanceof Tuples.TuplesType) {
            Path.Element ith = p.get(i);
            if (ith instanceof Tuples.TuplesIndex) {
                Tuples.TuplesType _T = (Tuples.TuplesType) T;
                Type[] ts = _T.getTypes();
                int index = ((Tuples.TuplesIndex) p.get(i)).getIndex();
                if (index < ts.length) {
                    ts = Arrays.copyOf(ts, ts.length);
                    ts[index] = strike(ts[index], p, i + 1);
                    return new Tuples.TuplesType(ts);
                }
                // afficher une erreur
            }
            return strike(T, p, i);
        }
        else {
            // T must be Type.Borrow
            String error = "cannot move out through borrow";

            // ajouter pour trc et clone
            // Deadcode
            return null;
        }
    }

    /*********************** DROP *********************************************************************************************************/
// ================================================================================
    // Drop
    // ================================================================================

    /**
     * Drop all variables declared in a given lifetime. For example, consider the
     * following:
     *
     * <pre>
     * let mut x = 1;
     * {
     *    let mut y = 2;
     * }
     * let mut z = y;
     * </pre>
     *
     * Right before the final declaration, all variables allocated in the inner
     * block are dropped and, subsequently, removed from the environment. This means
     * that the use of <code>y</code> in the final statement is identified as
     * referring to an undeclared variable.
     *
     * @param env
     * @param lifetime
     * @return
     */
    protected Pair<HashMap<String, Type>, Environment> drop(Environment env, Lifetime lifetime) {
        HashMap<String, Type> map = new HashMap<>();
        for (String name : env.bindings()) {
            Location cell = env.get(name);
            if (cell.getLifetime().equals(lifetime)) {
                env = env.remove(name);

                /**
                 * put the free automatically
                 */
                Type type = cell.getType();

                /**
                 * add name and type of the variable
                 */
                map.put(name, type);
            }
        }
        return new Pair<>(map,env);
    }

    /******************************* write and update ************************************************************************************/
    /**
     * Write a given type to a given lval in a given environment producing a
     * potentially updated environment. For example, the following illustates:
     *
     * <pre>
     * let mut x = 1;
     * x = 2;
     * </pre>
     *
     * The second statement calls this method to write type <code>int</code> to lval
     * <code>x</code>. As such, the environment is not updated. However, in the
     * following case, it is:
     *
     * <pre>
     * let mut x = 1;
     * let mut y = 2;
     * let mut u = &mut x;
     * let mut v = &mut y;
     * let mut p = &mut u;
     *
     * *p = v;
     * </pre>
     *
     * In this case, the environment is updated such that the type of <code>u</code>
     * after the final assignment is <code>&mut x,y</code>. This is because, for
     * whatever reason, Rust chooses against implementing this assignment as a
     * <i>strong update</i>.
     *
     * @param gam1     The environment in which the assignment is taking place.
     * @param lv     The lval being assigned
     * @param type1     The type being assigned to the lval.
     * @param strong Indicates whether or not to perfom a strong update. Currently,
     *               in Rust, these only occur when assigning directly to variables.
     * @return
     */
    public Environment write(Environment gam1, Lval lv, Type type1, boolean strong) {
        Path path = lv.path();
       // System.out.printf("\n path of the lval "+path.toString(lv.name())+"\n");
        // Extract target cell
        Location Cx = gam1.get(lv.name());
        // Destructure
        Type type2 = Cx.getType();
        Lifetime m = Cx.getLifetime();
        System.out.println("\n\n type 1 "+type2.toString());
        // Apply write
        Pair<Environment, Type> p = update(gam1, type2, path, 0, type1, strong);
        Environment gam2 = p.first();
        Type type4 = p.second();
        // Update environment
        return gam2.put(lv.name(), type4, m);
    }

    protected Pair<Environment, Type> update(Environment R, Type T1, Path p, int i, Type T2, boolean strong) {
        if (i == p.size()) {
            if (strong) {
                return new Pair<>(R, T2);
            } else {
                return new Pair<>(R, T1.union(T2));
            }
        } else {

            //
            if(T1 instanceof Type.Box) {
                Type.Box T = (Type.Box) T1;

                Pair<Environment, Type> r = update(R, T.getType(), p, i + 1, T2, true);
                // Done
                return new Pair<>(r.first(), new Type.Box(r.second()));
            } else if (T1 instanceof Type.Trc) {
                Type.Trc T = (Type.Trc) T1;

                Pair<Environment, Type> r = update(R, T.getType(), p, i + 1, T2, true);
                // Done
                return new Pair<>(r.first(), new Type.Trc(r.second()));

            }// tuple case
            else if (i < p.size() && T1 instanceof Tuples.TuplesType) {
                Path.Element element = p.get(i);
                if (element instanceof Tuples.TuplesIndex) {
                    Tuples.TuplesType _T1 = (Tuples.TuplesType) T1;
                    Type[] ts = _T1.getTypes();
                    int index = ((Tuples.TuplesIndex) p.get(i)).getIndex();
                    if (index < ts.length) {
                        ts = Arrays.copyOf(ts, ts.length);
                        Pair<Environment, Type> r = update(R, ts[index], p, i + 1, T2, strong);
                        ts[index] = r.second();;
                        return new Pair<>(r.first(), new Tuples.TuplesType(ts));
                    }
                }
                //afficher une erreur
                return update(R, T1, p, i, T2, strong);
            }
             else {
                Type.Borrow T = (Type.Borrow) T1;
                try {
                    check((!T.isMutable()), "lval is a `&` reference, so the data it refers to cannot be written ");
                } catch (ExceptionsMSG e) {
                    throw new RuntimeException(e);
                }
                /*if(!T.isMutable()){
                    System.out.printf("\n lval is not mutable");
                }*/
                Lval[] ys = T.lvals();
                //
                Environment Rn = R;
                // Consider all targets
                for (int j = 0; j != ys.length; ++j) {
                    // Traverse remainder of path from lval.
                    Lval y = ys[j].traverse(p, i + 1);
                    // NOTE: this prohibits a strong update in certain cases where it may, in fact,
                    // be possible to do this. It's not clear to me that this is always necessary or
                    // even desirable. However, at the time of writing, this mimics the Rust
                    // compiler.
                    Rn = write(Rn, y, T2, false);
                }
                //
                return new Pair<>(Rn, T);
            }
        }
    }

    /*********************** Compatible Shape *********************************************************************************************************/
// ================================================================================
    // Drop
    // ================================================================================

    public boolean compatibleShape(Environment R1, Type T1, Type T2) {
        if (T1 instanceof Type.Unit && T2 instanceof Type.Unit) {
            return true;
        } else if (T1 instanceof Type.Int && T2 instanceof Type.Int) {
            return true;
        } else if (T1 instanceof Type.Box && T2 instanceof Type.Box) {
            Type.Box _T1 = (Type.Box) T1;
            Type.Box _T2 = (Type.Box) T2;
            return compatibleShape(R1, _T1.getType(), _T2.getType());
        }else if (T1 instanceof Type.Trc && T2 instanceof Type.Trc) {
            Type.Trc _T1 = (Type.Trc) T1;
            Type.Trc _T2 = (Type.Trc) T2;
            return compatibleShape(R1, _T1.getType(), _T2.getType());
        }else if (T1 instanceof Type.Clone && T2 instanceof Type.Clone) {
            Type.Clone _T1 = (Type.Clone) T1;
            Type.Clone _T2 = (Type.Clone) T2;
            // NOTE: follow holds because all members of a single borrow must be compatible
            // by construction.
            Type ti = _T1.lvals()[0].typeOf(R1).first();
            Type tj = _T2.lvals()[0].typeOf(R1).first();
            //
            return compatibleShape(R1, ti, tj);
        }else if (T1 instanceof Type.Trc && T2 instanceof Type.Clone) {
            Type.Clone _T2 = (Type.Clone) T2;
            // NOTE: follow holds because all members of a single borrow must be compatible
            // by construction.
            Type tj = _T2.lvals()[0].typeOf(R1).first();
            //
            return compatibleShape(R1, T1, tj);
        }else if (T1 instanceof Type.Clone && T2 instanceof Type.Trc) {
            Type.Clone _T1 = (Type.Clone) T1;
            // NOTE: follow holds because all members of a single borrow must be compatible
            // by construction.
            Type ti = _T1.lvals()[0].typeOf(R1).first();
            //
            return compatibleShape(R1, ti, T2);
        }else if (T1 instanceof Type.Borrow && T2 instanceof Type.Borrow) {
            Type.Borrow _T1 = (Type.Borrow) T1;
            Type.Borrow _T2 = (Type.Borrow) T2;
            // NOTE: follow holds because all members of a single borrow must be compatible
            // by construction.
            Type ti = _T1.lvals()[0].typeOf(R1).first();
            Type tj = _T2.lvals()[0].typeOf(R1).first();
            //
            return _T1.isMutable() == _T2.isMutable() && compatibleShape(R1, ti, tj);
        }  else if (T1 instanceof Type.Undefined && T2 instanceof Type.Undefined) {
            Type.Undefined _T1 = (Type.Undefined) T1;
            Type.Undefined _T2 = (Type.Undefined) T2;
            return compatibleShape(R1, _T1.getType(), _T2.getType());
        } else if (T1 instanceof Type.Undefined) {
            Type.Undefined _T1 = (Type.Undefined) T1;
            return compatibleShape(R1, _T1.getType(), T2);
        } else if (T2 instanceof Type.Undefined) {
            Type.Undefined _T2 = (Type.Undefined) T2;
            return compatibleShape(R1, T1, _T2.getType());
        } else if(T1 instanceof Tuples.TuplesType && T2 instanceof Tuples.TuplesType) {
            Tuples.TuplesType _T1 = (Tuples.TuplesType) T1;
            Tuples.TuplesType _T2 = (Tuples.TuplesType) T2;
            if(_T1.getTypes().length != _T2.getTypes().length) {
                return false;
            } else {
                for(int i=0;i!=_T1.getTypes().length;++i) {
                    Type t1 = _T1.getTypes()[i];
                    Type t2 = _T2.getTypes()[i];
                    if(!compatibleShape(R1,t1,t2)) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /********************* ContainsTrc ****************************************************/

    protected boolean containsTrc(Environment gam, Type type) {
        if(type instanceof Type.Trc){
            return true;
        } else if (type instanceof Type.Clone) {
            return true;
        } else if(type instanceof Type.Box){
            Type.Box  T1 = (Type.Box) type;
           // System.out.printf("containsTrcBox "+ T1.getType());
            return containsTrc(gam, T1.getType());
        } else if (type instanceof Type.Borrow) {
            // is enough to testing one lval because the type is compatible
            Type.Borrow _T1 = (Type.Borrow) type;
            Type ti = _T1.lvals()[0].typeOf(gam).first();
            return containsTrc(gam, ti);
        }
        return false;
    }

    /********************* SafeTrc and traversTrc****************************************************/

    protected boolean SafeTrc(Environment gam) {
        // Check whether any acyive Trc type is borrowed in the current environment
        /*for (Location cell : gam.cells()) {
            Type type = cell.getType();
            if (!type.TrcSafe(gam)) {
                return false;
            }
        }
        return true;*/
        for (Location cell : gam.cells()) {
            Type type = cell.getType();
            Pair<Boolean, Type> C = type.ContainsRef(gam);
            if (C.first()) {
                Type.Borrow B = (Type.Borrow) C.second();
                Lval[] w = B.lvals();
                for (int i =0; i!= w.length; ++i) {
                    Lval wi = B.lvals()[i];
                    Location Cx = gam.get(wi.name());
                    Boolean travers =  traversTrc(gam, Cx.getType(), wi.path(), 0);
                    if (travers){
                        return false;
                    }
                }
            }
        }
        return true;

    }
    protected boolean traversTrc(Environment R, Type T, Path p, int i) {
        if (p.size() == i) {
            // Done.
            return false;
        } else if (T instanceof Type.Box ) {
            Type.Box B = (Type.Box) T;
            // Check path element is dereference
            Path.Deref d = (Path.Deref) p.get(i);
            // Continue writing
            return traversTrc(R, B.getType(), p, i + 1);
        } else if (T instanceof Type.Trc ) {
           return true;
        } else if (i < p.size() && T instanceof Tuples.TuplesType) {
            Path.Element ith = p.get(i);
            if (ith instanceof Tuples.TuplesIndex) {
                Tuples.TuplesType _T = (Tuples.TuplesType) T;
                Type[] ts = _T.getTypes();
                int index = ((Tuples.TuplesIndex) p.get(i)).getIndex();
                if (index < ts.length) {
                    return traversTrc(R, ts[index], p, i + 1);
                }
            }
            //affohcer une erreur
            return traversTrc(R, T, p, i);
        }
        // Default fallback
        else if (T instanceof Type.Borrow) {
            Type.Borrow t = (Type.Borrow) T;
            // Check path element is dereference
            Path.Deref d = (Path.Deref) p.get(i);
            Lval[] borrows = t.lvals();
            // Determine type of all borrows
            for(int r=0;r!= borrows.length;++r){
                Type Tj = borrows[r].typeOf(R).first();
                Boolean check = traversTrc(R, Tj, p, i + 1);
                if(check){

                    return check;
                }
                // type.
               // return traversTrc(R, Tj, p, i + 1);
            }


        } else {//clone
            return false;
        }
        return false;
    }
/******************************** Mut and Mutable *******************************************************/
protected boolean mut(Environment R, Lval w) {
    Location Cx = R.get(w.name());
    // Done
    return mutable(R, Cx.getType(), w.path(), 0);
}

    protected boolean mutable(Environment R, Type T, Path p, int i) {
        if (p.size() == i) {
            // Done.
            return true;
        } else if (T instanceof Type.Box ) {
            Type.Box B = (Type.Box) T;
            // Check path element is dereference
            Path.Deref d = (Path.Deref) p.get(i);
            // Continue writing
            return mutable(R, B.getType(), p, i + 1);
        } else if (T instanceof Type.Trc ) {
            Type.Trc B = (Type.Trc) T;
            // Check path element is dereference
            Path.Deref d = (Path.Deref) p.get(i);
            // Continue writing
            return mutable(R, B.getType(), p, i + 1);
        } else if (i < p.size() && T instanceof Tuples.TuplesType) {
            Path.Element ith = p.get(i);
            if (ith instanceof Tuples.TuplesIndex) {
                Tuples.TuplesType _T = (Tuples.TuplesType) T;
                Type[] ts = _T.getTypes();
                int index = ((Tuples.TuplesIndex) p.get(i)).getIndex();
                if (index < ts.length) {
                    return mutable(R, ts[index], p, i + 1);
                }
            }
            //affohcer une erreur
            return mutable(R, T, p, i);
        }
        // Default fallback
        else if (T instanceof Type.Borrow) {
            Type.Borrow t = (Type.Borrow) T;
            // Check path element is dereference
            Path.Deref d = (Path.Deref) p.get(i);
            //
            if (!t.isMutable()) {
                // Cannot write through immutable borrow
                return false;
            } else {
                Lval[] borrows = t.lvals();
                // Determine type of first borrow
                Type Tj = borrows[0].typeOf(R).first();
                // NOTE: is safe to ignore other lvals because every lval must have a compatible
                // type.
                return mutable(R, Tj, p, i + 1);
            }
        } else {//clone
            return false;
        }
    }
    /*************************************************************************************************************************************/
    /*****************************************************************************************************
                                        InvokeFunction
    /*****************************************************************************************************/

// ================================================================================
    // Carry Typing
    // ================================================================================

    /**
     * Apply "carry typing" to a given sequence of expressions.
     *
     * @param gam1    Initial environment before left-most term
     * @param l     Enclosing lifetime
     * @param expressions Sequence of terms
     * @return Final environment after right-most term, along with a type for each
     *         term.
     */
    public Pair<Environment,Type[]> typedArguments(Environment gam1, Lifetime l, Syntax.Expression[] expressions, int k) {
        String[] vars =fresh(expressions.length);
        Type[] types = new Type[expressions.length];
        Environment gam = gam1;
        // Type each element individually
        for(int i=0;i!=expressions.length;++i) {
            Syntax.Expression ith = expressions[i];
            // Type left-hand side
            Pair<Environment, Type> p1 = apply(gam, l, ith, k);
            Type Tn = p1.second();
            gam = p1.first();
            // Add type into environment temporarily
            gam = gam.put(vars[i], Tn, l.getRoot());
            //
            types[i] = p1.second();
        }
        // Remove all temporary types
        Environment R2 = gam.remove(vars);
        // Done
        return new Pair<>(R2,types);
    }

    /**
     * create a fresh variable
     * " unique variable "
     */
    /**
     * Return a unique variable name everytime this is called.
     *
     * @return
     */
    public static String fresh() {
        return "?" + (freshVar++);
    }

    /**
     * Return a sequence of zero or more fresh variable names
     *
     * @param n
     * @return
     */
    public static String[] fresh(int n) {
        String[] freshVars = new String[n];
        for (int i = 0; i != n; ++i) {
            freshVars[i] = fresh();
        }
        return freshVars;
    }
/************************************ Function Call **********************************************/
    /**
     * verify if we have f(x.clone, x)
     * @param arguments
     * @param gam
     * @return
     */
    public Boolean activeAndinactiveTrc(Type[] arguments, Environment gam) {
        for(int i =0; i!= arguments.length;++i){
            if(arguments[i].NotWellDefinedClone(gam)){
                return false;
            }

            }
               return true;
        }


    /**
     * compatibilities beteween signatures and types
     * @param parameters
     * @param arguments
     * @param gam
     * @return
     */
    public Boolean compatibleSigType(Pair<String, Signature>[] parameters, Type[] arguments, Environment gam) {
            // Retrieve all recognized abstract lifetimes.
            String[] abstractl = extractLifetimes(parameters);
            // Retrieve all accessible concrete lifetimes.
            Lifetime[] concretel = extractLifetimes(gam, arguments);
        /** List all possible bindings. **/
        label:
        for(Map<String,Lifetime> suitable : generatebinding(abstractl,concretel)) {
            System.out.println("\n\n taille of generated "+ generatebinding(abstractl,concretel).toString());

            for (int i = 0; i != arguments.length; ++i) {
                Signature sith = parameters[i].second();
                Type tith = arguments[i];
                if(!sith.isSubtype(gam, tith, suitable)) {
                    continue label;
                }
            }
            System.out.println("\n\n suitable "+ suitable.toString());
            // Candidate Done!
           /*if (!suitable.isEmpty()){
                System.out.println("\n\n isEmpty "+suitable.toString()+"\n\n\n");
           }*/
            if(abstractl.length!=0) {
                return !suitable.isEmpty();
            }else {
                return true;
            }
        }

        if(abstractl.length ==0) {
            return true;
        }

        return false;

            // Done

    }

    public String[] extractLifetimes(Pair<String, Signature>[] parameters) {
        ArrayList<String> lifetimes = new ArrayList<>();
        for(int i=0;i!=parameters.length;++i) {
            Signature ith = parameters[i].second();
           /* if(ith instanceof Signature.Borrow){
                if(!lifetimes.contains(((Signature.Borrow) ith).getLifetime())){
                    lifetimes.add(((Signature.Borrow) ith).getLifetime());
                }
            }*/
            ith.match(Signature.Borrow.class, b -> !lifetimes.add(b.getLifetime()));
        }
        String[] ls = lifetimes.toArray(new String[lifetimes.size()]);
        return ls;
    }

    private static Lifetime[] extractLifetimes(Environment R, Type[] arguments) {
        ArrayList<Lifetime> lifetimes = new ArrayList<>();
        for(int i=0;i!=arguments.length;++i) {
            arguments[i].consume(Type.Borrow.class, b -> extractLifetimes(R,b.lvals(),lifetimes));
        }
        Lifetime[] ls = lifetimes.toArray(new Lifetime[lifetimes.size()]);
        return ls;
    }

    private static void extractLifetimes(Environment R, Lval[] lvals, List<Lifetime> lifetimes) {
        for (int i = 0; i != lvals.length; ++i) {
            Lval w = lvals[i];
            Pair<Type, Lifetime> p = w.typeOf(R);
            // Record lifetime
            lifetimes.add(p.second());
            // Continue traversal
            p.first().consume(Type.Borrow.class, b -> extractLifetimes(R, b.lvals(), lifetimes));
        }
    }

    private static Iterable<Map<String,Lifetime>> generatebinding(String[] abstractl, Lifetime[] concretel) {
        ArrayList<Map<String,Lifetime>> results = new ArrayList<>();
        generate(0, new int[abstractl.length], abstractl, concretel, results);
        return results;
    }

    private static void generate(int i, int[] mapping, String[] abstracts, Lifetime[] concretes, List<Map<String,Lifetime>> candidates) {
        if(i == mapping.length) {
            // Base case
            HashMap<String,Lifetime> binding = new HashMap<>();
            for(int j=0;j!=mapping.length;++j) {
                binding.put(abstracts[j], concretes[mapping[j]]);
            }
            candidates.add(binding);
        } else {
            for (int j = 0; j != concretes.length; ++j) {
                mapping[i] = j;
                generate(i + 1, mapping, abstracts, concretes, candidates);
            }
        }
    }

    private Type ReturnType(Function decl, Environment gam, Lifetime lifetime, Type[] args) {
        return lift(decl.getRet(),decl.getParams(),gam,lifetime,args);
    }

    private Type lift(Signature target, Pair<String, Signature>[] params, Environment R, Lifetime l, Type[] args) {
        Map<String, Lifetime> binding = construct(l, params);
        ArrayList<Signature.Borrow> holes = new ArrayList<>();
        ArrayList<Signature.Clone> holesClone = new ArrayList<>();
        // Identify borrows which need to be lifted
        // this case juts for borrow
        target.match(Signature.Borrow.class, b -> holes.add(b));
        /*******************************************************/
        // this case juts for clone
        // fn f1()-> clone<int>
        // à faire f1() -> box<clone>
        target.match(Signature.Clone.class, b -> holesClone.add(b));
        HashMap<Signature.Clone, Type.Clone> lifting2 = new HashMap<>();
        for (Signature.Clone hole : holesClone) {
            for (int j = 0; j != args.length; ++j) {
                liftClone(params[j].second(), R, args[j], hole, binding, lifting2);
            }
        }
        /*******************************************************/
        // Construct the "lifting"
        HashMap<Signature.Borrow, Type.Borrow> lifting = new HashMap<>();
        for (Signature.Borrow hole : holes) {
            for (int j = 0; j != args.length; ++j) {
                lift(params[j].second(), R, args[j], hole, binding, lifting);
            }
        }
        //
        if(lifting2.isEmpty()) {
            return target.lift(lifting);
        }else {
            return target.liftClone(lifting2);
        }
    }

    private void lift(Signature param, Environment R, Type arg, Signature.Borrow hole,
                      Map<String, Lifetime> binding, Map<Signature.Borrow, Type.Borrow> lifting) {
        if(param instanceof Signature.Box) {
            Signature.Box sb = (Signature.Box) param;
            Type.Box tb = (Type.Box) arg;
            lift(sb.getOperand(), R, tb.getType(), hole, binding, lifting);
        }else if(param instanceof Signature.Trc) {
            Signature.Trc sb = (Signature.Trc) param;
            Type.Trc tb = (Type.Trc) arg;
            lift(sb.getOperand(), R, tb.getType(), hole, binding, lifting);
        }else if(param instanceof Signature.Clone) {
            // to complete
        }else if(param instanceof Signature.Borrow) {
            Signature.Borrow sb = (Signature.Borrow) param;
            Type.Borrow tb = (Type.Borrow) arg;
            //
            if (hole.isSubtype(binding, sb)) {
                // match!
                Type.Borrow b = lifting.get(hole);
                if (b != null) {
                    lifting.put(hole, (Type.Borrow) b.union(tb));
                } else {
                    lifting.put(hole, tb);
                }
            } else {
                // no match, continue traversing
                for (Lval lv : tb.lvals()) {
                    Pair<Type, Lifetime> p = lv.typeOf(R);
                    lift(sb.getSignature(), R, p.first(), hole, binding, lifting);
                }
            }
        } else {
            // do nothing for other cases
        }
    }

    /*********************************************************************************/
    private void liftClone(Signature param, Environment R, Type arg, Signature.Clone hole,
                           Map<String, Lifetime> binding,
                     Map<Signature.Clone, Type.Clone> lifting) {
        if(param instanceof Signature.Box) {
            Signature.Box sb = (Signature.Box) param;
            Type.Box tb = (Type.Box) arg;
            liftClone(sb.getOperand(), R, tb.getType(), hole, binding, lifting);
        }
        /*else if(param instanceof Signature.Trc) {
            Signature.Trc sb = (Signature.Trc) param;
            Type.Trc tb = (Type.Trc) arg;

            if (hole.getOperand().isSubtype(binding, sb.getOperand())) {
                // match!
                Type.Clone b = lifting.get(hole);
                if (b != null) {
                    lifting.put(hole, (Type.Clone) b.union(tb));
                } else {
                    lifting.put(hole, tb);
                }
            }

        }*/
        else if(param instanceof Signature.Clone) {

            // to complete
            Signature.Clone sb = (Signature.Clone) param;
            Type.Clone tb = (Type.Clone) arg;
            //
            if (hole.isSubtype(binding, sb)) {
                // match!
                Type.Clone b = lifting.get(hole);
                if (b != null) {
                    lifting.put(hole, (Type.Clone) b.union(tb));
                } else {
                    lifting.put(hole, tb);
                }
            }
        }
        else if(param instanceof Signature.Borrow) {
            Signature.Borrow sb = (Signature.Borrow) param;
            Type.Borrow tb = (Type.Borrow) arg;
            Type.Clone _tb = new Type.Clone(tb.lvals());
            //
            System.out.println("\n\n sb "+tb.lvals());
            if(sb.isMutable()){
                Signature _sb = sb.getSignature();
                if (_sb instanceof Signature.Trc){
                    Signature _sb2 = ((Signature.Trc) _sb).getOperand();
                    Signature _hole = hole.getOperand();
                    if (_hole.isSubtype(binding, _sb2)) {
                        // match!
                        Type b = lifting.get(hole);
                        if (b != null) {
                            lifting.put(hole, (Type.Clone) b.union(_tb));
                        } else {
                            lifting.put(hole, _tb);
                        }
                    } else {
                        // no match, continue traversing
                        for (Lval lv : tb.lvals()) {
                            Pair<Type, Lifetime> p = lv.typeOf(R);
                            liftClone(sb.getSignature(), R, p.first(), hole, binding, lifting);
                        }
                    }
                }
            }
        }
        else {
            // do nothing for other cases
        }
    }

    /*********************************************************************************/
    private Map<String,Lifetime> construct(Lifetime l, Pair<String,Signature>[] params) {
        // Our lifetime must be within all others
        Lifetime lifetime = new Lifetime(l);
        // Extract all lifetimes
        HashMap<String, Lifetime> suitable = new HashMap<>();
        Environment gam = BorrowChecker.EMPTY_ENVIRONMENT;
        // Lower parameters into environment
        for (int i = 0; i != params.length; ++i) {
            Signature s = params[i].second();
            // Lower signature into environment
            Pair<Environment, Type> r = s.lower(gam, suitable, lifetime);
            gam = r.first();
        }
        //
        return suitable;
    }

    private Environment liftSideEffects(Function declaration, Environment gam, Lifetime lifetime, Type[] args) {
        Pair<String,Signature>[] params = declaration.getParams();
        // Generate set of all possible side-effects
        ArrayList<Pair<Signature.Borrow, Type.Borrow>> effects = new ArrayList<>();
        for(int i=0;i!=params.length;++i) {
            liftSideEffects(params[i].second(), gam, args[i], effects);
        }
        // Apply all possible side-effects
        for(int i=0;i!=effects.size();++i) {
            Signature.Borrow sb = effects.get(i).first();
            Type.Borrow tb = effects.get(i).second();
            // Determine anything which could be written
            Type e = lift(sb.getSignature(), params, gam, lifetime, args);
            // Update targets with possible write
            for(Lval w : tb.lvals()) {
              //  System.out.println("\n\n omega is "+w.toString());
              //  System.out.println("\n\n type is "+sb.getSignature());
                gam = write(gam, w, e, false);
            }
        }
        //
        return gam;
    }

    private void liftSideEffects(Signature signature, Environment gam, Type arg,
                                 List<Pair<Signature.Borrow, Type.Borrow>> effects) {
        if (signature instanceof Signature.Box) {
            Signature.Box sb = (Signature.Box) signature;
            try {
                check(!(arg instanceof Type.Box), "Type incompatible with Signature.");
            } catch (ExceptionsMSG e) {
                throw new RuntimeException(e);
            }
            Type.Box tb = (Type.Box) arg;
            liftSideEffects(sb.getOperand(), gam, tb.getType(), effects);
        }else if (signature instanceof Signature.Trc) {
            Signature.Trc sb = (Signature.Trc) signature;
            try {
                check(!(arg instanceof Type.Trc), "Type incompatible with Signature.");
            } catch (ExceptionsMSG e) {
                throw new RuntimeException(e);
            }
            Type.Trc tb = (Type.Trc) arg;
            liftSideEffects(sb.getOperand(), gam, tb.getType(), effects);
        }else if (signature instanceof Signature.Clone) {
            //à completer
            Signature.Clone sb = (Signature.Clone) signature;
            try {
                check(!(arg instanceof Type.Clone), "Type incompatible with Signature.");
            } catch (ExceptionsMSG e) {
                throw new RuntimeException(e);
            }
            Type.Clone tb = (Type.Clone) arg;
            // Check for mutable borrow
                // Matched!
                for (Lval w : tb.lvals()) {
                    Pair<Type, Lifetime> p = w.typeOf(gam);

                    liftSideEffects(sb.getOperand(), gam, p.first(), effects);
                }
                //
                //effects.add(new Pair<>(sb, tb));
        }
        else if (signature instanceof Signature.Borrow) {
            Signature.Borrow sb = (Signature.Borrow) signature;
            try {
                check(!(arg instanceof Type.Borrow), "Type incompatible with Signature.");
            } catch (ExceptionsMSG e) {
                throw new RuntimeException(e);
            }
            Type.Borrow tb = (Type.Borrow) arg;
            // Check for mutable borrow
            if (sb.isMutable()) {
                // Matched!
                for (Lval w : tb.lvals()) {
                    Pair<Type, Lifetime> p = w.typeOf(gam);
                    liftSideEffects(sb.getSignature(), gam, p.first(), effects);
                }
                //
                effects.add(new Pair<>(sb, tb));
            }
        }
    }

/*************************************************************************************************************/
    /**
     * return index
     */
    public int index(Type[] arguments, int index){
        for(int i = index; i<arguments.length; i++){
            if(arguments[i] instanceof Type.Clone){
                return i;
            }
        }
        return arguments.length;
    }
    /**
     * InvariantTrcSpawn: verify if two inactive trcs are conflicts
     * @param arguments
     * @param gam
     * @return
     */
    public Boolean invariantTrcSpawn(Type[] arguments, Environment gam) {

        //int i = index(arguments, 0);
        int i = 0;// the first index
        int j=i+1;
        if(i == arguments.length -1){

            return true;
        }else {
            outer:
            while (j< (arguments.length)){
                if((arguments[j] instanceof Type.Clone) && arguments[i].conflictTrc(arguments[j])){
                    return false;
                }else if(arguments[j].ContainsTrc(gam)){
                    Type.Clone ithClone = (Type.Clone) arguments[j].returnClone(gam);
                    if(arguments[i].conflictTrc(ithClone)){
                        return false;
                    }
                }

                if(i == arguments.length-1){
                    return true;
                }else if(j == arguments.length-1){
                    i =index(arguments, i+1);
                    j=i+1;
                    continue outer;
                      }
                ++j;
            }
        }
        return true;
    }

    public Boolean containsType(Type[] arguments, Environment gam) {
            for(int i =0; i!= arguments.length; ++i){
                if(arguments[i].ContainsTrcType(gam) || arguments[i].ContainsRef(gam).first()){
                   //System.out.println("\n\n type "+arguments[i].toString()+" \n");
                    return false;
                }
            }
            return true;
    }



    /*****************************************************************************************************
     *                                  CARRY TYPE: to a given sequence of expressions.
     ****************************************************************************************************/
    /**
     *
     * @param gam
     * @param lifetime
     * @param expressions
     * @return
     */
    public Pair<Environment,Type[]> carry(Environment gam, Lifetime lifetime, Syntax.Expression[] expressions, int k) {
        String[] vars = BorrowChecker.fresh(expressions.length);
        Type[] types = new Type[expressions.length];
        Environment gamn = gam;
        // Type each element individually
        for(int i=0;i!=expressions.length;++i) {
            Syntax.Expression ith = expressions[i];
            // Type left-hand side
            Pair<Environment, Type> p1 = apply(gamn, lifetime, ith, k);
            Type Tn = p1.second();
            gamn = p1.first();
            // Add type into environment temporarily
            gamn = gamn.put(vars[i], Tn, lifetime.getRoot());
            //
            types[i] = p1.second();
        }
        // Remove all temporary types
        Environment gam2 = gamn.remove(vars);
        // Done
        return new Pair<>(gam2,types);
    }
    /*****************************************************************************************************
     *****************************************************************************************************/
    protected void check(Boolean check, String msg) throws ExceptionsMSG {

        if(check){
                throw new ExceptionsMSG("\n "+msg+" \n");
        }
    }
    /**
     * Provides a specific extension mechanism for the borrow checker.
     *
     */
    public abstract static class Extension implements ReductionRule.Extension<Environment, Type> {
        public BorrowChecker self;
    }
    /******************* JOIN eNVIRONMENT ***********************************************/
    private Environment join(Environment gam1, Environment gam2, Syntax.Expression expression, int k) {
        Set<String> gam1Variables = gam1.bindings();
        Set<String> gam2Variables = gam2.bindings();
        /** verify if gam1Variables and gam2Variables have the same variables
         */
        //if no
        try {
            check(!compareInt(gam1Variables.size(),gam2Variables.size()), "Invalid Environments!");
        } catch (ExceptionsMSG e) {
            throw new RuntimeException(e);
        }
        // if yes, join the two environments
        for(String var : gam1Variables) {
            Location _v1 = gam1.get(var);
            Location _v2 = gam2.get(var);
            /**
             * have the same lifetime?
             */
            try {
                check(!compare(_v1.getLifetime(),_v2.getLifetime()), "The locations are not valid when you join the environment.");
            } catch (ExceptionsMSG e) {
                throw new RuntimeException(e);
            }
            // compute the union type
            Type type = _v1.getType().union(_v2.getType());
            // Done
            gam1 = gam1.put(var, type, _v1.getLifetime());
        }
        return gam1;
    }

    public static Boolean compare(Lifetime l1, Lifetime l2){
        return l1 == l2;
    }
    public static Boolean compareInt(int l1, int l2){
        return l1 == l2;
    }
}

package fr.univorleans.mssl.DynamicSyntax;

import java.util.HashMap;

public class Syntax {

    /**
     * code for each expression
     */
    public final static int Let_Expression = 1;
    public final static int Assignment_Expression=2;
    public final static int Block_Expression=3;
    public final static int Dereference_Expression=4;

    public final static int Borrow_Expression=9;

    public final static int Box_Expression=10;

    public final static int Trc_Expression=11;

    public final static int Clone_Expression=12;

    public final static int Invoke_Expression=13;
    public final static int Cooperate_Expression=14;
    public final static int Declaration_Function=15;

    public final static int Tuples_Expression=16;

    public final static int Print_Expression=17;
    public final static int Sig_Expression=18;

    public final static int Emit_Expression=19;

    public final static int When_Expression=20;

    public final static int Watch_Expression=21;

    public final static int Conditional_Expression=22;

    public final static int IfElse_Expression=23;

    public interface Expression {
        public int getOpcode();

        /**
         * Represents a variable declaration of the form:
         *
         * <pre>
         * let mut x = e
         * </pre>
         */
        public class Declaration implements Expression {
            private String variable;
            private Expression initialiser;

            private int opcode = Let_Expression;

            public Declaration(String variable, Expression initialiser) {
                this.variable = variable;
                this.initialiser = initialiser;
            }

            public Declaration() {
            }

            @Override
            public int getOpcode() {
                return opcode;
            }

            public String getVariable() {
                return variable;
            }

            public Expression getInitialiser() {
                return initialiser;
            }

            @Override
            public String toString() {
                return "let mut " + variable + " = " + initialiser;
            }
        }

        public class IfElse implements Expression {
            private Expression conditions;
            private Block ifblock;

            private Block elseblock;

            public IfElse(Expression conditions, Block ifblock, Block elseblock) {
                this.conditions = conditions;
                this.ifblock = ifblock;
                this.elseblock = elseblock;
            }

            public Expression getConditions() {
                return conditions;
            }

            public Block getIfblock() {
                return ifblock;
            }

            public Block getElseblock() {
                return elseblock;
            }

            @Override
            public int getOpcode() {
                return IfElse_Expression;
            }
            @Override
            public String toString() {
                return "if("+this.getConditions().toString() + ")" + this.getIfblock().toString() + this.getElseblock().toString();
            }
        }

        public class Sig implements Expression{
            public String variable;

            public Sig(String variable) {
                this.variable = variable;
            }

            public String getVariable() {
                return variable;
            }

            public void setVariable(String variable) {
                this.variable = variable;
            }

            @Override
            public int getOpcode() {
                return Sig_Expression;
            }

            @Override
            public String toString() {
                return "Sig "+getVariable();
            }
        }

        public class Emit implements Expression{
            public String variable;

            public Emit(String variable) {
                this.variable = variable;
            }

            public String getVariable() {
                return variable;
            }

            public void setVariable(String variable) {
                this.variable = variable;
            }

            @Override
            public int getOpcode() {
                return Emit_Expression;
            }

            @Override
            public String toString() {
                return "emit( "+getVariable() +")";
            }
        }

        /**
         * when (s) e;
         */
        public class When implements Expression{
            public String variable;
            public Expression operand;

            public When(String variable, Expression operand) {
                this.variable = variable;
                this.operand=operand;
            }

            public String getVariable() {
                return variable;
            }

            public void setVariable(String variable) {
                this.variable = variable;
            }

            public Expression getOperand() {
                return operand;
            }

            public void setOperand(Expression operand) {
                this.operand = operand;
            }

            @Override
            public int getOpcode() {
                return When_Expression;
            }

            @Override
            public String toString() {
                return "when("+getVariable()+")"+getOperand();
            }
        }

        /**
         * when (s) e;
         */
        public class Watch implements Expression{
            public String variable;
            public Expression operand;

            public Watch(String variable, Expression operand) {
                this.variable = variable;
                this.operand=operand;
            }

            public String getVariable() {
                return variable;
            }

            public void setVariable(String variable) {
                this.variable = variable;
            }

            public Expression getOperand() {
                return operand;
            }

            public void setOperand(Expression operand) {
                this.operand = operand;
            }

            @Override
            public int getOpcode() {
                return Watch_Expression;
            }

            @Override
            public String toString() {
                return "watch("+getVariable()+")"+getOperand();
            }
        }

        /**
         * Represents an assignment such as the following:
         * w = e
         */
        public class Assignment implements Expression {
            public Lval lval;
            public Expression expr;

            private int opcode = Assignment_Expression;

            public Assignment() {
            }

            public Assignment(Lval lval, Expression expr) {
                this.lval = lval;
                this.expr = expr;
            }

            @Override
            public int getOpcode() {
                return opcode;
            }

            public Lval getLval() {
                return lval;
            }

            public Expression getExpr() {
                return expr;
            }

            @Override
            public String toString() {
                return lval + " = " + expr;
            }
        }

        /**
         * e==e
         */
        public class Conditional implements Expression {
            public Expression lftoperand;
            public Expression rghtoperand;

            public String operator;
            private int opcode = Conditional_Expression;

            public Conditional(Expression lftoperand, Expression rghtoperand, String operator) {
                this.lftoperand = lftoperand;
                this.rghtoperand = rghtoperand;
                this.operator = operator;
            }

            public Expression getLftoperand() {
                return lftoperand;
            }

           public Expression getRghtoperand() {
                return rghtoperand;
            }

            public String getOperator() {
                return operator;
            }


            @Override
            public int getOpcode() {
                return opcode;
            }


            @Override
            public String toString() {
                return this.getLftoperand() + this.getOperator() + this.getRghtoperand();
            }
        }

        /**
         * Represents a group of statements, such as the following:
         * { }^l
         * { let mut x = 1; }^l
         */
        public class Block implements Expression {
            private Lifetime lifetime;
            private Expression[] exprs;

            /**
             * needed to compile to c
             */
            private HashMap<String, Type> variablesType;

            private int opcode = Block_Expression;

            public Block(Lifetime lifetime, Expression[] exprs) {
                this.lifetime = lifetime;
                this.exprs = exprs;
                this.variablesType= new HashMap<>();
            }

            /**
             * variableType:put, return, remove
             */

            public void put(String name, Type type){
                this.variablesType.put(name,type);
            }

            public void put(HashMap<String,Type> map){
                this.variablesType=map;
            }

            public HashMap<String, Type> variablesType(){
                return this.variablesType;
            }

            public void remove(){
                this.variablesType.clear();
            }
            public Block() {

            }

            public Lifetime getLifetime() {
                return lifetime;
            }

            @Override
            public int getOpcode() {
                return opcode;
            }

            public void setLifetime(Lifetime lifetime) {
                this.lifetime = lifetime;
            }

            public Expression[] getExprs() {
                return exprs;
            }

            public Expression get(int i) {
                return exprs[i];
            }

            public int length() {
                return exprs.length;
            }

            @Override
            public String toString() {
                String contents = "";
                if (exprs != null) {
                    for (int i = 0; i != exprs.length; ++i) {
                        if (i != 0) {
                            contents += " ; ";
                        }
                        contents += exprs[i];
                    }
                }
                return "{ " + contents + " }@" + lifetime;
            }


        }

        public class Access implements Expression {

            private Kind kind;
            private final Lval slice;
            private int opcode = Dereference_Expression;

            public enum Kind {
                /**
                 * Forces a move
                 */
                MOVE,
                /**
                 * Forces a copy
                 */
                COPY,
                /**
                 * Represents neither copy nor move!
                 */
                TEMP,
            }

            public Access(Kind kind, Lval lv) {
                super();
                this.kind = kind;
                this.slice = lv;
            }

            @Override
            public int getOpcode() {
                return opcode;
            }


            /**
             * Determine whether this term is demarked as performing a copy of the value in
             * question.
             *
             * @return
             */
            public boolean copy() {
                return kind == Kind.COPY;
            }

            /**
             * Determine whether this term is for a short-lived (i.e. temporary) value.
             *
             * @return
             */
            public boolean temporary() {
                return kind == Kind.TEMP;
            }

            /**
             * Get the LVal being read.
             *
             * @return
             */
            public Lval operand() {
                return slice;
            }

            /**
             * Infer the kind of operation this dereference corresponds to.
             *
             * @param kind
             */
            public void infer(Kind kind) {
                this.kind = kind;
            }

            @Override
            public String toString() {
                if (kind == Kind.COPY) {
                    return "^" + slice;
                } else {
                    return slice.toString();
                }
            }

            public static Access construct(Lval lv, Boolean b) {
                Kind kind = b == true ? Kind.MOVE : Kind.COPY;
                return new Access(kind, lv);
            }


            /**
             * specific for borrow checker
             */

            /**
             * copy inference
             *
             * @return
             */
            public boolean isCopy() {

                return this.copy();
            }
        }


        public class Borrow implements Expression {
            private final Lval operand;
            private final boolean mutable;

            private int opcode = Borrow_Expression;

            public Borrow(Lval operand, boolean mutable) {
                this.operand = operand;
                this.mutable = mutable;
            }

            public Lval getOperand() {
                return operand;
            }

            public boolean isMutable() {
                return mutable;
            }

            public int getOpcode() {
                return opcode;
            }

            @Override
            public String toString() {
                if (mutable) {
                    return "&mut " + operand;
                } else {
                    return "&" + operand;
                }
            }
        }


        public class Box implements Expression {
            public Expression operand;
            private static int opcode = Box_Expression;

            public Box(Expression operand) {
                this.operand = operand;
            }

            public Expression getOperand() {
                return operand;
            }

            @Override
            public int getOpcode() {
                return opcode;
            }

            @Override
            public String toString() {
                return "box(" + operand + ")";
            }
        }

        public class Trc implements Expression {
            public Expression operand;
            private static int opcode = Trc_Expression;

            public Trc(Expression operand) {
                this.operand = operand;
            }

            public Expression getOperand() {
                return operand;
            }


            @Override
            public int getOpcode() {
                return opcode;
            }

            @Override
            public String toString() {
                return "trc(" + operand + ")";
            }
        }

        public class Clone implements Expression {
            private final Lval operand;

            private int opcode = Clone_Expression;

            public Clone(Lval operand) {
                this.operand = operand;
            }

            public Lval getOperand() {
                return operand;
            }

            public int getOpcode() {
                return opcode;
            }

            @Override
            public String toString() {
                return operand + ".clone";

            }
        }

        public class Variable implements Expression {
            public String variable;

            public Variable(String variable) {
                this.variable = variable;
            }

            public Variable() {
            }

            public String getVariable() {
                return variable;
            }

            @Override
            public int getOpcode() {
                return 0;
            }

            public String toString() {
                return this.getVariable();
            }
        }

        public class Cooperate implements Expression {
            private final static String name = "cooperate";
            private final int opcode = Cooperate_Expression;

            public Cooperate() {
            }


            public String cooperate() {
                return name;
            }

            @Override
            public int getOpcode() {
                return opcode;
            }

            public String toString() {

                return name + ";";
            }
        }

        public class Print implements Expression{
            private final Lval slice;

            public Print(Lval slice) {
                super();
                this.slice = slice;
            }

            @Override
            public int getOpcode() {
                return Print_Expression;
            }

            public Lval lval() {
                return slice;
            }

            public String toString() {

                return "print!("+lval().name()+")";
            }
        }


    }

    }

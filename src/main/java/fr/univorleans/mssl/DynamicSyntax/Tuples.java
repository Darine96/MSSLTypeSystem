package fr.univorleans.mssl.DynamicSyntax;

import fr.univorleans.mssl.DynamicSyntax.Syntax.Expression;
import fr.univorleans.mssl.SOS.Pair;
import fr.univorleans.mssl.SOS.StoreProgram;
import fr.univorleans.mssl.TypeSystem.BorrowChecker;
import fr.univorleans.mssl.TypeSystem.Environment;

import java.util.Arrays;

import static fr.univorleans.mssl.DynamicSyntax.Syntax.Tuples_Expression;

public class Tuples {


    /**
     * Semantics
     */
    public static class TuplesExpression implements Expression {
        protected final Expression[] expressions;
        private int opcode = Tuples_Expression;
        public TuplesExpression(Expression[] expressions) {
            this.expressions = expressions;
        }

        public Expression[] getExpressions() {
            return expressions;
        }

        public int size() {
            return expressions.length;
        }

        public Expression get(int i) {
            return (Expression) expressions[i];
        }

        @Override
        public boolean equals(Object o) {
            if(o instanceof TuplesExpression) {
                TuplesExpression p = (TuplesExpression) o;
                return Arrays.equals(expressions, p.expressions);
            }
            return false;
        }


        @Override
        public String toString() {
            String r = "";
            for(int i=0;i!=expressions.length;++i) {
                if(i != 0) {
                    r += ",";
                }
                r += expressions[i];
            }
            return "(" + r + ")";
        }


        @Override
        public int getOpcode() {
            return opcode;
        }
    }


    public static class TuplesValue extends TuplesExpression implements Value{
        public TuplesValue(Expression[] expressions) {
            super(expressions);
        }

        public TuplesValue(int index, Expression... expressions) {
            super(expressions);
        }

        @Override
        public Value read(int[] path, int index) {
            // Corresponding element should be an index
            if(index == path.length) {
                return this;
            } else {
                /**
                 Extract element index being read
                 */
                int i = path[index];
                /** Select element
                 *
                 */
                Value t = (Value) expressions[i];
                /**
                 * Read element
                 */
                return (t == null) ? t : t.read(path, index + 1);
            }
        }

        @Override
        public Value write(int[] path, int index, Value value) {
            if (index == path.length) {
                return value;
            } else {
                //
                Expression[] nterms = Arrays.copyOf(expressions, expressions.length);
                /** Extract element index being written
                 *
                 */
                int i = path[index];
                Value n = (Value) expressions[i];
                /** Write updated element
                 *
                 */
                nterms[i] = (n == null) ? value : n.write(path, index + 1, value);
                //Done
                return new TuplesValue(nterms);
            }
        }
    }

    /**
     * TuplesType
     */
    public static class TuplesType extends Type.AbstractType{
        private final Type[] types;
        public TuplesType(Type[] types) {
            this.types = types;
        }

        public Type get(int i) {
            return types[i];
        }

        public Type[] getTypes() {
            return types;
        }

        /**
         * verifier si la durée de vie est correcte pour éviter le dangling pointer
         * @param self
         * @param R
         * @param l
         * @return
         */
        @Override
        public boolean within(BorrowChecker self, Environment R, Lifetime l) {
            for(int i=0;i!=types.length;++i) {
                if(!types[i].within(self, R, l)) {
                    return false;
                }
            }
            return true;
        }

        /**
         * déduire la semantique d'un tuple
         * @return
         */
        @Override
        public boolean copyable() {
            for(int i=0;i!=types.length;++i) {
                if(!types[i].copyable()) {
                    return false;
                }
            }
            return true;
        }

        /**
         * determiner si ce tuple is well-typed
         * @return
         */
        @Override
        public boolean defined() {
            for(int i=0;i!=types.length;++i) {
                if(!types[i].defined()) {
                    return false;
                }
            }
            return true;
        }

        /**
         * determine if this tuple is read prohibited
         * e.g. (&mut x, &mut x)
         * @param lv
         * @return
         */
        @Override
        public boolean prohibitsReading(Lval lv) {
            for(int i=0;i!=types.length;++i) {
                if(types[i].prohibitsReading(lv)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * determine if this tuple is read prohibited
         * e.g. (&mut x, & x)
         * @param lv
         * @return
         */
        @Override
        public boolean prohibitsWriting(Lval lv) {
            for(int i=0;i!=types.length;++i) {
                if(types[i].prohibitsWriting(lv)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * union des tuples: notez qu'ils doivent être de même taille
         * @param type
         * @return
         */
        @Override
        public Type union(Type type) {
            if(type instanceof Undefined) {
                return type.union(this);
            } else if (type instanceof TuplesType) {
                TuplesType p = (TuplesType) type;
                if(types.length == p.types.length) {
                    Type[] ts = new Type[types.length];
                    for(int i=0;i!=ts.length;++i) {
                        ts[i] = types[i].union(p.types[i]);
                    }
                    // Recursively join components.
                    return new TuplesType(ts);
                }
            }
            throw new IllegalArgumentException("Invalid Union!");
        }

        /**
         * determine if this tuple contains a trc type
         */
        public Boolean containsTrc(){
            for (int i = 0; i!=types.length; ++i){
                if(types[i] instanceof Trc){
                    return true;
                }
            }
            return false;
        }

        /**
         * retourner un type "mové"
         * @return
         */
        @Override
        public Undefined undefine() {
            return new Undefined(this);
        }

        @Override
        public String toString() {
            String r = "";
            for(int i=0;i!=types.length;++i) {
                if(i != 0) {
                    r += ",";
                }
                r += types[i];
            }
            return "(" + r + ")";
        }
    }

    /**
     * TupleIndex
     */
    public static class TuplesIndex implements Path.Element{
        private final int index;

        public TuplesIndex(int index) {
            this.index = index;
        }


        public int getIndex() {
            return index;
        }

        @Override
        public int compareTo(Path.Element arg0) {
            if(arg0 instanceof TuplesIndex){
                TuplesIndex i = (TuplesIndex) arg0;
                return Integer.compare(index, i.index);
            }else {
                throw new IllegalArgumentException("cannot compare path elements!");
            }
        }

        @Override
        public boolean conflicts(Path.Element e) {
            if (e instanceof TuplesIndex) {
                TuplesIndex i = (TuplesIndex) e;
                return index == ((TuplesIndex) e).index;
            } else {
                throw new IllegalArgumentException("cannot compare path elements!");
            }
        }
        @Override
        public String toString() {
            return Integer.toString(index);
        }

        @Override
        public Value.Reference apply(StoreProgram.Store store, Value.Reference loc) {

            return loc.at(index);
        }
        @Override
        public String toString(String src) {
            return src + "." + index;
        }
        @Override
        public Pair<Type, Lifetime> apply(Environment env, Type T, Lifetime l) {
            if (T instanceof TuplesType) {
                TuplesType t = (TuplesType) T;
                Type[] ts = t.types;
                if (index < ts.length) {
                    return new Pair<>(ts[index], l);
                }
            }
            return null;
        }

        @Override
        public boolean equals(Object o) {
            if(o instanceof TuplesIndex) {
                TuplesIndex i = (TuplesIndex) o;
                return index == i.index;
            }
            return false;
        }
    }
}

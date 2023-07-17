package fr.univorleans.mssl.DynamicSyntax;

import fr.univorleans.mssl.SOS.Pair;
import fr.univorleans.mssl.TypeSystem.BorrowChecker;
import fr.univorleans.mssl.TypeSystem.Environment;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public interface Signature {

    /**
     * (1) Lower
     */
    public Pair<Environment, Type> lower(Environment gam, HashMap<String, Lifetime> suitable, Lifetime l);

    /**
     * (2) lifting
     */
    public Type lift(Map<Signature.Borrow, Type.Borrow> lifting);
    /**
     * (3) isSubtyping
     */
    /**
     * Check whether a given type is a subtype of this signature,under a given
     * binding or not.
     *
     * @param type
     */
    public boolean isSubtype(Environment env, Type type, Map<String, Lifetime> suitable);

    public boolean isSubtype(Map<String, Lifetime> suitbale, Signature signature);

    public boolean isSupertype(Environment env, Type type, Map<String, Lifetime> suitable);
    public <T extends Signature> void match(Class<T> kind, Predicate<T> matcher);


    /**
     * (4) number of reference
     */
    public int refcount();

    /**
     * (5) contains a Trc
     */
    public boolean containsTrc();

    /**
     * (6) position of a trc in the signature
     */
    public int posTrc();
    public static class Unit implements Signature{
        @Override
        public String toString() {
            return "void";
        }
        @Override
        public boolean equals(Object o) {
            return o instanceof Unit;
        }

        @Override
        public Pair<Environment, Type> lower(Environment gam, HashMap<String, Lifetime> suitable, Lifetime l) {
            return new Pair<>(gam, Type.Unit);

        }

        @Override
        public Type lift(Map<Signature.Borrow, Type.Borrow> lifting) {
            return Type.Unit;
        }

        @Override
        public int refcount() {
            return 0;
        }

        @Override
        public boolean containsTrc() {
            return false;
        }

        @Override
        public int posTrc() {
            return 0;
        }

        @Override
        public boolean isSubtype(Environment env, Type type, Map<String, Lifetime> suitable) {
            return type instanceof Type.Unit;
        }

        @Override
        public boolean isSubtype(Map<String, Lifetime> suitbale, Signature signature) {
            return signature instanceof Signature.Unit;
        }

        @Override
        public boolean isSupertype(Environment env, Type type, Map<String, Lifetime> suitable) {
            return type instanceof Type.Unit;
        }
        @SuppressWarnings("unchecked")
        @Override
        public <T extends Signature> void match(Class<T> kind, Predicate<T> matcher) {
            if(kind.isInstance(this)) {
                matcher.test((T) this);
            }
        }

    }

    public static class Int implements Signature{
        public Int() {
        }

        @Override
        public String toString() {
            return "int";
        }
        @Override
        public boolean equals(Object o) {
            return o instanceof Int;
        }

        @Override
        public Pair<Environment, Type> lower(Environment gam, HashMap<String, Lifetime> suitable, Lifetime l) {
            return new Pair<>(gam, Type.Int);

        }

        @Override
        public Type lift(Map<Signature.Borrow, Type.Borrow> lifting) {
            return Type.Int;
        }

        @Override
        public boolean isSubtype(Environment env, Type type, Map<String, Lifetime> suitable) {

            return type instanceof Type.Int;
        }

        @Override
        public boolean isSubtype(Map<String, Lifetime> suitbale, Signature signature) {
            return signature instanceof Signature.Int;
        }

        @Override
        public boolean isSupertype(Environment env, Type type, Map<String, Lifetime> suitable) {
            return type instanceof Type.Int;
        }
        @Override
        public int refcount() {
            return 0;
        }

        @Override
        public boolean containsTrc() {
            return false;
        }

        @Override
        public int posTrc() {
            return 0;
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T extends Signature> void match(Class<T> kind, Predicate<T> matcher) {
            if(kind.isInstance(this)) {
                matcher.test((T) this);
            }
        }
    }


    public static class Bool implements Signature{
        public Bool() {
        }

        @Override
        public String toString() {
            return "bool";
        }
        @Override
        public boolean equals(Object o) {
            return o instanceof Bool;
        }

        @Override
        public Pair<Environment, Type> lower(Environment gam, HashMap<String, Lifetime> suitable, Lifetime l) {
            return new Pair<>(gam, Type.Bool);

        }

        @Override
        public Type lift(Map<Signature.Borrow, Type.Borrow> lifting) {
            return Type.Bool;
        }

        @Override
        public boolean isSubtype(Environment env, Type type, Map<String, Lifetime> suitable) {

            return type instanceof Type.Bool;
        }

        @Override
        public boolean isSubtype(Map<String, Lifetime> suitbale, Signature signature) {
            return signature instanceof Signature.Bool;
        }

        @Override
        public boolean isSupertype(Environment env, Type type, Map<String, Lifetime> suitable) {
            return type instanceof Type.Bool;
        }
        @Override
        public int refcount() {
            return 0;
        }

        @Override
        public boolean containsTrc() {
            return false;
        }

        @Override
        public int posTrc() {
            return 0;
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T extends Signature> void match(Class<T> kind, Predicate<T> matcher) {
            if(kind.isInstance(this)) {
                matcher.test((T) this);
            }
        }
    }
    public static class Box implements Signature{
        private final Signature operand;

        public Box(Signature element) {
            this.operand = element;
        }

        public Signature getOperand() {
            return operand;
        }
        @Override
        public String toString() {
            return "box<"+operand.toString()+">";
        }

        @Override
        public boolean equals(Object o) {
            if(o instanceof Box) {
                Box b = (Box) o;
                return operand.equals(b.operand);
            }
            return false;
        }

        @Override
        public int refcount() {
            return 1+operand.refcount();
        }

        @Override
        public boolean containsTrc() {
            return operand.containsTrc();
        }
        @Override
        public int posTrc() {
            return 1+operand.posTrc();
        }

        @Override
        public Pair<Environment, Type> lower(Environment gam, HashMap<String, Lifetime> suitable,  Lifetime l) {
            Pair<Environment, Type> p = operand.lower(gam, suitable, l);
            return new Pair<>(p.first(), new Type.Box(p.second()));
        }

        @Override
        public Type lift(Map<Signature.Borrow, Type.Borrow> lifting) {
            return new Type.Box(operand.lift(lifting));
        }

        @Override
        public boolean isSubtype(Environment env, Type type, Map<String, Lifetime> suitable) {
            return (type instanceof Type.Box)&& operand.isSubtype(env, ((Type.Box) type).getType(), suitable);
        }

        @Override
        public boolean isSubtype(Map<String, Lifetime> binding, Signature sig) {
            return (sig instanceof Signature.Box) && operand.isSubtype(binding, ((Signature.Box) sig).operand);
        }

        @Override
        public boolean isSupertype(Environment env, Type type, Map<String, Lifetime> suitable) {
            return (type instanceof Type.Box)&& operand.isSupertype(env, ((Type.Box) type).getType(), suitable);
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T extends Signature> void match(Class<T> kind, Predicate<T> matcher) {
            if(kind.isInstance(this) && !matcher.test((T) this)) {
                // Continue searcher
                operand.match(kind, matcher);
            }
        }


    }


    public static class Trc implements Signature{
        private final Signature operand;

        public Trc(Signature element) {
            this.operand = element;
        }

        public Signature getOperand() {
            return operand;
        }
        @Override
        public String toString() {
            return "trc<"+operand.toString()+">";
        }

        @Override
        public int refcount() {
            return 1+operand.refcount();
        }

        @Override
        public boolean containsTrc() {
            return true;
        }

        @Override
        public int posTrc() {
            return 1;
        }
        @Override
        public boolean equals(Object o) {
            if(o instanceof Trc) {
                Trc b = (Trc) o;
                return operand.equals(b.operand);
            }
            return false;
        }

        @Override
        public Pair<Environment, Type> lower(Environment gam, HashMap<String, Lifetime> suitable, Lifetime l) {
            Pair<Environment, Type> p = operand.lower(gam, suitable, l);
            return new Pair<>(p.first(), new Type.Trc(p.second()));
        }

        @Override
        public Type lift(Map<Signature.Borrow, Type.Borrow> lifting) {
            return new Type.Trc(operand.lift(lifting));
        }

        /* @Override
       * public boolean isSubtype(Environment env, Type type) {
             if(type instanceof Type.Clone){
             if(type instanceof Type.Clone){
                 return true;
             }else {
                 return false;
             }
         }*/
        @Override
        public boolean isSubtype(Environment env, Type type, Map<String, Lifetime> suitable) {
            return (type instanceof Type.Trc)&& operand.isSubtype(env, ((Type.Trc) type).getType(), suitable);
        }

        @Override
        public boolean isSubtype(Map<String, Lifetime> binding, Signature sig) {
            return (sig instanceof Signature.Trc) && operand.isSubtype(binding, ((Signature.Trc) sig).operand);
        }

        @Override
        public boolean isSupertype(Environment env, Type type, Map<String, Lifetime> suitable) {
            return (type instanceof Type.Trc)&& operand.isSupertype(env, ((Type.Trc) type).getType(), suitable);
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T extends Signature> void match(Class<T> kind, Predicate<T> matcher) {
            if (kind.isInstance(this) && !matcher.test((T) this)) {
                // Continue searcher
                operand.match(kind, matcher);
            }
        }
    }

    public static class Clone implements Signature{
        private final Signature operand;

        public Clone(Signature element) {
            this.operand = element;
        }

        public Signature getOperand() {
            return operand;
        }
        @Override
        public String toString() {
            return "clone<"+operand.toString()+">";
        }

        @Override
        public boolean equals(Object o) {
            if(o instanceof Clone) {
                Clone b = (Clone) o;
                return operand.equals(b.operand);
            }
            return false;
        }

        @Override
        public int refcount() {
            return 1+operand.refcount();
        }

        @Override
        public boolean containsTrc() {
            return true;
        }

        @Override
        public int posTrc() {
            return 1;
        }
        @Override
        public Pair<Environment, Type> lower(Environment gam, HashMap<String, Lifetime> suitable, Lifetime l) {
            //(1) ensure that l outlives the lifetime of this borrow
            /** necessary to instatiate a concret lifetime (l_1 within the root lifetime)
             * to the abstract lifetime ('a, 'b) => result binding = [('a, l_1), ('b, l_2)] where
             * l_1< lroot and l_2<lroot
             **/
            Lifetime _l = instantiateTargetLifetime(l);
            //(2) recursively compute suitable type
            Pair<Environment, Type> p = operand.lower(gam, suitable, _l);
            //(3) create a fresh lifetime
            String fresh = BorrowChecker.fresh();
            //(4) put into gam
            Environment gam2 = p.first().put(fresh, p.second(), _l);
            // Done
            return new Pair<>(gam2, new Type.Clone(new Lval(fresh, Path.EMPTY)));
        }

        private Lifetime instantiateTargetLifetime(Lifetime lft) {
           // Target lifetime must be within root
            Lifetime  _l = new Lifetime(lft.getRoot());
            // Done
            return _l;
        }
        @Override
        public Type lift(Map<Signature.Borrow, Type.Borrow> lifting) {
            return lifting.get(this);
        }

        @Override
        public boolean isSubtype(Environment env, Type type, Map<String, Lifetime> suitable) {
            if (type instanceof Type.Clone) {
                Type.Clone b = (Type.Clone) type;
                for (Lval lv : b.lvals()) {
                    Pair<Type, Lifetime> p = lv.typeOf(env);
                    Type T = p.first();
                    return  operand.isSubtype(env, T, suitable);

                }

                }
            return false;
            }

        @Override
        public boolean isSubtype(Map<String, Lifetime> binding, Signature sig) {
            return (sig instanceof Signature.Clone) && operand.isSubtype(binding, ((Signature.Clone) sig).operand);
        }

        @Override
        public boolean isSupertype(Environment env, Type type, Map<String, Lifetime> suitable) {
            if (type instanceof Type.Clone) {
                Type.Clone b = (Type.Clone) type;
                for (Lval lv : b.lvals()) {
                    Pair<Type, Lifetime> p = lv.typeOf(env);
                    Type T = p.first();
                    return  operand.isSupertype(env, T, suitable);

                }

            }
            return false;
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T extends Signature> void match(Class<T> kind, Predicate<T> matcher) {
            if (kind.isInstance(this) && !matcher.test((T) this)) {
                // Continue searcher
                operand.match(kind, matcher);
            }
        }
    }


    public class Borrow implements Signature{
        private final String lifetime;
        private final boolean mutable;
        private final Signature signature;

        public Borrow(String lifetime, boolean mutable, Signature signature) {
            this.lifetime = lifetime;
            this.mutable = mutable;
            this.signature = signature;
        }

        public String getLifetime() {
            return lifetime;
        }

        public boolean isMutable() {
            return mutable;
        }

        public Signature getSignature() {
            return signature;
        }

        @Override
        public Pair<Environment, Type> lower(Environment gam, HashMap<String, Lifetime> suitable, Lifetime l) {
            //(1) ensure that l outlives the lifetime of this borrow
            /** necessary to instatiate a concret lifetime (l_1 within the root lifetime)
            * to the abstract lifetime ('a, 'b) => result binding = [('a, l_1), ('b, l_2)] where
             * l_1< lroot and l_2<lroot
             **/
            Lifetime _l = instantiateTargetLifetime(suitable, l);
            //(2) recursively compute suitable type
            Pair<Environment, Type> p = signature.lower(gam, suitable, _l);
            //(3) create a fresh lifetime
            String fresh = BorrowChecker.fresh();
            //(4) put into gam
            Environment gam2 = p.first().put(fresh, p.second(), _l);
            // Done
            return new Pair<>(gam2, new Type.Borrow(mutable, new Lval(fresh, Path.EMPTY)));
        }

        private Lifetime instantiateTargetLifetime(Map<String, Lifetime> binding, Lifetime lft) {
            Lifetime _l = binding.get(lifetime);
            if (_l == null) {
                // Target lifetime must be within root
                _l = new Lifetime(lft.getRoot());
                binding.put(lifetime, _l);
            }
            lft.assertWithin(_l);
            // Done
            return _l;
        }

        @Override
        public Type lift(Map<Signature.Borrow, Type.Borrow> lifting) {
            return lifting.get(this);
        }

        @Override
        public boolean isSubtype(Environment R, Type type, Map<String, Lifetime> suitable) {
            if (type instanceof Type.Borrow) {
                Type.Borrow b = (Type.Borrow) type;
                if (b.isMutable() == mutable) {
                    Lifetime l = suitable.get(lifetime);
                    //
                    for (Lval lv : b.lvals()) {
                        /** We must determine if this is a suitable candidate for borrowing or not.
                         **/
                         Pair<Type, Lifetime> p = lv.typeOf(R);
                        Type T = p.first();
                        Lifetime m = p.second();
                       /** Verify the covariance and contravariance. **/
                        if (!m.contains(l) || !signature.isSubtype(R, T, suitable)) {
                            return false;
                        } else if(mutable && (!l.contains(m) || !signature.isSupertype(R, T, suitable))) {
                            // mutable borrows are invariant.
                            return false;
                        }
                    }
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean isSubtype(Map<String, Lifetime> binding, Signature sig) {
            if (sig instanceof Signature.Borrow) {
                Signature.Borrow bsig = (Signature.Borrow) sig;
                Lifetime l = binding.get(lifetime);
                Lifetime b = binding.get(bsig.lifetime);
                if (mutable && bsig.mutable) {
                    // mutable borrows are invariant
                    return b.contains(l) && l.contains(b) && signature.isSubtype(binding, bsig.signature)
                            && bsig.signature.isSubtype(binding, signature);
                } else if (!mutable && !bsig.mutable) {
                    return b.contains(l) && signature.isSubtype(binding, bsig.signature);
                }
            }
            return false;
        }


        @Override
        public boolean isSupertype(Environment R, Type type, Map<String, Lifetime> suitable ) {
            if (type instanceof Type.Borrow) {
                Type.Borrow b = (Type.Borrow) type;
                if (b.isMutable() == mutable) {
                    Lifetime l = suitable.get(lifetime);
                    //
                    for (Lval lv : b.lvals()) {
                        Pair<Type, Lifetime> p = lv.typeOf(R);
                        Type T = p.first();
                        Lifetime m = p.second();
                        /** Verify the covariance and contravariance. **/
                        if (!l.contains(m) || !signature.isSupertype(R, T, suitable)) {
                            return false;
                        } else if(mutable && (!m.contains(l) || !signature.isSubtype(R, T, suitable))) {
                            // mutable borrows are invariant.
                            return false;
                        }
                    }
                    return true;
                }
            }
            return false;
        }

        @Override
        public int refcount() {
            return 0;
        }

        @Override
        public boolean containsTrc() {
            return false;
        }

        @Override
        public int posTrc() {
            return 0;
        }

        @Override
        public String toString() {
            return "&'" + lifetime + (mutable ? " mut " : " ") + signature.toString();
        }

        @Override
        public boolean equals(Object o) {
            if(o instanceof Signature.Borrow) {
                Signature.Borrow _borrow = (Signature.Borrow) o;
                return mutable == _borrow.mutable && lifetime.equals(_borrow.lifetime) && signature.equals(_borrow.signature);
            }
            return false;
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T extends Signature> void match(Class<T> kind, Predicate<T> matcher) {
            if (kind.isInstance(this) && !matcher.test((T) this)) {
                // Continue searcher
                signature.match(kind, matcher);
            }
        }
    }
}

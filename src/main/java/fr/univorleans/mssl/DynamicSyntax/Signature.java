package fr.univorleans.mssl.DynamicSyntax;

import fr.univorleans.mssl.SOS.Pair;
import fr.univorleans.mssl.TypeSystem.Environment;

import java.util.Map;

public interface Signature {

    /**
     * (1) Lower
     */
    public Pair<Environment, Type> lower(Environment gam, Lifetime l);

    /**
     * (2) lifting
     */
    public Type lift(Map<Signature, Type.Borrow> lifting);
    /**
     * (3) isSubtyping
     */
    /**
     * Check whether a given type is a subtype of this signature,under a given
     * binding or not.
     *
     * @param type
     */
    public boolean isSubtype(Environment env, Type type);

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
        public Pair<Environment, Type> lower(Environment gam, Lifetime l) {
            return new Pair<>(gam, Type.Unit);

        }

        @Override
        public Type lift(Map<Signature, Type.Borrow> lifting) {
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
        public boolean isSubtype(Environment env, Type type) {
            return type instanceof Type.Unit;
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
        public Pair<Environment, Type> lower(Environment gam, Lifetime l) {
            return new Pair<>(gam, Type.Int);

        }

        @Override
        public Type lift(Map<Signature, Type.Borrow> lifting) {
            return Type.Int;
        }

        @Override
        public boolean isSubtype(Environment env, Type type) {
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
        public Pair<Environment, Type> lower(Environment gam, Lifetime l) {
            return new Pair<>(gam, Type.Bool);

        }

        @Override
        public Type lift(Map<Signature, Type.Borrow> lifting) {
            return Type.Bool;
        }

        @Override
        public boolean isSubtype(Environment env, Type type) {
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
        public Pair<Environment, Type> lower(Environment gam, Lifetime l) {
            Pair<Environment, Type> p = operand.lower(gam, l);
            return new Pair<>(p.first(), new Type.Box(p.second()));
        }

        @Override
        public Type lift(Map<Signature, Type.Borrow> lifting) {
            return new Type.Box(operand.lift(lifting));
        }

        @Override
        public boolean isSubtype(Environment env, Type type) {
            return (type instanceof Type.Box)&& operand.isSubtype(env, ((Type.Box) type).getType());
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
        public Pair<Environment, Type> lower(Environment gam, Lifetime l) {
            Pair<Environment, Type> p = operand.lower(gam, l);
            return new Pair<>(p.first(), new Type.Trc(p.second()));
        }

        @Override
        public Type lift(Map<Signature, Type.Borrow> lifting) {
            return new Type.Trc(operand.lift(lifting));
        }

        @Override
        public boolean isSubtype(Environment env, Type type) {
            if(type instanceof Type.Clone){
                return true;
            }else {
                return false;
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
        public Pair<Environment, Type> lower(Environment gam, Lifetime l) {
            return null;
        }

        @Override
        public Type lift(Map<Signature, Type.Borrow> lifting) {
            return lifting.get(this);
        }

        @Override
        public boolean isSubtype(Environment env, Type type) {
            return false;
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
        public Pair<Environment, Type> lower(Environment gam, Lifetime l) {
            return null;
        }

        @Override
        public Type lift(Map<Signature, Type.Borrow> lifting) {
            return null;
        }

        @Override
        public boolean isSubtype(Environment env, Type type) {
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
    }
}

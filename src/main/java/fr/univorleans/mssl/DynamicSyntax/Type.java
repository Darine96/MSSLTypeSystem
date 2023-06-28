package fr.univorleans.mssl.DynamicSyntax;

import fr.univorleans.mssl.SOS.Pair;
import fr.univorleans.mssl.TypeSystem.BorrowChecker;
import fr.univorleans.mssl.TypeSystem.Environment;
import fr.univorleans.mssl.TypeSystem.Location;

import java.util.Arrays;
import java.util.BitSet;

public interface Type {
     /**
     * Create an instance of the unit type
     */
    public static Type Unit = new Unit();
    /**
     * Create an instance of the Sig type
     */
    public static Type Sig = new Unit();
    /**
     * Create an instance of the int type
     */
    public static Type Int = new Int();

    /**
     * Create an instance of the bool type
     */
    public static Type Bool = new Bool();

    /**
     * Check whether this type can be copied or not. Some types (e.g. primitive
     * integers) can be copied whilst others (e.g. mutable borrows) cannot.
     *
     * @return
     */
    public boolean copyable();

    /**
     * Join two types together from different execution paths.
     *
     * @param type
     * @return
     */
    public Type union(Type type);
    /**
     * Check whether this type can safely live within a given lifetime.
     * Like subtype in Rust
     *
     * @param borrowChecker
     * @param gam
     * @param lifetime
     * @return
     */
    public boolean within(BorrowChecker borrowChecker, Environment gam, Lifetime lifetime);

    /**
     * Check whether a type is defined. That is, ensure it doesn't contain a shadow.
     * @param
     * @return
     */
    public boolean defined();

    public boolean TrcSafe(Environment gam);

    /**
     * find the equivalent undefined type
     * (i.e. update type)
     * @return
     */
    public Undefined undefine();

    /**
     * Determine whether this type prohibits a given lval from being read. For
     * example, if this type mutable borrows the lval then it cannot be read.
     *
     * @param lv lval being checked.
     * @return
     */
    public boolean prohibitsReading(Lval lv);

    /**
     * Determine whether this type prohibits a given lval from being written. For
     * example, if this type borrows the lval then it cannot be written.
     *
     * @param lv
     * @return
     */
    public boolean prohibitsWriting(Lval lv);
    /**
     * Determine whether this type prohibits a given lval from being moved. For
     * example, if this type takes the ownership of the lval where
     * this lval is cloned into the environment then it cannot be moved.
     *
     * @param lv
     * @return
     */
    public boolean prohibitsTrcMoving(Lval lv);

    /**
     * determine if a type contains a Trc type
     * @param gam
     * @return
     */
    public boolean ContainsTrc(Environment gam);

    /**
     * determine if this type contains a borrow type
     * @param gam
     * @return
     */
    public Pair<Boolean, Type> ContainsRef(Environment gam);

    /**
     *
     * @param name variable
     * @param i counter if *
     * @param free string to be return
     * @param pos position of the trc
     * @return
     */
    public String free(String name, int i, String free, int pos);


    /**
     * box(x.clone))
     * then return x.clone
     * @param gam
     * @return
     */
    public Type returnClone(Environment gam);

    /**
     * return position Trc
     * @param gam
     * @return
     */
    public int positionTrc(Environment gam);

    /**
     * get the counter of reference for a given type
     */
    public int refcount(Environment gam);

    /**
     * determine if two types point to the same memory
     * @param T2
     * @return
     */
    public boolean conflictTrc(Type T2);

    /**
     * return type of the refrence
     */
    public Type returnType(Environment gam, int count);

    public class AbstractType implements Type{
        @Override
        public boolean copyable() {
            return true;
        }

        //@Override
       public Type union(Type t) {
            if (t instanceof Undefined) {
                return t.union(this);
            } else if (equals(t)) {
                return this;
            } else {
                throw new IllegalArgumentException("invalid union");
            }
        }


        @Override
        public boolean within(BorrowChecker borrowChecker, Environment gam, Lifetime lifetime) {
            return true;
        }


        @Override
        public boolean defined() {
            return true;
        }

        @Override
        public boolean TrcSafe(Environment gam) {
            return true;
        }

        @Override
        public Undefined undefine() {
            return new Undefined(this);
        }


        @Override
        public boolean prohibitsReading(Lval lv) {
            return false;
        }

        @Override
        public boolean prohibitsWriting(Lval lv) {
            return false;
        }

        @Override
        public boolean prohibitsTrcMoving(Lval lv) {
            return false;
        }

        @Override
        public boolean ContainsTrc(Environment gam) {
            return false;
        }

        @Override
        public Pair<Boolean, Type> ContainsRef(Environment gam) {
            return new Pair<>(false, this);
        }

        @Override
        public String free(String name, int i, String free, int pos) {
            return free;
        }

        @Override
        public Type returnClone(Environment gam) {
            return null;
        }

        @Override
        public int positionTrc(Environment gam) {
            return 0;
        }

        @Override
        public int refcount(Environment gam) {
            return 0;
        }

        @Override
        public boolean conflictTrc(Type T2) {
            return false;
        }

        @Override
        public Type returnType(Environment gam, int count) {
            return this;
        }
    }

    public class Unit extends AbstractType{
        public Unit() {
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof Type.Unit;
        }
        @Override
        public String toString() {
            return "void";
        }
    }

    public class Sig extends AbstractType{
        public Sig() {
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof Type.Sig;
        }
        @Override
        public String toString() {
            return "Sig";
        }
    }

    public class Int extends AbstractType{
        public Int() {
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof Type.Int;
        }

        @Override
        public String toString() {
            return "int";
        }
    }

    public class Bool extends AbstractType{
        public Bool() {
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof Type.Bool;
        }

        @Override
        public String toString() {
            return "int";
        }
    }

    public class Box extends AbstractType{
        public final Type type;

        public Box(Type type) {
            this.type = type;
        }

        public Type getType() {
            return type;
        }

        @Override
        public boolean prohibitsReading(Lval lv) {
            return type.prohibitsReading(lv);
        }

        @Override
        public boolean ContainsTrc(Environment gam) {
            return type.ContainsTrc(gam);
        }

        @Override
        public Pair<Boolean, Type> ContainsRef(Environment gam) {
            return type.ContainsRef(gam);
        }
        @Override
        public Type returnClone(Environment gam) {
            return type.returnClone(gam);
        }


        public int positionTrc(Environment gam){
            return 1+type.positionTrc(gam);
        }

        @Override
        public int refcount(Environment gam) {
            return 1+ type.refcount(gam);
        }

        @Override
        public boolean prohibitsWriting(Lval lv) {
            return type.prohibitsWriting(lv);
        }

        @Override
        public boolean prohibitsTrcMoving(Lval lv) {
            return type.prohibitsTrcMoving(lv);
        }

        @Override
        public boolean TrcSafe(Environment gam) {
            return type.TrcSafe(gam);
        }


        @Override
        public boolean within(BorrowChecker borrowChecker, Environment gam, Lifetime l) {
            return type.within(borrowChecker, gam, l);
        }

        public Type returnType(Environment gam, int count){
            Type _type = this;
            if (count>=1){
                count -=1;
                return type.returnType(gam, count);
            }

            /*while(count>=1){
                count -=1;
                _type = type.returnType(gam, count);

            }*/
            return _type;
        }
        public boolean conflictTrc(Type T2){
            return type.conflictTrc(T2);
        }
        @Override
        public boolean copyable() {
            // NOTE: boxes always exhibit linear semantics.
            return false;
        }

        /**
         *  put free into the code c
         */

        @Override
        public String free(String name, int i, String free, int pos){

            if(free.contains("_destroy") || (free=="" && pos!=0)){
                if(pos==0 || free=="") {
                    free = "free(" + "*".repeat(i) + "(int " + "*".repeat(i + 1) + ")_get_value(" +name + "));" + free;
                }else{
                    free = "free(" + "*".repeat(i-pos) + "(int " + "*".repeat(i-pos+1) +")_get_value(" + "*".repeat(pos) + name + "));" + free;
                }
            }else{
                free = "free("+ "*".repeat(i)+name+");"+free;
            }
            i++;
            //System.out.println("\n free "+free+"\n");
            return type.free(name, i, free,pos);
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Box) {
                Box b = (Box) o;
                return type.equals(b.type);
            }
            return false;
        }

    }

    public class Trc extends AbstractType{
        public final Type type;

        public Trc(Type type) {
            this.type = type;
        }

        public Type getType() {
            return type;
        }

        @Override
        public boolean prohibitsReading(Lval lv) {
            return type.prohibitsReading(lv);
        }

        @Override
        public Pair<Boolean, Type> ContainsRef(Environment gam) {
            return type.ContainsRef(gam);
        }
        
        public int positionTrc(Environment gam){
            return 1;
        }

        @Override
        public int refcount(Environment gam) {
            return 1+ type.refcount(gam);
        }
        @Override
        public boolean prohibitsWriting(Lval lv) {
            return type.prohibitsWriting(lv);
        }

        @Override
        public boolean prohibitsTrcMoving(Lval lv) {
            return type.prohibitsTrcMoving(lv);
        }

        @Override
        public boolean TrcSafe(Environment gam) {
            return type.TrcSafe(gam);
        }

        @Override
        public String free(String name, int i, String free, int pos){
            free = "_destroy("+ "*".repeat(i)+name+");"+free;
            i++;
            //System.out.println("\n free "+free+"\n");
            return type.free(name, i, free, i-1);
        }

        @Override
        public boolean within(BorrowChecker borrowChecker, Environment gam, Lifetime l) {
            return type.within(borrowChecker, gam, l);
        }

        /**
         * trc(box(int)) return box(int) etc..
         * @param gam
         * @param count
         * @return
         */
        public Type returnType(Environment gam, int count){
            Type _type = this;
            if (count>=1){
                count -=1;
                return type.returnType(gam, count);
            }

            /*while(count>=1){
                count -=1;
                _type = type.returnType(gam, count);

            }*/
            return _type;
        }
        @Override
        public boolean copyable() {
            // NOTE: boxes always exhibit linear semantics.
            return false;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Trc) {
                Trc b = (Trc) o;
                return type.equals(b.type);
            }
            return false;
        }

    }


    public static class Clone extends AbstractType {
        private final Lval[] lvals;

        public Clone(Lval item) {
            this(new Lval[] { item });
        }
        public Clone(Lval[] paths) {
            assert paths.length > 0;
            this.lvals = paths;
            // Ensure sorted invariant
            Arrays.sort(paths);
        }

        @Override
        public boolean copyable() {
            // NOTE: mutable borrows have linear semantics.
            return false;
        }

        @Override
        public boolean within(BorrowChecker checker, Environment gam, Lifetime l) {
            boolean r = true;
            for (int i = 0; i != lvals.length; ++i) {
                Lval ith = lvals[i];
                if(gam.get(ith.name()) == null){
                    System.out.printf("\n .UNDECLARED_VARIABLE \n");
                }
                Location location = gam.get(ith.name());
                Type T1 = location.getType();
                // NOTE: this differs from the presentation as, in fact, we don't need to type
                // the lval fully.
                // is equivalent to r = r && location.getLifetime().contains(l)
                r &= T1.within(checker, gam, l);
            }
            return r;
        }

        public boolean ContainsTrc(Environment gam){
            return true;
        }

        public Type returnClone(Environment gam){
            return this;
        }

        public int positionTrc(Environment gam){
            return 1;
        }

        public Type returnType(Environment gam, int count){
            Lval lval =lvals[0];//is enough to test one lval
            String x = lval.name();
            Location location = gam.get(x);
            Type type = location.getType();
            if (count>=1){
                //count -=1;
                return type.returnType(gam, count);
            }

            /*while(count>=1){
                count -=1;
                _type = type.returnType(gam, count);

            }*/
            return type;
        }

        @Override
        public int refcount(Environment gam) {
            Lval lval =lvals[0];//is enough to test one lval
            String x = lval.name();
            Location location = gam.get(x);
            Type type = location.getType();
            return 1+ type.refcount(gam);
        }
        @Override
        public boolean conflictTrc(Type T2){
            // Any conflicting inactive Trcs
            if(T2 instanceof Clone) {
                Clone jth = (Clone) T2;
                Lval[] jlval = jth.lvals();
                for (int i = 0; i != lvals.length; ++i) {
                    Lval ith = lvals[i];
                    for (int j = 0; j != jlval.length; ++j){
                        if(ith.conflicts(jlval[j])){
                            return true;
                        }
                    }
                }
            }
                return false;
            }

        /**
         *
         * @param name variable
         * @param i counter if *
         * @param free string to be return
         * @param pos position of the trc
         * @return
         */
        @Override
        public String free(String name, int i, String free, int pos){
            free = "_destroy("+ "*".repeat(i)+name+");"+free;
            i++;
            //System.out.println("\n free "+free+"\n");
            return free;
        }

        /**
         * append
         * @param lhs
         * @return
         */
        public static <T> T[] append(T[] lhs, T... rhs) {
            T[] rs = Arrays.copyOf(lhs, lhs.length + rhs.length);
            System.arraycopy(rhs, 0, rs, lhs.length, rhs.length);
            return rs;
        }

        public static <T> T[] sortedRemoveDuplicates(T[] items) {
            int count = 0;
            // First, identify duplicates and store this information in a bitset.
            BitSet duplicates = new BitSet(items.length);
            for (int i = 1; i < items.length; ++i) {
                T ith = items[i];
                T jth = items[i-1];
                if(ith != null) {
                    if (ith.equals(jth)) {
                        duplicates.set(i-1);
                        count = count + 1;
                    }
                } else if(jth == null) {
                    duplicates.set(i-1);
                    count = count + 1;
                }
            }
            // Second, eliminate duplicates (if any)
            if (count == 0) {
                // nothing actually needs to be removed
                return items;
            } else {
                T[] nItems = Arrays.copyOf(items, items.length - count);
                for (int i = 0, j = 0; i != items.length; ++i) {
                    if (duplicates.get(i)) {
                        // this is a duplicate, ignore
                    } else {
                        nItems[j++] = items[i];
                    }
                }
                return nItems;
            }
        }

        /**
         * remove any lval duplicate
         * @param children
         * @return
         */
        public static <T extends S, S extends Comparable<S>> T[] sortAndRemoveDuplicates(T[] children) {

            //verified
            int r = 0;
            for (int i = 1; i < children.length; ++i) {
                int c = children[i - 1].compareTo(children[i]);
                if (c == 0) {
                    // Duplicate found, though still could be in sorted order.
                    r = 1;
                } else if (c > 0) {
                    // NOT in sorted order
                    r =  -1;
                }
            }
            //
            switch (r) {
                case 0:
                    // In this case, the array is already sorted and no duplicates were
                    // found.
                    return children;
                case 1:
                    // In this case, the array is already sorted, but duplicates were
                    // found
                    return sortedRemoveDuplicates(children);
                default:
                    // In this case, the array is not sorted and may or may not
                    // contain duplicates.
                    children = Arrays.copyOf(children, children.length);
                    Arrays.sort(children);
                    return sortedRemoveDuplicates(children);
            }
        }
        @Override
        public Type union(Type t) {

            if (t instanceof Undefined) {
                return t.union(this);
            } else if (t instanceof Clone) {
                Clone c = (Clone) t;
                    // Append both sets of names together
                    Lval[] ps = append(lvals, c.lvals);
                    // Remove any duplicates and ensure result is sorted
                    ps = sortAndRemoveDuplicates(ps);
                    // Done
                    return new Clone(ps);
            }
            throw new IllegalArgumentException("invalid union");
        }

        public Lval[] lvals() {
            return lvals;
        }

        @Override
        public boolean prohibitsTrcMoving(Lval lv) {
            // Any conflicting borrow prohibits an lval from being written.
            for (int i = 0; i != lvals.length; ++i) {
                Lval ith = lvals[i];
                //System.out.printf("conflicts");
                // Check whether potential conflict
                if (lv.conflicts(ith)) {
                    return true;
                }
            }
            return false;
        }


        @Override
        public boolean equals(Object o) {
            if (o instanceof Clone) {
                Clone c = (Clone) o;
                return Arrays.equals(lvals, c.lvals);
            }
            return false;
        }

        @Override
        public String toString() {
            return "#" + toString(lvals);

        }

        private static String toString(Lval[] slices) {
            if (slices.length == 1) {
                return slices[0].toString();
            } else {
                String r = "";
                for (int i = 0; i != slices.length; ++i) {
                    if (i != 0) {
                        r = r + ",";
                    }
                    r = r + slices[i];
                }
                return "(" + r + ")";
            }
        }
    }

    public static class Borrow extends AbstractType {
        private final boolean mut;
        private final Lval[] lvals;

        public Borrow(boolean mut, Lval item) {
            this(mut, new Lval[] { item });
        }
        public Borrow(boolean mut, Lval[] paths) {
            assert paths.length > 0;
            this.mut = mut;
            this.lvals = paths;
            // Ensure sorted invariant
            Arrays.sort(paths);
        }

        public int positionTrc(Environment gam){
            //System.out.printf("heloooooooooooooo");
            Lval lval =lvals[0];//is enough to test one lval
            String x = lval.name();
            Location location = gam.get(x);
            Type type = location.getType();
            /**
             * a = &mut *x;
             */
            int deref= lval.path().size();
            //System.out.printf("\n\n positionTrc "+ type.positionTrc(gam));
            return 1+ type.positionTrc(gam)-deref;
        }

        @Override
        public boolean prohibitsReading(Lval lv) {
            if (mut) {
                // Only a mutable borrow can prohibit other borrows from being read.
                return prohibitsWriting(lv);
            }
            return false;
        }

        @Override
        public boolean prohibitsWriting(Lval lv) {
            // Any conflicting borrow prohibits an lval from being written.
            for (int i = 0; i != lvals.length; ++i) {
                Lval ith = lvals[i];
                // Check whether potential conflict
                if (lv.conflicts(ith)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean TrcSafe(Environment gam) {
            // is enough to verify on of lval because there types are compatible
                Lval ith = lvals[0];
            /*Pair<Type, Lifetime> location = ith.typeOf(gam);
            Type type = location.first();*/
            String x = ith.name();
            Location location = gam.get(x);
            Type type = location.getType();
                if (type instanceof Trc || ContainsTrc(gam)){
                    return false;
                }else if (type instanceof Tuples.TuplesType) {
                    return !((Tuples.TuplesType) type).containsTrc();
                }
                return true;
        }

        public boolean isMutable() {
            return mut;
        }

        @Override
        public boolean copyable() {
            // NOTE: mutable borrows have linear semantics.
            return !mut;
        }
        public boolean ContainsTrc(Environment gam){
            Lval lval =lvals[0];//is enough to test one lval
            String x = lval.name();
            Location location = gam.get(x);
            Type type = location.getType();
            return type.ContainsTrc(gam);
        }

        @Override
        public Pair<Boolean, Type> ContainsRef(Environment gam) {
            return new Pair<>(true, this);
        }

        @Override
        public int refcount(Environment gam) {
            Lval lval =lvals[0];//is enough to test one lval
            String x = lval.name();
            Location location = gam.get(x);
            Type type = location.getType();
            return 1+ type.refcount(gam)-lval.path().size();
        }

        public Type returnType(Environment gam, int count){
            Lval lval =lvals[0];//is enough to test one lval
            String x = lval.name();
            Location location = gam.get(x);
            Type type = location.getType();
            if (count>=1){
               // count -=1;
                return type.returnType(gam, count);
            }

            /*while(count>=1){
                count -=1;
                _type = type.returnType(gam, count);

            }*/
            return type;
        }

        @Override
        public boolean within(BorrowChecker checker, Environment gam, Lifetime l) {
            boolean r = true;
            //System.out.printf(" gam2 within "+gam.toString()+"\n");
            for (int i = 0; i != lvals.length; ++i) {
                Lval ith = lvals[i];
                if(gam.get(ith.name()) == null){
                    System.out.printf("\n .UNDECLARED_VARIABLE "+ith.name()+"\n");
                }
                Location location = gam.get(ith.name());
                // NOTE: this differs from the presentation as, in fact, we don't need to type
                // the lval fully.
                // is equivalent to r = r && location.getLifetime().contains(l)
                r &= location.getLifetime().contains(l);
            }
            return r;
        }

        /**
         * append
         * @param lhs
         * @return
         */
        public static <T> T[] append(T[] lhs, T... rhs) {
            T[] rs = Arrays.copyOf(lhs, lhs.length + rhs.length);
            System.arraycopy(rhs, 0, rs, lhs.length, rhs.length);
            return rs;
        }

        public static <T> T[] sortedRemoveDuplicates(T[] items) {
            int count = 0;
            // First, identify duplicates and store this information in a bitset.
            BitSet duplicates = new BitSet(items.length);
            for (int i = 1; i < items.length; ++i) {
                T ith = items[i];
                T jth = items[i-1];
                if(ith != null) {
                    if (ith.equals(jth)) {
                        duplicates.set(i-1);
                        count = count + 1;
                    }
                } else if(jth == null) {
                    duplicates.set(i-1);
                    count = count + 1;
                }
            }
            // Second, eliminate duplicates (if any)
            if (count == 0) {
                // nothing actually needs to be removed
                return items;
            } else {
                T[] nItems = Arrays.copyOf(items, items.length - count);
                for (int i = 0, j = 0; i != items.length; ++i) {
                    if (duplicates.get(i)) {
                        // this is a duplicate, ignore
                    } else {
                        nItems[j++] = items[i];
                    }
                }
                return nItems;
            }
        }

        /**
         * remove any lval duplicate
         * @param children
         * @return
         */
        public static <T extends S, S extends Comparable<S>> T[] sortAndRemoveDuplicates(T[] children) {

            //verified
            int r = 0;
            for (int i = 1; i < children.length; ++i) {
                int c = children[i - 1].compareTo(children[i]);
                if (c == 0) {
                    // Duplicate found, though still could be in sorted order.
                    r = 1;
                } else if (c > 0) {
                    // NOT in sorted order
                    r =  -1;
                }
            }
            //
            switch (r) {
                case 0:
                    // In this case, the array is already sorted and no duplicates were
                    // found.
                    return children;
                case 1:
                    // In this case, the array is already sorted, but duplicates were
                    // found
                    return sortedRemoveDuplicates(children);
                default:
                    // In this case, the array is not sorted and may or may not
                    // contain duplicates.
                    children = Arrays.copyOf(children, children.length);
                    Arrays.sort(children);
                    return sortedRemoveDuplicates(children);
            }
        }
        @Override
        public Type union(Type t) {

            if (t instanceof Undefined) {
                return t.union(this);
            } else if (t instanceof Borrow) {
                Borrow b = (Borrow) t;
                if (mut == b.mut) {
                    // Append both sets of names together
                    Lval[] ps = append(lvals, b.lvals);
                    // Remove any duplicates and ensure result is sorted
                    ps = sortAndRemoveDuplicates(ps);
                    // Done
                    return new Borrow(mut, ps);
                }
            }
            throw new IllegalArgumentException("invalid union");
        }

        public Lval[] lvals() {
            return lvals;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Borrow) {
                Borrow b = (Borrow) o;
                return mut == b.mut && Arrays.equals(lvals, b.lvals);
            }
            return false;
        }

        @Override
        public String toString() {
            if (mut) {
                return "&mut " + toString(lvals);
            } else {
                return "&" + toString(lvals);
            }
        }

        private static String toString(Lval[] slices) {
            if (slices.length == 1) {
                return slices[0].toString();
            } else {
                String r = "";
                for (int i = 0; i != slices.length; ++i) {
                    if (i != 0) {
                        r = r + ",";
                    }
                    r = r + slices[i];
                }
                return "(" + r + ")";
            }
        }
    }

    public class Undefined extends AbstractType{
        public final Type type;

        public Undefined(Type type) {
            this.type = type;
        }

        public Type getType() {
            return type;
        }

        @Override
        public boolean within(BorrowChecker borrowChecker, Environment gam, Lifetime l) {
            // Should never be able to assign an effected type
            throw new IllegalArgumentException("\n this lval has a partial type");
        }
        @Override
        public boolean prohibitsReading(Lval lv) {
            // NOTE: shadow types do not correspond with actual values and, instead, are
            // used purely to retain knowledge of the "structure". Hence, they do not
            // prohibit other types from being read/written.
            return false;
        }

        @Override
        public boolean prohibitsWriting(Lval lv) {
            // NOTE: shadow types do not correspond with actual values and, instead, are
            // used purely to retain knowledge of the "structure". Hence, they do not
            // prohibit other types from being read/written.
            return false;
        }

        @Override
        public boolean defined() {
            return false;
        }

        @Override
        public boolean copyable() {
            return false;
        }

        @Override
        public Type union(Type t) {
            // Strip effect for joining
            t = (t instanceof Undefined) ? ((Undefined) t).type : t;
            //
            return new Undefined(type.union(t));
        }

        public Undefined undefine() {
            return this;
        }

        @Override
        public String toString() {
            return "|_" + type + "_|";
        }

    }
}

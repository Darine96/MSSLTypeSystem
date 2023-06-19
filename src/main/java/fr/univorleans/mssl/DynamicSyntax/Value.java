package fr.univorleans.mssl.DynamicSyntax;

import java.util.Arrays;

public interface Value extends Syntax.Expression {
    public final static int Integer_Expression=5;
    public final static int Unit_Expression=6;
    public final static int Bool_Expression=7;

    public final static int Location_Expression=8;


        public static Unit Unit = new Unit();

        /**
         * Read the value at a given path within this value. For example,
         * 1: let mut x = 0;
         *    let mut y = x; // the path of x is empty then return its value
         * 2: let mut x = 0;
         *    let mut y = &mut x;
         *    let mut a = & *y; //the path of y is not empty then this lval is a deref
         * @param index Position in path being read
         * @param path
         * @return
         */
        public Value read(int[] path, int index);

        /**
         * Write a give value into this value at a given path, returned the updated
         * value.
         *
         * @param index
         * @param path
         * @param value
         * @return
         */
        public Value write(int[] path, int index, Value value);

        /**
         * the Class Atom represent the primitive types
         */
        public class Atom implements Value {
            private final int opcode;
            public Atom(int opcode) {
                this.opcode = opcode;
            }

            @Override
            public int getOpcode(){
                return opcode;
            }

            @Override
            public Value read(int[] path, int index) {
                assert index == path.length;
                return this;
            }

            @Override
            public Value write(int[] path, int index, Value value) {
                assert index == path.length;
                return value;
            }
        }

        public class Unit extends Atom {

            private int opcode = Unit_Expression;
            public Unit() {
                super(Unit_Expression);
            }

            @Override
            public boolean equals(Object o) {
                return o instanceof Unit;
            }

            @Override
            public int getOpcode() {
                return opcode;
            }

            @Override
            public String toString() {
                return "unit";
            }
        }

        public class Integer extends Atom {
            private int value;
            private int counter;
            public Integer(int value) {
                super(Integer_Expression);
                this.value = value;
            }

            public Integer(int value, int counter) {
                super(Integer_Expression);
                this.value = value;
                this.counter = counter;
            }

            public int getCounter() {
                return counter;
            }

            public int value() {
                return value;
            }

            public void setCounter(int i){
                this.counter=this.counter+i;
            }
            @Override
            public int hashCode() {
                return value;
            }

            @Override
            public boolean equals(Object o) {
                return o instanceof Integer && ((Integer) o).value == value;
            }

            @Override
            public String toString() {
                return java.lang.Integer.toString(value);
            }

            public static Integer construct(java.lang.Integer i) {
                return new Integer(i);
            }
        }


    public class Boolean extends Atom {
        private final boolean value;

        public Boolean(boolean value) {
            super(Bool_Expression);
            this.value = value;
        }

        public boolean value() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof Boolean && ((Boolean) o).value == value;
        }

        @Override
        public String toString() {
            return java.lang.Boolean.toString(value);
        }

    }


    /**
     * box, trc, clone, pointeur
     */
    public class Reference extends Atom {
        private static final int[] OWNER = new int[0];
        private static final int[] OTHER = new int[0];

        /**
         * specific for the trc
         */
        private int counter_trc = 0;
        /**
         * The location's address in the machines memory
         */
        private final int address;
        /**
         * Identifies the position within the location to which this reference refers.
         */
        private final int[] path;

        public Reference(int address) {
            this(address, OWNER);
        }
        public Reference(int address, int counter) {
            this(address, OWNER);
            this.counter_trc=counter;
        }

        private Reference(int address, int[] path) {
            super(Location_Expression);
            this.address = address;
            this.path = path;
        }

        public int getCounter_trc() {
            return counter_trc;
        }

        public void setCounter_trc(int counter_trc) {
            this.counter_trc = counter_trc;
        }

        /**
         * increment the current counter
         * @return
         */
        public int add(){
            setCounter_trc(++this.counter_trc);
            return getCounter_trc();
        }

        /**
         * decrement the current counter
         * @return
         */
        public int decrement(){
            setCounter_trc(--this.counter_trc);
            return getCounter_trc();
        }

        /**
         * Get the base address of the location this reference refers to.
         *
         * @return
         */
        public int getAddress() {
            return address;
        }

        /**
         * Get the path to the position within the location which this references refers
         * to.
         *
         * @return
         */
        public int[] getPath() {
            return path;
        }

        /**
         * Determine the ownership status of this location. Specifically, whether or not
         * this reference is responsible for deallocating its location.
         * For example, box and trc
         * @return
         */
        public boolean owner() {
            return path == OWNER;
        }

        /**
         * Access a location within this location at the given offset.
         *
         * @param offset
         * @return
         */
        public Reference at(int offset) {
            int[] npath = Arrays.copyOf(path, path.length + 1);
            npath[path.length] = offset;
            return new Reference(address, npath);
        }
        @Override
        public boolean equals(Object o) {
            if (o instanceof Reference) {
                Reference l = ((Reference) o);
                return l.address == address && Arrays.equals(path, l.path);
            }
            return false;
        }

        /**
         * Obtain a reference which is not an owner to which it refers.
         *
         * @return
         */
        public Reference toBorrowed() {
            if (path == OWNER) {
                return new Reference(address, OTHER);
            } else {
                return this;
            }
        }

        /**
         * Obtain a reference which is not an owner to which it refers.
         *
         * @return
         */
        public Reference toCloned() {

                return this;

        }

        @Override
        public String toString() {
            String p = "";
            for (int i = 0; i != path.length; ++i) {
                p = p + ":" + path[i];
            }
            String q = owner() ? "+" : "";
            return "&" + q + address + p;
        }
    }

}

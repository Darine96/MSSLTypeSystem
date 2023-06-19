package fr.univorleans.mssl.DynamicSyntax;

import fr.univorleans.mssl.DynamicSyntax.Value.Reference;
import fr.univorleans.mssl.SOS.Pair;
import fr.univorleans.mssl.SOS.StoreProgram;
import fr.univorleans.mssl.TypeSystem.Environment;

import java.util.Arrays;

public class Path implements Comparable<Path> {
    public static Element DEREF_ELEMENT = new Deref();
    public final static Path EMPTY = new Path();
    public final static Path DEREF = new Path(new Element[] { DEREF_ELEMENT });

    /**
     * The sequence of elements making up this path
     */
    public final Element[] elements;

    public Path() {
        this(new Element[0]);
    }

    public Path(Element[] elements) {
        this.elements = elements;
    }

    /**
     * Identifiers how many elements in this path.
     *
     * @return
     */
    public int size() {
        return elements.length;
    }

    /**
     * Read a particular element from this path.
     */
    public Element get(int index) {
        return elements[index];
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Path) {
            Path p = (Path) o;
            return Arrays.deepEquals(elements, p.elements);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(elements);
    }

    @Override
    public int compareTo(Path p) {
        if (elements.length < p.elements.length) {
            return -1;
        } else if (elements.length > p.elements.length) {
            return 1;
        }
        for (int i = 0; i != elements.length; ++i) {
            int c = elements[i].compareTo(p.elements[i]);
            if (c != 0) {
                return c;
            }
        }
        return 0;
    }


    /**
     * Apply this path to a given location. For example, if this path represents a
     * dereference then we will read the location and returns its contents (as a
     * location).
     *
     * @param location
     * @param store
     * @return
     */
    public Reference apply(Reference location, StoreProgram.Store store) {
        for (int i = 0; i != elements.length; ++i) {
            location = elements[i].apply(store, location);
        }
        return location;
    }

    public boolean conflicts(Path p) {
        final int n = Math.min(elements.length, p.elements.length);
        for (int i = 0; i < n; ++i) {
            Element ith = elements[i];
            Element pith = p.elements[i];
            System.out.printf("\n pith "+pith);
            System.out.printf("\n pith "+ith.conflicts(pith));
            if (!ith.conflicts(pith)) {
                return false;
            }
        }
        // Done
        return true;
    }
    public String toString(String src) {
        for (int i = 0; i != elements.length; ++i) {
            if (i != 0) {
                src = "(" + src + ")";
            }
            src = elements[i].toString(src);
        }
        return src;
    }

    /**
     * specific for borrow checker
     */
    /**
     * update the type, for example a dereference to a box into tuple
     * e.g. let mut x = (box(0), 1);
     * let mut y = *x.0;
     * @param env
     * @param type
     * @return
     */
    public Pair<Type, Lifetime> apply(Environment env, Type type, Lifetime l) {
        for (int i = 0; i != elements.length; ++i) {
            Element ith = elements[i];
            Pair<Type, Lifetime> p = ith.apply(env, type, l);
            if (p == null) {

                return null;
            } else {
                type = p.first();
                l = p.second();
            }
        }
        return new Pair<>(type, l);
    }


    /****/

    public interface Element extends Comparable<Element> {
        int compareTo(Element arg0);

        /**
         * Determine whether a given path element conflicts with another path element.
         *
         * @param e
         * @return
         */
        public boolean conflicts(Element e);
        /**
         * Apply this element to a given location in a given store, producing an updated
         * location.
         *
         * @param loc
         * @param store
         * @return
         */
        public Reference apply(StoreProgram.Store store, Reference loc);
        public String toString(String src);

        /**
         * specific for borrow checker
         */
        public Pair<Type, Lifetime> apply(Environment env, Type T, Lifetime l);
    }

    /**
     * Represents a dereference path element
     */
    public static class Deref implements Element {

        @Override
        public int compareTo(Element arg0) {
            if (arg0 == DEREF_ELEMENT) {
                return 0;
            } else {
                // FIXME: do something here?
                throw new IllegalArgumentException("GOT HERE");
            }
        }

        @Override
        public boolean conflicts(Element e) {
            return true;
        }

        @Override
        public Reference apply(StoreProgram.Store store, Reference loc) {
            return (Reference) store.read(loc);
        }

        @Override
        public Pair<Type, Lifetime> apply(Environment env, Type type, Lifetime lifetime) {
            if (type instanceof Type.Box) {
                Type.Box box = (Type.Box) type;
                return new Pair<>(box.getType(), lifetime);
            } else if (type instanceof Type.Trc) {
                Type.Trc trc = (Type.Trc) type;
                return new Pair<>(trc.getType(), lifetime);
            } else if(type instanceof Type.Clone){
                // just one lval is enough to check
                Lval lval = ((Type.Clone) type).lvals()[0];
                Pair<Type, Lifetime> ith = lval.typeOf(env);
                return ith;
            } else if (type instanceof Type.Borrow) {
                Type.Borrow borrow = (Type.Borrow) type;
                Lval[] lvals = borrow.lvals();
                Type T = null;
                Lifetime l = null;
                for (int i = 0; i != lvals.length; ++i) {
                    Pair<Type, Lifetime> ith = lvals[i].typeOf(env);
                    T = (T == null) ? ith.first() : T.union(ith.first());
                    l = (l == null) ? ith.second() : l.min(ith.second());
                }
                return new Pair<>(T, l);
            } else {
                // ill typed
                return null;
            }
        }

        @Override
        public String toString(String src) {
            return "*" + src;
        }


        @Override
        public boolean equals(Object o) {
            return o instanceof Deref;
        }
    }
}

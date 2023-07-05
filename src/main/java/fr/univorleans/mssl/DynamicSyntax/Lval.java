package fr.univorleans.mssl.DynamicSyntax;


import fr.univorleans.mssl.DynamicSyntax.Value.Reference;
import fr.univorleans.mssl.SOS.Pair;
import fr.univorleans.mssl.SOS.StoreProgram;
import fr.univorleans.mssl.TypeSystem.Environment;
import fr.univorleans.mssl.TypeSystem.Location;

public class Lval implements Comparable<Lval>, Syntax.Expression {

    private final String name;
    private final Path path;

    public Lval(String name, Path path) {
        this.name = name;
        this.path = path;
    }

    public String name() {
        return name;
    }

    public Path path() {
        return path;
    }

    public boolean conflicts(Lval lv) {
        System.out.printf("conflicts");
        return name.equals(lv.name) && path.conflicts(lv.path);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Lval) {
            Lval s = (Lval) o;
            return name.equals(s.name) && path.equals(s.path);
        }
        return false;
    }

    @Override
    public int compareTo(Lval s) {
        int c = name.compareTo(s.name);
        if (c == 0) {
            c = path.compareTo(s.path);
        }
        return c;
    }

    /**
     * Locate the location to which this path refers in the given state by repected the condition of the lifetime.
     * test with lifetime
     * @param state
     * @return
     */
    public Reference locate(StoreProgram.State state, Lifetime lifetime) {
        // Identify root of path
        /**
         * test with lifetime
         */
        Reference l = state.locate(name, lifetime);
        // Apply each element in turn
        return path.apply(l, state.getHeap());
    }
    @Override
    public String toString() {
        return path.toString(name);
    }

    /**
     * part specific for the borrower
     * @return
     * </p>
     * <p>
     * Also determines the smallest lifetime of a given lval.
     * @param env
     * @return
     */
    public Pair<Type, Lifetime> typeOf(Environment env) {
        Location Cx = env.get(name);
       //

        return (Cx == null) ? null : path.apply(env, Cx.getType(), Cx.getLifetime());
    }

    /**
     * for tuple case
     * @param p
     * @param i
     * @return
     */
    public Lval traverse(Path p, int i) {
        final Path.Element[] path_elements = path.elements;
        final Path.Element[] p_elements = p.elements;
        // Handle common case
        if (p_elements.length == i) {
            return this;
        } else {
            final int n = path_elements.length;
            final int m = p_elements.length - i;
            Path.Element[] nelements = new Path.Element[n + m];
            System.arraycopy(path_elements, 0, nelements, 0, n);
            System.arraycopy(p_elements, i, nelements, n, m);
            return new Lval(name, new Path(nelements));
        }
    }
    @Override
    public int getOpcode() {
        return 0;
    }
}

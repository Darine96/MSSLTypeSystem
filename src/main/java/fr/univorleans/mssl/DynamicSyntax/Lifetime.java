package fr.univorleans.mssl.DynamicSyntax;

import java.util.Arrays;

public class Lifetime {
    private static int LIFETIME_COUNTER = 0;
    private final int index;
    private Lifetime[] parents;

    public Lifetime() {
        this.parents = new Lifetime[0];
        this.index = LIFETIME_COUNTER++;
    }

    public Lifetime(Lifetime... parents) {
        this.parents = parents;
        this.index = LIFETIME_COUNTER++;
    }

    /**
     *Check whether a lifetime given in parameter (m) is comprised in the current lifetime (l).
     * e.g { { { }^m  }^r  }^l
     * @param l
     * @return
     */
    public boolean contains(Lifetime l) {
        if (l == null) {
            return false;
        } else if (l == this) {
            // Base case
            return true;
        } else {
            // Recursive case
            for (int i = 0; i != l.parents.length; ++i) {
                if (contains(l.parents[i])) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * get lifetime
     */
    public Lifetime get(){
        if(parents.length == 0){
            return this;
        }
        return parents[parents.length-1];
    }
    /**
     * Return the smallest lifetime between this and a given lifetime.
     *
     * @param l
     * @return
     */
    public Lifetime min(Lifetime l) {
        if (this.contains(l)) {
            return l;
        } else if (l.contains(this)) {
            return this;
        } else {
            throw new IllegalArgumentException("ambiguous lifetimes");
        }
    }

    /**
     * Get the outermost lifetime which this lifetime is within.
     *
     * @return
     */
    public Lifetime getRoot() {
        if (parents.length == 0) {
            return this;
        } else {
            return parents[0].getRoot();
        }
    }

    /**
     * Construct a fresh lifetime within this lifetime.
     *
     * @return
     */
    public Lifetime freshWithin() {
        // Create the new lifetime
        return new Lifetime(this);
    }

    /**
     * Assert that this lifetime is within a given lifetime.
     *
     * @param l
     */
    public void assertWithin(Lifetime l) {
        // Check whether contraint already exists
        if (!l.contains(this)) {
            // Nope
            final int n = parents.length;
            this.parents = Arrays.copyOf(parents, n + 1);
            this.parents[n] = l;
        }
    }

    @Override
    public String toString() {
        return "l" + index;
    }
}

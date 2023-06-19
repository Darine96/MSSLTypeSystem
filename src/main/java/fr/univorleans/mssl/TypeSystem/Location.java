package fr.univorleans.mssl.TypeSystem;

import fr.univorleans.mssl.DynamicSyntax.Lifetime;
import fr.univorleans.mssl.DynamicSyntax.Type;

/**
 * Represents the information associated about a given variable in the
 * environment. This includes its <i>type</i> and <i>lifetime</i>
 *
 */
public class Location {
    public final Type type;
    public final Lifetime lifetime;

    public Location(Type type, Lifetime lifetime) {
        this.type = type;
        this.lifetime = lifetime;
    }

    public Type getType() {
        return type;
    }

    public Lifetime getLifetime() {
        return lifetime;
    }

    /**
     * same type and lifetime
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Location) {
            Location c = (Location) o;
            return type.equals(c.type) && lifetime.equals(c.lifetime);
        }
        return false;
    }

    @Override
    public String toString() {
        return "<" + type + ", " + lifetime + ">";
    }
}

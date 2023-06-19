package fr.univorleans.mssl.TypeSystem;

import fr.univorleans.mssl.DynamicSyntax.Lifetime;
import fr.univorleans.mssl.DynamicSyntax.Type;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Environment {

    /**
     * Mapping from variable names to types
     */
    private final HashMap<String, Location> mapping;


    /**
     * Construct an environment for a given lifetime
     */
    protected Environment() {
        this.mapping = new HashMap<>();
    }

    /**
     * Construct an environment for a given liftime and variable mapping
     *
     * @param
     * @param mapping
     */
    public Environment(Map<String, Location> mapping) {
        this.mapping = new HashMap<>(mapping);
    }

    /**
     * Get the cell associated with a given path
     *
     * @param name
     * @return
     */
    public Location get(String name) {
        return mapping.get(name);
    }

    public HashMap<String, Location> getMap() {
        return mapping;
    }

    /**
     * Update the cell associated with a given variable name
     *
     * @param name
     * @param type
     * @return
     */
    public Environment put(String name, Type type, Lifetime lifetime) {
      //  System.out.printf(" put "+ name +" type "+type +"\n");
        return put(name, new Location(type, lifetime));
    }

    /**
     * Update the cell associated with a given variable name
     *
     * @param name
     * @return
     */
    public Environment put(String name, Location cell) {
        Environment nenv = new Environment(mapping);
        nenv.mapping.put(name, cell);
        return nenv;
    }


    /**
     * UpdateALL the cell associated with a given variable name
     * Merge two environment
     * @param env
     * @return
     */
    public Environment putALL(Environment env) {
        Environment nenv = new Environment(mapping);
        nenv.mapping.putAll(env.mapping);
       // System.out.printf("\n\n put ALL "+nenv.mapping.toString());
        return nenv;
    }
    /**
     * Remove a given variable mapping.
     *
     * @param name
     * @return
     */
    public Environment remove(String name) {
        Environment nenv = new Environment(mapping);
        //System.out.printf("\n names[i] "+name);
        nenv.mapping.remove(name);
        return nenv;
    }

    /**
     * Remove a given variable mapping.
     * @return
     */
    public Environment remove(String... names) {
        Environment nenv = new Environment(mapping);
        for (int i = 0; i != names.length; ++i) {
            nenv.mapping.remove(names[i]);
        }
        return nenv;
    }

    /**
     * Get collection of all cells in the environment.
     *
     * @return
     */
    public Collection<Location> cells() {
        return mapping.values();
    }

    /**
     * Get set of all bound variables in the environment
     *
     * @return
     */
    public Set<String> bindings() {
        return mapping.keySet();
    }

    @Override
    public String toString() {
        String body = "{";
        boolean firstTime = true;
        for (Map.Entry<String, Location> e : mapping.entrySet()) {
            if (!firstTime) {
                body = body + ",";
            }
            firstTime = false;
            body = body + e.getKey() + ":" + e.getValue();
        }
        return body + "}";
    }
}

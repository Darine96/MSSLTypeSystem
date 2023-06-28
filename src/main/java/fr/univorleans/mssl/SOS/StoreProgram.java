package fr.univorleans.mssl.SOS;

import fr.univorleans.mssl.DynamicSyntax.Lifetime;
import fr.univorleans.mssl.DynamicSyntax.Lval;
import fr.univorleans.mssl.DynamicSyntax.Tuples;
import fr.univorleans.mssl.DynamicSyntax.Value;
import fr.univorleans.mssl.DynamicSyntax.Value.Reference;

import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public class StoreProgram {

    public static final State EMPTY_STATE = new State();

    public static class State {
        private final StackFrame stack;
        private final Store heap;

        public State(StackFrame stack, Store heap) {
            this.stack = stack;
            this.heap = heap;
        }

        public State() {
            this.stack = new StackFrame();
            this.heap = new Store();
        }

        /**
         * Getter
         */
        public StackFrame getStack() {
            return stack;
        }

        public Store getHeap() {
            return heap;
        }

        /**
         * verified if the heap is empty
         */
        public boolean isHeapEmpty() {
            return heap.isEmpty();
        }

        /**
         * Push a new stack frame onto this state for the new thread
         *
         * @param m
         * @return
         */
        public State push(Lifetime m) {
            return new State(stack.push(m), heap);
        }

        /**
         * Determine the location associated with a given variable name.
         * test with lifetime
         *
         * @param name
         * @return
         */
        public Reference locate(String name, Lifetime l) {
            return stack.get(name, l);
        }
        /**
         * Determine the location associated with a given variable name.
         *
         * @param name
         * @return
         */
        public Reference locate(String name) {
            return stack.get(name);
        }

        /**
         * Allocate a new cell in memory with a given lifetime and initial value.
         *
         * @param lifetime Lifetime of cell to be allocated
         * @param v        Initial value to be used for allocated cell
         * @return
         */
        public Pair<State, Reference> allocate(Lifetime lifetime, Value v) {
            // Allocate cell in store
            Pair<Store, Reference> p = heap.allocate(lifetime, v);
            // Return updated state
            return new Pair<>(new State(stack, p.first()), p.second());
        }


        /**
         * Allocate a new cell in memory with a given lifetime and initial value.
         * Specific for trc
         *
         * @param lifetime Lifetime of cell to be allocated
         * @param v        Initial value to be used for allocated cell
         * @return
         */
        public Pair<State, Reference> allocate(Lifetime lifetime, Value v, int counter) {
            // Allocate cell in store
            Pair<Store, Reference> p = heap.allocate(lifetime, v, counter);
            // Return updated state
            return new Pair<>(new State(stack, p.first()), p.second());
        }
        /**
         * Read the contents of a given lval.
         * <p>
         * Location to read
         *
         * @return
         */
        /**
         * update with a lifetime
         * @param lv
         * @return
         */
        public Value read(Lval lv, Lifetime l) {
            return heap.read(lv.locate(this, l));
        }

        /**
         * Update the value of a given location, thus creating an updated state.
         *
         * @param
         * @param value Value to be written
         * @return
         */
        public State write(Lval lv, Value value, Lifetime l) {
            Store nstore = heap.write(lv.locate(this, l), value);
            return new State(stack, nstore);
        }
        /**
         * Bind a name to a given location, thus creating an updated state.
         *
         *            Name to bind
         * @param location
         *            Location to be bound
         * @return
         */
        public State bind(String var, Reference location, Lifetime l) {
            StackFrame nstack = stack.bind(var, location, l);
            return new State(nstack, heap);
        }

        /**
         * Drop all locations created within a given lifetime.
         *
         * @param temporaries items currently live which are not on the stack or in the
         *                    heap. This is needed only for debugging.
         * @return
         */
        public State drop(Lifetime m, Value... temporaries) {
            BitSet locations = findAll(stack.locations, m);
            return new State(stack.pop(m), heap.drop(locations, temporaries));
        }

        /**
         * drop value
         */

        /**
         * Drop an overwritten value, this creating an updated state. In the process,
         * any owned locations will be dropped.
         *
         *            Location to overwrite
         * @param value
         *            Value to be written
         * @return
         */
        public State drop(Value value) {
            return new State(stack, heap.drop(value));
        }


        /**
         * Find all locations allocated in a given lifetime
         *
         * @param lifetime
         * @return
         */
        public BitSet findAll(HashMap<String, Reference> locations, Lifetime lifetime) {
            return heap.findAll(locations, lifetime);
        }
        /**
         * increment the counter of a given reference
         */
        public Pair<State, Reference> increment_counter(Lval lval, Lifetime l){
            Reference location = lval.locate(this, l);
            Pair<Store, Reference> store = heap.increment(location);
            return new Pair<>(new State(stack, store.first()), store.second());
        }

    }

    /**
     * Represents a stack of bindings from variable names to abstract locations.
     *
     * @author djp
     */
    public static class StackFrame {
        private final StackFrame parent;
        private final Lifetime lifetime;
        private final HashMap<String, Reference> locations;
        private final HashMap<String, Lifetime> variables;

        /**
         * Construct an initial (empty) call stack
         */
        public StackFrame() {
            this.parent = null;
            this.lifetime = null;
            this.locations = new HashMap<>();
           this.variables = new HashMap<>();
        }

        /**
         * A copy constructor for call stacks.
         *
         * @param locations
         */
        private StackFrame(StackFrame parent, Lifetime lifetime, HashMap<String, Reference> locations, HashMap<String, Lifetime> variables ) {
            this.parent = parent;
            this.lifetime = lifetime;
            this.locations = locations;
            this.variables = variables;

        }

        /**
         * Check whether frame is empty or not.
         *
         * @return
         */
        public boolean isEmpty() {
            return locations.isEmpty();
        }

        /**
         * Get the location bound to a given variable name
         *
         * @param name
         *            Name of location to return
         * @return
         */
        public Reference get(String name) {
            return locations.get(name);
        }

        /**
         * Get the location bound to a given variable name
         *
         * @param name Name of location to return
         * @return
         */
        /**
         * test with lifetime
         * @param name
         * @return
         */
        public Reference get(String name, Lifetime l) {
            if(variables.get(name) != null){
                Lifetime m = variables.get(name);
                if(m.contains(l)){
                    return locations.get(name);
                }
                else return this.parent.get(name, l);
            }
            else  return this.parent.get(name, l);
        }
        /**
         * Push an empty frame on this stack
         */
        public StackFrame push(Lifetime lifetime) {
            return new StackFrame(this, lifetime, new HashMap<>(), new HashMap<>());
        }

        /**
         * Pop a given scope of this stack.
         *
         * @param lifetime
         * @return
         */
        public StackFrame pop(Lifetime lifetime) {
            if (this.lifetime == lifetime) {
                return parent;
            } else {
                return this;
            }
        }

        /**
         * Bind a given name to a given location producing an updated stack. Observe
         * that the name may already exist, in which case the original binding is simply
         * lost.
         *
         * @param name
         *            Variable name to bind
         * @param location
         *            Location to be bound
         * @return
         */
        public StackFrame bind(String name, Reference location, Lifetime l) {
            // Clone the locations map in order to update it
            HashMap<String, Reference> nlocations = new HashMap<>(locations);
            // Update new mapping
            nlocations.put(name, location);
             /**
             * variable and lifetime
             */
            HashMap<String, Lifetime> nvariables = new HashMap<>(variables);
            nvariables.put(name, l);
            return new StackFrame(parent, lifetime, nlocations, nvariables);
        }

        @Override
        public String toString() {
            String r = locations.toString();
            if (parent != null) {
                r += parent.toString();
            }
            return r;
        }

    }

    public static class Store {
        // met le privat if you want
        public Slot[] cells;

        public Store() {
            this.cells = new Slot[0];
        }

        private Store(Slot[] cells) {
            this.cells = cells;
        }

        /**
         * Check whether heap is empty or not
         *
         * @return
         */
        public boolean isEmpty() {
            for (int i = 0; i != cells.length; ++i) {
                if (cells[i] != null) {
                    return false;
                }
            }
            return true;
        }

        /**
         * Allocate a new location in the store.
         *
         * @return
         */
        public Pair<Store, Reference> allocate(Lifetime lifetime, Value v) {
            // Create space for new cell at end of array
            Slot[] ncells = Arrays.copyOf(cells, cells.length + 1);
            // Create new cell using given contents
            ncells[cells.length] = new Slot(lifetime, v);
            //System.out.printf("\n tuplesValue in store porgram "+v.toString()+"\n");
            // Return updated store and location
            return new Pair<>(new Store(ncells), new Reference(cells.length));
        }


        /**
         * Allocate a new location in the store: specific for trc
         *
         * @return
         */
        public Pair<Store, Reference> allocate(Lifetime lifetime, Value v, int counter) {
            // Create space for new cell at end of array
            Slot[] ncells = Arrays.copyOf(cells, cells.length + 1);
            // Create new cell using given contents
            ncells[cells.length] = new Slot(lifetime, v);
            // Return updated store and location
            return new Pair<>(new Store(ncells), new Reference(cells.length, counter));
        }
        /**
         * Read the cell at a given location. If the cell does not exist, then an
         * exception is raised signaling a dangling pointer.
         *
         * @param location
         * @return
         */
        public Value read(Reference location) {
            int address = location.getAddress();
            int[] path = location.getPath();
            Slot cell = cells[address];
            // Read value at location
            Value contents = cell.contents();
            // Extract path (if applicable)
            return (path.length == 0) ? contents : contents.read(path, 0);
        }


        /**
         * Write the cell at a given location. If the cell does not exist, then an
         * exception is raised signaling a dangling pointer.
         *
         * @param location
         * @return
         */
        public Store write(Reference location, Value value) {
            int address = location.getAddress();
            int[] path = location.getPath();
            // Read cell from given base address
            Slot cell = cells[address];
            // Read value at location
            Value n = cell.contents();
            // Construct new value
            Value nv = (path.length == 0) ? value : n.write(path, 0, value);
            // Copy cells ahead of write
            Slot[] ncells = Arrays.copyOf(cells, cells.length);
            // Perform actual write
            ncells[address] = new Slot(cell.lifetime, nv);
            // Done
            return new Store(ncells);
        }

        /**
         * Identify all cells with a given lifetime.
         *
         * @param lifetime
         * @return
         */
        public BitSet findAll(HashMap<String, Reference> locations, Lifetime lifetime) {
            BitSet matches = new BitSet();

            for (Map.Entry<String, Reference> entry : locations.entrySet()) {
                Reference location = entry.getValue();
                Slot ncell = cells[location.getAddress()];
                if(ncell != null && ncell.lifetime() == lifetime){
                    // Mark address
                    matches.set(location.getAddress());
                }
            }
            // Action the drop
            return matches;
        }


        /**
         * drop
         * @return
         */
        /**
         * Drop all cells at the given locations. This recursively drops all reachable
         * and uniquely owned locations.
         *
         * @return
         */
        public Store drop(BitSet locations, Value... temporaries) {
            Slot[] ncells = Arrays.copyOf(cells, cells.length);
            for (int i = locations.nextSetBit(0); i != -1; i = locations.nextSetBit(i + 1)) {

                destroy(ncells, i);

            }
            // Check the heap invariant still holds!
            /**
             * check if we have a dangling pointer
             */
            if(!heapInvariant(ncells,temporaries)) {
                 throw new IllegalArgumentException("a dangling pointer is detected!");
            }
           // Done
            return new Store(ncells);
        }

        /**
         * increment the counter of the given reference (location) specific for clone
         */
        public Pair<Store, Reference> increment(Reference location){
            int adress = location.getAddress();
            // Read cell from given base address
            Slot cell = cells[adress];
            //Read the reference
            Value v = cell.value;
            Slot[] ncells = Arrays.copyOf(cells, cells.length);
            Reference lv = null;
            // increment the reference
            if(v instanceof Reference){
                lv = (Reference) v;
                if(lv.getPath().length == 0 && lv.getCounter_trc()>=1){
                    /**
                     * increment the counter
                     */
                    lv.add();
                    ncells[adress] = new Slot(cell.lifetime, lv);

                }
                /**
                 * afficher une erreur
                 */
            }
            return new Pair<>(new Store(ncells), lv);
        }
        /**
         * Destroy a location and ensure its contents (including anything contained
         * within) are finalised. For example, if the location contains an owned
         * reference some heap memory, then this is destroyed as well. Likewise, if the
         * location contains a compound value then we must traverse this looking for any
         * owner references within.
         *
         * in our cases: we have three types of owner reference: Box, Trc and clone
         * For trc and clone: Trc is responsable for the deallocation according to the counter
         *
         * @param cells   Cells to update
         * @param address Address of location to destroy.
         * @return
         */
        private static void destroy(Slot[] cells, int address) {
            // Locate cell being dropped
            Slot cell = cells[address];
            // Save value for later
            Value v = cell.value;
            // Physically drop the location
            cells[address] = null;
            // Finalise value by dropping any owned values.
            finalise(cells,v);
            // Finalise value specially to TuplesValue
            finaliseTuplesValue(cells,v);
        }

        /**
         *
         * @param cells
         * @param v
         */
        private static void finaliseTuplesValue(Slot[] cells, Value v) {
           // System.out.printf("\n entrer dans finalise " + v.toString());
            if (v instanceof Tuples.TuplesValue) {
                Tuples.TuplesValue tu = (Tuples.TuplesValue) v;
                for (int i = 0; i != tu.size(); ++i) {
                   // System.out.printf("\n entrer dans finalise TuplesValues " + (Value) tu.get(i));
                    finalise(cells, (Value) tu.get(i));
                }
            }
        }

        /**
         * Finalise a value after the location containing it has been destroyed. If this
         * is an owner reference to heap memory, then we must collect that memory as
         * well. Likewise, if its a compound value then we must traverse its contents
         *
         * @param cells
         * @param v
         */
        private static void finalise(Slot[] cells, Value v) {
            if(v instanceof Reference) {
                Reference ref = (Reference) v;

                // Check whether is an owner reference or not. If it is, then it should be
                // deallocated.
                /**
                 * voir si c'est box or trc, then if it is trc verified if the counter is 1
                 */
                if(ref.owner() ) {
                    if (ref.getCounter_trc() == 0 || ref.getCounter_trc() == 1) {
                        final int l = ref.getAddress();
                        /**
                         * case if trc
                         */
                        assert cells[l] != null;
                        assert cells[l].hasGlobalLifetime();
                        // Recursively finalise this cell.
                            destroy(cells, l);

                    }
                    /**
                     * si c'est un clone or trc but the counter uis deferent to 1, then
                     * the deallocation is resumed for decrement the counter
                     */
                    else if (ref.getCounter_trc()>1){
                        ref.decrement();
                    }
                }

            }         }
        /**
         * The heap invariant states that every heap location should have exactly one
         * owning reference.
         *
         * @param slots
         * @return
         */
        private static boolean heapInvariant(Slot[] slots, Value... temporaries) {

            int[] owners = new int[slots.length];
            // First, mark all owned locations
            for(int i=0;i!=slots.length;++i) {

                Slot ith = slots[i];

                if(ith != null) {
                    markOwners(ith.contents(),owners);

                }
            }
            // Second, mark temporary value(s)
            for(int i=0;i!=temporaries.length;++i) {
                markOwners(temporaries[i], owners);
            }
            // Third. look for any heap locations which are not owned.
            for(int i=0;i!=slots.length;++i) {

                Slot ith = slots[i];
                if(ith != null) {
                    //trc and clone and box
                    if (ith.hasGlobalLifetime() && owners[i] < 1) {
                        return false;
                    } else if(hasDanglingReference(ith.contents(),slots)) {
                        return false;
                    }
                }
            }
            return true;
        }

        private static void markOwners(Value v, int[] owners) {

            if(v instanceof Reference) {
                Reference r = (Reference) v;

                int l = r.getAddress();
                if (r.owner()) {
                    owners[l]++;
                }
            }
        }

        private static boolean hasDanglingReference(Value v, Slot[] slots) {
            if(v instanceof Reference) {
                Reference r = (Reference) v;
                int l = r.getAddress();
                if(slots[l] == null) {
                    return true;
                }
            }
            return false;
        }

        /**
         * drop value
         * @return
         */

        /**
         * Drop locations based on a value being overwritten. Thus, if the value is a
         * reference to an owned location then that will be recursively dropped.
         *
         * @param
         *            v-being overwritten
         * @return
         */
        public Store drop(Value v) {
            if(containsOwnerReference(v)) {
                // Prepare for the drop by copying all cells
                Slot[] ncells = Arrays.copyOf(cells, cells.length);
                // Perform the physical drop
                finalise(ncells, v);
                // Check heap invariant still holds!
                assert heapInvariant(ncells);
                // Done
                return new Store(ncells);
            }
            return this;
        }

        /**
         * Check whether a given value is or contains an owner reference. This indicates
         * that, should the value in question be dropped, then we need to do some
         * additional work.
         *
         * @param v
         * @return
         */
        private static boolean containsOwnerReference(Value v) {
            if (v instanceof Reference) {
                Reference r = (Reference) v;
                return r.owner();
            }
            return false;
        }

        public Slot[] toArray() {
            return cells;
        }

    }

    public static class Slot {
        private final Lifetime lifetime;
        private final Value value;

        public Slot(Lifetime lifetime, Value value) {
            this.lifetime = lifetime;
            this.value = value;
        }

        /**
         * Get the lifetime associated with this cell
         *
         * @return
         */
        public Lifetime lifetime() {
            return lifetime;
        }

        /**
         * Get the contents of this cell (i.e. the value stored in this cell).
         *
         * @return
         */
        public Value contents() {
            return value;
        }

        /**
         * Check whether this cell was allocated in the global space or not. This
         * indicates whether or not the cell was allocated on the heap.
         *
         * @return
         */
        public boolean hasGlobalLifetime() {
            Lifetime globalLifetime = lifetime.getRoot();
            return lifetime == globalLifetime;
        }
        @Override
        public String toString() {
            return "<" + value + ";" + lifetime + ">";
        }
    }
}

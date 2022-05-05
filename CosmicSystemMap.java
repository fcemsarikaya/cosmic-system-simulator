import java.util.NoSuchElementException;

// define class
public class CosmicSystemMap implements CosmicSystemIndex, myMap, BodyIterable {
    private final ComplexCosmicSystem css;
    private Body[] ks;
    private ComplexCosmicSystem[] vs;
    private int count;
    private int kols;


    // Creates a hash map from the specified 'system'.
    // The resulting map has multiple (key, value) pairs, one for each
    // body of 'system'. The value is the reference
    // to the system (only the direct parent) to which the body belongs.
    public CosmicSystemMap(ComplexCosmicSystem system) {
        css = system;
        kols = 0;

        ks = new Body[65];
        vs = new ComplexCosmicSystem[65];
        for (Body body : system) {
            put(body, system.getParent(body));
            count++;
        }
    }

    public void put(Body k, ComplexCosmicSystem v) {
        if (k == null || v == null) {
            return;
        }
        int i = find(k);
        ComplexCosmicSystem old = vs[i];
        vs[i] = v;
        if (ks[i] == null) {
            ks[i] = k;
        }
    }

    public ComplexCosmicSystem get(Body k) {
        return vs[find(k)];
    }

    private int find(Body k) {
        if (k == null) {
            return ks.length - 1;
        }
        int i = k.hashCode() & (ks.length - 2);
        if (ks[i] != null && !ks[i].equals(k)) {
            kols++;
        }
        while (ks[i] != null && !ks[i].equals(k)) {
            i = (i + 1) & (ks.length - 2);
        }
        return i;
    }

    public int countCollisions() {
        return kols;
    }


    public int size() {
        return count;
    }

    public boolean containsKey(Body k) {
        return ks[find(k)] != null;
    }

    // Returns the 'ComplexCosmicSystem' (value) with which a
    // body (key) is associated. If 'b' is not contained, 'null'
    // is returned.
    public ComplexCosmicSystem getParent(Body b) {
        return vs[find(b)];
    }

    // Returns 'true' if the specified 'b' is listed
    // in the index.
    public boolean contains(Body b) {
        return containsKey(b);
    }

    public Body[] toArray() {
        int ind = 0;
        Body[] array = new Body[count];
        for (int i = 0; i < ks.length; i++) {
            if (ks[i] != null) {
                array[ind] = new Body(ks[i]);
                ind++;
            }
        }
        return array;
    }

    public BodyCollection getBodies() {
        return new myCollection(this);
    }

    public String toString() {
        String output = "";
        for (int i = 0; i < ks.length; i++) {
            if (ks[i] != null) {
                output += ks[i].getName() + "\n";
            }
        }
        return output;
    }

    public BodyIterator iterator() {
        return new CosmicSystemMap.theMapIterator(this);
    }

    public class theMapIterator implements BodyIterator {

        CosmicSystemMap map;
        Body intermediate;
        int ind;
        boolean nextHasBeenCalled = false;
        boolean enteredwl = false;


        public theMapIterator(CosmicSystemMap map1) {
            map = map1;
            intermediate = map.ks[0];
            ind = 0;
        }


        @Override
        public boolean hasNext() {
            if (ind < map.ks.length) {
                return true;
            } else {
                return false;
            }
        }


        @Override
        public Body next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            } else {
                for (;ind<map.ks.length;ind++){
                    if(map.ks[ind] != null){
                        break;
                    }
                }
                nextHasBeenCalled = true;
                Body res = map.ks[ind];
                ind++;
                return res;
            }
        }

        public void remove() {
            if (!nextHasBeenCalled) {
                throw new IllegalStateException();
            } else {
                map.ks[ind-1] = null;
                map.vs[ind-1] = null;
                map.count--;
            }

            nextHasBeenCalled =false;
        }
    }
}
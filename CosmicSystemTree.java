//This class represents a binary search tree for objects of class 'CosmicSystem'
public class CosmicSystemTree {
    private String key;
    private CosmicSystem value;
    private CosmicSystemTree left, right;

    public CosmicSystemTree(String k, CosmicSystem v) {
        key = k;
        value = v;
    }


    // Adds a system of bodies to the tree. Since the keys of the tree are the names of bodies,
    // adding a system adds multiple (key, value) pairs to the tree, one for each body of the
    // system, with the same value, i.e., reference to the cosmic system.
    // An attempt to add a system with a body that already exists in the tree
    // leaves the tree unchanged and the returned value would be 'false'.
    // For example, it is not allowed to have a system with the bodies "Earth" and "Moon" and another
    // system with the bodies "Jupiter" and "Moon" indexed by the tree, since "Moon" is not unique.
    // The method returns 'true' if the tree was changed as a result of the call and
    // 'false' otherwise.
    public boolean add(CosmicSystem system) {
        for (int i = 0; i < system.size(); i++) {
            if (this.get(system.get(i).getName()) != null ) {
                return false;
            }
        }
        for (int i = 0; i < system.size(); i++) {
            this.myPut(system.get(i).getName(), system);
        }
        return true;
    }

    public void myPut(String k, CosmicSystem v){
        int cmp = this.myCompare(k);
        if (key == null) {
            key = k;
            value = v;
        }
        else if (cmp < 0) {
            if (left != null) {
                left.myPut(k, v);

        } else {
            left = new CosmicSystemTree(k, v);
        }
        }
        else if (cmp > 0) {
            if (right != null) {
                right.myPut(k, v);

            } else {
                right = new CosmicSystemTree(k, v);
            }
        }
    }

    public int numberOfLeafNodes() {
        int k = 1;
        if (left != null) {
            k += left.numberOfLeafNodes();
        }
        if (right != null) {
            k += right.numberOfLeafNodes();
        }
        if (left == null && right == null ) {
            return 1;
        }
        return k;
    }


    // Returns the cosmic system in which a body with the specified name exists.
    // For example, if the specified name is "Europa", the system of Jupiter (Jupiter, Io,
    // Europa, Ganymed, Kallisto) will be returned.
    // If no such system is found, 'null' is returned.
    public CosmicSystem get(String name) {
        int cmp = this.myCompare(name);
        if (cmp == 0) {
            return value;
        }
        CosmicSystemTree tree = cmp < 0 ? left : right;
        if (tree == null) {
            return null;
        }
        return tree.get(name);
    }

    // Returns the overall number of bodies indexed by the tree.
    public int numberOfBodies() {
        int n = 0;
        if (value != null) {
            n++;
        }
        if (left != null) {
            n += left.numberOfBodies();
        }
        if (right != null) {
            n += right.numberOfBodies();
        }
        return n;
    }

    // Returns a readable representation with (key, value) pairs, sorted alphabetically by the key.
    //E.g.,
    //    (Deimos,Mars System)
    //    (Earth,Earth System)
    //
    //Hint: for this you will also need a method in CosmicSystem.java to access the name of a CosmicSystem object.
    public String toString() {
        String output = "";
        if (left != null) {
            output += left.toString();
        }
        output += "\r\n" + "("  + key + ", " + value.getName() + ")";

        if (right != null) {
            output += right.toString();
        }
        return output;
    }


    //BONUS TASK: sets a new canvas and draws the tree using StdDraw
    public void drawTree() {
    }

    private int myCompare(String k) {
        if (k == null) {
            return key == null ? 0 : -1;
        }
        if (key == null) {
            return 1;
        }
        return k.compareTo(key);
    }

}



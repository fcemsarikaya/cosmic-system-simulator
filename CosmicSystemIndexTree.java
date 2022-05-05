// A binary search tree implementation of 'CosmicSystemIndex'. This binary tree
// uses a specified comparator for sorting its keys.

import java.util.NoSuchElementException;

public class CosmicSystemIndexTree implements CosmicSystemIndex, BodyIterable {

    private IndexTreeNode root;
    private BodyComparator comparator;

    // Initialises this index with a 'comparator' for sorting
    // the keys of this index.
    public CosmicSystemIndexTree(BodyComparator comparator) {
        this.comparator = comparator;
        this.root = IndexTreeNullNode.NIL; // NIL in the beginning.

    }

    // Adds a system of bodies to the index.
    // Adding a system adds multiple (key, value) pairs to the
    // index, one for each body of the system, with the same
    // value, i.e., reference to the celestial system.
    // An attempt to add a system with a body that already exists
    // in the index leaves the index unchanged and the returned
    // value would be 'false'.
    // The method returns 'true' if the index was changed as a
    // result of the call and 'false' otherwise.
    public boolean add(ComplexCosmicSystem system) {

        if (system == null || system.numberOfBodies() == 0) {
            return false;
        }


        //Invariant for following for-loops: hasNext() in Iterator of CCS is true.

        for (Body b: system) {
            if (this.contains(b)) {
                return false;
            }
        }

        for (Body b: system) {
            //VB: This tree doesn't contain the body to be added as a key in its nodes.
            root = root.add(new IndexTreeNonNullNode(b, system.getParent(b), comparator));
            //NB: A node which consists of the body and its system is added to the tree.
        }

        return true;

    }

    // Returns the system with which a body is
    // associated. If body is not contained as a key, 'null'
    // is returned.
    public ComplexCosmicSystem getParent(Body body) {
        //VB: The interrogated body is the key of one of the nodes in this tree.
        return root.get(body);
        //NB: The system that this body belongs is returned.
    }

    // Returns 'true' if the specified 'body' is listed
    // in the index.
    public boolean contains(Body body) {
        return getParent(body) != null;
    }

    // Returns a readable representation with (key, value) pairs sorted by the key.
    public String toString() {
        //VB: There's at least one node in this tree with representable key&value attributes.
        return "{" + root.toString() + "}";
        //NB: The key&value pairs of each node in this tree gets returned in String form.
    }

    // Returns the comparator used in this index.
    public BodyComparator getComparator() {
        return comparator;
    }

    @Override
    // Returns an iterator iterating over all celestial bodies of this index.
    public BodyIterator iterator() {
        //VB: There's at least one node in this tree, which consists a body.
        return root.iterator(new TreeNodeIterator(null, null));
        //NB: An iterator, which enables to traverse in the body objects that nodes of this tree contain, is returned.
    }

    @Override
    // Returns a 'BodyCollection' view of all bodies of this index.
    public BodyCollection getBodies() {

        return new TreeBodyCollection(this);
    }

    public int size() {
        //VB: There exists at least one node with non-null attributes in this tree.
        return root.size();
        //NB: The number of nodes in this tree is returned in Integer form.
    }

    @Override
    public Body[] toArray() {
        return new Body[0];
    }
}

interface IndexTreeNode {

    //VB: There's at least one node in the tree to be compared with the node to be added, while the node to be added
    // has a comparable attribute.
    //NB: The node has been added to a suitable position, as long as a node with the same key wasn't available
    // before function call. Otherwise nothing's changed.
    //
    // Adds the specified 'node' to the tree of which 'this' is the root
    // node. If the tree already has a node with the same key as that
    // of 'node' the tree remains unchanged.
    IndexTreeNode add(IndexTreeNode node);

    //VB: There's at least one node in the tree, the key of which to be compared.
    //NB: The cosmic system which contains the key of the body is returned, as long as it exists.
    //Otherwise null is returned.
    //
    // Returns the cosmic system with which a body is associated, if 'body' is a key
    // which is contained in this tree (the tree of which 'this' is the root node).
    // If body is not contained as a key, 'null' is returned.
    ComplexCosmicSystem get(Body body);

    //VB: The tree contains at least one node, so that it's represented in String form.
    //NB: Representation of the tree gets returned in String form.
    //
    // Returns a readable representation of the tree of which 'this' is the root node.
    String toString();

    //VB: There's at least one iterable node in the tree.
    //NB: An iterator, which enables to traverse among keys of the tree is returned.
    //
    // Returns an iterator over all keys of the tree of which 'this' is the root node.
    // 'parent' is the iterator of the parent (path from the root).
    TreeNodeIterator iterator(TreeNodeIterator parent);

    //VB: This specific body is not null.
    //NB: The key of this body is returned.
    //
    // Returns the key of this node.
    Body getKey();

    //VB: There's at least one node in tree that allows to start traversing it, so that total number of entries
    //in the tree is possible to be interrogated.
    //NB: Number of total entries in this tree is returned in integer form.
    //
    // Returns the number of entries in the tree of which 'this' is the root
    // node.
    int size();

}

// Implements a terminal node with no content (used instead of 'null').
class IndexTreeNullNode implements IndexTreeNode {
    // Singleton: only one instance is needed, because the
    // state of 'this' can not be changed.
    public static final IndexTreeNullNode NIL = new IndexTreeNullNode();

    // private to avoid object creation from outside
    private IndexTreeNullNode() {}

    // VB: -
    // NB: Input parameter is directly returned.
    //
    // Since VB is lighter and NB tighter than in interface, conditions are valid.
    //
    public IndexTreeNode add(IndexTreeNode node) {
        return node;
    }

    // VB: -
    // NB: null is returned.
    //
    // Since VB is lighter and NB tighter than in interface, conditions are valid.
    //
    public ComplexCosmicSystem get(Body body) {
        return null;
    }

    // VB: -
    // NB: An empty string is returned.
    //
    // Since VB is lighter and NB tighter than in interface, conditions are valid.
    //
    public String toString() {
        return "";
    }

    // VB: -
    // NB: Iterator doesn't point at another node.
    //
    // Since VB is lighter and NB tighter than in interface, conditions are valid.
    //
    public TreeNodeIterator iterator(TreeNodeIterator parent) {
        return parent;
    }


    // VB: -
    // NB: null is returned.
    //
    // Since VB is lighter and NB tighter than in interface, conditions are valid.
    //
    public Body getKey() {
        return null;
    }

    // VB: -
    // NB: 0 is returned.
    //
    // Since VB is lighter and NB tighter than in interface, conditions are valid.
    //
    public int size() { return 0; }

}

// A node with a content.
class IndexTreeNonNullNode implements IndexTreeNode {
    private IndexTreeNode left;
    private IndexTreeNode right;
    private Body key;
    private ComplexCosmicSystem cs;
    private BodyComparator comparator;

    public IndexTreeNonNullNode(Body key, ComplexCosmicSystem cs,
                                BodyComparator comparator) {
        this.key = key;
        this.cs = cs;
        this.left = IndexTreeNullNode.NIL;
        this.right = IndexTreeNullNode.NIL;
        this.comparator = comparator;

    }

    //VB: The node to be added contains a body that's comparable as key.
    //NB: The node is added to its appropriate place in tree, as long as
    // such a node with same key wasn't in tree already. In that case nothing is changed.
    //
    // Since VB is lighter than and NB is same as in interface, conditions are valid.
    //
    public IndexTreeNode add(IndexTreeNode node) {
        int comp = this.comparator.compare(this.key, node.getKey());
        if (comp > 0) {
            left = left.add(node);
        } else {
            if (comp < 0) {
                right = right.add(node);
            }
        }
        return this;
    }


    //VB: A cosmic system which contains the searched body is included in the subtree of this object.
    //NB: If there's a node with searched body as key in the subtree of this object, the parent of this
    //body is returned. Else, nullPointerException.
    //
    // Since NB is tighter than in interface, conditions are not valid, although NB is same as in interface.
    //
    public ComplexCosmicSystem get(Body body) {
        if (key.equals(body)) {
            return cs;
        }

        if (this.comparator.compare(this.key, body) > 0) {
            return left.get(body);
        } else {
            return right.get(body);
        }
    }


    //VB: This object and its children have as String representable Body and ComplexCosmicSystem object variables.
    //NB: String form representation of the Body and ComplexCosmicSystem object variables in nodes of the subtree
    // of this object is returned.
    //
    // Since NB is tighter than in interface, conditions are not valid, although NB is also tighter than in interface.
    //
    public String toString() {
        String result;
        String right = this.right.toString();
        result = this.left.toString();
        result += result.isEmpty() ? "" : ",\n";
        result += this.key + " belongs to " + this.cs;
        result += right.isEmpty() ? "" : ",\n";
        result += right;
        return result;
    }


    //VB: This object has a non-null Body object variable.
    //NB: The object variable key is returned.
    //
    // Since VB and NB are same as in interface, conditions are valid.
    //
    public Body getKey() {
        return key;
    }


    //VB: Left is not null.
    //NB: The Iterator of IndexTreeNode class is returned for left child of this object.
    //
    // Since NB is tighter than in interface, conditions are not valid, although NB is also tighter than in interface.
    //
    public TreeNodeIterator iterator(TreeNodeIterator next) {
        return left.iterator(new TreeNodeIterator(this, next));
    }

    //VB: hasNext() of the iterator is not null.
    //NB: The output of next() in Iterator is returned.
    //
    public TreeNodeIterator nextStep(TreeNodeIterator next) {
        return right.iterator(next);
    }


    //VB: This object is not null.
    //NB: The size of the subtree, the root of which is this this object, is returned.
    //
    // Since VB and NB are same as in interface, conditions are valid.
    //
    public int size() { return 1 + left.size() + right.size(); }

}

class TreeNodeIterator implements BodyIterator {
    private IndexTreeNonNullNode node;
    private TreeNodeIterator next;

    public TreeNodeIterator(IndexTreeNonNullNode node, TreeNodeIterator next) {
        this.node = node;
        this.next = next;
    }

    @Override
    public boolean hasNext() {
        return node != null;
    }

    @Override
    public Body next() {
        if (hasNext()) {
            Body key = node.getKey();
            next = node.nextStep(next);
            node = next.node;
            next = next.next;
            return key;
        }
        throw new NoSuchElementException("This index has no (more) entries!");
    }

    @Override
    public void remove() {}

}

class TreeBodyCollection implements BodyCollection {

    private CosmicSystemIndexTree tree;

    public TreeBodyCollection(CosmicSystemIndexTree tree) {
        this.tree = tree;
    }

    public boolean add(Body b) {
        return false;
    }

    public boolean contains(Body b) {
        return tree.contains(b);
    }

    public int size() {
        return tree.size();

    }

    public Body[] toArray() {
        Body[] result = new Body[tree.size()];

        BodyIterator it = tree.iterator();

        for (int i = 0; i < result.length; i++) {

            result[i] = it.next();
        }

        return result;
    }

    @Override
    public BodyIterator iterator() {
        return tree.iterator();

    }
}



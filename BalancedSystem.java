import java.io.IOException;

public class BalancedSystem implements Cluster {

    private Cluster left;
    private Cluster right;

    //the constructor should throw a BalancedSystemIllegalArgumentException (to be implemented), if one of the arguments is null
    BalancedSystem(Cluster left, Cluster right) throws BalancedSystemIllegalArgumentException, IOException {
        if (left == null || right == null) {throw new BalancedSystemIllegalArgumentException();}
        this.left = left;
        this.right = right;
    }

    @Override
    //adds a Body b to the BalancedSystem. If the mass of the left cluster is less than the mass of the right cluster,
    //the body is added to the left cluster, otherwise to the right cluster.
    //Returns this after the operation
    public Cluster add(Body b) throws IOException, BalancedSystemIllegalArgumentException {
        if (left.getMass() > right.getMass()) {right = right.add(b);}
        else {left = left.add(b);}
        return this;
    }

    @Override
    //returns overall number of bodies in the cluster (ans its sub-clusters)
    public int numberOfBodies() {
        return left.numberOfBodies() + right.numberOfBodies();
    }

    @Override
    //returns the summed mass of all the bodies in the cluster (ans its sub-clusters)
    public double getMass() {
        return left.getMass() + right.getMass();
    }

    @Override
    //returns an iterator over all bodies
    public BodyIterator iterator() {
        return new myIterator(this);
    }

    @Override
    //returns a String indicating the masses of the left and right subsystem
    //E.g., "Left mass: 10.0, right mass: 12.0
    public String toString() {
        return "Left mass: " + left.getMass() + ", right mass: " + right.getMass();
    }

    public class myIterator implements BodyIterator {
        private final BodyIterator leftIterator;
        private final BodyIterator rightIterator;

        public myIterator (BalancedSystem system) {
            leftIterator = left.iterator();
            rightIterator = right.iterator();
        }

        @Override
        public boolean hasNext() {
            return leftIterator.hasNext() || rightIterator.hasNext();
        }

        @Override
        public Body next() {
            if (leftIterator.hasNext()) {return leftIterator.next();}
            else {return rightIterator.next();}
        }

        @Override
        public void remove() {}

    }
}
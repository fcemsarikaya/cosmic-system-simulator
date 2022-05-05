import java.util.LinkedList;

public class myCollection implements BodyCollection {

    CosmicSystemIndex list;

    public myCollection(CosmicSystemIndex ccs) {
        list = ccs;
    }

    @Override
    public boolean add(Body b) {
        return false;
    }

    @Override
    public boolean contains(Body b) {
        return list.contains(b);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public Body[] toArray() {
        return list.toArray();
    }

    @Override
    public BodyIterator iterator() {
        return list.iterator();
    }
}

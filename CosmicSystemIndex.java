// A complex cosmic system regarded as an associative data structure.
// The key is a single body and the associated value returned
// by 'getParent' is the reference to the smallest complex cosmic system to which
// the body belongs. For example, searching in the entire solar system for 'Io' returns
// the jupiter system (Jupiter, Io, Europa, Ganymede, Kallisto, ...).
public interface CosmicSystemIndex {

    // Returns the 'ComplexCosmicSystem' (value) with which a
    // body (key) is associated. If 'b' is not contained, 'null'
    // is returned.
    ComplexCosmicSystem getParent(Body b);

    // Returns 'true' if the specified 'b' is listed
    // in the index.
    boolean contains(Body b);

    // Returns a collection view of all `Body`-Objects listed in this
    // index (i.e. the set of keys). Subsequent changes in `this` will be
    // reflected in the returned collection view.
    BodyCollection getBodies();

    int size();

    Body[] toArray();

    BodyIterator iterator();
}

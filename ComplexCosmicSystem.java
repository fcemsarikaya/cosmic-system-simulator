import java.awt.*;
import java.util.NoSuchElementException;

//This class represents a double-linked list for objects of class 'CosmicComponent'.
public class ComplexCosmicSystem implements CosmicComponent, BodyIterable, CosmicSystemIndex {

    private myCosmicNode head;
    private final String name;


    // Initialises this system with a name and at least two cosmic components.
    public ComplexCosmicSystem(String name, CosmicComponent c1, CosmicComponent c2,
                               CosmicComponent... ci) {
        this.name = name;
        this.add(c1);
        this.add(c2);
        for (CosmicComponent i : ci) {
            this.add(i);
        }
    }

    public ComplexCosmicSystem myGetParent(Body body) {
        myCosmicNode current = head;
        while (current != null) {
            if ((current.get() instanceof Body)) {
                if (((Body) current.get()).equals(body)) {
                    return this;
                }
            } else if (current.get() instanceof ComplexCosmicSystem) {
                if (((ComplexCosmicSystem) current.get()).myGetParent(body) != null) {
                    return ((ComplexCosmicSystem) current.get()).myGetParent(body);
                }
            }
            current = current.getNext();
        }
        return null;
    }

    public ComplexCosmicSystem getParent(Body b) {
        if(b == null){return null;}
        return this.myGetParent(b);
    }

    public boolean contains(Body b) {
        BodyIterator iter = this.iterator();
        while (iter.hasNext()) {
            if (iter.next().equals(b)) {
                return true;
            }
        }
        return false;
    }

    public boolean equals(CosmicComponent comp) {
        if (this.hashCode() == comp.hashCode()) {
            return true;
        } else {
            return false;
        }
    }

    public int hashCode() {
        int hash = name.hashCode();
        myCosmicNode temp = head;
        while (temp != null) {
            hash += temp.get().hashCode();
            temp = temp.getNext();
        }
        return hash;
    }


     //Initialises this system as an empty system with a name.
    public ComplexCosmicSystem(String name) {
        this.name = name;
    }

    // Adds 'comp' to the list of cosmic components of the system if the list does not already contain a
    // component with the same name as 'comp', otherwise does not change the object state. The method
    // returns 'true' if the list was changed as a result of the call and 'false' otherwise.
    public boolean add(CosmicComponent comp) {
        myCosmicNode node = new myCosmicNode(comp);
        if (head == null) {
            head = node;
            head.setNext(null);
            head.setPrev(null);
        } else {
            myCosmicNode last = head;
            while (last.getNext() != null) {
                if (last.getName().equals(comp.getName())) {
                    return false;
                }
                last = last.getNext();
            }
            last.setNext(node);
            node.setPrev(last);
            node.setNext(null);
        }
        return true;
    }

    //Removes a component from the list if the list contains a component with the same name as the input component.
    //Returns true if removal was done, and false otherwise (no component with the same name).
    public boolean remove(CosmicComponent comp) {
        myCosmicNode temp = head;
        while (temp != null) {
            if (temp.getName().equals(comp.getName())) {
                break;
            }
            temp = temp.getNext();
        }
        if (temp == null) {
            return false;
        } else if (temp == head && temp.getNext() == null) {
            head = null;
            return true;
        } else if (temp == head && temp.getNext() != null) {
            temp.getNext().setPrev(null);
            head = temp.getNext();
            return true;
        } else if (temp != head && temp.getNext() == null) {
            temp.getPrev().setNext(null);
            temp.setPrev(null);
            return true;
        }
        myCosmicNode n = temp.getNext();
        myCosmicNode p = temp.getPrev();
        p.setNext(n);
        n.setPrev(p);
        return true;
    }

    // Returns the CosmicComponent with the specified name or 'null' if no such component exists in the list.
    public CosmicComponent get(String name) {
        myCosmicNode temp = head;
        while (temp != null) {
            if (temp.getName().equals(name)) {break;}
            temp = temp.getNext();
        }
        if (temp != null) {return temp.get();}
        else {return null;}
    }

    // Returns the CosmicComponent with the same name as the input component or 'null' if no such CosmicComponent exists in the list.
    public CosmicComponent get(CosmicComponent c) {
           return get(c.getName());
    }

    // Returns the name of this system.
    public String getName() {
        return this.name;
    }

    // Returns the number of CosmicComponent entries of the list.
    public int size() {
        return numberOfBodies();
    }

    // Returns a readable representation of the ComplexCosmicSystem.
    // The representation should list all the names of its bodies and sub-systems, where the hierarchy is indicated by {} brackets
    //For instance, considering if we have a system called "Solar System" with the entries "Sun", "Earth System" and "Jupiter System".
    //"Sun" is a body, "Earth System" is a system with the bodies "Earth" and "Moon", and "Jupiter System" is a system with the body "Jupiter".
    //Then the output should be "Solar System{Sun, Earth System{Earth, Moon}, Jupiter System{Jupiter}}"
    //An empty system is indicated by empty brackets, e.g. "Jupiter System{}"
    //
    //CONSTRAINT: use the concept of dynamic binding to fulfill this task, i.e. don't use type casts, getClass() or instanceOf().
    public String toString() {
        String result = name + "{";
        myCosmicNode temp = head;
        while (temp.getNext() != null) {
            result += temp.get().toString() + ", ";
            temp = temp.getNext();
        }
        result += temp.get().toString() + "}";
        return result;
    }

    /*
     public String toStringReversed() {
       String result = name + "{";
      myCosmicNode temp = head;
      while (temp.getNext() != null) {temp = temp.getNext();}
      while (temp.getPrev() != null) {
          result += temp.get().toStringReversed()  + ", ";
          temp = temp.getPrev();
      }
      result += temp.get().toStringReversed() + "}";
      return result;
    }
*/
    //Returns the overall number of bodies (i.e. objects of type 'Body') contained in the ComplexCosmicSystem.
    //For instance, the System "Solar System{Sun, Earth System{Earth, Moon}, Jupiter System{Jupiter}}" contains 4 bodies (Sun, Earth, Moon and Jupiter).
    //
    //CONSTRAINT: use the concept of dynamic binding to fulfill this task, i.e. don't use type casts, getClass() or instanceOf().
    public int numberOfBodies() {
        int index = 0;
        myCosmicNode temp = head;
        while (temp != null) {
            index += temp.get().numberOfBodies();
            temp = temp.getNext();
        }
        return index;
    }

    //Returns the overall mass (sum of all contained components).
    //In case of an empty system, a mass of 0.0 should be returned.
    //
    //CONSTRAINT: use the concept of dynamic binding to fulfill this task, i.e. don't use type casts, getClass() or instanceOf().
    public double getMass() {
        double total = 0;
        myCosmicNode temp = head;
        while (temp != null) {
            total += temp.get().getMass();
            temp = temp.getNext();
        }
        return total;
    }

    //Returns the gravitational center of this component (weighted average of contained components).
    //In case of an empty system, a vector [0.0, 0.0, 0.0] should be returned.
    //
    //CONSTRAINT: use the concept of dynamic binding to fulfill this task, i.e. don't use type casts, getClass() or instanceOf().
    public Vector3 getMassCenter() {
        Vector3 total = new Vector3(0.0, 0.0, 0.0);
        myCosmicNode temp = head;
        while (temp != null) {
            total = total.plus(temp.get().getMassCenter().times(temp.get().getMass()));
            temp = temp.getNext();
        }

        if (this.getMass() == 0) {
            return new Vector3(0.0, 0.0, 0.0);
        }
        total = total.times(1 / this.getMass());
        return total;
    }

    public Body[] toArray() {
        int count = 0;
        Body[] array = new Body[numberOfBodies()];
        for (Body i : this) {
            array[count] = new Body(i);
            count++;
        }
        return array;
    }

    public BodyCollection getBodies() {
        return new myCollection(this);
    }


    @Override
    public BodyIterator iterator() {
        return new theIterator(this);
    }


    public class theIterator implements BodyIterator {

        ComplexCosmicSystem ccs;
        Body current;
        myCosmicNode intermediate;
        BodyIterator bItTemp = null;
        boolean nextHasBeenCalled = false;


        public theIterator(ComplexCosmicSystem ccs_) {
            ccs = ccs_;
            intermediate = ccs.head;
        }


        @Override
        public boolean hasNext() {
            return intermediate != null;
        }


        @Override
        public Body next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            } else {

                if (intermediate.get() instanceof Body) {
                    Body result = (Body) intermediate.get();
                    intermediate = intermediate.getNext();
                    nextHasBeenCalled = true;
                    current = result;
                    return result;
                } else {
                    if (bItTemp == null) {
                        bItTemp = ((ComplexCosmicSystem) intermediate.get()).iterator();
                    }
                    Body result = bItTemp.next();
                    if (!bItTemp.hasNext()) {
                        intermediate = intermediate.getNext();
                        bItTemp = null;
                    }
                    nextHasBeenCalled = true;

                    current = result;
                    return result;
                }
            }
        }

        public void remove() {
            if (!nextHasBeenCalled) {
                throw new IllegalStateException();
            } else {

                ComplexCosmicSystem c = ccs.getParent(current);
                if (c != null) {
                    c.remove(current);
                } else {
                    ccs.remove(current);
                }
            }
            nextHasBeenCalled = false;
        }
    }
}

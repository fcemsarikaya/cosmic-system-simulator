//This class represents a linked list for objects of class 'Body'
public class CosmicSystem {
    private myDLNode head;
    private final String name;

    // Initialises this system as an empty system with a name.
    public CosmicSystem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // Adds 'body' to the end of the list of bodies if the list does not already contain a
    // body with the same name as 'body', otherwise does not change the object state. The method
    // returns 'true' if the list was changed as a result of the call and 'false' otherwise.
    public boolean add(Body body) {
        myDLNode node = new myDLNode(body);
        if (head == null) {
            head = node;
            head.setNext(null);
            head.setPrev(null);
        } else {
            myDLNode last = head;
            while (last.getNext() != null) {
                if (last.getName().equals(body.getName())) {
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

    // Returns the 'body' with the index 'i'. The body that was first added to the list has the
    // index 0, the body that was most recently added to the list has the largest index (size()-1).
    // Precondition: 'i' is a valid index.
    public Body get(int i) {
        if (i >= this.size()) {return null;}
        myDLNode current = head;
        for (int j = 0; j < i; j++) {
            current = current.getNext();
        }
        return current.get();
    }


    // Returns the body with the specified name or 'null' if no such body exits in the list.
    public Body get(String name) {
        myDLNode current = head;
        while (current != null) {
            if (current.getName().equals(name)) {
                break;
            }
            current = current.getNext();
        }
        return current == null ? null : current.get();
    }

    public myDLNode myGetNode(int i) {
        if (i >= this.size()) {return null;}
        myDLNode current = head;
        for (int j = 0; j < i; j++) {
            current = current.getNext();
        }
        return current;
    }

    public int myGetIndex(String name) {
        for (int i = 0; i < (this.size() - 1); i++) {
            if (this.get(i).getName().equals(name)) {return i;}
        }
        return -1;
    }

    // Returns the body with the same name as the input body or 'null' if no such body exits in the list.
    public Body get(Body body) {
        return get(body.getName());
    }

    // returns the number of entries of the list.
    public int size() {
        myDLNode current = head;
        int index = 0;
        while (current != null) {
            current = current.getNext();
            index++;
        }
        return index;
    }

    // Inserts the specified 'body' at the specified position
    // in this list if 'i' is a valid index and there is no body
    // in the list with the same name as that of 'body'.
    // Shifts the element currently at that position (if any) and
    // any subsequent elements to the right (adds 1 to their
    // indices). The first element of the list has the index 0.
    // Returns 'true' if the list was changed as a result of
    // the call, 'false' otherwise.
    public boolean add(int i, Body body) {
        myDLNode node = new myDLNode(body);
        for (int j = 0; j < this.size(); j++) {
            if (this.get(j).getName().equals(body.getName())) {return false;}
        }
        if (i < 0 || this.size() < i) {return false;}
        else {
            if (i == 0) {
                node.setNext(head);
                head.setPrev(node);
                head = node;
            }
            else if (i == this.size()) {
                this.myGetNode(i-1).setNext(node);
                this.myGetNode(i).setPrev(this.myGetNode(i-1));
            }
            else {
                this.myGetNode(i).setPrev(node);
                node.setPrev(this.myGetNode(i-1).getPrev());
                node.setNext(this.myGetNode(i));
                this.myGetNode(i-1).setNext(node);
            }
            return true;
        }
    }

    //removes the body at index i from the list, if i is a valid index
    //returns true if removal was done, and false otherwise (invalid index)
    public boolean remove(int i) {
        if ((this.size() - 1) < i || i < 0) {return false;}
        if (i == (this.size() - 1)) {
            this.myGetNode(i-1).setNext(null);
            return true;
        }
        else {
            if (i == 0 && this.size() == 1) {
                head.setNext(null);
                head.setPrev(null);
                head = null;
            }
            else if (i == 0 && this.size() > 1) {
                head = head.getNext();
                head.setPrev(null);
            }
            else {
                myDLNode previous = this.myGetNode(i-1);
                myDLNode next = previous.getNext().getNext();
                previous.setNext(next);
                next.setPrev(previous);
            }
            return true;
        }
    }

    //removes a body from the list, if the list contains a body with the same name as the input body
    //returns true if removal was done, and false otherwise (no body with the same name)
    public boolean remove(Body body) {
        int index = this.myGetIndex(body.getName());
        if (index != -1) {
            this.remove(index);
            return true;
        }
        return false;
    }

    // Returns a new list that contains the same elements as this list in reverse order. The list 'this'
    // is not changed and only the references to the bodies are copied, not their content (shallow copy).
    public CosmicSystem reverse() {
        CosmicSystem result = new CosmicSystem(this.getName());
        int size = this.size();
        for (int i = 0; i < size; i++) {
            myDLNode last = this.myGetNode(size - 1 - i);
            result.add(last.get());
        }
        return result;
    }

    // Returns a readable representation with the name of the system and all bodies in order of the list.
    // E.g.,
    // Jupiter System:
    // Jupiter, 1.898E27 kg, radius: 6.9911E7 m, position: [0.0,0.0,0.0] m, movement: [0.0,0.0,0.0] m/s.
    // Io, 8.9E22 kg, radius: 1822000.0 m, position: [0.0,0.0,0.0] m, movement: [0.0,0.0,0.0] m/s.
    //
    //Hint: also use toString() in Body.java for this.
    public String toString() {
        String result = this.getName() + ":" + "\r\n";
        for (int i = 0; i < this.size(); i++) {
            result += this.get(i).getName() + ", " + this.get(i).getMass() + " kg, radius: " + this.get(i).getRadius() + " m, position: "
                    + this.get(i).getPosition()+ " m, movement: " + this.get(i).getCurrentMovement()+ " m/s." + "\r\n";
        }
        return result;
    }

    public void deleteEveryNthElement(int N) {
        int i = 0;
        myDLNode current = head;
        if (N > 0) {
            while (current != null) {
                if (((i+1) % N) == 0) {
                    if (current.getPrev() == null) {
                        current.getNext().setPrev(null);
                        head = current.getNext();
                    } else if (current.getNext() == null) {
                        current.getPrev().setNext(null);
                    } else {
                        current.getPrev().setNext(null);
                        current.getNext().setPrev(null);
                    }
                }
                i++;
                current = current.getNext();
            }
        }
    }
}

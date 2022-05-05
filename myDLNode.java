public class myDLNode {
    private final Body value;
    private myDLNode next;
    private myDLNode prev;

    public myDLNode(Body body) {
        this.value = body;
        next = prev = null;
    }

    public String getName() {return this.value.getName();}
    public Body get() {return this.value;}

    public myDLNode getNext() {return this.next;}
    public myDLNode getPrev() {return this.prev;}

    public void setNext(myDLNode n) {
        this.next = n;
    }

    public void setPrev(myDLNode n) {
        this.prev = n;
    }
}

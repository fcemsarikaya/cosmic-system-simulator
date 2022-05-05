public class myCosmicNode {
    private final CosmicComponent value;
    private myCosmicNode next;
    private myCosmicNode prev;


    public myCosmicNode(CosmicComponent obj) {
        this.value = obj;
        next = prev = null;
    }


    public String getName() {return this.value.getName();}
    public CosmicComponent get() {return this.value;}

    public myCosmicNode getNext() {return this.next;}
    public myCosmicNode getPrev() {return this.prev;}

    public void setNext(myCosmicNode n) {
        this.next = n;
    }

    public void setPrev(myCosmicNode n) {
        this.prev = n;
    }
}

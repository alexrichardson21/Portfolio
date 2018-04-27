package int_linkedlist;
  
public class IntLinkedList {
	
	private Node head;
	private int size;
	
	IntLinkedList() {
		head = null;
		size = 0;
	}
	
	public int getSize() {
		return size;
	}
	
	public void prepend (int toAdd) {
		// LL could be empty or have something already at the head
		Node prevHead = head;
		head = new Node(toAdd);
		head.next = prevHead;
		size++;
	}
	
	public void removeAt (int index) {
	    Node current = head,
	         prev = null;
	      
	    // Find the Node just before the one to remove
	    // (if there is one before it)
	    while (current != null && index > 0) {
	        prev = current;
	        current = current.next;
	        index--;
	    }
	      
	    if (current == null) {return;}
	    if (current == head) { head = current.next; }
	    if (prev != null) {
	    	  	//remove it
	        prev.next = current.next;
	    }
	    size--;
	}
	
	public Iterator getInteratorAt (int index) {
		if (index > size || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		
		Iterator it = new Iterator();
		while (index > 0) {
			it.next();
			index--;
		}
		
		return it;
	}
	
	private class Node {
		int data;
		Node next;
		
		Node (int d) {
			data = d;
			next = null;
		}
    }
	
    public class Iterator {
    	  	private Node current;
    	  	
    	  	Iterator () {
    	  		current = head;
    	  	}
    		
    	  	public boolean hasNext () {
    	  		return current != null && current.next != null;
    	  	}
    	  	
    	  	public void next () {
    	  		if (current == null)
    	  			return;
    	  		current = current.next;
    	  	}
    	  	
    	  	public int getCurrentInt () {
    	  		if (current == null) { throw new IllegalStateException(); }
    	  		return current.data;
    	  	}
    }
    
    
    public static void main (String[] args) {
    		IntLinkedList LL = new IntLinkedList();
    		LL.prepend(1);
    		LL.prepend(2);
    		LL.prepend(3);
    		IntLinkedList.Iterator it = LL.getInteratorAt(0);
    		System.out.println(it.getCurrentInt());
    		it.next();
    		System.out.println(it.getCurrentInt());
    		it.next();
    		System.out.println(it.getCurrentInt());
    		LL.removeAt(1);
    		
    		LL.removeAt(0);
    }
}
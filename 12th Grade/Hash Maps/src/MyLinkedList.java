
public class MyLinkedList<K,V> implements SimpleList<K,V>{
	private int size;
	private Node<K,V> head;
	private Node<K,V> tail;
	
	public MyLinkedList(){
		size=0;
		head=null;
		tail=null;
	}
	
	
	public int size(){
		return size;
	}
	public void clear(){
		head=null;
		tail=null;
		size=0;
	}
	public boolean isEmpty(){
		if (head == null)
			return true;
		else
			return false;
	}
	public V getFirstValue(){
		if (head != null)
			return head.value;
		else
			throw new IndexOutOfBoundsException("uh oh");
	}
	public V getLastValue(){
		if (tail != null)
			return tail.value;
		else
			throw new IndexOutOfBoundsException("uh oh");
	}
	public void addLast(K key, V value){
		Node<K,V> newNode = new Node<K,V> (key,value,tail);
		if (tail != null)
			tail.next=newNode;
		else
			head=newNode;
		tail=newNode;
		size++; 
	}
	
	public boolean add(K key, V value){
		addLast(key,value);
		return true;
	}
	
	public V get(K key){
		Node<K,V> node = head;
		for (int i=0; i<size; i++){
			if (node.key.equals(key)){
				return node.value;
			}
			node = node.next;
		}
		return null;
	}
	public V set(K key, V value){
		Node<K,V> node = getNode(key);
		V oldValue = node.value;
		node.value=value;
		return oldValue;
	}
	
	public boolean contains(K key){
		Node<K,V> node = head;
		for (int i=0; i<size; i++){
			if (node.key.equals(key)){
				return true;
			}
			node = node.next;
		}
		return false;
	}
	
	
	
	private Node<K,V> getNode(K key){
		Node<K,V> node = head;
		for (int i=0; i<size; i++){
			if (node.key.equals(key)){
				return node;
			}
			node = node.next;
		}
		throw new IndexOutOfBoundsException("uh oh");
	}
	Node<K,V> getHead(){
		return head;
	}
	
	void printList(){
		Node<K,V> current = head;
		while (current != null){
			System.out.println(current.key);
			current = current.next;
		}
	}
}

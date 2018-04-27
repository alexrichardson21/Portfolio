import java.util.HashSet;
import java.util.Set;

public class MyMap <K,V> implements SimpleMap <K,V>{
	private int size;
	private MyLinkedList<K,V>[] map;
	private int mapLength;
	
	@SuppressWarnings("unchecked")
	public MyMap(int mapSize){
		size = 0;
		map = new MyLinkedList[mapSize];
		for (int i = 0; i < mapSize; i+=1){
			this.map[i]= new MyLinkedList<K,V>();
		}
		mapLength = mapSize;
	}
	public MyMap(){
		this(7);
		mapLength = 7;
	}
	public V put(K key, V value){
		// fix duplicates and returning null
		int hashcode = hash(key);
		if (map[hashcode].contains(key)==false){
			map[hashcode].add(key, value);
			size++;
			return null;
		}else{
			return map[hashcode].set(key, value);
		}
	}
	public V get(K key){
		int hashcode = hash(key);
		return map[hashcode].get(key);
	}
	public boolean containsKey(K key){
		int hashcode = hash(key);
		return map[hashcode].contains(key);
	}
	@SuppressWarnings("unchecked")
	public void clear(){
		map = new MyLinkedList[mapLength];
		size = 0;
		for (int i = 0; i < mapLength; i++){
			this.map[i]= new MyLinkedList<K,V>();
		}
	}
	public boolean isEmpty(){
		if (size == 0)
			return true;
		else
			return false;
	}
	public int size(){
		return size;  
	}
	public Set<K> keySet(){
		// Make a hashSet
		// need not be O(1)
		Set<K> set = new HashSet<K>();
		for (int i = 0; i < mapLength; i+=1){
			Node<K,V> current = map[i].getHead();
			while (current != null){
				set.add(current.key);
				current = current.next;
			}
		}
		return set;
	}
	public int hash(K key){
		int hash = key.hashCode();
		hash=Math.abs(hash);
		hash=hash%mapLength;
		return hash;
	}
}

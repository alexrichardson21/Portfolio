
interface SimpleList<K,V>
{
  // This is based on the java.util.List interface.
  // For documentation of these methods,
  //    see http://docs.oracle.com/javase/8/docs/api/java/util/LinkedList.html
  // You should throw an IndexOutOfBoundsException
  //    if you get an index that is out of bounds.
 
  public int size();
  public void clear();
  public boolean isEmpty();
 
  public V getFirstValue();
  public V getLastValue();
  public void addLast(K key, V value);

  public V get(K key);
  public V set(K key, V value);
 
  public boolean contains(K key);
}
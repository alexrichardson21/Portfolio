class Node<K,V>
{
  K key;
  V value;
  Node<K,V> next;
 
  Node(K key, V value)
  {
    this.key = key;
    this.value = value;
    this.next = null;  
  }
 
  Node(K key, V value, Node<K,V> next)
  {
    this.key = key;
    this.next = next;
    this.value = value;  
  }
 
}
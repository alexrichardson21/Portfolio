
public class MapTester {
	public static void main(String[] args)
	{
	    SimpleMap<String,Integer> mymap = new MyMap<String,Integer>(5);
	    
	    System.out.println("isEmpty should be true: " + mymap.isEmpty());
	    System.out.println("size should be 0: " + mymap.size());
	  
	    mymap.put("Bird", 1);
	    mymap.put("Dog", 2);
	    
	    System.out.println("Bird should be 1: " + mymap.get("Bird"));
	    System.out.println("Dog should be 2: " + mymap.get("Dog"));
	    
	    System.out.println("size should be 2: " + mymap.size());
	    System.out.println("Contains Dog should be true: " + mymap.containsKey("Dog"));
	    
	    mymap.clear();
	    System.out.println("isEmpty should be true: " + mymap.isEmpty());
	    System.out.println("size should be 0: " + mymap.size());
	    
	    mymap.put("Gecko", 20);
	    mymap.put("Lion", 30);
	    mymap.put("Tortoise", 40);
	    mymap.put("Platypus", 50);
	    mymap.put("Gazelle", 60);
	    mymap.put("Lion", 70);
	    System.out.println("Gecko should be 20: " + mymap.get("Gecko"));
	    System.out.println("Tortoise should be 40: " + mymap.get("Tortoise"));
	    System.out.println("Lion should be 70: " + mymap.get("Lion"));
	    System.out.println("Cat should be null: " + mymap.get("Cat"));
	    System.out.println("isEmpty should be false: " + mymap.isEmpty());
	    System.out.println("Set: " + mymap.keySet());
	}
}

import java.io.File;
import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;

public class AirportCache {
	
	// DO NOT MODIFY THE CLASS Airport 
	public static class Airport {
		String code;
		String name;
		String latitude;
		String longitude;
		
		public Airport(String code, String name, String latitude, String longitude) {
			this.code = code;
			this.name = name;
			this.latitude = latitude;
			this.longitude = longitude;
		}
		
		public String toString() {
			StringBuilder result = new StringBuilder();
			result.append("[");
			result.append(this.code);
			result.append("] ");
			result.append(this.name);
			result.append(" (");
			result.append(latitude);
			result.append(",");
			result.append(longitude);
			result.append(")");
			return result.toString();
		}
		
		public static Airport buildAirport(String line) {
			String[] fields = line.split(",");
			return new Airport(fields[0], fields[1], fields[2], fields[3]);
		}
	}
	
	// DO NOT MODIFY THE INTERFACE Cache
	public static interface Cache {
		public Airport get(String code);
		public void add(String code, Airport obj);
	}
	
	// WRITE CODE HERE; UPDATE THE CLASS FIFOCache 
	public static class FIFOCache implements Cache {
		private int size;
	    
	    private Queue <Airport> queue = new LinkedList<Airport>(); // using queue because it has the FIFO property
	    
		public FIFOCache(int size) {
			this.size = size;
		}
		
		// WRITE CODE HERE
		public Airport get(String code) {
			// if object found in cache
			for(Airport a: queue) {
				if (a.code.equals(code)) {
					return a;
				}
			}
			// when object not in cache
			return null; 
		}
		
		// WRITE CODE HERE
		public void add(String code, Airport obj) {
			// when the data is found in cache, no further action is required
			for(Airport a : queue) {
				if(a.code.equals(code)) {
					break;
				}
			}
			if (queue.size() >= size) {
	            queue.poll(); // Remove the oldest entry if the cache is full
	        }
	        queue.add(obj); // Add the new entry to the end of the queue
		}
	}
	
	// WRITE CODE HERE; UPDATE THE CLASS LRUCache
	public static class LRUCache implements Cache {
		private int size;
		
		private LinkedList<Airport> list = new LinkedList<Airport>();
		
		public LRUCache(int size) {
			this.size = size;
		}
		
		// WRITE CODE HERE
		public Airport get(String code) {
			// when object is found in cache
			for(Airport a: list) {
				if (a.code.equals(code)) {
				// remove object to update position of the object in queue so it is the last recently used
					list.remove(a); // Remove the found airport
	                list.addFirst(a); // Add it to the front (most recently used)
                    return a; 
					
				}
			}
			// UPDATE RETURN STATEMENT
			return null;
		}
		
		// WRITE CODE HERE
		public void add(String code, Airport obj) {
			for (Airport airport : list) {
	            if (airport.code.equals(code)) {
	                list.remove(airport); // Remove if already present to update its position    
	            }
	        }
			
	        if (list.size() >= size) {
	            Airport last = list.getLast();
	            list.remove(last);// Remove the least recently used (last element) if cache is full
	        }
	        list.add(obj); // Add the new entry to the front (most recently used)
	    }
	}
	
	// DO NOT MODIFY THE METHOD findAirportFromCache
	private static Airport findAirportFromCache(String code, Cache c) {
		return c.get(code);
	}
	
	// DO NOT MODIFY THE METHOD findAirportFromFile EXCEPT FOR FILEPATH
	private static Airport findAirportFromFile(String code, Cache c) {
		// PLACE THE FILE airport.csv AT THE LOCATION IT CAN BE READ
		// IF NEEDED, MODIFY FILEPATH **ONLY**
		try (Scanner scanner = new Scanner(new File("airport.csv"))) {
		    while (scanner.hasNextLine()) {
		    	Airport current = Airport.buildAirport(scanner.nextLine());
		    	if(current.code.equals(code)) {
		    		c.add(code, current);
		    		return current; 
		    	}
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;		
	}
	
	// DO NOT MODIFY THE METHOD findAirport
	public static Airport findAirport(String code, Cache c) {
		Airport result = findAirportFromCache(code, c);
		if (result == null) {
			System.out.println("CACHE MISS");
			return findAirportFromFile(code, c);
		}
		System.out.println("CACHE HIT");
		return result;
	}
	
	// TRY OTHER TEST CASES BY UPDATING THE METHOD main
	public static void main(String[] args) {
		int CACHE_SIZE = 3;
		
		System.out.println("=== LRU  Cache ===");
		Cache lc = new LRUCache(CACHE_SIZE);
		System.out.println(findAirport("SFO", lc));
		System.out.println(findAirport("JFK", lc));
		System.out.println(findAirport("LAX", lc));
		System.out.println(findAirport("LAX", lc));
		System.out.println(findAirport("JFK", lc));
		System.out.println(findAirport("SFO", lc));
		System.out.println(findAirport("ICN", lc));
		System.out.println(findAirport("SFO", lc));
		System.out.println(findAirport("ICN", lc));
		
		System.out.println("=== FIFO Cache ===");
		Cache fc = new FIFOCache(CACHE_SIZE);
		System.out.println(findAirport("SFO", fc));
		System.out.println(findAirport("JFK", fc));
		System.out.println(findAirport("LAX", fc));
		System.out.println(findAirport("LAX", fc));
		System.out.println(findAirport("JFK", fc));
		System.out.println(findAirport("SFO", fc));
		System.out.println(findAirport("ICN", fc));
		System.out.println(findAirport("SFO", fc));
		System.out.println(findAirport("ICN", fc));
	}
}

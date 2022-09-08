/*
 * Class to create the HashMapLP Class
 * @author	Steven Kravitz
 * @version	1.0
 * created 5/4/2021
 * last edited 5/4/2021
 */
import java.util.ArrayList;
import java.util.LinkedList;
public class HashMapLP <K, V> {
	private int size;
	private double loadFactor;
	private MapEntry<K, V>[] hashTable;
	// Constructors
	public HashMapLP() { //O(log n)
		this(100, 0.5);
	}
	public HashMapLP(int c) { //O(log n)
		this(c, 0.5);
	}
	public HashMapLP(int c, double lf) { //O(log n)
		hashTable = new MapEntry[trimToPowerOf2(c)];
		loadFactor = lf;
	}
	// private methods
	private int trimToPowerOf2(int c) { //O(log n)
		int capacity = 1;
		while (capacity < c) {
			capacity = capacity << 1; // * 2
		}
		return capacity;
	}
	private int hash(int hashCode) {  //O(1)
		return hashCode & (hashTable.length-1);
	}
	private void rehash() { //O(n)
		ArrayList<MapEntry<K, V>> list = toList();
		hashTable = new MapEntry[hashTable.length << 1];
		size = 0;
		for(MapEntry<K, V> entry: list) {
			put(entry.getKey(), entry.getValue());
		}
	}
	// public interface
	public int size() { //O(1)
		return size;
	}
	public void clear() { //O(1)
		size = 0;
		for(int i=0; i < hashTable.length; i++) {
			if(hashTable[i] != null) {
				hashTable[i] = null;
			}
		}
	}
	public boolean isEmpty() { //O(1)
		return (size == 0);
	}
	// search for key in the hash map, returns true if found
	public boolean containsKey(K key) { //O(1)
		if(get(key) != null) {
			return true;
		}
		return false;
	}
	// returns the value of key if found, otherwise null
	public V get(K key) { //O(1)
		HashMaps.LPIterations = 0;
		int index = hash(key.hashCode());
		while(hashTable[index] != null) {
			HashMaps.LPIterations++;
			if(hashTable[index].getKey().equals(key)) {
				return hashTable[index].getValue();
			}
			index = (index + 1) % hashTable.length;
		}
		return null;
	}
	// remove a key if found
	public void remove(K key) { //O(1)
		int index = hash(key.hashCode());
		if(containsKey(key)) {
			while(!key.equals(hashTable[index].getKey())) {
				index = (index + 1) % hashTable.length;
			}
			hashTable[index] = null;
			size--;
		}
		
	}
	// Function to add a new element to the hashmap
	public V put(K key, V value) { // The key is in the hash map  //O(1)
		int index = hash(key.hashCode());
		int temp = index;
		int j = 1;
		do {
			// key not in the hash map - check load factor
			if(get(key) == null) {
				if(size >= hashTable.length * loadFactor) {
					rehash();
				}
				hashTable[index] = new MapEntry<K, V>(key, value);
				size++;
				return hashTable[index].getValue();
			}
			if(hashTable[index].getKey().equals(key)) {
				V old = hashTable[index].getValue();
				hashTable[index].value = value;
				return old;
			}
			index = (index + 1) % hashTable.length;
		} while(index != temp);
		System.out.println(index);
		return null;
	}
	// returns the elements of the hash map as a list
	public ArrayList<MapEntry<K, V>> toList(){ //O(n)
		ArrayList<MapEntry<K, V>> list = new ArrayList<>();
		for(int i=0; i< hashTable.length; i++) {
			if(hashTable[i]!= null) {
				list.add(hashTable[i]);
			}
		} 
		return list;
	}
	// returns the elements of the hash map as a string
	public String toString() { //O(n)
		String out = "[";
		for(int i=0; i<hashTable.length; i++) {
			if(hashTable[i] != null) {
				out += hashTable[i].toString();
				out += "\n";
			}
		}
		out += "]";
		return out;
	}
}

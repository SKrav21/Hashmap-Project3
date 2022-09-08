/*
 * Class to create the HashMapSC Class
 * @author	Steven Kravitz
 * @version	1.0
 * created 5/4/2021
 * last edited 5/4/2021
 */
import java.util.ArrayList;
import java.util.LinkedList;
public class HashMapSC <K, V> {
	private int size;
	private double loadFactor;
	private LinkedList<MapEntry<K, V>>[] hashTable;
	
	// Constructors
	public HashMapSC() { //O(log n)
		this(100, 0.9);
	}
	public HashMapSC(int c) { //O(log n)
		this(c, 0.9);
	}
	public HashMapSC(int c, double lf) { //O(log n)
		hashTable = new LinkedList[trimToPowerOf2(c)];
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
		hashTable = new LinkedList[hashTable.length << 1];
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
		for(int i=0; i<hashTable.length; i++) {
			if(hashTable[i] != null) {
				hashTable[i].clear();
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
		HashMaps.SCIterations = 0;
		int bucketIndex = hash(key.hashCode());
		if(hashTable[bucketIndex] != null) {
			LinkedList<MapEntry<K, V>> bucket = hashTable[bucketIndex];
			for(MapEntry<K, V> entry: bucket) {
				HashMaps.SCIterations++;
				if(entry.getKey().equals(key)) {
					return entry.getValue();
				}
			}
		}
		return null;
	}
	// remove a key if found
	public void remove(K key) { //O(1)
		int bucketIndex = hash(key.hashCode());
		if (hashTable[bucketIndex] != null) { // key is in the hash map
			LinkedList<MapEntry<K, V>> bucket = hashTable[bucketIndex];
			for(MapEntry<K, V> entry: bucket) {
				if(entry.getKey().equals(key)) {
					bucket.remove(entry);
					size--;
					break;
				}
			}
		}
	}
	// adds a new key or modifies an existing key
	public V put(K key, V value) { // The key is in the hash map  //O(1)
		if(get(key) != null) {
			int bucketIndex = hash(key.hashCode());
			LinkedList<MapEntry<K, V>> bucket = hashTable[bucketIndex];
			for(MapEntry<K, V> entry: bucket) {
				if(entry.getKey().equals(key)) {
					V old = entry.getValue();
					entry.value = value;
					return old;
				}
			}
		}
		// key not in the hash map - check load factor
		if(size >= hashTable.length * loadFactor) {
			rehash();
		}
		int bucketIndex = hash(key.hashCode());
		//create a new bucket if bucket is empty
		if(hashTable[bucketIndex] == null) {
			hashTable[bucketIndex] = new LinkedList<MapEntry<K, V>>();
		}
		hashTable[bucketIndex].add(new MapEntry<K, V>(key, value));
		size++;
		return value;
	}
	// returns the elements of the hash map as a list
	public ArrayList<MapEntry<K, V>> toList(){ //O(n)
		ArrayList<MapEntry<K, V>> list = new ArrayList<>();
		for(int i=0; i< hashTable.length; i++) {
			if(hashTable[i]!= null) {
				LinkedList<MapEntry<K, V>> bucket = hashTable[i];
				for(MapEntry<K, V> entry: bucket) {
					list.add(entry);
				}
			}
		} 
		return list;
	}
	// returns the elements of the hash map as a string
	public String toString() { //O(n)
		String out = "[";
		for(int i=0; i<hashTable.length; i++) {
			if(hashTable[i]!=null) {
				for(MapEntry<K, V> entry: hashTable[i]) {
					out += entry.toString();
				}
				out += "\n";
			}
		}
		out += "]";
		return out;
	}
}
/*
 * Class to create the ManEntry Class
 * @author	Steven Kravitz
 * @version	1.0
 * created 5/4/2021
 * last edited 5/4/2021
 */
public class MapEntry <K, V> {
	//data members
	K key;
	V value;
	/*
	 * Constructor with 2 parameters
	 * @param k for the Key
	 * @param v for the value
	 */
	MapEntry(K k, V v) {
		key = k;
		value = v;
	}
	//getter for key
	K getKey() {
		return key;
	}
	//getter for value
	V getValue() {
		return value;
	}
	//returns the information of the class in a formatted String
	public String toString() {
		return "(" + key + ", " + value + ")";
	}
	
	
}

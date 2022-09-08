/*
 * Class to create the HashMapLP Class
 * @author	Steven Kravitz
 * @version	1.0
 * created 5/4/2021
 * last edited 5/4/2021
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
public class HashMaps {
	//data members
	public static int SCIterations = 0;
	public static int LPIterations = 0;
	public static int QPIterations = 0;
	//main method
	public static void main(String[] args) {
		HashMapSC<String, String> SC = new HashMapSC<>();
		HashMapLP<String, String> LP = new HashMapLP<>();
		HashMapQP<String, String> QP = new HashMapQP<>();
		ArrayList<MapEntry<String, String>> words = new ArrayList<>();
		File file = new File("dictionary.txt");
		Scanner readFile = null;
		try {
			readFile = new Scanner(file);
		}
		catch(FileNotFoundException e) {
			System.out.println("File not found");
			System.exit(0);
		}
		while(readFile.hasNextLine()) {
			String[] line = readFile.nextLine().split("\\|");
			SC.put(line[0], line[1]);
			LP.put(line[0], line[1]);
			QP.put(line[0], line[1]);
			MapEntry<String, String> me = new MapEntry<>(line[0], line[1]);
			words.add(me);
		}
		readFile.close();
		Random random = new Random();
		double SCTotal = 0;
		double LPTotal = 0;
		double QPTotal = 0;
		System.out.printf("%-20s\t%-20s\t%-20s\t%-20s\n", "Word", "Seperate Chaining", "Linear Probing", "Quadratic Probing");
		for(int i = 0; i < 100; i++) {
			int index = random.nextInt(words.size());
			MapEntry<String, String> me = words.get(index);
			SC.containsKey(me.getKey());
			SCTotal += SCIterations;
			LP.containsKey(me.getKey());
			LPTotal += LPIterations;
			QP.containsKey(me.getKey());
			QPTotal += QPIterations;
			//System.out.println(me.getKey());
			//System.out.println(QP.get(me.getKey()).hashCode());
			System.out.printf("%-20s\t%-20d\t%-20d\t%-20d\n", me.getKey(), SCIterations, LPIterations, QPIterations);
		}
		SCTotal = SCTotal / 100;
		LPTotal = LPTotal / 100;
		QPTotal = QPTotal / 100;
		System.out.printf("%-20s\t%-20.2f\t%-20.2f\t%-20.2f\n", "Average: ", SCTotal, LPTotal, QPTotal);
	}
	/*
	 * Theoretically I would expect the average performance to be, from best to worst, SC > LP > QP.
	 * Due to an error that I cannot comprehend despite immeasurable effort, some elements weren't properly put into LP and QP.
	 * As such, when these elements are searched, a value of 0 is returned for the number of iterations. 
	 * This caused the LP and QP to be artificially low.
	 * As a result, LP and QP are roughly equal (averaging around 1.00) and SC is a bit slower (averaging around 1.25)
	 */
}

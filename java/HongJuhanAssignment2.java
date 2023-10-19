/*  
* Name: Juhan Hong      
* Class CS1450 001 (T/R)  
* Due: 2/2/2023 
* Description: Assignment 2 
* 
* This program will read from a given text file of different types of disasters and place them into an array accordingly. Each different types of disaster
* will have different names, the year it happened, and the strength. This program will then create another array only to filter out storms from the 
* original set of arrays. This program will display the both array of disaster and the array of storms. Lastly, this assignment will demonstrate my understanding 
* with using object oriented programming and different array manipulation. 
*
*/

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class Disaster {
	// instance variables
	private String disasterType;
	private int disasterYear;
	private double disasterStrength;
	private String disasterName;

	// constructors
	public Disaster(int disasterYear, double disasterStrength, String disasterName, String disasterType) {
		this.disasterType = disasterType;
		this.disasterYear = disasterYear;
		this.disasterStrength = disasterStrength;
		this.disasterName = disasterName;
	}

	// getter
	public String getType() {
		return this.disasterType;
	}

	public int getYear() {
		return this.disasterYear;
	}

	public double getStrength() {
		return this.disasterStrength;
	}

	public String getName() {
		return this.disasterName;
	}

	public String disasterInfo() {
		return getName() + "\t" + getType() + "\t" + getYear() + "\t" + getStrength() + "\t";
	}
	
	//generic ratingscale method to be overridden
	public String ratingScale() {
		return "generic rating";
	}

}

//tornado
class Tornado extends Disaster {
	public Tornado(int disasterYear, double disasterStrength, String disasterName) {
		super(disasterYear, disasterStrength, disasterName, "Tornado");
	}

	@Override
	public String ratingScale() {
		return "\tFujita Scale: F0 to F5";
	}
}

//earthquake
class Earthquake extends Disaster {
	public Earthquake(int disasterYear, double disasterStrength, String disasterName) {
		super(disasterYear, disasterStrength, disasterName, "Earthquake");
	}

	@Override
	public String ratingScale() {
		return "Richter Scale: 1.0 to 9.0 ";
	}
}

//hurricane
class Hurricane extends Disaster {
	public Hurricane(int disasterYear, double disasterStrength, String disasterName) {
		super(disasterYear, disasterStrength, disasterName, "Hurricane");
	}

	@Override
	public String ratingScale() {
		return "Saffir-Simpson Wind Scale: 1-5";
	}
}

//volcano
class Volcano extends Disaster {
	public Volcano(int disasterYear, double disasterStrength, String disasterName) {
		super(disasterYear, disasterStrength, disasterName, "Volcano");
	}

	@Override
	public String ratingScale() {
		return "Volcanic Explosivity Index: 0-8";
	}
}

//storm class
class Storm {
	private int tornadoCount;
	private int hurricaneCount;
	private Disaster[] stormArray;

	// findStorm
	public void findStorm(Disaster[] disasters, double disasterStrength) {
		// using instanceof to find either tornado or hurricane
		for (Disaster stormName : disasters) {
			if (stormName instanceof Tornado) {
				tornadoCount++;
			} else if (stormName instanceof Hurricane) {
				hurricaneCount++;
			}
		}
		// creating a new array just for storms
		int index = 0;
		Disaster[] stormArray = null;
		stormArray = new Disaster[tornadoCount + hurricaneCount];

		// adding storms into the array
		for (Disaster stormDisaster : disasters) {
			if (stormDisaster instanceof Tornado || stormDisaster instanceof Hurricane) {
				stormArray[index++] = stormDisaster;
			}
		}
	}
	
	
	//print storms
	public void printStorms(Disaster[] disasters, String disasterName, double disasterStrength) {
		System.out.println("Disasters that are storms");
		System.out.println("----------------------------------------");
		System.out.println("Number of tornados: \t" + tornadoCount);
		System.out.println("Number of hurricanes: \t" + hurricaneCount);
		System.out.println("");
		
		for (Disaster stormDisaster : disasters) {
			System.out.println(
					stormDisaster.getName() + "\t" + stormDisaster.getType() + "\t" + stormDisaster.getStrength());
		}
	}

}

//main
public class HongJuhanAssignment2 {
	public static void main(String[] args) throws IOException {

		// reading text file
		File inputFileName = new File("Disasters.txt");
		Scanner inputFile = new Scanner(inputFileName);

		// getting the number of disasters to determine the size of array
		Disaster[] disasterArray = null;
		int arraySize = inputFile.nextInt();
		disasterArray = new Disaster[arraySize];
		int indexCounter = 0;

		// after reading from the text file, input the data into right class
		while (inputFile.hasNextLine()) {

			// created variables for each input for easier read.
			String disasterType = inputFile.next();
			int disasterYear = inputFile.nextInt();
			double disasterStrength = inputFile.nextDouble();
			String disasterName = inputFile.nextLine();
			Disaster disaster = null;

			// placing input data into the right disaster
			if (disasterType.equals("tornado")) {
				disaster = new Tornado(disasterYear, disasterStrength, disasterName);

			} else if (disasterType.equals("earthquake")) {
				disaster = new Earthquake(disasterYear, disasterStrength, disasterName);

			} else if (disasterType.equals("hurricane")) {
				disaster = new Hurricane(disasterYear, disasterStrength, disasterName);
				;

			} else if (disasterType.equals("volcano")) {
				disaster = new Volcano(disasterYear, disasterStrength, disasterName);
				;

			}
			disasterArray[indexCounter++] = disaster;
		}
		// close input
		inputFile.close();

		// printing values in the disasterArray
		System.out.println("Disaster Name \t\t Type \t Year \t Strength \t Rating Scale");
		System.out.println(
				"------------------------------------------------------------------------------------------------");
		for (int i = 0; i < disasterArray.length; i++) {
			System.out.println(disasterArray[i].getName() + "\t\t" + disasterArray[i].getType() + "\t\t"
					+ disasterArray[i].getYear() + "\t" + disasterArray[i].getStrength() + "\t"
					+ disasterArray[i].ratingScale());
		}
		System.out.println();

		// run the storm portion
		Storm stormDisaster = new Storm();
		stormDisaster.findStorm(disasterArray, 5.0);
		stormDisaster.printStorms(disasterArray, null, 5.0);

	} // main

}// assignment2

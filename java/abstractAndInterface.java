/*  
* Name: Juhan Hong      
* Class CS1450 001 (T/R)  
* Due: 2/10/2023 
* Description: Assignment 3 
* 
* Given set of txt file w/ different types of athletes and what their disciplines and their speed during each events, create a class 
* using abtract classes and interfaces to sort the information correctly and utilize the data correctly using interface. This program will 
* demonstrate my understanding in using abstract classes and interfaces in addition to continue to demonstrate my understading in OOP.
*
*/


import java.io.File; 
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class abstractAndInterface {

	public static void main(String[] args) throws IOException {
		// reading files
		final String FILE_NAME = "Athletes.txt";

		File athleteFile = new File(FILE_NAME);
		Scanner inputFile = new Scanner(athleteFile);
		int numAthlete = inputFile.nextInt();
		// create an array with correct number of athletes inside
		Athlete[] athletes = new Athlete[numAthlete];

		// input and sort correct data to the right athlete
		for (int i = 0; i < numAthlete; i++) {
			// organizing the components for easier read
			String athleteType = inputFile.next();
			double bikeSpeed = inputFile.nextDouble();
			double runSpeed = inputFile.nextDouble();
			double swimSpeed = inputFile.nextDouble();
			String athleteName = inputFile.nextLine().trim();
			switch (athleteType) {
			case "triathlete":
				athletes[i] = new Triathlete(bikeSpeed, runSpeed, swimSpeed, athleteName);
				break;
			case "marathoner":
				athletes[i] = new Marathoner(runSpeed, athleteName);
				break;
			case "aquathlete":
				athletes[i] = new Aquathlete(runSpeed, swimSpeed, athleteName);
				break;
			case "duathlete":
				athletes[i] = new Duathlete(bikeSpeed, runSpeed, athleteName);
				break;
			}

		}

		// calling displayAthlete method
		displayAthlete(athletes);
		ArrayList<Athlete> runnerArray = findRunners(athletes);

		// display runners and their info
		System.out.println("--------------------------------------------------------------------");
		System.out.println("ATHLETES THAT RUN");
		System.out.println("--------------------------------------------------------------------");
		System.out.println("Name \t\t Type \t\t Running Speed");
		System.out.println("--------------------------------------------------------------------");
		for (int i = 0; i < athletes.length; i++) {
			System.out.println(
					athletes[i].getName() + "\t" + athletes[i].getSportsType() + "\t" + ((Runner) athletes[i]).run());

		}

		
		System.out.println("-------------------------------");
		System.out.println("BIKING RACE");
		System.out.println("-------------------------------");
		System.out.println("Bikers get ready... get set... go!");
		bikeRace(athletes);
		for (int i = 0; i < athletes.length; i++) {
			System.out.println("The winnter is " + athletes[i].getName() + "with the biking speed of " + ((Biker) athletes[i]).bike()) ;
			
		}
	}//main

	public static void displayAthlete(Athlete[] athletes) {

		System.out.println("Athletes and Disciplines");
		System.out.println(
				"---------------------------------------------------------------------------------------------------");
		System.out.println();
		for (int i = 0; i < athletes.length; i++) {
			System.out.println();
			System.out.println(athletes[i].getName() + " is a " + athletes[i].getSportsType());
			System.out.println(athletes[i].discipline());

			// if else statement to use interface to call the right components
			// when they interface is not called, else stmt is called for default of 0.00
			if (athletes[i] instanceof Biker) {
				System.out.printf("Bike speed: " + ((Biker) athletes[i]).bike() + "\t");
			} else {
				System.out.printf("Bike speed: 0.00 \t");
			}
			if (athletes[i] instanceof Runner) {
				System.out.printf("Run speed: " + ((Runner) athletes[i]).run() + "\t");
			} else {
				System.out.printf("Run speed: 0.00 \t");
			}
			if (athletes[i] instanceof Swimmer) {
				System.out.printf("Swim speed: " + ((Swimmer) athletes[i]).swim() + "\t");
			} else {
				System.out.printf("Swim speed: 0.00 \t");
			}
			System.out.println();
		}

	}// display athlete

	public static ArrayList<Athlete> findRunners(Athlete[] athletes) {
		ArrayList<Athlete> runnerArray = new ArrayList<Athlete>();

		// using instanceof and interface runner to find all the class that is linked by
		// runner
		for (Athlete runner : athletes) {
			if (runner instanceof Runner) {
				runnerArray.add(runner);
			}
		}
		return runnerArray;

	}

	public static Athlete[] bikeRace(Athlete[] athletes) {
		int bikerCount = 0;
		double fastestBike = 0;

		// create array just for biker
		for (int i = 0; i < athletes.length; i++) {
			if (athletes[i] instanceof Biker) {
				bikerCount++;
			}
		}
		Athlete[] bikerArray = new Athlete[bikerCount];
		Athlete[] fastestBiker = new Athlete[1];
		for (int i = 0; i < bikerArray.length; i++) {
			if (((Biker) bikerArray[i]).bike() > fastestBike) {
				bikerArray[i] = fastestBiker[i];
			}
		}

		return fastestBiker;

	}

}// assignment 3

//interfaces for swim, bike, and run
interface Swimmer {
	public abstract double swim();
}

interface Biker {
	public abstract double bike();
}

interface Runner {
	public abstract double run();
}

//athlete
abstract class Athlete {
	private String sportsType;
	private String athleteName;

	public void setSportsType(String sportsType) {
		this.sportsType = sportsType;
	}

	public String getSportsType() {
		return sportsType;
	}

	public void setName(String athleteName) {
		this.athleteName = athleteName;
	}

	public String getName() {
		return athleteName;

	}

	// made a generic one to be overrode by all athletes
	public abstract String discipline();
	

}

//triathlete class
class Triathlete extends Athlete implements Swimmer, Biker, Runner {

	private double runSpeed;
	private double bikeSpeed;
	private double swimSpeed;

	public Triathlete(double bikeSpeed, double runSpeed, double swimSpeed, String athleteName) {
		setName(athleteName);
		setSportsType("Triathlete");
		this.bikeSpeed = bikeSpeed;
		this.runSpeed = runSpeed;
		this.swimSpeed = swimSpeed;

	}

	@Override
	public String discipline() {
		return "During the Ironman triathlon, I swim 2.4 miles, bike 112 miles, then run 26.2 miles.";
	}

	@Override
	public double run() {
		return runSpeed;
	}

	@Override
	public double bike() {
		return bikeSpeed;
	}

	@Override
	public double swim() {
		return swimSpeed;
	}

}// triathlete

//marathoner class
class Marathoner extends Athlete implements Runner {

	private double runSpeed;
	// private String athleteName;

	public Marathoner(double runSpeed, String athleteName) {
		setName(athleteName);
		setSportsType("Marathoner");
		this.runSpeed = runSpeed;
	}

	@Override
	public String discipline() {
		return "During a full marathon I run 26.2 miles!";
	}

	@Override
	public double run() {
		return runSpeed;
	}

}// marathoner

//aquathlete class
class Aquathlete extends Athlete implements Swimmer, Runner {

	private double swimSpeed;
	private double runSpeed;

	public Aquathlete(double runSpeed, double swimSpeed, String athleteName) {
		setName(athleteName);
		setSportsType("Aquathlete");
		this.runSpeed = runSpeed;
		this.swimSpeed = swimSpeed;
	}

	@Override
	public String discipline() {
		return "I run, swim, then run again. In the swedish OTILLO Championship, the race takes place over 24 islands and require 6 miles of swimming between the"
				+ " islands and 40miles of trail running.";
	}

	@Override
	public double run() {
		return runSpeed;
	}

	@Override
	public double swim() {
		return swimSpeed;
	}

}// aquathlete

//duathlete
class Duathlete extends Athlete implements Runner, Biker {

	private double runSpeed;
	private double bikeSpeed;

	public Duathlete(double bikeSpeed, double runSpeed, String athleteName) {
		setName(athleteName);
		setSportsType("Duathlete");
		this.bikeSpeed = bikeSpeed;
		this.runSpeed = runSpeed;
	}

	@Override
	public String discipline() {
		return "I run, bike, then sometimes run again. In a long distance duathlon, I run 6.2 miles, bike 93 miles, then run 18.6 miles.";
	}

	@Override
	public double bike() {
		return bikeSpeed;
	}

	@Override
	public double run() {
		return runSpeed;
	}
}// duathlete

/*	Juhan Hong 
 *	CS1450 (T/R)
 * 	Due: 3/23/2023
 * 	Assignment #7
 * 	Description: By using the previously done assignment 4 as base, i am going to add more to it to imitate Panama Canel. This program 
 *  will make all the ships element go from Atlantic ocean to newly created pacific ocean through panama canel using queue and priority
 *  queue. The program will show the atlantic ocean with ships in first, then ships that went into the channel, transitways, and ships 
 *  moving into the pacific ocean. The program will lastly show the empty atlantic ocean and pacific ocean that is now filled with ships.
 *  This program should demonstrate my understanding with queue, priority queue, and using comparable.
 * 
 */

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class HongJuhanAssignment7 {

	public static void main(String[] args) throws IOException {

		// Name of files to read from
		final String OCEAN_TEXT = "Ocean7.txt";
		final String LAND_TEXT = "Land7.txt";
		final String SHIP_TEXT = "Ships7.txt";

		//setting up files to read from
		File oceanFileName = new File(OCEAN_TEXT);
		File landFileName = new File(LAND_TEXT);
		File shipsFileName = new File(SHIP_TEXT);

		
		Scanner oceanFile = new Scanner(oceanFileName);
		String oceanName = oceanFile.nextLine();
		int numberRows = oceanFile.nextInt();
		int numberColumns = oceanFile.nextInt();
		oceanFile.nextLine();
		String nextName = oceanFile.nextLine();
		int nextNumRow = oceanFile.nextInt();
		int nextNumCol = oceanFile.nextInt();

		// Create the ocean object
		Ocean7 atlanticOcean = new Ocean7(oceanName, numberRows, numberColumns);
		Ocean7 pacificOcean = new Ocean7(nextName, nextNumRow, nextNumCol);
		
		//create the panama canal
		Canal panamaCanal = new Canal();

		//from assignment 4
		System.out.println("Loading " + oceanName + " ocean grid with ships and land...");
		System.out.println();

		
		Scanner shipsFile = new Scanner(shipsFileName);
		while (shipsFile.hasNext()) {

			//reading ship information to be put on the ocean
			int rowNumber = shipsFile.nextInt();
			int columnNumber = shipsFile.nextInt();
			int shipCapacity = shipsFile.nextInt();
			String shipClass = shipsFile.next(); 
			String shipName = shipsFile.nextLine().trim();

			// Place ship object into its correct location in the ocean
			Ship7 ship = new Ship7(shipClass, shipCapacity, shipName);
			atlanticOcean.addElement(rowNumber, columnNumber, ship);

		} 

		// Open the land file for reading by creating a scanner for the file
		// Create the land objects from the details provided in the file.
		Scanner landFile = new Scanner(landFileName);
		while (landFile.hasNext()) {

			// read from land file to be put into the atlantic ocean
			int rowNumber = landFile.nextInt();
			int columnNumber = landFile.nextInt();

			// Create a land object
			Land7 land = new Land7();
			atlanticOcean.addElement(rowNumber, columnNumber, land);

		} 

		// Close all the files
		oceanFile.close();
		landFile.close();
		shipsFile.close();

		// Display the ocean's grid
		atlanticOcean.displayOcean();
		System.out.println();
		ControlCenter controlCenter = new ControlCenter();
	    // move ships from Atlantic Ocean to channel
	    System.out.println("Controller: Moving ships from ocean to channel that forms approach to the transit ways:");
	    controlCenter.moveShipsIntoChannel(atlanticOcean, panamaCanal);
	    System.out.println();

	    // move ships waiting in channel to transit ways
	    System.out.println("Controller: Moving ships waiting in the channel into transit ways:");
	    controlCenter.moveShipsIntoTransitWays(panamaCanal);
	    System.out.println();

	    // move ships from transit ways into Pacific Ocean
	    System.out.println("Controller: Moving ships from transit ways into Pacific Ocean:");
	    controlCenter.moveShipsIntoPacificOcean(pacificOcean, 8, 1, panamaCanal);
	    System.out.println();

	    // display Atlantic Ocean with no ships
	    System.out.println("Show Atlantic Ocean with no ships....");
	    atlanticOcean.displayOcean();
	    System.out.println();

	    // display Pacific Ocean with ships
	    System.out.println("Show Pacific Ocean with ships....");
	    pacificOcean.displayOcean();
	    System.out.println();

	} // main


} // Assignment 7

//ocean class
class Ocean7 {

	private String name;
	private int numberRows; 
	private int numberColumns; 
	private OceanElement7[][] grid; 

	public Ocean7(String name, int numberRows, int numberColumns) {
		this.name = name;
		this.numberRows = numberRows;
		this.numberColumns = numberColumns;

		grid = new OceanElement7[numberRows][numberColumns];
	}

	public String getName() {
		return name;
	}

	public int getNumberRows() {
		return numberRows;
	}

	public int getNumberColumns() {
		return numberColumns;
	}

	//adding ocean elements
	public void addElement(int row, int column, OceanElement7 oceanElement) {
		grid[row][column] = oceanElement;
	}

	//Returns the ocean element
	//when the location is empty returns null.
	public OceanElement7 getElement(int row, int column) {
		return grid[row][column];
	}

	public void removeElement(int row, int cols) {
		grid[row][cols] = null;
	}


	public void displayOcean() {
		for (int column = 0; column < numberColumns; column++) {
			System.out.printf("\t%s %d", "Column", column);
		}
		System.out.printf("\n\n");

		for (int row = 0; row < numberRows; row++) {

			System.out.printf("%s%d", "Row ", row);
			for (int column = 0; column < numberColumns; column++) {

				// Get element in location [row,column]
				OceanElement7 element = grid[row][column];

				if (element != null) {
					if (element instanceof Ship7) {
						System.out.printf("\t%-8s", ((Ship7) element).getName());
					} else {
						System.out.printf("\t%-8s", element.getType());
					}
				} else {
					System.out.printf("\t%-8s", "-------");
				}
			}

			System.out.println();
			System.out.println();

		} // for each row

	} // displayOcean

} // Ocean

// ocean element class
class OceanElement7 {
	private String elementType; 

	public OceanElement7(String type) {
		this.elementType = type;
	}

	public String getType() {
		return elementType;
	}

} // OceanElement

//ship class
//comparable to use compareTo Method
class Ship7 extends OceanElement7 implements Comparable<Ship7> {

	private String name;
	private String classification; 
	private int capacity; 

	// Create a ship
	public Ship7(String classification, int capacity, String name) {

		super("Ship");
		this.classification = classification;
		this.capacity = capacity;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getClassification() {
		return classification;
	}
	public int getCapacity() {
		return capacity;
	}

	@Override
	public String toString() {
		return String.format("%-10s\t\t%-10s\t\t%d", classification, name, capacity);

	}

	@Override // This is where we write the code to compare two Ships.
	// In this assignment, compare two ships based on their capacity.
	// By writing this method, it is now possible to sort an array of ships
	// by their capacity when calling the Collections.sort method
	public int compareTo(Ship7 otherShip) {

		switch (this.classification) {
		case "Navy":
			if (otherShip.getClassification().equals("Navy")) {
				return 0;
			} else {
				return -1;
			}
		case "Tanker":
			if (otherShip.getClassification().equals("Navy")) {
				return 1;
			} else if (otherShip.getClassification().equals("Tanker")) {
				return 0;
			} else {
				return -1;
			}
		case "Cargo":
			if (otherShip.getClassification().equals("Cruise")) {
				return -1;
			} else if (otherShip.getClassification().equals("Cargo")) {
				return 0;
			} else {
				return 1;
			}
		case "Cruise":
			if (otherShip.getClassification().equals("Cruise")) {
				return 0;
			} else {
				return 1;
			}
		default:
			return 0;
		}
	}

} // Ship

//land class
class Land7 extends OceanElement7 {
	// Create land
	public Land7() {
		super("Land");
	}

} // Land

//canal class
class Canal {

	private PriorityQueue<Ship7> channel; // priority queue of ships waiting to enter one of the transit ways
	private Queue<Ship7> transitWay1; // queue of ships for 1st sequence of locks and lakes in canal
	private Queue<Ship7> transitWay2; // queue of ships for 2nd sequence of locks and lakes in canal

	// Constructor to allocate memory for each of the 3 queues
	public Canal() {
		channel = new PriorityQueue<>();
		transitWay1 = new LinkedList<>();
		transitWay2 = new LinkedList<>();
	}
	public PriorityQueue<Ship7> getChannel() {
        return channel;
    }

    public Queue<Ship7> getTransitWay1() {
        return transitWay1;
    }

    public Queue<Ship7> getTransitWay2() {
        return transitWay2;
    }

	// Returns true when channel queue is empty
	public boolean isChannelEmpty() {
		return channel.isEmpty();
	}

	// Adds (offers) ship to channel queue
	public void addShipToChannel(Ship7 ship) {
		channel.offer(ship);
	}

	// Removes ship from channel queue & returns ship
	public Ship7 removeShipFromChannel() {
		return channel.poll();
	}

	// Returns true when transitWay1 queue is empty
	public boolean isTransitWay1Empty() {
		return transitWay1.isEmpty();
	}

	// Adds (offers) ship to transitWay1 queue
	public void addShipToTransitWay1(Ship7 ship) {
		transitWay1.offer(ship);
	}

	// Removes ship from transitWay1 queue & returns ship
	public Ship7 removeShipFromTransitWay1() {
		return transitWay1.poll();
	}

	// Returns true when transitWay2 queue is empty
	public boolean isTransitWay2Empty() {
		return transitWay2.isEmpty();
	}

	// Adds (offers) ship to transitWay2 queue
	public void addShipToTransitWay2(Ship7 ship) {
		transitWay2.offer(ship);
	}

	// Removes ship from transitWay2 queue & returns ship
	public Ship7 removeShipFromTransitWay2() {
		return transitWay2.poll();
	}
}

//ship class comparator
//with this class we are able to compare the classification of each ship. 
//still using the return values of -1. 0, and 1
//
abstract class ShipClassificationComparator1 implements Comparator<Ship7> {

	public int compareTo(Ship7 ship1, Ship7 ship2) {
		if (ship1.getClassification().equals("Navy")) {
			return -1;
		} else if (ship1.getClassification().equals("Tanker") && !ship2.getClassification().equals("Navy")) {
			return -1;
		} else if (ship1.getClassification().equals("Cargo") && !ship2.getClassification().equals("Navy")
				&& !ship2.getClassification().equals("Tanker")) {
			return -1;
		} else if (ship1.getClassification().equals("Cruise") && ship2.getClassification().equals("Cruise")) {
			return 0;
		} else {
			return 1;
		}
	}
}


//control center
class ControlCenter{
	
	//moving ship into the cananl
	public void moveShipsIntoChannel(Ocean7 ocean, Canal canal) {
	    for (int i = 0; i < ocean.getNumberRows(); i++) {
	        for (int j = 0; j < ocean.getNumberColumns(); j++) {
	            OceanElement7 element = ocean.getElement(i, j);
	            //using instance of ship to get the ship to move into the canal
	            if (element instanceof Ship7) {
	                Ship7 ship = (Ship7) element;
	                canal.addShipToChannel(ship);
	                ocean.removeElement(i, j);
	                System.out.println("Moved to channel: " + ship.getClassification() + " /t" + ship.getName() + " /t" + ship.getCapacity());
	            }
	        }
	    }
	}
	//moving shipo into the transit ways
	public void moveShipsIntoTransitWays(Canal canal) {
	    // Rule 2: If transitWay1 has less than 5 ships, move ships from channel to transitWay1
	    while (!canal.isChannelEmpty() && canal.isTransitWay1Empty() ) {
	        Ship7 ship = canal.removeShipFromChannel();
	        System.out.printf("Moved into transit way #1: %s\t%s\t%d\n", ship.getClassification(), ship.getName(), ship.getCapacity());
	        canal.addShipToTransitWay1(ship);
	    }

	    // Rule 3: Move ships from channel to transitWay2
	    while (!canal.isChannelEmpty()) {
	        Ship7 ship = canal.removeShipFromChannel();
	        System.out.printf("Moved into transit way #2: %s\t%s\t%d\n", ship.getClassification(), ship.getName(), ship.getCapacity());
	        canal.addShipToTransitWay2(ship);
	    }
	}
	

	public void moveShipsIntoPacificOcean(Ocean7 ocean, int row, int column, Canal canal) {
	    // Rule 4: Take ships out of the transit ways in round-robin order
	    int numShipsInTransitWay1 = 0;
	    if (!canal.isTransitWay1Empty()) {
	        numShipsInTransitWay1 = canal.getTransitWay1().size();
	    }
	    
	    int numShipsInTransitWay2 = 0;
	    if (!canal.isTransitWay2Empty()) {
	        numShipsInTransitWay2 = canal.getTransitWay2().size();
	    }
	    
	    Queue<Ship7> roundRobinQueue = new LinkedList<>();
	    
	    while (numShipsInTransitWay1 > 0 || numShipsInTransitWay2 > 0) {
	        if (numShipsInTransitWay1 > 0) {
	            roundRobinQueue.add(canal.removeShipFromTransitWay1());
	            numShipsInTransitWay1--;
	        }
	        
	        if (numShipsInTransitWay2 > 0) {
	            roundRobinQueue.add(canal.removeShipFromTransitWay2());
	            numShipsInTransitWay2--;
	        }
	    }
	    
	    // Move the remaining ships in the non-empty transit way into the Pacific ocean
	    if (!canal.isTransitWay1Empty()) {
	        while (!canal.isTransitWay1Empty()) {
	            roundRobinQueue.add(canal.removeShipFromTransitWay1());
	        }
	    } else if (!canal.isTransitWay2Empty()) {
	        while (!canal.isTransitWay2Empty()) {
	            roundRobinQueue.add(canal.removeShipFromTransitWay2());
	        }
	    }
	    
	    // Rule 5: Place ships into the Pacific ocean with ship placement starting at the given row and column
	    while (!roundRobinQueue.isEmpty()) {
	        Ship7 ship = roundRobinQueue.poll();
	        ocean.addElement(row, column, ship);
	        System.out.printf("Moved into Pacific ocean: %-8s%-20s%d\n", ship.getClassification(), ship.getName(), ship.getCapacity());
	        row--;
	    }
	}


}

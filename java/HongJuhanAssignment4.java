/*	Juhan Hong 
 *	CS1450 (T/R)
 * 	Due: 2/23/2023
 * 	Assignment #4
 * 	Description: Create a program that will use 2d array to mimic an ocean with land and ships. The element for each are given through
 *	text file. The ocean will be displayed in rows and columns and with the rows and columns, each elements will find their correct spots 
 *	within the ocean. The program will also use collection to find the ship's capacity from the highest to lowest and display it at the end
 *	of the program.  
 * 
 * */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;



interface Compareable<Ship>{
	int compareTo(Ship otherShip);
}

class Ocean{
	
	private int numRows;
	private int numCols;
	private OceanGrid[][] grid;
	private List<Ship> ships;
	
	public Ocean(String oceanText, String landText, String shipText ) throws IOException {
		
		//created a string to read the files 
		final String oceanFile = "Ocean.txt";
		final String landFile = "Land.txt";
		final String shipFile = "Ships.txt";
		
		//created new files according the the text file name
		File oceanTextFile = new File(oceanFile);
		File landTextFile = new File(landFile);
		File shipTextFile = new File(shipFile);
		
		//start reading ocean and start making the ocean grid according to the number given
		Scanner oceanText1 = new Scanner(oceanTextFile);
		String oceanName = oceanText1.next();
		this.numRows = oceanText1.nextInt();
		this.numCols = oceanText1.nextInt();
		
		oceanText1.close();
		
		//add elements from ocean to the grid using this.
		this.grid = new OceanGrid[numRows][numCols];
		
		//same method as ocean but do it with land
		Scanner landText1 = new Scanner(landTextFile);
		while(landText1.hasNext()) {
			int landRow = landText1.nextInt();
			int landCol = landText1.nextInt();
			
			this.grid[landRow][landCol] = new Land(landRow, landCol);
			
		}
		landText1.close();
		
		//same method as ocean but do it with ships
		//since ships have more elements, we will use polymorphism90oooo
		
		Scanner shipText1 = new Scanner(shipTextFile);
		this.ships = new ArrayList<>();
		while(shipText1.hasNext()) {
	
			int shipRow = shipText1.nextInt();
			int shipCol = shipText1.nextInt();
			int shipCap = shipText1.nextInt();
			String shipCat = shipText1.next();
			String shipName = shipText1.nextLine().trim();
			
			this.grid[shipRow][shipCol] = new Ship(shipRow, shipCol, shipName,shipCat, shipCap);
			ships.add(new Ship(shipRow, shipCol, shipName,shipCat, shipCap));
	
		}
		shipText1.close();
		
	}
	
	//print grid
	public void printGrid() {
		for(int i = 0; i < this.numRows;i++) {
			for (int j = 0; j < this.numCols; j++) {
				OceanGrid oceanGrid = this.grid[i][j];
				if(oceanGrid == null) {
					System.out.print("----\t\t");
				}
				else {
					System.out.print(oceanGrid.someSymbol() + "\t");
				}
			}
			System.out.println();
		}
	}
	
	
	//using collection here to print the report
	public void printShipReport() {
		Collections.sort(ships);
		System.out.println();
		System.out.println("******************************************************************************************");
		System.out.println("\t\t\tOCEAN SHIP REPORT");
		System.out.println("\t\tFROM LARGEST CAPACITY TO SMALLEST CAPACITY");
		System.out.println("******************************************************************************************");
		System.out.println("Classification \t\t Name \t\t Capacity");
		for (Ship ship: ships) {
			System.out.println(ship.getType()+ "\t\t\t" + ship.getName() + "\t\t" + ship.getCapacity());
		}
	}
	
	
	
}

//Ocean grid. I decided to use this name instead of ocean element
abstract class OceanGrid {
	int row;
	int col;
	
	public OceanGrid(int row, int col) {
		this.row = row;
		this.col = col;
		
	}
	public int compareTo(Ship otherShip) {
		
		return 0;
	}
	public void setRow(int row) {
		this.row = row;
	}
	
	public int getRow() {
		return row;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public int getCol() {
		return col;
	}
	public abstract String someSymbol();
}

//land class
class Land extends OceanGrid{

	public Land(int row, int col) {
		super(row, col);
	
	}
	
	@Override
	public String someSymbol() {
		return "land\t";
	}
	
}

//ship class
//we are using the interface we created earlier for the comparable in order to find the biggest to smallest
class Ship extends OceanGrid implements Comparable<Ship>{
	private String shipName;
	private String shipType;
	private int shipCap;

	public Ship(int row, int col, String shipName, String shipType, int shipCap) {
		super(row, col);
		this.shipName = shipName;
		this.shipType = shipType;
		this.shipCap = shipCap;

	}
	public void setCapacity(int shipCap) {
		this.shipCap = shipCap;
	}
	public int getCapacity() {
		return shipCap;
	}
	public void setType(String shipType) {
		this.shipType = shipType;
	}
	public String getType() {
		return shipType;
	}

	
	public void setName(String shipName) {
		this.shipName = shipName;
	}
	public String getName() {
		return shipName;
	}

	
	@Override
	public String someSymbol() {
		return shipName;
	}
	
	//comepare to method to find the biggest to smallest
	@Override
	public int compareTo(Ship otherShip) {
		if(this.getCapacity() > otherShip.getCapacity()) {
			return -1;
		}
		else if(this.getCapacity() < otherShip.getCapacity()) {
			return 1;
		}
		else {
			return 0;
		}
	}
	
}
public class HongJuhanAssignment4 {

	public static void main(String[] args) throws IOException {
		Ocean oceans = new Ocean("Ocean.txt", "Land.txt", "Ship.txt");
		oceans.printGrid();
		oceans.printShipReport();

	}

}

/*	Juhan Hong
 *	CS1450 (T/R)
 * 	Due: 
 * 	Assignment #1
 * 	Description: This program is to randomly generate 20 integers between 10-40 and have it put into an array. The program will also display the 
 * 	sum, average, and median of all the values generated. Then, this program will also turn the 1d array into a 2d array with 4 rows and 5 
 * 	columns. This program will also demonstrate my skills with exporting my final product to a file. 
 * 
 * */

import java.util.Arrays;
import java.io.File; 
import java.io.IOException;
import java.io.PrintWriter;

public class HongJuhanAssignment1 {

	public static void main(String[] args) throws IOException {

		// initializing rows and columns for 2d array
		final int NUM_ROWS = 4;
		final int NUM_COLS = 5;

		int arraySum = 0;
		int[] randomArray = new int[20];
		// inputting random numbers into array
		for (int i = 0; i < randomArray.length; i++) {
			// filling array with random number
			randomArray[i] = 10 + (int) (Math.random() * 31);
			System.out.println(randomArray[i]);
		}
		// for loop for finding the sum of all elements
		for (int i = 0; i < randomArray.length; i++) {
			arraySum = arraySum + randomArray[i];

		}
		System.out.println();
		System.out.println("Sum\t = " + arraySum);
		// finding average
		int arrayAverage = arraySum / 20;
		System.out.println("Average\t = " + arrayAverage);

		// finding the median after sorting the array
		Arrays.sort(randomArray);
		if (randomArray.length % 2 == 0) {
			System.out.println("Median\t = " + randomArray[randomArray.length / 2]);
		}
		
		// finding the largest number and how many times
		int largeCount = 0;
		int largeNumber = 0;
		for (int i = 0; i < randomArray.length; i++) {
			if (randomArray[i] > largeNumber) {
				largeNumber = randomArray[i];
			}
		}
		for (int i = 0; i < randomArray.length; i++) {
			if (randomArray[i] == largeNumber) {
				largeCount++;
			}
		}
		System.out.println("Largest number is " + largeNumber + " which occured " + largeCount + " times");
		System.out.println();
		
		// putting array into 2d
		int newArray[][] = new int[NUM_ROWS][NUM_COLS];
		// initial for loop for 1d array
		for (int i = 0; i < randomArray.length; i++) {
			//for loops for rows
			for (int rows = 0; rows < NUM_ROWS; rows++) {
				//for loops for columns
				for (int cols = 0; cols < NUM_COLS; cols++) {
					//inputting 1d array elements into 2d 
					newArray[rows][cols] = randomArray[(rows * 5) + cols];
				}//cols
			}//rows
		}//initial
		
		//printing 2d array
		System.out.println("Values placed in 2d");
		int numRows = newArray.length;
		int numCols = newArray[0].length;

		for (int row = 0; row < numRows; row++) {
			for (int cols = 0; cols < numCols; cols++) {
				System.out.print(newArray[row][cols] + " ");
			}
			System.out.println();
		}
		
		System.out.println();
		System.out.println("Random values sotred in file in sorted order");
		for (int i = 0; i < randomArray.length; i++) {
			System.out.println(randomArray[i]);
		}
		System.out.println();
		//exporting data to a text file
		File assignment1File = new File("HongJuhanAssignment11.txt");
		PrintWriter resultsFile = new PrintWriter(assignment1File);
		for (int i = 0; i < randomArray.length; i++) {
			resultsFile.println(randomArray[i]);
		}
		resultsFile.close();
		// Shows where the file is located on your hard drive
		System.out.println("File is in directory: " + assignment1File.getAbsolutePath());
		System.out.println();

	}

}

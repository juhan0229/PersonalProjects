/*
 * Name: Juhan Hong      
 * Class CS1450 001 (T/R)  
 * Due: 3/3/2023 
 * Description: Assignment 5
 * 
 * Create a program to work with stacks. First use a non generic stack and a couple of non generic methods to remove all odd numbers
 * and display the stack. Then create a genericStack class that can perform various tasks including handle incoming values from text files, 
 * it could be different data type(int or string), input the data into a generic stack, sort the stack, and ask the user to input
 * something from the stack to move to the bottom of the stack, execute that command, and display the stack. 
 * 
 * 
 * 
 */


import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Scanner;
import java.io.File;

public class HongJuhanAssignment5 {

	public static void main(String[] args) throws IOException {
		int[] numbers = { 7, 2, 4, 3, 5, 1, 6, 8, 9 };

		// inputting values into the stack
		Stack<Integer> numbStack = new Stack<>();
		for (int i = 0; i < numbers.length; i++) {
			numbStack.push(numbers[i]);
		}

		// call remove odd value method
		removeOddValues(numbStack);
		printStack(numbStack);

		//generic stack for int and string for the text files to go into
		GenericStack<Integer> intStack = new GenericStack<>();
		GenericStack<String> stringStack = new GenericStack<>();
		
		//reading the text file
		File numbFile = new File("Numbers.txt");
		File stringFile = new File("Strings.txt");

		Scanner numNum = new Scanner(numbFile);
		Scanner strString = new Scanner(stringFile);
		Scanner userInput = new Scanner(System.in);
		
		//input the data from number.txt to the integer stack
		while (numNum.hasNext()) {
			int num = numNum.nextInt();
			intStack.push(num);
		}
		
		//input the data from string.txt to the string stack
		while (strString.hasNext()) {
			String stringText = strString.nextLine();
			stringStack.push(stringText);
		}
		
		// printing the integer portion
		System.out.println("Values read from file and pushed onto number stack");
		intStack.printStack();
		System.out.println("Number Stack sorted - smallest to highest:");
		intStack.sortStack();
		intStack.printStack();
		System.out.println("Enter value to move to bottom of integer stack:");
		int userInt = userInput.nextInt();
		intStack.moveToBottom(userInt);
		intStack.printStack();

		// printing the string portion
		System.out.println("Values read from file and pushed onto string stack");
		stringStack.printStack();
		System.out.println("String Stack sorted - alphabetical order: ");
		stringStack.sortStack();
		stringStack.printStack();
		System.out.println("Enter string to move to bottom of string stack: ");
		String userString = userInput.next();
		stringStack.moveToBottom(userString);
		stringStack.printStack();
		
		numNum.close();
		strString.close();
		userInput.close();
	}

	//non generic removeOddValue
	public static void removeOddValues(Stack<Integer> stack) {
		//create a temporary stack
		Stack<Integer> tempStack = new Stack<>();
		while (!stack.isEmpty()) {
			int num = stack.pop();
			
			//if remainder is 0, it is even so it goes in the temp stack
			if (num % 2 == 0) {
				tempStack.push(num);
			}
		}

		while (!tempStack.isEmpty()) {
			stack.push(tempStack.pop());
		}
	}

	//non generic printStack
	public static void printStack(Stack<Integer> stack) {
		System.out.println("Stack After Odd Values Are Removed");
		System.out.println("----------------------------------------");
		for (Integer numStack : stack) {
			System.out.println(numStack);
		}
	}

}

//GenericStack class
class GenericStack<E extends Comparable<E>> {
	private ArrayList<E> list;

	public GenericStack() {
		list = new ArrayList<E>();
	}

	//push = add
	public void push(E value) {
		list.add(value);
	}

	//pop = remove
	public E pop() {
		return list.remove(list.size() - 1);
	}

	//peek = get
	public E peek() {
		return list.get(list.size() - 1);
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	
	public int getSize() {
		return list.size();
	}

	//generic sort stack
	public void sortStack() {
		//making a temp stack
		GenericStack<E> tempStack = new GenericStack<>();
		while (!isEmpty()) {
			E smallest = pop();
			//finding the smaller value and putting in into the stack
			while (!tempStack.isEmpty() && tempStack.peek().compareTo(smallest) > 0) {
				push(tempStack.pop());
			}
			tempStack.push(smallest);
		}
		while (!tempStack.isEmpty()) {
			push(tempStack.pop());
		}
	}

	//remove smallest
	public static <E extends Comparable<E>> E removeSmallest(GenericStack<E> stack) {
		if (stack.isEmpty()) {
			return null;
		}
		E smallest = stack.pop();
		while (!stack.isEmpty()) {
			E element = stack.pop();
			if (element.compareTo(smallest) < 0) {
				smallest = element;
			}
		}
		return smallest;
	}

	//generic move to bottom
	public void moveToBottom(E value) {
		//see the input value. If input value does exist in the stack, we can move the value to the bottom
		if (list.contains(value)) {
			while (!list.get(0).equals(value)) {
				list.add(list.remove(0));
			}
		} 
		else {
			//if it does not exist in the stack, continue to ask the user to input the value that exist in the stack
			Scanner scanner = new Scanner(System.in);
			System.out.print("Value not found. Please enter a value that exists in the stack: ");
			E newValue = (E) scanner.next();
			moveToBottom(newValue);
			
		}
		
	}

	//generic print stack
	public void printStack() {
		System.out.println("-------------------------------------------------");
		for (int i = list.size() - 1; i >= 0; i--) {
			System.out.println(list.get(i));
		}
	}
}

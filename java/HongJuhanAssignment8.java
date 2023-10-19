/*	Juhan Hong
 *	CS1450 (T/R)
 * 	Due: 4/6/2023
 * 	Assignment #8
 * 	Description: Given a few txt files with encrypted messages, this program reads 6 files containing a message that is encrypted using a key. 
 * 	The program uses the key to decrypt the message and display the decrypted message. The program uses 2 classes: Translator and DecodingStack. 
 * 	The Translator class contains a method to translate the message. The DecodingStack class contains a Stack<Character> to store the translated message.
 *  
 *
 * */


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class HongJuhanAssignment8 {

    public static void main(String[] args) throws IOException {
        // Read files and create ArrayLists for the 1st message
        ArrayList<Character> arrayPart1 = readFileCharacters("ArrayPart1.txt");
        ArrayList<Integer> arrayPart2 = readFileIntegers("ArrayPart2.txt");
        ArrayList<Integer> arrayKey = readFileIntegers("ArrayKey.txt");

        // Create iterators for ArrayLists
        Iterator<Character> arrayPart1Iterator = arrayPart1.iterator();
        Iterator<Integer> arrayPart2Iterator = arrayPart2.iterator();
        Iterator<Integer> arrayKeyIterator = arrayKey.iterator();

        // Create a Translator instance
        Translator translator = new Translator();

        // Call the translate method and display the 1st translated message
        String decryptedMessage1 = translator.translate(arrayPart1Iterator, arrayPart2Iterator, arrayKeyIterator);
        System.out.println("The secret string is: : " + decryptedMessage1);
        System.out.println();

        // Read files and create Queues for the 2nd message
        Queue<Character> queuePart1 = readFileCharactersAsQueue("QueuePart1.txt");
        Queue<Integer> queuePart2 = readFileIntegersAsQueue("QueuePart2.txt");
        Queue<Integer> queueKey = readFileIntegersAsQueue("QueueKey.txt");

        // Call the translate method and display the 2nd translated message
        String decryptedMessage2 = translator.translate(queuePart1.iterator(), queuePart2.iterator(), queueKey.iterator());
        System.out.println("What is the second secret message??? " + decryptedMessage2);
        System.out.println();
    }

    // Method to read characters from a file and return an ArrayList
    public static ArrayList<Character> readFileCharacters(String fileName) throws IOException {
        ArrayList<Character> list = new ArrayList<>();
        Scanner scanner = new Scanner(new File(fileName));

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            for (int i = 0; i < line.length(); i++) {
                char ch = line.charAt(i);
                list.add(ch);
            }
        }
        scanner.close();

        return list;
    }
    // Method to read integers from a file and return an ArrayList
    public static ArrayList<Integer> readFileIntegers(String fileName) throws IOException {
        ArrayList<Integer> list = new ArrayList<>();
        Scanner scanner = new Scanner(new File(fileName));

        while (scanner.hasNext()) {
            list.add(scanner.nextInt());
        }
        scanner.close();

        return list;
    }

    // Method to read characters from a file and return a Queue
    public static Queue<Character> readFileCharactersAsQueue(String fileName) throws IOException {
        LinkedList<Character> queue = new LinkedList<>();
        Scanner scanner = new Scanner(new File(fileName));

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            for (int i = 0; i < line.length(); i++) {
                char ch = line.charAt(i);
                queue.add(ch);
            }
        }
        scanner.close();

        return queue;
    }

    // Method to read integers from a file and return a Queue
    public static Queue<Integer> readFileIntegersAsQueue(String fileName) throws IOException {
        LinkedList<Integer> queue = new LinkedList<>();
        Scanner scanner = new Scanner(new File(fileName));

        while (scanner.hasNext()) {
            queue.add(scanner.nextInt());
        }
        scanner.close();

        return queue;
    }
}

//translator class
class Translator {

    private DecodingStack stack;

    public Translator() {
        stack = new DecodingStack();
    }

    // Method to translate the message
    // The method takes 3 iterators as parameters and returns a String containing the translated message
    // The method uses a DecodingStack to store the translated message
    public String translate(Iterator<Character> part1Iterator, Iterator<Integer> part2Iterator, Iterator<Integer> keyIterator) {
        while (keyIterator.hasNext()) {
            int key = keyIterator.next();

            if (key == 0) {
                stack.push(part1Iterator.next());
            } else {
                int intValue = part2Iterator.next();
                stack.push((char) intValue);
            }
        }

        // Create a StringBuilder to store the translated message
        // Pop the characters from the stack and append them to the StringBuilder
        StringBuilder decryptedMessage = new StringBuilder();
        while (!stack.isEmpty()) {
            decryptedMessage.append(stack.pop());
        }

        return decryptedMessage.toString();
    }
}

// DecodingStack class
// The class uses a Stack<Character> to store the translated message
// The class provides methods to push, pop, and check if the stack is empty or not empty and to get the size of the stack
class DecodingStack {

    private Stack<Character> stack;

    public DecodingStack() {
        stack = new Stack<>();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int size() {
        return stack.size();
    }

    public void push(Character value) {
        stack.push(value);
    }

    public Character pop() {
        return stack.pop();
    }
}

/*	Juhan Hong
 * 	4/21/2023
 * 
 * */

import java.util.PriorityQueue;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class PQpractice {

	public static void main(String[] args) throws IOException {
		final String AIRBNB_TEXT = "Airbnbs.txt";
		final String AIRBNB_REQUIREMENT = "AirbnbRequirements.txt";
		
		File airbnbText = new File(AIRBNB_TEXT);
		File airbnbRequireText = new File(AIRBNB_REQUIREMENT);
		
		Scanner readText = new Scanner (airbnbText);
		
		AirbnbLinkedList airbnbLinked = new AirbnbLinkedList();
		// Create doubleLinkedlist 
        DoubleLinkedList airbnbDoubleLinked = new DoubleLinkedList();
		
		while(readText.hasNext()) {
			int nightRate = readText.nextInt();
			int numGuests = readText.nextInt();
			int numRooms = readText.nextInt();
			String houseType = readText.nextLine().trim();
			
			//create airbnbs objects and add them to linked list
			Airbnb airBnb = new Airbnb(nightRate, numGuests, numRooms, houseType);
			airbnbLinked.addByGuests(airBnb);
			airbnbDoubleLinked.addToEnd(airBnb);
        }

        // Close the Scanner
        readText.close();

        // Print the Airbnb properties in the singly linked list ordered by the number of guests
        System.out.println("Airbnbs in Single Linked List - Ordered by Number of Guests:");
        airbnbLinked.printHeader();
        airbnbLinked.print();
        


        // Print the Airbnb properties in the doubly linked list in reverse order
        System.out.println("\nAirbnbs in Doubly Linked List - Airbnbs Printed Backwards:");
        airbnbLinked.printHeader();
        airbnbDoubleLinked.printBackwards();
        
        
        // Read the airbnbRequirement.txt file
        Scanner readRequirement = new Scanner(airbnbRequireText);

        int requiredNightlyRate = readRequirement.nextInt();
        int requiredNumGuests = readRequirement.nextInt();
        String requiredType = readRequirement.nextLine().trim();

        // Close the Scanner
        readRequirement.close();

        // Find Airbnbs meeting the specific requirements
        PriorityQueue<Airbnb> matchingAirbnbs = airbnbLinked.findAirbnb(requiredNightlyRate, requiredNumGuests, requiredType);

        // Print the Airbnbs meeting the requirements
        System.out.println("\nAirbnbs in Priority Queue Meeting Requirements: ");
        System.out.println("$" + requiredNightlyRate + " a night or less, " + requiredNumGuests + "+ guests, and " + requiredType);
        airbnbLinked.printHeader();
        while (!matchingAirbnbs.isEmpty()) {
            System.out.println(matchingAirbnbs.poll());
        }
        int removedApartments = airbnbLinked.removeSpecificType("Apartment");
        int removedCondos = airbnbLinked.removeSpecificType("Condo");
       

        // Print the Airbnbs in the single linked list with apartments and condos removed
        System.out.println("\nAirbnbs in Single Linked List – Apartments and Condos Removed:");
        System.out.println("\nNumber of apartments removed: " + removedApartments);
        System.out.println("Number of condos removed: " + removedCondos);
        airbnbLinked.printHeader();
        airbnbLinked.print();
        
    }
}


class Airbnb implements Comparable<Airbnb>{
	private int nightlyRate;
	private int numGuest;
	private int numRooms;
	private String type;
	
	public Airbnb(int price, int guest, int rooms, String type) {
		this.nightlyRate = price;
		this.numGuest = guest;
		this.numRooms = rooms;
		this.type = type;
	}
	
	public int getPrice() {
		return nightlyRate;
	}
	
	public int getGuests() {
		return numGuest;
	}
	
	public int getRooms() {
		return numRooms;
	}
	
	public String getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return String.format("%-20s $%5d %6d %6d", type, nightlyRate, numGuest, numRooms);
	}
	
	public int compareTo(Airbnb otherAirBnb) {
		if(this.numGuest > otherAirBnb.numGuest) {
			return 1;
		}
		else if(this.numGuest < otherAirBnb.numGuest) {
			return -1;
		}
		else {
			return 0;
		}
	}
}

class AirbnbLinkedList {
    private Node head;
 
    public AirbnbLinkedList() {
        head = null;
    }

    private class Node {
        private Airbnb airbnb;
        private Node next;

        public Node(Airbnb airbnb) {
            this.airbnb = airbnb;
            this.next = null;
        }
    }

    public void addByGuests(Airbnb airbnbToAdd) {
        Node newNode = new Node(airbnbToAdd);

        if (head == null || head.airbnb.getGuests() > airbnbToAdd.getGuests()) {
            newNode.next = head;
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null && current.next.airbnb.getGuests() < airbnbToAdd.getGuests()) {
                current = current.next;
            }
            newNode.next = current.next;
            current.next = newNode;
        }
    }

    public PriorityQueue<Airbnb> findAirbnb(int nightlyRate, int numGuests, String type) {
        PriorityQueue<Airbnb> foundAirbnbs = new PriorityQueue<>(Airbnb::compareTo);

        Node current = head;
        while (current != null) {
            if (current.airbnb.getPrice() <= nightlyRate &&
                    current.airbnb.getGuests() >= numGuests &&
                    current.airbnb.getType().equalsIgnoreCase(type)) {
                foundAirbnbs.offer(current.airbnb);
            }
            current = current.next;
        }

        return foundAirbnbs;
    }

    public int removeSpecificType(String typeToRemove) {
        int removedCount = 0;

        while (head != null && head.airbnb.getType().equalsIgnoreCase(typeToRemove)) {
            head = head.next;
            removedCount++;
        }

        if (head != null) {
            Node current = head;

            while (current.next != null) {
                if (current.next.airbnb.getType().equalsIgnoreCase(typeToRemove)) {
                    current.next = current.next.next;
                    removedCount++;
                } else {
                    current = current.next;
                }
            }
        }

        return removedCount;
    }

    public void print() {
        Node current = head;

        while (current != null) {
            System.out.println(current.airbnb.toString());
            current = current.next;
        }
    }
    
    public void printHeader() {
    	System.out.println();
        System.out.println("Airbnb Type         Nightly Rate Guests Rooms");
        System.out.println("-----------------------------------------------");
    }
}

class DoubleLinkedList {
    private Node head;
    private Node tail;

    public DoubleLinkedList() {
        head = null;
        tail = null;
    }

    private class Node {
        private Airbnb airbnb;
        private Node next;
        private Node prev;

        public Node(Airbnb airbnb) {
            this.airbnb = airbnb;
            this.next = null;
            this.prev = null;
        }
    }

    public void addToEnd(Airbnb airbnbToAdd) {
        Node newNode = new Node(airbnbToAdd);

        if (tail == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    public void printBackwards() {
        Node current = tail;

        while (current != null) {
            System.out.println(current.airbnb.toString());
            current = current.prev;
        }
    }
}

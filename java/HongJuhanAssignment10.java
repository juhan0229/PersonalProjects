/*	Juhan Hong
 * 	CS 1450 (T/R)
 * 	Due 5/02/2023
 * 	Assignment 10
 * 	Description: This program reads a text file that contains parrots' id, name, and song word. Then, it creates a binary tree and inserts
 *  parrots into the tree. After that, it prints out the parrots' song words in level order and the parrots' name on the leaf nodes. This
 *  program will demonstrate my understanding about binary search trees and level order traversal. 
 * 
 */

import java.util.LinkedList;
import java.util.Queue;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class HongJuhanAssignment10 {

    public static void main(String[] args) throws IOException {
        final String PARROT_TEXT = "parrots.txt";

        File parrotText = new File(PARROT_TEXT);

        Scanner readParrot = new Scanner(parrotText);

        //creating a binary tree and a queue to store parrots
        BinaryTree treeTest = new BinaryTree();
        Queue<Parrot> parrotsTest = new LinkedList<>();

        while (readParrot.hasNext()) {
            int id = readParrot.nextInt();
            String name = readParrot.next();
            String songWord = readParrot.next().trim();
            Parrot parrots = new Parrot(id, name, songWord);
            parrotsTest.add(parrots);
        }
        //inserting parrots into the binary tree
        for (Parrot parrot : parrotsTest) {
            treeTest.insert(parrot);
        }

        System.out.println("Parrot's Song");
        System.out.println("-------------------------------");
        treeTest.levelOrder();
        System.out.println();
        System.out.println();
        System.out.println();

        // Visit the leaf nodes and print each parrot's name
        System.out.println("Parrots on Leaf Nodes");
        System.out.println("-------------------------------");
        treeTest.visitLeaves();

        readParrot.close();
    }

}

// Parrot class
// Parrots are comparable by their id
class Parrot implements Comparable<Parrot> {
    int id;
    String name;
    String songWord;

    public Parrot(int id, String name, String songWord) {
        this.id = id;
        this.name = name;
        this.songWord = songWord;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSongWord() {
        return songWord;
    }

    public String sing() {
        return songWord + " ";
    }

    @Override
    public int compareTo(Parrot otherParrot) {
        if (this.id > otherParrot.id) {
            return 1;
        } else if (this.id < otherParrot.id) {
            return -1;
        } else {
            return 0;
        }
    }
}

// Binary Tree class

class BinaryTree {
    private TreeNode root;

    public BinaryTree() {
        root = null;
    }

    public boolean insert(Parrot parrotToAdd) {
        if (root == null) {
            root = new TreeNode(parrotToAdd);
            return true;
        } else {
            return root.insert(parrotToAdd);
        }
    }

    // Level order traversal
    public void levelOrder() {
        if (root == null) {
            return;
        }
    
        // Create an empty queue for level order traversal
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode currentNode = queue.poll();

            System.out.print(currentNode.parrot.sing());

            // Enqueue left child
            if (currentNode.left != null) {
                queue.add(currentNode.left);
            }

            // Enqueue right child
            if (currentNode.right != null) {
                queue.add(currentNode.right);
            }
        }
    }

    public void visitLeaves() {
        visitLeaves(root);
    }

    // Helper method for visitLeaves
    // Recursively visit the leaves of the tree
    private void visitLeaves(TreeNode node) {
        if (node == null) {
            return;
        }

        if (node.left == null && node.right == null) {
            System.out.println(node.parrot.getName());
        }

        visitLeaves(node.left);
        visitLeaves(node.right);
    }

    // TreeNode class
    // Each node contains a parrot and two children
    private class TreeNode {
        Parrot parrot;
        TreeNode left, right;

        public TreeNode(Parrot parrot) {
            this.parrot = parrot;
            left = right = null;
        }

        // Insert a parrot into the tree
        // Return true if the parrot is inserted successfully
        // Return false if the parrot is already in the tree
        public boolean insert(Parrot parrotToAdd) {
            if (this.parrot.compareTo(parrotToAdd) < 0) {
                if (this.right == null) {
                    this.right = new TreeNode(parrotToAdd);
                    return true;
                } else {
                    return this.right.insert(parrotToAdd);
                }
            } else if (this.parrot.compareTo(parrotToAdd) > 0) {
                if (this.left == null) {
                    this.left = new TreeNode(parrotToAdd);
                    return true;
                } else {
                    return this.left.insert(parrotToAdd);
                }
            }
            // If parrotToAdd is equal to the current parrot, we do not insert it and return
            // false
            return false;
        }
    }
}
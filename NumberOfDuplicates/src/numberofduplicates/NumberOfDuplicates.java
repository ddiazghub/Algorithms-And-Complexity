/*
 * Algorithms and Complexity                                August 5, 2022
 * IST 4310
 * Prof. M. Diaz-Maldonado
 * Name: David Eduardo Díaz de Moya
 *
 * Synopsis:
 * Creates a file and writes random integers to it, then counts the number of duplicate integers.
 *
 *
 * Copyright (c) 2022 David Eduardo Díaz de Moya
 * This file is released under the GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 *
 * References:
 * [0] Files: www.w3schools.com/java/java_files_create.asp
 * [1] PrintWriter: (docs.oracle.com/en/java/javase/11/docs/api/java.base/
 *                   java/io/PrintWriter.html)
 * [2] IOException: (docs.oracle.com/javase/7/docs/api/java/io/
 *                   IOException.html)
 * [3] FileNotFoundException: (docs.oracle.com/javase/7/docs/api/java/io/
 *                             FileNotFoundException.html)
 * [4] Scanner: docs.oracle.com/javase/7/docs/api/java/util/Scanner.html
 * [5] www.javatpoint.com/throw-keyword
 * [6] Random: (docs.oracle.com/javase/8/docs/api/java/util/Random.html)
 *
 */

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package numberofduplicates;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author dedemoya
 */
public class NumberOfDuplicates {
    // Class which contains a key-value pair
    public static class KeyValuePair<T, K> {
        // The key
        public T key;
        
        // The value
        public K value;

        // Creates a new key-value pair
        public KeyValuePair(T key, K value) {
            this.key = key;
            this.value = value;
        }
    }
    
    // Interface which contains a single parameter function to run
    public interface Function<T> {
        public void function(T parameter);
    }
    
    // A node belonging to a singly linked list
    public static class LinkedListNode<T> {
        // The data contained by the node
        public T data;
        
        // The next node in the list
        public LinkedListNode<T> next;

        // Creates a new node
        public LinkedListNode(T data) {
            this(data, null);
        }

        // Creates a new node
        public LinkedListNode(T data, LinkedListNode<T> next) {
            this.data = data;
            this.next = next;
        }
    }
    
    // A singly linked list
    public static class LinkedList<T> {
        // First node in the list, null if empty
        public LinkedListNode<T> head;
        
        // Last node in the list, null if empty
        public LinkedListNode<T> tail;
        
        // Number of nodes in the list
        public int size;

        // Creates a new linkedlist
        public LinkedList() {
            this.head = null;
            this.tail = null;
            this.size = 0;
        }

        // Adds a node to the list with given data
        public void add(T data) {
            // Creates the node
            LinkedListNode<T> node = new LinkedListNode<>(data);

            if (this.head == null) {
                // If the list is empty, the node is placed at the head
                this.head = node;
            } else {
                // If the list is not empty, the node is placed at the end
                this.tail.next = node;
            }

            // The new node becomes the tail of the list and the size is incremented
            this.tail = node;
            this.size++;
        }

        // Loops through the list while running the given function for each node, supplying the node data as parameter
        public void forEach(Function<T> function) {
            // Starts at the head
            LinkedListNode<T> current = this.head;

            // While the end hasn´t been reached
            while (current != null) {
                // Run the given function
                function.function(current.data);
                // Go to the next node
                current = current.next;
            }
        }
    }
    
    // Class which counts frequency in a linked list of integers, represented by a linked list of key-value pairs containing each integer and it's frequency.
    public static class IntFrequencyCounter extends LinkedList<KeyValuePair<Integer, Integer>> {
        // Creates a counter for the given list of numbers
        public IntFrequencyCounter(LinkedList<Integer> numbers) {
            // Loops through the list and for each number...
            numbers.forEach(number -> {
                // Gets the current frequency for the number
                KeyValuePair<Integer, Integer> count = this.get(number);

                if (count == null) {
                    // If the number is added if it isn't on the counter
                    this.add(new KeyValuePair<>(number, 1));
                } else {
                    // If a key-value pair was found for the number, it's frequency is incremented
                    count.value++;
                }
            });
        }

        // Gets the count for an integer
        public KeyValuePair<Integer, Integer> get(int number) {
            // Starts at the head
            LinkedListNode<KeyValuePair<Integer, Integer>> current = this.head;

            // Loops through the list
            while (current != null) {
                // Checks if the integer has been found and returns the pair
                if (current.data.key == number) {
                    return current.data;
                }

                // Goes to the next node
                current = current.next;
            }
            
            // If the number wasn't found, returns null.
            return null;
        }
    }

    public static class FileHandler {
        // implementations:
        public static void create ()
        // creates a file
        {
            try
            {
                // defines the filename
                String fname = ("data.txt");
                // creates a new File object
                File f = new File (fname);

                String msg = "creating file `" + fname + "' ... ";
                System.out.println();
                System.out.printf("%s", msg);
                // creates the new file
                f.createNewFile();
                System.out.println("done");

            }
            catch (IOException err)
            {
                // complains if there is an Input/Output Error
                err.printStackTrace();
            }

            return;
        }


        public static void write ()
        // writes data to a file
        {
            try
            {
                // defines the filename
                String filename = ("data.txt");
                // creates new PrintWriter object for writing file
                PrintWriter out = new PrintWriter (filename);

                int numel = 256;
                Random rng = new Random();

                String msg = "writing %d numbers ... ";
                System.out.printf(msg, numel);

                // writes the integers in the range [0, 256)
                for (int i = 0; i != numel; ++i)
                    out.printf("%d\n", rng.nextInt(256));

                System.out.println("done");

                System.out.printf("closing file ... ");
                out.close();	// closes the output stream
                System.out.println("done");
            }
            catch (FileNotFoundException err)
            {
                // complains if file does not exist
                err.printStackTrace();
            }

            return;
        }

        public static LinkedList<Integer> store ()
        // stores the file contents into an array and prints the array
        {
            String filename = "data.txt";
            File f = new File (filename);

            try
            {
                Scanner in = new Scanner (f);

                // allocates list for storing the numbers in file
                LinkedList<Integer> list = new LinkedList<>();

                // reads numbers into array
                while (in.hasNextInt())
                    list.add(in.nextInt());

                System.out.printf("\nnumbers in array:\n");
                list.forEach(number -> System.out.printf("%4d\n", number));

                String msg = ("array stores %d numbers\n");
                System.out.printf(msg, list.size);

                in.close();	// closes the input stream

                return list;
            }
            catch (FileNotFoundException err)
            {
                // complains if file does not exist
                err.printStackTrace();
            }


            return null;
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Creates file
        FileHandler.create();
        
        // Writes 256 random integers to file
        FileHandler.write();
        
        // Reads all integers from file and stores them in a linked list. Then counts duplicates.
        IntFrequencyCounter counter = new IntFrequencyCounter(FileHandler.store());
        
        System.out.println("Count complete:");
        
        // For each number, displays the amount of duplicates
        counter.forEach(count -> {
            System.out.println(count.key + " -> " + count.value);
        });
    }
    
}

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
import java.io.FileWriter;
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
        private long comparisons;
        private final long elapsedTime;
        private final long inputSize;
        
        // Creates a counter for the given list of numbers
        public IntFrequencyCounter(LinkedList<Integer> numbers) {
            this.inputSize = numbers.size;
            this.comparisons = 0;
            long start = System.nanoTime();

            // Loops through the list and for each number...
            numbers.forEach(number -> {
                // Gets the current frequency for the number
                KeyValuePair<KeyValuePair<Integer, Integer>, Integer> numberData = this.get(number);
                KeyValuePair<Integer, Integer> count = numberData.key;
                
                this.comparisons += numberData.value;
                
                if (count == null) {
                    // If the number is added if it isn't on the counter
                    this.add(new KeyValuePair<>(number, 1));
                } else {
                    // If a key-value pair was found for the number, it's frequency is incremented
                    count.value++;
                }
            });
            
            this.elapsedTime = System.nanoTime() - start;
        }

        // Gets the count for an integer
        public KeyValuePair<KeyValuePair<Integer, Integer>, Integer> get(int number) {
            // Starts at the head
            LinkedListNode<KeyValuePair<Integer, Integer>> current = this.head;
            int i = 1;
            
            // Loops through the list
            while (current != null) {
                // Checks if the integer has been found and returns the pair
                if (current.data.key == number) {
                    return new KeyValuePair<>(current.data, i);
                }

                // Goes to the next node
                current = current.next;
                i++;
            }
            
            // If the number wasn't found, returns null.
            return new KeyValuePair<>(null, i);
        }

        public long getComparisons() {
            return comparisons;
        }

        public long getElapsedTime() {
            return elapsedTime;
        }

        public long getInputSize() {
            return inputSize;
        }
        
    }

    public static class InputFileHandler {
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

                if (f.exists())
                    f.delete();
                
                f.createNewFile();
            }
            catch (IOException err)
            {
                // complains if there is an Input/Output Error
                err.printStackTrace();
            }

            return;
        }


        public static void writeRandom(int n)
        // writes data to a file
        {
            try
            {
                // defines the filename
                String filename = ("data.txt");
                // creates new PrintWriter object for writing file
                PrintWriter out = new PrintWriter (filename);

                Random rng = new Random();

                // writes n random integers in the range 1-1000
                for (int i = 0; i < n; ++i)
                    out.printf("%d\n", rng.nextInt(1000));

                out.close();	// closes the output stream
            }
            catch (FileNotFoundException err)
            {
                // complains if file does not exist
                err.printStackTrace();
            }

            return;
        }
        
        public static void writeOne(int n)
        // writes data to a file
        {
            try
            {
                // defines the filename
                String filename = ("data.txt");
                // creates new PrintWriter object for writing file
                PrintWriter out = new PrintWriter (filename);

                // writes n random integers in the range 1-1000
                for (int i = 0; i < n; ++i)
                    out.printf("%d\n", 1);

                out.close();	// closes the output stream
            }
            catch (FileNotFoundException err)
            {
                // complains if file does not exist
                err.printStackTrace();
            }

            return;
        }
        
        public static void writeDifferent(int n)
        // writes data to a file
        {
            try
            {
                // defines the filename
                String filename = ("data.txt");
                // creates new PrintWriter object for writing file
                PrintWriter out = new PrintWriter (filename);

                // writes n random integers in the range 1-1000
                for (int i = 0; i < n; ++i)
                    out.printf("%d\n", i);

                out.close();	// closes the output stream
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
    
    public static class OutputFileHandler {
        public enum OutputType {
            BEST,
            WORST,
            AVERAGE
        }
        
        // implementations:
        public static void create (OutputType type)
        // creates a file
        {
            try
            {
                // defines the filename
                String fname = "";
                
                switch (type) {
                    case BEST:
                        fname = "bestcase.txt";
                        break;
                    
                    case WORST:
                        fname = "worstcase.txt";
                        break;
                        
                    case AVERAGE:
                        fname = "averagecase.txt";
                        break;
                }
                
                // creates a new File object
                File f = new File (fname);

                if (f.exists())
                    f.delete();
                
                // creates the new file
                f.createNewFile();
                
                try (PrintWriter out = new PrintWriter(f)) {
                    out.println("size time comparisons");
                }
            }
            catch (IOException err)
            {
                // complains if there is an Input/Output Error
                err.printStackTrace();
            }

            return;
        }


        public static void write(OutputType type, long inputSize, long elapsedTime, long comparisons)
        // writes data to a file
        {
            // defines the filename
            String filename = "";

            switch (type) {
                case BEST:
                    filename = "bestcase.txt";
                    break;

                case WORST:
                    filename = "worstcase.txt";
                    break;

                case AVERAGE:
                    filename = "averagecase.txt";
                    break;
            }

            // creates new PrintWriter object for writing file
            try(
                FileWriter fileWriter = new FileWriter(filename, true);
                PrintWriter out = new PrintWriter(fileWriter);
            ) {
                out.printf("%d %d %d\n", inputSize, elapsedTime, comparisons);
            } catch (IOException err) {
                err.printStackTrace();
            }
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        OutputFileHandler.create(OutputFileHandler.OutputType.BEST);
        OutputFileHandler.create(OutputFileHandler.OutputType.AVERAGE);
        OutputFileHandler.create(OutputFileHandler.OutputType.WORST);
        
        System.out.println("Best case");
        // Best case
        for (int i = 2; i < 1000000; i *= 2) {
            // Creates file
            InputFileHandler.create();

            // Writes 256 random integers to file
            InputFileHandler.writeOne(i);

            // Reads all integers from file and stores them in a linked list. Then counts duplicates.
            IntFrequencyCounter counter = new IntFrequencyCounter(InputFileHandler.store());
            
            // Writes results to output file
            OutputFileHandler.write(OutputFileHandler.OutputType.BEST, counter.getInputSize(), counter.getElapsedTime(), counter.getComparisons());
        }
        
        System.out.println("Done");
        
        System.out.println("Average case");
        
        // Average case
        for (int i = 2; i < 1000000; i *= 2) {
            // Creates file
            InputFileHandler.create();

            // Writes 256 random integers to file
            InputFileHandler.writeRandom(i);

            // Reads all integers from file and stores them in a linked list. Then counts duplicates.
            IntFrequencyCounter counter = new IntFrequencyCounter(InputFileHandler.store());

            /*
            // For each number, displays the amount of duplicates
            counter.forEach(count -> {
                if (count.value > 1) {
                    System.out.println(count.key + " -> " + (count.value - 1));
                }
            });
            */
            
            OutputFileHandler.write(OutputFileHandler.OutputType.AVERAGE, counter.getInputSize(), counter.getElapsedTime(), counter.getComparisons());
        }
        
        System.out.println("Done");
        System.out.println("Worst case");
        
        // Worst case
        for (int i = 2; i < 100000; i *= 2) {
            // Creates file
            InputFileHandler.create();

            // Writes 256 random integers to file
            InputFileHandler.writeDifferent(i);

            // Reads all integers from file and stores them in a linked list. Then counts duplicates.
            IntFrequencyCounter counter = new IntFrequencyCounter(InputFileHandler.store());

            /*
            // For each number, displays the amount of duplicates
            counter.forEach(count -> {
                if (count.value > 1) {
                    System.out.println(count.key + " -> " + (count.value - 1));
                }
            });
            */
            
            OutputFileHandler.write(OutputFileHandler.OutputType.WORST, counter.getInputSize(), counter.getElapsedTime(), counter.getComparisons());
        }
        
        System.out.println("Done");
    }
    
}

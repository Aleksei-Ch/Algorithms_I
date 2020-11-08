import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] array;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        array = (Item[]) new Object[10];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item cannot be null");
        }

        if (size == array.length) {
            // enlarge array
            Item[] newArray = (Item[]) new Object[array.length == 0 ? 1 : array.length * 2];
            System.arraycopy(array, 0, newArray, 0, array.length);
            array = newArray;
        }

        array[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }

        int itemId = StdRandom.uniform(0, size);
        try {
            return array[itemId];
        } finally {
            array[itemId] = array[size - 1]; // replace removed element with the last one
            array[size-- - 1] = null; // remove last element and reduce size

            if (array.length / 4 > 0 && size == array.length / 4) {
                // reduce array but do not make it less than 1
                Item[] newArray = (Item[]) new Object[array.length / 2];
                System.arraycopy(array, 0, newArray, 0, size);
                array = newArray;
            }
        }
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }

        return array[StdRandom.uniform(0, size)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RQIterator<>(array, size);
    }

    private static class RQIterator<Item> implements Iterator<Item> {

        private final Item[] iArray;
        private int iSize;

        public RQIterator(Item[] array, int size) {
            iArray = (Item[]) new Object[array.length];
            System.arraycopy(array, 0, iArray, 0, array.length);
            iSize = size;
        }

        @Override
        public boolean hasNext() {
            return iSize > 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Iterator has reach the end of queue");
            }

            // copy from dequeue
            int itemId = StdRandom.uniform(0, iSize);
            try {
                return iArray[itemId];
            } finally {
                iArray[itemId] = iArray[iSize - 1];
                iArray[iSize-- - 1] = null;
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("This iterator cannot remove elements");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        queue.enqueue("a");
        queue.enqueue("b");
        queue.enqueue("c");
        queue.enqueue("d");

        System.out.println("Iterated queue:");
        for (String s : queue) {
            System.out.print(s + " ; ");
        }
        System.out.println();

        System.out.println("Random samples: ");
        System.out.println(queue.sample());
        System.out.println(queue.sample());
        System.out.println(queue.sample());
        System.out.println(queue.sample());
        System.out.println(queue.sample());

        System.out.println("Random removes: ");
        System.out.print("Size before: ");
        System.out.println(queue.size());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());

        System.out.print("Size after: ");
        System.out.println(queue.size());

        System.out.println("Adding-removing sequence: ");
        queue.enqueue("a");
        System.out.println(queue.dequeue());
        queue.enqueue("b");
        queue.enqueue("c");
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());

    }

}
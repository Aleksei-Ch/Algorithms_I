import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size;

    private class Node {
        Item item;
        Node next;
        Node prev;

        private Node(Item item, Node next, Node prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item cannot be null");
        }

        Node newFirst = new Node(item, first, null);
        if (first != null) {
            first.prev = newFirst; // shift first element right
        }
        first = newFirst; // replace first element with new

        if (isEmpty()) {
            last = first; // first added element is also the last one
        }

        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item cannot be null");
        }

        Node newLast = new Node(item, null, last);
        if (last != null) {
            last.next = newLast; // shift last element left
        }
        last = newLast; // replace last element with new

        if (isEmpty()) {
            first = last; // first added element is also the last one
        }

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty.");
        }

        try {
            size--;
            return first.item; // return first item
        } finally {
            first = first.next; // remove first element
            if (first != null) {
                first.prev = null;
            } else {
                last = null; // if it was last element in deque, drop last link too
            }
        }
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty.");
        }

        try {
            size--;
            return last.item; // return last item
        } finally {
            last = last.prev; // remove last element
            if (last != null) {
                last.next = null;
            } else {
                first = null; // if it was last element in deque, drop first link too
            }
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {

            Node current = first; // starts from first element

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Item next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("Iterator has reach the end of deque");
                }

                try {
                    return current.item;
                } finally {
                    current = current.next;
                }
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("This iterator cannot remove elements");
            }
        };
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = initTestDeque();
        // Print
        System.out.println("Plain print (check iterator):");
        for (String s : deque) {
            System.out.print(s + " ; ");
        }
        System.out.println("\n");

        // Removing from last
        System.out.println("Removing from last:");
        String s = deque.removeLast();
        while (s != null) {
            System.out.print(s + "(" + deque.size() + ") ; ");
            s = deque.isEmpty() ? null : deque.removeLast();
        }
        System.out.println("\n");

        // Removing from first
        System.out.println("Removing from first:");
        deque = initTestDeque();
        s = deque.removeFirst();
        while (s != null) {
            System.out.print(s + "(" + deque.size() + ") ; ");
            s = deque.isEmpty() ? null : deque.removeFirst();
        }
        System.out.println("\n");


        // Random removing
        System.out.println("Random removing:");
        deque = initTestDeque();
        s = StdRandom.uniform(0, 2) == 0 ? deque.removeFirst() : deque.removeLast();

        while (s != null) {
            System.out.print(s + "(" + deque.size() + ") ; ");
            s = deque.isEmpty() ? null : StdRandom.uniform(0, 2) == 0 ? deque.removeFirst() : deque.removeLast();
        }
        System.out.println("\n");

    }

    private static Deque<String> initTestDeque() {
        Deque<String> deque = new Deque<>();
        deque.addFirst("bb");
        deque.addLast("cc");
        deque.addLast("dd");
        deque.addFirst("aa");
        deque.addLast("ee");
        return deque;
    }

}
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

// Linked list Implementation of Dequeue (double-ended queue )


public class Deque<Item> implements Iterable<Item> {

    private Node<Item> first;
    private Node<Item> last;
    private int N;


    private class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> previous;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        N = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return N;
    }

    // add the item to the front
    public void addFirst(Item item) {

        if (item == null) throw new IllegalArgumentException();

        Node<Item> new_node = new Node<>();
        new_node.item = item;

        if (first != null) {
            first.previous = new_node;
            new_node.next = first;
            first = new_node;
        }
        else {
            first = new_node;
            last = first;
        }

        N++;
    }

    // add the item to the back
    public void addLast(Item item) {

        if (item == null) throw new IllegalArgumentException();

        Node<Item> new_node = new Node<Item>();
        new_node.item = item;

        if (last != null) {
            new_node.previous = last;
            last.next = new_node;
            last = new_node;
        }
        else {
            last = new_node;
            first = last;
        }
        
        N++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        Item item = first.item;

        if (size() != 1) {
            first = first.next;
            first.previous = null;
        }
        else {
            first = null;
            last = null;
        }
        N--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();

        Item item = last.item;
        if (size() != 1) {
            last = last.previous;
            last.next = null;
        }
        else {
            last = null;
            first = null;
        }
        N--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new LinkedIterator();
    }


    private class LinkedIterator implements Iterator<Item> {
        private Node<Item> current;

        public LinkedIterator() {
            current = first;
        }

        public boolean hasNext(){
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();

            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();

        int n = 5;

        deque.addFirst(n);
        for (int x : deque)
            StdOut.print(x);
        StdOut.println();
        StdOut.println(deque.size());

        for (int i = n-1; i >= 0; i--) {
            deque.addFirst(i);
        }
        
        for(Integer x : deque) StdOut.print(x);
        StdOut.println();
        StdOut.println(deque.size());

        for (int i = 1; i <= n; i++) deque.addLast(n+i);
        Iterator<Integer> itr = deque.iterator();
        while(itr.hasNext()){
            StdOut.print(itr.next());
        }
        StdOut.println();
        StdOut.println(deque.size());

        try {
            itr.next();
        } 
        catch ( NoSuchElementException e) {
            StdOut.println(true);
        }

        for (int i = n-1; i >= 0; i--) {
            StdOut.println("removed: " + deque.removeFirst());
            for(Integer x : deque) StdOut.print(x);
            StdOut.println();
            StdOut.println(deque.size());
        }
        


        for (int i = 1; i <= n; i++) {
            try {
                StdOut.println("removed: " + deque.removeLast());
                for(Integer x : deque) StdOut.print(x);
                StdOut.println();
                StdOut.println(deque.size());
            }
            catch (NoSuchElementException e) {
                StdOut.println("Erroe at " + i + " removal");
            }
        }
    }

}
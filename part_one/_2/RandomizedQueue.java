import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] queue;
    private int N;

    // construct an empty randomized queue
    public RandomizedQueue() {
        queue = (Item[]) new Object[1];
        N = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return N;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();

        if (N == queue.length) resize(2 * queue.length);
        queue[N++] = item;
    }

    private void resize(int size) {
        Item[] copy = (Item[]) new Object[size];
        for (int i = 0; i < N; i++) {
            copy[i] = queue[i];
        }
        queue = copy;
    }

    private void swap(Item[] items, int i, int j) {
        Item temp = items[i];
        items[i] = items[j];
        items[j] = temp;
    }

    // remove and return a random item
    public Item dequeue() {
        if (N == 0) throw new NoSuchElementException();

        int choosen = randomIndex(N);
        Item item = queue[choosen];
        swap(queue, choosen, --N);
        queue[N] = null;
        if (N > 0 && N == queue.length / 4) resize(queue.length / 2);
        return item;

    }

    private int randomIndex(int n) {
        // n must be positive non zero
        return StdRandom.uniform(n);
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (N == 0) throw new NoSuchElementException();

        return queue[randomIndex(N)];

    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    private class RandomIterator implements Iterator<Item> {

        private Item[] items;
        private Item current;
        private int index;

        public RandomIterator() {
            items = (Item[]) new Object[N];
            for (int i = 0; i < N; i++) {
                items[i] = queue[i];
            }
            shuffle(items);
            index = 0;
            if (items.length > 0) {
                current = items[index];
            }
            else {
                current = null;
            }
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();

            Item item = current;
            if (index + 1 != items.length) {
                current = items[++index];
            }
            else {
                current = null;
            }
            return item;
        }

        private void shuffle(Item[] items) {
            for (int i = 1; i < items.length; i++) {
                swap(items, i, randomIndex(i + 1));
            }
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();

        StdOut.println(queue.isEmpty());
        for (int i = 0; i < 20; i++) {
            queue.enqueue(i);
            StdOut.println(queue.size() + " " + queue.sample());
        }
        // queue.printQ();
        StdOut.println(queue.sample());

        Iterator<Integer> itr = queue.iterator();
        while (itr.hasNext()) {
            StdOut.print(itr.next());
            // queue.enqueue(20);
            StdOut.print(" ");
        }
        StdOut.println();

        StdOut.println(queue.size());
        for (Integer i : queue) {
            StdOut.print(i);
            queue.dequeue();
            StdOut.print(" - " + queue.size() + " ");
        }
        StdOut.println();
        for (Integer i : queue) {
            StdOut.print(i);
            // queue.dequeue();
            StdOut.print(" ");

        }
    }

}

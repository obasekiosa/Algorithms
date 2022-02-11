/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;

public class MaxPQ<Key extends Comparable<Key>> {

    private Object[] pq;
    private int N;


    public MaxPQ() {
        this.pq = new Object[1];
        this.N = 0;
    }

    public MaxPQ(Key[] keys) {
        this();
        for (Key key : keys) {
            this.insert(key);
        }
    }

    public void insert(Key key) {
        if (this.N == this.pq.length - 1) resize(2 * this.pq.length);
        this.pq[++this.N] = key;
        swim(this.N);
    }

    public Key delMax() {
        if (N == 0) throw new NoSuchElementException();
        final Key max = (Key) this.pq[1];
        exch(1, this.N);
        pq[this.N--] = null;
        sink(1);
        if (this.N == this.pq.length / 4) resize(this.pq.length / 2);
        return max;
    }

    public boolean isEmpty() {
        return this.N == 0;
    }

    public Key max() {
        final Key max = (Key) this.pq[1];
        return max;
    }

    private void exch(int i, int j) {
        Key tmp = (Key) this.pq[i];
        this.pq[i] = this.pq[j];
        this.pq[j] = tmp;
    }

    private boolean less(int i, int j) {
        Key first = (Key) this.pq[i];
        Key second = (Key) this.pq[j];
        return first.compareTo(second) < 0;
    }

    public void print() {
        for (int i = 0; i < this.pq.length; i++) {
            StdOut.print((Key) this.pq[i] + " ");
        }
        StdOut.println();
    }

    private void resize(int n) {
        Object[] tmp = new Object[n];
        for (int i = 0; i <= this.N; i++) {
            tmp[i] = this.pq[i];
        }
        this.pq = tmp;
    }

    private void swim(int k) {
        while (k > 1) {
            if (!less(k / 2, k)) break;
            exch(k / 2, k);
            k /= 2;
        }
    }

    private void sink(int k) {
        while (2 * k <= N) {
            int j = 2 * k;
            if (j < N && less(j, j + 1)) j++;
            if (!less(k, j)) break;
            exch(j, k);
            k = j;
        }
    }

    public int size() {
        return this.N;
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);

        MaxPQ<Integer> pq = new MaxPQ<>();
        int i = 0;
        while (i < N) {
            pq.insert(i++);
            StdOut.println(pq.size());
        }
        pq.print();
        i = 0;
        while (i < pq.size()) {
            StdOut.println("max = " + pq.delMax());
        }


    }
}

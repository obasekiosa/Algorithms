/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);

        if (k > 0) {
            RandomizedQueue<String> Q = new RandomizedQueue<>();

            while (!StdIn.isEmpty()) {
                String in = StdIn.readString();
                Q.enqueue(in);
            }

            for (int i = 0; i < k; i++) {
                StdOut.println(Q.dequeue());
            }
        }
    }
}

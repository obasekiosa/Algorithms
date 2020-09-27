/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.StdRandom;

public class RandomPoints {
    private static int N = 32768;

    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);

        Out out = new Out("output.txt");
        out.println(n);
        for (int i = 0; i < n; i++) {
            int x = StdRandom.uniform(N);
            int y = StdRandom.uniform(N);

            out.println(x + "    " + y);
        }
        out.close();

    }
}

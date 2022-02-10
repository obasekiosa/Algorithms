import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double results[];
    private int N;
    private double mean;
    private double stddev;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if ( n <= 0 || trials <= 0) throw new IllegalArgumentException();

        this.N = n;

        this.results = new double[trials];
        for (int i = 0; i < this.results.length; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;

                percolation.open(row, col);
            }

            results[i] = (percolation.numberOfOpenSites() * 1.0) / (this.N * this.N);
        }

        this.mean = StdStats.mean(results);
        this.stddev = StdStats.stddev(results);
    }

    // sample mean of percolation threshold
    public double mean() {
        return this.mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {

        return this.stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double mean = this.mean();
        double std = this.stddev();

        return mean - (1.96 * std / Math.sqrt(results.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double mean = this.mean();
        double std = this.stddev();

        return mean + (1.96 * std / Math.sqrt(results.length));
    }

   // test client (see below)
   public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats experiment = new PercolationStats(n, T);

        StdOut.println("mean                   = " + experiment.mean());
        StdOut.println("stddev                 = " + experiment.stddev());
        StdOut.println("95 confidence interval = [" + experiment.confidenceLo() + ", " + experiment.confidenceHi() + "]");
   }

}
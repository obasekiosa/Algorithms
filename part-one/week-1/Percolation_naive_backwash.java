
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int openSites;
    private boolean grid[][];
    private WeightedQuickUnionUF uf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {

        if (n <= 0) throw new IllegalArgumentException();

        this.grid = new boolean[n][n];
        int size = n*n;
        this.uf = new WeightedQuickUnionUF(size + 2);
        this.openSites = 0;

        // connect virtual sites 0 and size + 1
        for (int i = 1; i <= n; i++) {
            this.uf.union(0, i);
            this.uf.union(size + 1, size - n + i);
        }
    }

    private void validate(int p) {
        if (p <= 0 || p > this.grid.length)
            throw new IllegalArgumentException();
    }

    private int index(int row, int col) {
        return this.grid.length * (row - 1) + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOpen(row, col)) return;

        this.grid[row - 1][col - 1] = true;
        openSites++;
        
        int left = col - 1;
        int right = col + 1;
        int top = row - 1;
        int bottom = row + 1;

        if (left > 0 && isOpen(row, left))
            this.uf.union(index(row, left), index(row, col));

        if (right <= this.grid.length && isOpen(row, right))
            this.uf.union(index(row, right), index(row, col));

        if (top > 0 && isOpen(top, col))
            this.uf.union(index(top, col), index(row, col));

        if (bottom <= this.grid.length && isOpen(bottom, col))
            this.uf.union(index(bottom, col), index(row, col));
        
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row);
        validate(col);
        return this.grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return isOpen(row, col) && this.uf.find(0) == this.uf.find(index(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        if (grid.length == 1) {
            return grid[0][0];
        }
        return this.uf.find(0) == this.uf.find(this.grid.length * this.grid.length + 1);
    }

    // test client (optional)
    public static void main(String[] args){
        StdOut.println(new Percolation(2).percolates());
    }
}
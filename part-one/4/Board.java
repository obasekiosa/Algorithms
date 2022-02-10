/* *****************************************************************************
 *  Name:              Osakpolor Tolulope Obaseki
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board {

    private final int[][] board;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.board = new int[tiles.length][tiles.length];
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles.length; col++) {
                this.board[row][col] = tiles[row][col];
            }
        }
    }

    // string representation of this board
    public String toString() {
        String output = "";
        output += this.board.length + "\n";
        for (int row = 0; row < this.board.length; row++) {
            for (int col = 0; col < this.board.length; col++) {
                output += " " + this.board[row][col];
            }
            output += "\n";
        }

        return output;
    }

    // board dimension n
    public int dimension() {
        return this.board.length;
    }

    // number of tiles out of place
    public int hamming() {
        int count = 0;
        for (int row = 0; row < this.board.length; row++) {
            for (int col = 0; col < this.board.length; col++) {
                if (row == this.board.length - 1 && col == this.board.length - 1) {
                    if (this.board[row][col] != 0)
                        count += 0;
                }
                else {
                    if (this.board[row][col] != row * this.board.length + col + 1)
                        count++;
                }
            }
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int count = 0;
        for (int row = 0; row < this.board.length; row++) {
            for (int col = 0; col < this.board.length; col++) {
                int tile = this.board[row][col];
                int actualRow;
                int actualCol;

                if (tile == 0) {
                    continue;
                }
                else {
                    actualRow = (tile - 1) / this.board.length;
                    actualCol = (tile - 1) % this.board.length;
                }

                count += Math.abs(actualRow - row) + Math.abs(actualCol - col);
            }
        }
        return count;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int row = 0; row < this.board.length; row++) {
            for (int col = 0; col < this.board.length; col++) {
                if (row == this.board.length - 1 && col == this.board.length - 1) {
                    if (this.board[row][col] != 0)
                        return false;
                }
                else {
                    if (this.board[row][col] != row * this.board.length + col + 1)
                        return false;
                }
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (this == y) return true;
        if (y.getClass() != this.getClass()) return false;

        Board tmp = (Board) y;
        if (this.board.length != tmp.dimension()) return false;

        for (int row = 0; row < this.board.length; row++) {
            for (int col = 0; col < this.board.length; col++) {
                if (this.board[row][col] != tmp.board[row][col])
                    return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Bag<Board> bag = new Bag<>();
        int zeroRow = 0;
        int zeroCol = 0;
        for (int row = 0; row < this.board.length; row++) {
            for (int col = 0; col < this.board.length; col++) {
                if (this.board[row][col] == 0) {
                    zeroCol = col;
                    zeroRow = row;
                    break;
                }
            }
            if (this.board[zeroRow][zeroCol] == 0) break;
        }


        int[][] offset = new int[][] { { -1, 0 }, { 0, 1 }, { 1, 0 }, { 0, -1 } };
        for (int i = 0; i < offset.length; i++) {
            Board neigbour = new Board(this.board);
            int newRow = zeroRow + offset[i][0];
            int newCol = zeroCol + offset[i][1];
            if (newRow < 0 || newRow > this.board.length - 1 ||
                    newCol < 0 || newCol > this.board.length - 1) continue;

            swap(neigbour, zeroRow, zeroCol, newRow, newCol);
            bag.add(neigbour);
        }
        return bag;
    }

    private void swap(Board board, int row0, int col0, int row1, int col1) {
        int tmp = board.board[row0][col0];
        board.board[row0][col0] = board.board[row1][col1];
        board.board[row1][col1] = tmp;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {

        int[][] indexs = new int[2][2];

        int i = 0;
        for (int row = 0; row < this.board.length; row++) {
            for (int col = 0; col < this.board.length; col++) {
                if (board[row][col] != 0) {
                    indexs[i++] = new int[] { row, col };
                }

                if (i > 1) break;
            }
            if (i > 1) break;
        }

        Board t = new Board(this.board);
        swap(t, indexs[0][0], indexs[0][1], indexs[1][0], indexs[1][1]);
        return t;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        StdOut.println(initial);

        int[][] tile2 = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (i == n - 1 && j == n - 1) tile2[i][j] = 0;
                else tile2[i][j] = i * n + j + 1;

        Board goal = new Board(tiles);

        StdOut.println(goal.isGoal());
        StdOut.println(goal.manhattan());
        StdOut.println(goal.hamming());
        StdOut.println(goal.equals(initial));
        StdOut.println(goal);

        // for (Board neigbour : initial.neighbors()) {
        //     StdOut.println(neigbour);
        //     StdOut.println(neigbour.twin());
        //     StdOut.println(neigbour.dimension());
        //     StdOut.println(neigbour.equals(initial));
        //     StdOut.println(neigbour.isGoal());
        //     StdOut.println(neigbour.hamming());
        //     StdOut.println(neigbour.manhattan());
        // }
        //
        // StdOut.println(initial.twin());
        // StdOut.println(initial.equals(initial));
        // StdOut.println(initial.isGoal());
        // StdOut.println(initial.hamming());
        // StdOut.println(initial.manhattan());
    }

}

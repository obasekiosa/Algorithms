/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {
    private State goal;


    // private Comparator<State> HAMMING_PRIORITY = new Comparator<State>() {
    //     public int compare(State o1, State o2) {
    //         // int score1 = o1.board.hamming() + o1.numMoves;
    //         // int score2 = o2.board.hamming() + o2.numMoves;
    //         // return  score1 - score2;
    //         // return o1.hammingHeuristic - o2.hammingHeuristic;
    //     }
    // };

    private Comparator<State> MANHATTAN_PRIORITY = new Comparator<State>() {
        public int compare(State o1, State o2) {
            // int score1 = o1.board.manhattan() + o1.numMoves;
            // int score2 = o2.board.manhattan() + o2.numMoves;
            // return score1 - score2;

            return o1.manhanttanHeuristic - o2.manhanttanHeuristic;

        }
    };


    private class State {
        private Board board;
        private int numMoves;
        private State previousState;
        private int manhanttanHeuristic;
        // private int hammingHeuristic;

        public State(Board current, int numMoves, State previous) {
            this.board = current;
            this.previousState = previous;
            this.numMoves = numMoves;
            this.manhanttanHeuristic = board.manhattan() + numMoves;
            // this.hammingHeuristic = board.hamming() + numMoves;
        }
    }


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        State initState = new State(initial, 0, null);
        State initStateTwin = new State(initial.twin(), 0, null);

        MinPQ<State> pq = new MinPQ<>(MANHATTAN_PRIORITY);
        MinPQ<State> pqTwin = new MinPQ<>(MANHATTAN_PRIORITY);

        pq.insert(initState);
        pqTwin.insert(initStateTwin);

        State current = pq.delMin();
        State currentTwin = pqTwin.delMin();

        while (true) {

            if (current.board.isGoal()) break;
            if (currentTwin.board.isGoal()) break;

            for (Board n : current.board.neighbors()) {
                if (current.previousState != null) {
                    if (!n.equals(current.previousState.board))
                        pq.insert(new State(n, current.numMoves + 1, current));

                }
                else {
                    pq.insert(new State(n, current.numMoves + 1, current));
                }
            }

            for (Board n : currentTwin.board.neighbors()) {
                if (current.previousState != null) {
                    if (!n.equals(currentTwin.previousState.board))
                        pqTwin.insert(new State(n, currentTwin.numMoves + 1, currentTwin));
                }
                else {
                    pqTwin.insert(new State(n, currentTwin.numMoves + 1, currentTwin));
                }
            }


            if (pq.isEmpty()) break;
            if (pqTwin.isEmpty()) break;

            current = pq.delMin();
            currentTwin = pqTwin.delMin();

            // StdOut.println(current.board);
            // StdOut.println(currentTwin.board);
        }

        if (current.board.isGoal()) {
            goal = current;
        }
        else {
            goal = null;
        }

    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return goal != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) return -1;
        return this.goal.numMoves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;

        Queue<Board> q = new Queue<>();
        getSolutionSequence(q, goal);

        return q;
    }

    private void getSolutionSequence(Queue<Board> solution, State state) {
        if (state.previousState != null)
            getSolutionSequence(solution, state.previousState);
        solution.enqueue(state.board);
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}

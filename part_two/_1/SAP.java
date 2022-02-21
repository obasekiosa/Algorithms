// package part_two._1;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;

public class SAP {

    private final Digraph digraph;


    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) throw new IllegalArgumentException("Null Argument passed. Digraph can not be null");
        this.digraph = new Digraph(G);
    }

    // public SAP(SAP sap) {
    //     if (sap == null) throw new IllegalArgumentException();

    //     this.digraph = new Digraph(sap.digraph);
    // }

    private int[] sap(int v, int w) {

        BreadthFirstDirectedPaths bfdpV = new BreadthFirstDirectedPaths(this.digraph, v);
        BreadthFirstDirectedPaths bfdpW = new BreadthFirstDirectedPaths(this.digraph, w);

        int minLength = Integer.MAX_VALUE;
        int vertex = -1;

        for (int k = 0; k < this.digraph.V(); k++) {
            if (bfdpV.hasPathTo(k) && bfdpW.hasPathTo(k) && (bfdpV.distTo(k) + bfdpW.distTo(k)) < minLength) {
                minLength = bfdpV.distTo(k) + bfdpW.distTo(k);
                vertex = k;
            }
        }

        int[] result = new int[2];
        result[0] = vertex;
        result[1] = minLength;

        return result;
    
    }
    private int[] sap(Iterable<Integer> v, Iterable<Integer> w) {

        BreadthFirstDirectedPaths bfdpV = new BreadthFirstDirectedPaths(this.digraph, v);
        BreadthFirstDirectedPaths bfdpW = new BreadthFirstDirectedPaths(this.digraph, w);

        int minLength = Integer.MAX_VALUE;
        int vertex = -1;

        for (int k = 0; k < this.digraph.V(); k++) {
            if (bfdpV.hasPathTo(k) && bfdpW.hasPathTo(k) && (bfdpV.distTo(k) + bfdpW.distTo(k)) < minLength) {
                minLength = bfdpV.distTo(k) + bfdpW.distTo(k);
                vertex = k;
            }
        }

        int[] result = new int[2];
        result[0] = vertex;
        result[1] = minLength;

        return result;
    }

    private void validateVertex(Integer vertex) {
        if (vertex == null) throw new IllegalArgumentException("Null vertex passed. Vertex can not be null;");
        if (vertex >= this.digraph.V() || vertex < 0) throw new IllegalArgumentException("Specified vertex does not exist in graph");
    }


    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        
        validateVertex(v);
        validateVertex(w);

        int[] result = sap(v, w);
        
        return result[0] == -1 ? -1 : result[1];
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        validateVertex(v);
        validateVertex(w);

        int[] result = sap(v, w);

        return result[0] == -1 ? -1 : result[0];
    }


    private boolean validateVertexIterator(Iterable<Integer> vertexes) {
        if (vertexes == null)
            throw new IllegalArgumentException("Null vertexs given");

        int count = 0;
        for (Integer k : vertexes) {
            validateVertex(k);
            count++;
        }
        return count != 0;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (!validateVertexIterator(v) || !validateVertexIterator(w))
            return -1;

        int[] result = sap(v, w);

        return result[0] == -1 ? -1 : result[1];
    }
    

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (!validateVertexIterator(v) || !validateVertexIterator(w)) return -1;

        int[] result = sap(v, w);

        return result[0] == -1 ? -1 : result[0];
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            System.out.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}

package part_two._1;

import java.util.List;

import edu.princeton.cs.algs4.Digraph;

public class SAP {

    private final Digraph digraph;
    // constructor takes a digraph (not necessarily a DAG)
   public SAP(Digraph G) {
    this.digraph = G;
   }

   private List<Integer> sap(int v, int w) {
       return null;
   }

   // length of shortest ancestral path between v and w; -1 if no such path
   public int length(int v, int w) {
    return 0;
   }

   // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
   public int ancestor(int v, int w) {
    return 0;
   }

   // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
   public int length(Iterable<Integer> v, Iterable<Integer> w) {
    return 0;
   }

   // a common ancestor that participates in shortest ancestral path; -1 if no such path
   public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
    return 0;
   }

   // do unit testing of this class
   public static void main(String[] args) {

   }
}
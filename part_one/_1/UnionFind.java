package part_one._1;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class UnionFind {
    int id[];

    // 0: WeightedQuickdUnionPathCompression, 1: WeightedQuickdUnion, 2: QuickdUnionPathCompression,
    // 3: QuickdUnion, 4: QuickdFind
    int type;

    QuickdFind quickdFind;
    QuickdUnion quickdUnion;
    QuickdUnionPathCompression QUPC;
    WeightedQuickdUnion weightedQuickdUnion;
    WeightedQuickdUnionPathCompression WQUPC;


    public UnionFind(int N) {
        this(N, 0);
    }

    public UnionFind(int N, int type) {
        this.id = new int[N];
        for (int i = 0; i < N; i++)
            this.id[i] = i;

        this.type = type;

        switch (type) {
            case 0:
                WQUPC = new WeightedQuickdUnionPathCompression();
                break;
            case 1:
                weightedQuickdUnion = new WeightedQuickdUnion();
                break;
            case 2:
                QUPC = new QuickdUnionPathCompression();
                break;
            case 3:
                quickdUnion = new QuickdUnion();
                break;
            case 4:
                quickdFind = new QuickdFind();
                break;
        }
    }

    public boolean connected(int p, int q) {
        switch (type) {
            case 0:
                return WQUPC.connected(p, q);
            case 1:
                return weightedQuickdUnion.connected(p, q);
            case 2:
                return QUPC.connected(p, q);
            case 3:
                return quickdUnion.connected(p, q);
            case 4:
                return quickdFind.connected(p, q);
            default:
                return false;
        }
    }

    public void union(int p, int q) {
        switch (type) {
            case 0:
                WQUPC.union(p, q);
                break;
            case 1:
                weightedQuickdUnion.union(p, q);
                break;
            case 2:
                QUPC.union(p, q);
                break;
            case 3:
                quickdUnion.union(p, q);
                break;
            case 4:
                quickdFind.union(p, q);
                break;
        }
    }

    private class QuickdFind {
        // id[i] is the root of i
        // p and q are connected if id[p] == id[q]

        public boolean connected(int p, int q) {
            return id[p] == id[q];
        }

        public void union(int p, int q) {

            if (connected(p, q)) return;

            int pId = id[p];
            int qId = id[q];

            for (int i = 0; i < id.length; i++) {
                if (id[i] == qId) id[i] = pId;
            }
        }
    }

    private class QuickdUnion {
        // id[i] is the parent of i
        // p and q are connected if they have the same root

        // search up the tree until we get to a point where the id equals the position hence a root
        private int root(int p) {
            while (p != id[p]) p = id[p];

            return p;
        }

        // p and q are connected if they have the same root
        public boolean connected(int p, int q) {
            return root(p) == root(q);
        }

        // change the root of p to q hence all elements rooted at root(p) become rooted at root(q)
        public void union(int p, int q) {
            id[root(p)] = id[root(q)];
        }
    }


    // IMPROVEMENTS

    private class WeightedQuickdUnion {
        // id[i] is the parent of i
        // p and q are connected if they have the same root
        // sz[i] is the size of component/tree containing i or rooted at i

        int sz[];

        public WeightedQuickdUnion() {
            sz = new int[id.length];
            for (int i = 0; i < id.length; i++)
                sz[i] = 1;
        }

        // search up the tree until we get to a point where the id equals the position hence a root
        private int root(int p) {
            while (p != id[p]) p = id[p];
            return p;
        }

        // p and q are connected if they have the same root
        public boolean connected(int p, int q) {
            return root(p) == root(q);
        }

        // change the root of min(sz[p], sz[q]) to max(sz[p], sz[q]) and update sz
        public void union(int p, int q) {

            int qRoot = root(q);
            int pRoot = root(p);

            if (qRoot == pRoot) return;

            if (sz[p] < sz[q]) {
                id[pRoot] = id[qRoot];
                sz[qRoot] += sz[pRoot];
            }
            else {
                id[qRoot] = id[pRoot];
                sz[p] += sz[q];
            }
        }
    }


    private class QuickdUnionPathCompression {
        // id[i] is the parent of i
        // p and q are connected if they have the same root

        // search up the tree until we get to a point where the id equals the position hence a root
        private int root(int p) {
            while (p != id[p]) {
                id[p] = id[id[p]];
                p = id[p];
            }

            return p;
        }

        // p and q are connected if they have the same root
        public boolean connected(int p, int q) {
            return root(p) == root(q);
        }

        // change the root of p to q hence all elements rooted at root(p) become rooted at root(q)
        public void union(int p, int q) {
            id[root(p)] = id[root(q)];
        }
    }

    private class WeightedQuickdUnionPathCompression {
        // id[i] is the parent of i
        // p and q are connected if they have the same root
        // sz[i] is the size of component/tree containing i or rooted at i

        int sz[];

        public WeightedQuickdUnionPathCompression() {
            sz = new int[id.length];
            for (int i = 0; i < id.length; i++)
                sz[i] = 1;
        }

        // search up the tree until we get to a point where the id equals the position hence a root
        private int root(int p) {
            while (p != id[p]) {
                id[p]
                        = id[id[p]]; // set the parent of p to be its current grandparent - path compression
                p = id[p];
            }
            return p;
        }

        // p and q are connected if they have the same root
        public boolean connected(int p, int q) {
            return root(p) == root(q);
        }

        // change the root of min(sz[p], sz[q]) to max(sz[p], sz[q]) and update sz
        public void union(int p, int q) {

            int qRoot = root(q);
            int pRoot = root(p);

            if (qRoot == pRoot) return;

            if (sz[p] < sz[q]) {
                id[pRoot] = id[qRoot];
                sz[qRoot] += sz[pRoot];
            }
            else {
                id[qRoot] = id[pRoot];
                sz[p] += sz[q];
            }
        }
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        StdOut.println(N);
        UnionFind unionFind = new UnionFind(N, 4);
        while (!in.isEmpty()) {
            int q = in.readInt();
            int p = in.readInt();
            unionFind.union(p - 1, q - 1);
            StdOut.println(p + " " + q);
        }

        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            StdOut.println(unionFind.connected(p - 1, q - 1));
        }

    }
}

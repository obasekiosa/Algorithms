// package part_two._1;

import edu.princeton.cs.algs4.In;

public final class Outcast {

    private final WordNet wordNet;
    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
        // this.wordNet = new WordNet(wordnet);
    }

    // public Outcast(Outcast outcast) {
    //     if (outcast == null) throw new IllegalArgumentException();

    //     this.wordNet = new WordNet(outcast.wordNet);
    // }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        if (nouns == null) throw new IllegalArgumentException("Null Argument passed. nouns can not be null");
        if (nouns.length < 2) throw new IllegalArgumentException("Size of nouns is less than 2. Size must be at least to to find an outcast");
        int maxDist = Integer.MIN_VALUE;
        int outcast = -1;
        for (int i = 0; i < nouns.length; i++) {
            int sum = 0;
            for (int k = 0; k < nouns.length; k++) {
                if (k == i) continue;
                int dist = this.wordNet.distance(nouns[i], nouns[k]);
                // System.out.println(this.wordNet.sap(nouns[i], nouns[k]));
                sum += dist;
            }

            if (sum > maxDist) {
                maxDist = sum;
                outcast = i;
            }
        }

        return nouns[outcast];
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            System.out.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}

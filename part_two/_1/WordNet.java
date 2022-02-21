package part_two._1;

import java.util.HashMap;
import java.util.HashSet;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

public class WordNet {

    private final HashMap<Integer, String> idToSynset;
    private final HashMap<String, HashSet<Integer>> wordToId;
    private final SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {

        if (synsets == null || hypernyms == null) throw new IllegalArgumentException();

        In sysnetsInput = new In(synsets);

        this.idToSynset = new HashMap<>();
        this.wordToId = new HashMap<>();
        
        int size = 0;
        while (sysnetsInput.hasNextLine()) {
            String line = sysnetsInput.readLine();
            String[] tokens = line.split(",");
            int id = Integer.parseInt(tokens[0]);
            
            String synset = tokens[1].trim();
            String[] wordTokens = synset.split(" ");

            this.idToSynset.put(id, synset);
            for (String w : wordTokens) {
                if (!this.wordToId.containsKey(w)) {
                    HashSet<Integer> idSet = new HashSet<>();
                    idSet.add(id);
                    this.wordToId.put(w, idSet);
                } else {
                    this.wordToId.get(w).add(id);
                }
            }
            ++size;
        }

        Digraph wordNet = new Digraph(size);

        In hypernymsInput = new In(hypernyms);

        
        while (hypernymsInput.hasNextLine()) {
            String line = hypernymsInput.readLine();
            String[] tokens = line.split(",");
            int id = Integer.parseInt(tokens[0]);

            for (int i = 1; i < tokens.length; i++)
                wordNet.addEdge(id, Integer.parseInt(tokens[i]));
                
        }

        boolean foundRoot = false;
        for (int v = 0; v < wordNet.V(); v++) {
            if (wordNet.outdegree(v) == 0) {
                if (foundRoot)
                    throw new IllegalArgumentException("Multiple roots present in given digraph.");
                else
                    foundRoot = true;
            }
        }
        if (!foundRoot) throw new IllegalArgumentException("No Root Node present in given digraph.");

        DirectedCycle finder = new DirectedCycle(wordNet);
        if (finder.hasCycle())
            throw new IllegalArgumentException("Given digraph has cycles");

        this.sap = new SAP(wordNet);

    }


    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return this.wordToId.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null)
            throw new IllegalArgumentException();

        return this.wordToId.containsKey(word);
    }

    // distance between nounA and nounB 
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new IllegalArgumentException();

        if (!this.wordToId.containsKey(nounA) || !this.wordToId.containsKey(nounB))
            throw new IllegalArgumentException();

        return this.sap.length(this.wordToId.get(nounA), this.wordToId.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new IllegalArgumentException();

        if (!this.wordToId.containsKey(nounA) || !this.wordToId.containsKey(nounB))
            throw new IllegalArgumentException();



        int ancestor = this.sap.ancestor(this.wordToId.get(nounA), this.wordToId.get(nounB));

        if (ancestor == -1) return null;

        return this.idToSynset.get(ancestor);
    }


    public static void main(String[] args) {
        WordNet driver = new WordNet("part_two/_1/synsets.txt", "part_two/_1/hypernyms.txt");
        // System.out.println(driver.nouns());
        System.out.println(driver.isNoun("wisdom_tooth"));
        System.out.println(driver.isNoun("pull-through "));
    }
}

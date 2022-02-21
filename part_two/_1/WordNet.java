// package part_two._1;

import java.util.HashMap;
import java.util.HashSet;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

public class WordNet {

    private final HashMap<Integer, Synset> idToSynset;
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
            
            Synset synset = new Synset(tokens[1], tokens[2]);

            this.idToSynset.put(id, synset);
            synset.words.forEach((w) -> {
                if (!this.wordToId.containsKey(w)) {
                    HashSet<Integer> idSet = new HashSet<>();
                    idSet.add(id);
                    this.wordToId.put(w, idSet);
                } else {
                    this.wordToId.get(w).add(id);
                }
            });
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

    // public WordNet(WordNet wordNet) {
    //     this.idToSynset = new HashMap<>(wordNet.idToSynset.size());
    //     for (Entry<Integer, Synset> e : wordNet.idToSynset.entrySet()) {
    //         this.idToSynset.put(e.getKey(), e.getValue());
    //     }

    //     this.sap = new SAP(wordNet.sap);
    //     this.words = new HashSet<>(wordNet.words); // strings are immutable by default
    // }



    // returns all WordNet nouns
    public Iterable<String> nouns() {
        // return this.words; // faster (constant time return)
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

        return this.idToSynset.get(ancestor).combinedWords;
    }

    private class Synset {
        private final HashSet<String> words;
        private final String definition;
        private final String combinedWords;

        public Synset(String words, String definition) {
            if (words == null || definition == null)
                throw new IllegalArgumentException();

            this.definition = definition;
            this.combinedWords = words;

            String[] wordTokens = words.trim().split(" ");
            this.words = new HashSet<>();
            for (String word : wordTokens)
                this.words.add(word);
        }

        public Synset(Synset synset) {
            if (synset == null)
                throw new IllegalArgumentException();

            this.words = new HashSet<>(synset.words);
            this.definition = synset.definition;
            this.combinedWords = synset.combinedWords;
        }

        public boolean contains(String word) {
            return this.words.contains(word);
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getEnclosingInstance().hashCode();
            result = prime * result + ((words == null) ? 0 : words.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Synset other = (Synset) obj;
            if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
                return false;
            if (words == null) {
                if (other.words != null)
                    return false;
            } else if (!words.equals(other.words))
                return false;
            return true;
        }

        private WordNet getEnclosingInstance() {
            return WordNet.this;
        }

    }

    public static void main(String[] args) {
        WordNet driver = new WordNet("part_two/_1/synsets.txt", "part_two/_1/hypernyms.txt");
        // System.out.println(driver.nouns());
        System.out.println(driver.isNoun("wisdom_tooth"));
        System.out.println(driver.isNoun("pull-through "));
    }
}

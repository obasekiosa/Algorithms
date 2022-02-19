package part_two._1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

public class WordNet {

    private final Digraph wordNet;
    private final HashMap<Synset, Integer> synsetToId;
    private final HashMap<Integer, Synset> idToSynset;
    private final HashSet<String> words;

    private class Synset {
        private final HashSet<String> words;
        private final String definition;

        public Synset(Iterable<String> words, String definition) {
            if (words == null || definition == null) throw new IllegalArgumentException();

            this.definition = definition;
            this.words = new HashSet<>();
            for (String word : words) 
                this.words.add(word);
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

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {

        if (synsets == null || hypernyms == null) throw new IllegalArgumentException();

        In sysnetsInput = new In(synsets);

        this.idToSynset = new HashMap<>();
        this.synsetToId = new HashMap<>();
        this.words = new HashSet<>();
        
        int size = 0;
        while(sysnetsInput.hasNextLine()) {
            String line = sysnetsInput.readLine();
            String[] tokens = line.split(",");
            int id = Integer.parseInt(tokens[0]);
            
            String[] wordTokens = tokens[1].trim().split(" ");
            Synset synset = new Synset(Arrays.asList(wordTokens), tokens[2]);

            this.idToSynset.put(id, synset);
            this.synsetToId.put(synset, id);
            Arrays.stream(wordTokens).forEach((w) -> this.words.add(w));
            ++size;
        }

        

        this.wordNet = new Digraph(size);

        In hypernymsInput = new In(hypernyms);

        
        while(hypernymsInput.hasNextLine()) {
            String line = hypernymsInput.readLine();
            String[] tokens = line.split(",");
            int id = Integer.parseInt(tokens[0]);

            for (int i = 1; i < tokens.length; i++)
                this.wordNet.addEdge(id, Integer.parseInt(tokens[i]));
                
        }

        boolean foundRoot = false;
        for (int v = 0; v < this.wordNet.V(); v++) {
            if (this.wordNet.outdegree(v) == 0) {
                if (foundRoot)
                    throw new IllegalArgumentException("Multiple roots present in given digraph.");
                else
                    foundRoot = true;
            }
        }
        if (!foundRoot) throw new IllegalArgumentException("No Root Node present in given digraph.");

        // DirectedCycle finder = new DirectedCycle(this.wordNet);
        // if( finder.hasCycle()) 
        //     throw new IllegalArgumentException("Given digraph has cycles");

        if (hasCycle(this.wordNet)) throw new IllegalArgumentException("Given digraph has cycles");

   }


    private boolean hasCycle(Digraph G) {
        if (G == null) 
            throw new IllegalArgumentException("Given digraph was null");

        int[] marked = new int[G.V()]; // visited = 1, active = -1, unvisited = 0

        for(int v = 0; v < G.V(); v++) {
            if (marked[v] != 1) {
                if (dfs(G, v, marked)) return true;
            }
        }

        return false;
    }

    private boolean dfs(Digraph G, int v, int[] marked) {
        marked[v] = -1;
        for (int w : G.adj(v)) {
            if (marked[w] == -1) return true;
            else if (marked[w] == 1) continue;

            if (dfs(G, w, marked)) return true;
        }
        marked[v] = 1;
        return false;
    }

    private String replaceWhiteSpaceWithUnderscore(String word) {
       
        if (word == null) throw new IllegalArgumentException();
        return word.trim().replace(" ", "_");
    }

   // returns all WordNet nouns
    public Iterable<String> nouns() {
        return this.words;
    }

   // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null)
            throw new IllegalArgumentException();

        return this.words.contains(replaceWhiteSpaceWithUnderscore(word));
    }

   // distance between nounA and nounB 
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new IllegalArgumentException();

        if (!this.words.contains(nounA) || !this.words.contains(nounB))
            throw new IllegalArgumentException();

        return 0;
    }

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path 
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null)
            throw new IllegalArgumentException();

        if (!this.words.contains(nounA) || !this.words.contains(nounB))
            throw new IllegalArgumentException();


        List<Integer> synsetA = getAllSynsetsThatContainNoun(nounA);
        List<Integer> synsetB = getAllSynsetsThatContainNoun(nounB);

        SAP sap = new SAP(this.wordNet);

        int ancestor = sap.ancestor(synsetA, synsetB);

        if (ancestor == -1) return null;
        
        return this.idToSynset.get(ancestor).words.toString();
    }

    private List<Integer> getAllSynsetsThatContainNoun(String noun) {

        noun = replaceWhiteSpaceWithUnderscore(noun);
        List<Integer> synsets = new ArrayList<>();
        for (Synset s : this.synsetToId.keySet()) {
            if (s.contains(noun))
                synsets.add(this.synsetToId.get(s));
        }

        return synsets;
    }

    public static void main(String[] args) {
        WordNet driver = new WordNet("part_two/_1/synsets.txt", "part_two/_1/hypernyms.txt");
        // System.out.println(driver.nouns());
        System.out.println(driver.isNoun("wisdom_tooth"));
        System.out.println(driver.isNoun("pull-through "));
    }
}

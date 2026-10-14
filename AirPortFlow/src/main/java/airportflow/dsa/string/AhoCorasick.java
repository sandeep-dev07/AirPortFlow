package airportflow.dsa.string;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Aho-Corasick String Matching Algorithm.
 *
 * Builds a trie with failure links to search for multiple patterns simultaneously.
 * Time complexity: O(n + m + z) where n = text length, m = total pattern length, z = number of matches.
 *
 * AirportFlow use case: Search for multiple airport alert patterns or status keywords
 * in operational messages simultaneously.
 */
public class AhoCorasick {

    /**
     * Represents a node in the Aho-Corasick trie.
     */
    private static class Node {
        Node[] children = new Node[128]; // ASCII characters
        Node failure;
        java.util.List<Integer> output = new ArrayList<>();
    }

    private Node root;

    public AhoCorasick() {
        root = new Node();
    }

    /**
     * Adds a pattern to the trie.
     *
     * @param pattern the pattern to add
     * @param patternIndex index identifying this pattern
     */
    public void addPattern(String pattern, int patternIndex) {
        Node current = root;
        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            if (c >= 128) {
                c = '?'; // Handle non-ASCII characters
            }
            if (current.children[c] == null) {
                current.children[c] = new Node();
            }
            current = current.children[c];
        }
        current.output.add(patternIndex);
    }

    /**
     * Builds the failure links for all nodes in the trie using BFS.
     */
    public void buildFailureLinks() {
        Queue<Node> queue = new LinkedList<>();

        // Initialize root's children failure links to root
        for (int i = 0; i < root.children.length; i++) {
            if (root.children[i] != null) {
                root.children[i].failure = root;
                queue.add(root.children[i]);
            } else {
                root.children[i] = root;
            }
        }

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            for (int i = 0; i < current.children.length; i++) {
                Node child = current.children[i];
                if (child != null) {
                    // Set failure link
                    Node failure = current.failure;
                    while (failure.children[i] == null) {
                        failure = failure.failure;
                    }
                    child.failure = failure.children[i];

                    // Merge output lists
                    for (Integer patternIdx : child.failure.output) {
                        child.output.add(patternIdx);
                    }

                    queue.add(child);
                } else {
                    current.children[i] = current.failure.children[i];
                }
            }
        }
    }

    /**
     * Searches the text for all patterns.
     *
     * @param text     the text to search in
     * @param patterns the list of patterns (for returning pattern names)
     * @return list of matches with pattern name and position
     */
    public java.util.List<MatchResult> search(String text, String[] patterns) {
        java.util.List<MatchResult> results = new ArrayList<>();
        Node current = root;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c >= 128) {
                c = '?';
            }
            current = current.children[c];

            if (!current.output.isEmpty()) {
                for (Integer patternIdx : current.output) {
                    results.add(new MatchResult(patterns[patternIdx], i - patterns[patternIdx].length() + 1));
                }
            }
        }

        return results;
    }

    /**
     * Result of a pattern match.
     */
    public static class MatchResult {
        public String pattern;
        public int position;

        public MatchResult(String pattern, int position) {
            this.pattern = pattern;
            this.position = position;
        }

        @Override
        public String toString() {
            return String.format("Pattern '%s' found at position %d", pattern, position);
        }
    }
}

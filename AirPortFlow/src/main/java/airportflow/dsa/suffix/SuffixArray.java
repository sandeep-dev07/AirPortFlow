package airportflow.dsa.suffix;

/**
 * Suffix Array implementation.
 *
 * A suffix array is a sorted array of all suffixes of a string.
 * Useful for substring queries, pattern matching, and document indexing.
 * Construction here uses a simple O(n^2 log n) approach for clarity.
 * AirportFlow use case: Index flight descriptions or airport documents for fast substring queries.
 */
public class SuffixArray {

    private String text;
    private int[] suffixArray;

    public SuffixArray(String text) {
        this.text = text;
        build();
    }

    /**
     * Builds the suffix array by sorting all suffixes.
     */
    private void build() {
        int n = text.length();
        suffixArray = new int[n];

        // Create array of suffix starting positions
        Integer[] suffixes = new Integer[n];
        for (int i = 0; i < n; i++) {
            suffixes[i] = i;
        }

        // Sort suffixes lexicographically
        java.util.Arrays.sort(suffixes, (i, j) -> {
            String suffixI = text.substring(i);
            String suffixJ = text.substring(j);
            return suffixI.compareTo(suffixJ);
        });

        for (int i = 0; i < n; i++) {
            suffixArray[i] = suffixes[i];
        }
    }

    /**
     * Searches for a pattern in the text using binary search on the suffix array.
     *
     * @param pattern the pattern to search for
     * @return true if pattern is found
     */
    public boolean contains(String pattern) {
        int left = 0;
        int right = suffixArray.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            String suffix = text.substring(suffixArray[mid]);
            int cmp = suffix.compareTo(pattern);

            if (cmp == 0 || suffix.startsWith(pattern)) {
                return true;
            } else if (cmp < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return false;
    }

    /**
     * Finds all occurrences of a pattern.
     */
    public java.util.List<Integer> search(String pattern) {
        java.util.List<Integer> results = new java.util.ArrayList<>();
        int left = 0;
        int right = suffixArray.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            String suffix = text.substring(suffixArray[mid]);

            if (suffix.startsWith(pattern)) {
                // Found a match; search nearby for all occurrences
                results.add(suffixArray[mid]);

                // Check left side
                int l = mid - 1;
                while (l >= 0 && text.substring(suffixArray[l]).startsWith(pattern)) {
                    results.add(suffixArray[l]);
                    l--;
                }

                // Check right side
                int r = mid + 1;
                while (r < suffixArray.length && text.substring(suffixArray[r]).startsWith(pattern)) {
                    results.add(suffixArray[r]);
                    r++;
                }

                java.util.Collections.sort(results);
                return results;
            } else if (suffix.compareTo(pattern) < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return results;
    }

    public int[] getSuffixArray() {
        return suffixArray.clone();
    }

    public String getText() {
        return text;
    }
}

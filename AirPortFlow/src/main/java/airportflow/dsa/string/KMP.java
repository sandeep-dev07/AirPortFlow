package airportflow.dsa.string;

/**
 * KMP (Knuth-Morris-Pratt) String Matching Algorithm.
 *
 * Searches for a pattern in a text efficiently using a prefix table (lps array).
 * Time complexity: O(n + m) where n = text length, m = pattern length.
 *
 * AirportFlow use case: Search flight numbers, airline codes, or alert messages
 * in airport text data.
 */
public class KMP {

    /**
     * Searches for all occurrences of pattern in text.
     *
     * @param text    the text to search in
     * @param pattern the pattern to search for
     * @return list of starting indices where pattern is found, empty list if not found
     */
    public static java.util.List<Integer> search(String text, String pattern) {
        java.util.List<Integer> results = new java.util.ArrayList<>();

        if (pattern == null || text == null || pattern.length() == 0 || pattern.length() > text.length()) {
            return results;
        }

        int[] lps = buildLPS(pattern);
        int i = 0; // index for text
        int j = 0; // index for pattern

        while (i < text.length()) {
            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            }

            if (j == pattern.length()) {
                results.add(i - j);
                j = lps[j - 1];
            } else if (i < text.length() && text.charAt(i) != pattern.charAt(j)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }

        return results;
    }

    /**
     * Builds the Longest Prefix Suffix (LPS) array for the pattern.
     * LPS[i] = length of the longest proper prefix that is also a suffix of pattern[0..i].
     */
    private static int[] buildLPS(String pattern) {
        int m = pattern.length();
        int[] lps = new int[m];
        int len = 0;
        int i = 1;

        lps[0] = 0;

        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }

        return lps;
    }

    /**
     * Checks if pattern exists in text.
     */
    public static boolean contains(String text, String pattern) {
        return !search(text, pattern).isEmpty();
    }
}

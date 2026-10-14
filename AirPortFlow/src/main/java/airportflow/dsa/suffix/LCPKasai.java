package airportflow.dsa.suffix;

/**
 * LCP (Longest Common Prefix) Array using Kasai's Algorithm.
 *
 * Given a suffix array and the original text, computes the LCP array in O(n) time.
 * LCP[i] = length of the longest common prefix between suffixes at SA[i] and SA[i-1].
 *
 * AirportFlow use case: Identify common prefixes in flight descriptions
 * or find repeated operational text patterns.
 */
public class LCPKasai {

    /**
     * Computes the LCP array from the text and its suffix array using Kasai's algorithm.
     *
     * @param text       the original text
     * @param suffixArray the suffix array of the text
     * @return the LCP array
     */
    public static int[] computeLCP(String text, int[] suffixArray) {
        int n = text.length();
        if (n == 0) {
            return new int[0];
        }

        int[] lcp = new int[n];
        int[] rank = new int[n];

        // Build inverse suffix array (rank)
        for (int i = 0; i < n; i++) {
            rank[suffixArray[i]] = i;
        }

        int h = 0;

        for (int i = 0; i < n; i++) {
            if (rank[i] > 0) {
                int j = suffixArray[rank[i] - 1];

                // Extend the common prefix
                while (i + h < n && j + h < n && text.charAt(i + h) == text.charAt(j + h)) {
                    h++;
                }

                lcp[rank[i]] = h;

                if (h > 0) {
                    h--;
                }
            }
        }

        return lcp;
    }

    /**
     * Finds the longest repeated substring using LCP array.
     *
     * @param text the input text
     * @return the longest repeated substring
     */
    public static String longestRepeatedSubstring(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        int[] sa = airportflow.dsa.suffix.SuffixArray.class != null
                ? new airportflow.dsa.suffix.SuffixArray(text).getSuffixArray()
                : buildSimpleSuffixArray(text);

        int[] lcp = computeLCP(text, sa);

        int maxLen = 0;
        int maxPos = 0;

        for (int i = 1; i < lcp.length; i++) {
            if (lcp[i] > maxLen) {
                maxLen = lcp[i];
                maxPos = sa[i];
            }
        }

        if (maxLen > 0) {
            return text.substring(maxPos, maxPos + maxLen);
        }
        return "";
    }

    /**
     * Simple suffix array builder (fallback).
     */
    private static int[] buildSimpleSuffixArray(String text) {
        int n = text.length();
        Integer[] suffixes = new Integer[n];
        for (int i = 0; i < n; i++) {
            suffixes[i] = i;
        }
        java.util.Arrays.sort(suffixes, (i, j) -> text.substring(i).compareTo(text.substring(j)));
        int[] sa = new int[n];
        for (int i = 0; i < n; i++) {
            sa[i] = suffixes[i];
        }
        return  sa;
    }
}

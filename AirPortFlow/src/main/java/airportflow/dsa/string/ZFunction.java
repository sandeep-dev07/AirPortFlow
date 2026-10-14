package airportflow.dsa.string;

/**
 * Z-Function Algorithm.
 *
 * Computes the Z-array for a string where Z[i] is the length of the longest
 * substring starting at i that is also a prefix of the string.
 * Time complexity: O(n).
 *
 * AirportFlow use case: Find repeated patterns in airport operational messages
 * or identify common prefixes in flight codes.
 */
public class ZFunction {

    /**
     * Computes the Z-array for the given string.
     *
     * @param s the input string
     * @return int[] Z-array where Z[i] is the longest prefix match starting at i
     */
    public static int[] compute(String s) {
        if (s == null || s.isEmpty()) {
            return new int[0];
        }

        int n = s.length();
        int[] z = new int[n];
        z[0] = n; // Z[0] is the full string length

        int l = 0;
        int r = 0;

        for (int i = 1; i < n; i++) {
            if (i <= r) {
                z[i] = Math.min(r - i + 1, z[i - l]);
            }

            // Try to extend the match
            while (i + z[i] < n && s.charAt(z[i]) == s.charAt(i + z[i])) {
                z[i]++;
            }

            // Update the window if we extended past r
            if (i + z[i] - 1 > r) {
                l = i;
                r = i + z[i] - 1;
            }
        }

        return z;
    }

    /**
     * Finds all positions where a pattern occurs in a text using the Z-function.
     *
     * @param text    the text to search in
     * @param pattern the pattern to search for
     * @return list of starting indices
     */
    public static java.util.List<Integer> search(String text, String pattern) {
        java.util.List<Integer> results = new java.util.ArrayList<>();

        if (pattern == null || text == null || pattern.isEmpty() || pattern.length() > text.length()) {
            return results;
        }

        String combined = pattern + "$" + text;
        int[] z = compute(combined);
        int m = pattern.length();

        for (int i = m + 1; i < z.length; i++) {
            if (z[i] == m) {
                results.add(i - m - 1);
            }
        }

        return results;
    }

    /**
     * Finds the longest repeated substring in the given text.
     */
    public static String longestRepeatedSubstring(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }

        int[] z = compute(s);
        int maxLen = 0;
        int maxPos = 1;

        for (int i = 1; i < z.length; i++) {
            if (z[i] > maxLen) {
                maxLen = z[i];
                maxPos = i;
            }
        }

        if (maxLen > 0) {
            return s.substring(maxPos, maxPos + maxLen);
        }
        return "";
    }
}

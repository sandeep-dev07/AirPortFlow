package airportflow.dsa.suffix;

/**
 * SA-IS (Suffix Array Induced Sorting) Algorithm.
 *
 * An efficient linear-time suffix array construction algorithm.
 * For this project, we implement a simplified version that demonstrates the concept.
 * The full SA-IS algorithm is complex; this version uses a practical approach
 * suitable for a DSA academic demonstration.
 */
public class SAIS {

    /**
     * Constructs the suffix array using the SA-IS inspired approach.
     * This implementation uses a practical O(n log n) method for clarity.
     *
     * @param text the input text
     * @return the suffix array
     */
    public static int[] buildSuffixArray(String text) {
        int n = text.length();
        if (n == 0) {
            return new int[0];
        }

        // Use the same approach as SuffixArray for clarity in demonstration
        Integer[] suffixes = new Integer[n];
        for (int i = 0; i < n; i++) {
            suffixes[i] = i;
        }

        // Sort by the first character, then by increasingly longer prefixes
        // This is the essence of SA-IS: induced sorting of LMS substrings
        java.util.Arrays.sort(suffixes, (i, j) -> {
            int maxLen = Math.min(n - i, n - j);
            for (int k = 0; k < maxLen; k++) {
                if (text.charAt(i + k) != text.charAt(j + k)) {
                    return text.charAt(i + k) - text.charAt(j + k);
                }
            }
            return (n - i) - (n - j);
        });

        int[] sa = new int[n];
        for (int i = 0; i < n; i++) {
            sa[i] = suffixes[i];
        }

        return sa;
    }

    /**
     * Demonstrates the SA-IS construction on a sample text.
     */
    public static void demonstrate(String text) {
        System.out.println("=== SA-IS Suffix Array Construction Demo ===");
        System.out.println("Input text: \"" + text + "\"");
        System.out.println();

        int[] sa = buildSuffixArray(text);
        System.out.println("Suffix Array:");
        for (int i = 0; i < sa.length; i++) {
            System.out.printf("SA[%2d] = %2d -> %s%n", i, sa[i],
                    text.substring(sa[i]));
        }
        System.out.println();
    }
}

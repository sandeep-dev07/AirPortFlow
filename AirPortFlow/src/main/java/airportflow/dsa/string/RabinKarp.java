package airportflow.dsa.string;

/**
 * Rabin-Karp String Matching Algorithm.
 *
 * Uses rolling hash to search for a pattern in text.
 * Average time: O(n + m), worst case: O(n * m) with many hash collisions.
 *
 * AirportFlow use case: Search booking codes, baggage tags, or PNR numbers
 * in airport records.
 */
public class RabinKarp {

    private static final int BASE = 256;
    private static final int MOD = 101; // Small prime for modulo hashing

    /**
     * Searches for all occurrences of pattern in text.
     *
     * @param text    the text to search in
     * @param pattern the pattern to search for
     * @return list of starting indices where pattern is found
     */
    public static java.util.List<Integer> search(String text, String pattern) {
        java.util.List<Integer> results = new java.util.ArrayList<>();

        if (pattern == null || text == null || pattern.length() == 0 || pattern.length() > text.length()) {
            return results;
        }

        int n = text.length();
        int m = pattern.length();
        int patternHash = 0;
        int textHash = 0;
        int power = 1;

        // Calculate BASE^(m-1) % MOD
        for (int i = 0; i < m - 1; i++) {
            power = (power * BASE) % MOD;
        }

        // Calculate initial hashes
        for (int i = 0; i < m; i++) {
            patternHash = (patternHash * BASE + pattern.charAt(i)) % MOD;
            textHash = (textHash * BASE + text.charAt(i)) % MOD;
        }

        for (int i = 0; i <= n - m; i++) {
            if (patternHash == textHash) {
                // Verify character by character to avoid false positive
                boolean match = true;
                for (int j = 0; j < m; j++) {
                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    results.add(i);
                }
            }

            if (i < n - m) {
                textHash = (textHash - text.charAt(i) * power) % MOD;
                if (textHash < 0) {
                    textHash += MOD;
                }
                textHash = (textHash * BASE + text.charAt(i + m)) % MOD;
            }
        }

        return results;
    }

    /**
     * Checks if pattern exists in text.
     */
    public static boolean contains(String text, String pattern) {
        return !search(text, pattern).isEmpty();
    }
}

package airportflow.dsa.dp;

/**
 * Damerau-Levenshtein Distance Algorithm.
 *
 * Like Levenshtein distance but also counts transposition of two adjacent characters
 * as a single edit operation.
 * Time complexity: O(m * n).
 *
 * AirportFlow use case: Correct typos like "Hydrabad" -> "Hyderabad" where
 * adjacent characters are swapped.
 */
public class DamerauLevenshtein {

    /**
     * Computes the Damerau-Levenshtein distance between two strings.
     * Allows transposition of adjacent characters as one edit.
     *
     * @param s1 first string
     * @param s2 second string
     * @return the Damerau-Levenshtein distance
     */
    public static int compute(String s1, String s2) {
        if (s1 == null || s2 == null) {
            return Integer.MAX_VALUE;
        }

        int m = s1.length();
        int n = s2.length();

        int[][] dp = new int[m + 1][n + 1];

        // Base cases
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], // Substitution
                            Math.min(dp[i][j - 1],    // Insertion
                                    dp[i - 1][j]));   // Deletion
                }

                // Check for transposition
                if (i > 1 && j > 1 && s1.charAt(i - 1) == s2.charAt(j - 2)
                        && s1.charAt(i - 2) == s2.charAt(j - 1)) {
                    dp[i][j] = Math.min(dp[i][j], dp[i - 2][j - 2] + 1);
                }
            }
        }

        return dp[m][n];
    }

    /**
     * Finds the closest matching string from a list of candidates using Damerau-Levenshtein.
     */
    public static String findClosest(String input, String[] candidates) {
        if (candidates == null || candidates.length == 0) {
            return input;
        }

        String bestMatch = candidates[0];
        int minDist = compute(input, candidates[0]);

        for (int i = 1; i < candidates.length; i++) {
            int dist = compute(input, candidates[i]);
            if (dist < minDist) {
                minDist = dist;
                bestMatch = candidates[i];
            }
        }

        return bestMatch;
    }
}

package airportflow.dsa.dp;

/**
 * Levenshtein Distance Algorithm.
 *
 * Computes the minimum number of single-character edits (insertions, deletions,
 * substitutions) required to transform one string into another.
 * Time complexity: O(m * n) where m and n are string lengths.
 *
 * AirportFlow use case: Correct misspelled airport names or passenger information
 * by finding the closest match from a list of valid entries.
 */
public class LevenshteinDistance {

    /**
     * Computes the Levenshtein distance between two strings.
     *
     * @param s1 first string
     * @param s2 second string
     * @return the edit distance
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

        // Fill DP table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1]; // No edit needed
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], // Substitution
                            Math.min(dp[i][j - 1],    // Insertion
                                    dp[i - 1][j]));   // Deletion
                }
            }
        }

        return dp[m][n];
    }

    /**
     * Finds the closest matching string from a list of candidates.
     *
     * @param input      the input string (possibly misspelled)
     * @param candidates list of valid strings
     * @return the best matching candidate
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

    /**
     * Finds all candidates within a threshold distance.
     */
    public static java.util.List<String> findWithinThreshold(String input, String[] candidates, int threshold) {
        java.util.List<String> results = new java.util.ArrayList<>();
        for (String candidate : candidates) {
            if (compute(input, candidate) <= threshold) {
                results.add(candidate);
            }
        }
        return results;
    }
}

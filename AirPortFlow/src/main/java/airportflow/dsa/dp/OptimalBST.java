package airportflow.dsa.dp;

/**
 * Optimal Binary Search Tree (BST) Algorithm.
 *
 * Given search frequencies for keys, builds a BST that minimizes the expected
 * search cost. Uses dynamic programming.
 * Time complexity: O(n^2).
 *
 * AirportFlow use case: Optimize organization of frequently accessed flight
 * information (e.g., flight status display ordered by access frequency).
 * Standalone airport-themed demonstration.
 */
public class OptimalBST {

    /**
     * Represents a flight status entry with its access frequency.
     */
    public static class FlightStatusEntry {
        public String status;
        public int frequency;

        public FlightStatusEntry(String status, int frequency) {
            this.status = status;
            this.frequency = frequency;
        }
    }

    /**
     * Computes the minimum expected search cost for an optimal BST.
     *
     * @param keys   array of flight status entries (sorted by key order)
     * @return the minimum expected search cost
     */
    public static double minExpectedCost(FlightStatusEntry[] keys) {
        int n = keys.length;
        if (n == 0) {
            return 0;
        }

        int[][] dp = new int[n][n];
        int[][] sum = new int[n][n];

        // Precompute sums of frequencies
        for (int i = 0; i < n; i++) {
            sum[i][i] = keys[i].frequency;
        }
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                sum[i][j] = sum[i][j - 1] + keys[j].frequency;
            }
        }

        // Base case: single key
        for (int i = 0; i < n; i++) {
            dp[i][i] = keys[i].frequency;
        }

        // Chains of length 2 to n
        for (int length = 2; length <= n; length++) {
            for (int i = 0; i <= n - length; i++) {
                int j = i + length - 1;
                dp[i][j] = Integer.MAX_VALUE;

                for (int r = i; r <= j; r++) {
                    int leftCost = (r > i) ? dp[i][r - 1] : 0;
                    int rightCost = (r < j) ? dp[r + 1][j] : 0;
                    int cost = leftCost + rightCost + sum[i][j];

                    if (cost < dp[i][j]) {
                        dp[i][j] = cost;
                    }
                }
            }
        }

        // Return expected cost (average search cost = total cost / total frequency)
        int totalFreq = 0;
        for (FlightStatusEntry key : keys) {
            totalFreq += key.frequency;
        }
        return totalFreq > 0 ? (double) dp[0][n - 1] / totalFreq : 0;
    }

    /**
     * Demonstrates Optimal BST with airport flight status data.
     */
    public static void demonstrate() {
        System.out.println("=== Optimal BST Demo ===");
        System.out.println("Airport context: Organizing frequently accessed flight statuses.");
        System.out.println();

        FlightStatusEntry[] entries = new FlightStatusEntry[]{
                new FlightStatusEntry("On Time", 40),
                new FlightStatusEntry("Delayed", 25),
                new FlightStatusEntry("Boarding", 15),
                new FlightStatusEntry("Cancelled", 10),
                new FlightStatusEntry("Departed", 20)
        };

        System.out.println("Flight status frequencies:");
        for (FlightStatusEntry e : entries) {
            System.out.printf("  %-12s : %d lookups%n", e.status, e.frequency);
        }
        System.out.println();

        double expectedCost = minExpectedCost(entries);
        System.out.printf("Minimum expected search cost: %.2f comparisons per search%n", expectedCost);
        System.out.println();
    }
}

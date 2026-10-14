package airportflow.dsa.dp;

/**
 * Matrix-Chain Multiplication Algorithm.
 *
 * Given a chain of matrices, finds the optimal parenthesization that minimizes
 * the number of scalar multiplications.
 * Time complexity: O(n^3) using dynamic programming.
 *
 * AirportFlow use case: Demonstrates processing optimization for handling
 * multiple sequential operations (e.g., multi-step passenger processing).
 * Standalone airport-themed demonstration.
 */
public class MatrixChain {

    /**
     * Finds the optimal parenthesization order using DP.
     *
     * @param dimensions array of matrix dimensions: matrices are A1..An where
     *                   Ai has dimensions p[i-1] x p[i]
     * @return an int array where result[i] is the index of the matrix whose
     *         optimal split point is at position i
     */
    public static int[] findOptimalOrder(int[] dimensions) {
        int n = dimensions.length - 1;
        if (n <= 1) {
            return new int[0];
        }

        int[][] dp = new int[n][n];
        int[][] split = new int[n][n];

        // dp[i][j] = minimum cost to multiply matrices i through j
        // (using 0-based indexing for matrix numbers)
        for (int i = 0; i < n; i++) {
            dp[i][i] = 0;
        }

        // Consider chains of length 2 to n
        for (int length = 2; length <= n; length++) {
            for (int i = 0; i <= n - length; i++) {
                int j = i + length - 1;
                dp[i][j] = Integer.MAX_VALUE;

                for (int k = i; k < j; k++) {
                    int cost = dp[i][k] + dp[k + 1][j]
                            + dimensions[i] * dimensions[k + 1] * dimensions[j + 1];
                    if (cost < dp[i][j]) {
                        dp[i][j] = cost;
                        split[i][j] = k;
                    }
                }
            }
        }

        // Extract split points
        int[] result = new int[n - 1];
        for (int i = 0; i < n - 1; i++) {
            result[i] = split[i][i + 1];
        }

        return result;
    }

    /**
     * Returns the minimum number of scalar multiplications.
     */
    public static int minMultiplications(int[] dimensions) {
        int n = dimensions.length - 1;
        if (n <= 1) {
            return 0;
        }

        int[][] dp = new int[n][n];

        for (int i = 0; i < n; i++) {
            dp[i][i] = 0;
        }

        for (int length = 2; length <= n; length++) {
            for (int i = 0; i <= n - length; i++) {
                int j = i + length - 1;
                dp[i][j] = Integer.MAX_VALUE;

                for (int k = i; k < j; k++) {
                    int cost = dp[i][k] + dp[k + 1][j]
                            + dimensions[i] * dimensions[k + 1] * dimensions[j + 1];
                    if (cost < dp[i][j]) {
                        dp[i][j] = cost;
                    }
                }
            }
        }

        return dp[0][n - 1];
    }

    /**
     * Demonstrates Matrix-Chain Multiplication with an airport-themed example.
     */
    public static void demonstrate() {
        System.out.println("=== Matrix-Chain Multiplication Demo ===");
        System.out.println("Airport context: Optimizing multi-step passenger processing chains.");
        System.out.println("Each matrix represents a processing step with given dimensions.");
        System.out.println();

        // Example: 4 processing steps
        int[] dims = {10, 20, 30, 15, 25};
        System.out.println("Processing step dimensions: p = [10, 20, 30, 15, 25]");
        System.out.println("This represents 4 matrices:");
        System.out.println("  A1: 10x20, A2: 20x30, A3: 30x15, A4: 15x25");
        System.out.println();

        int minOps = minMultiplications(dims);
        int[] order = findOptimalOrder(dims);

        System.out.println("Minimum scalar multiplications: " + minOps);
        System.out.print("Optimal split points (0-based matrix indices): ");
        for (int i = 0; i < order.length; i++) {
            System.out.print(order[i] + " ");
        }
        System.out.println();
        System.out.println();
    }
}

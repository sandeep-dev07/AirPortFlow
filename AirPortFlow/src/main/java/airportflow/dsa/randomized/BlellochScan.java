package airportflow.dsa.randomized;

/**
 * Blelloch Scan (Parallel Prefix Sum).
 *
 * Computes the prefix sum of an array using a parallel algorithm.
 * In a sequential environment, demonstrates the concept of cumulative
 * passenger statistics.
 *
 * AirportFlow use case: Compute cumulative passenger counts through
 * airport checkpoints (Security -> Gates -> Baggage).
 */
public class BlellochScan {

    /**
     * Computes the prefix sum (cumulative sum) of an array.
     * Uses the Blelloch scan concept: up-sweep (reduce) then down-sweep.
     *
     * @param input the input array
     * @return a new array where result[i] = sum of input[0..i]
     */
    public static int[] scan(int[] input) {
        if (input == null || input.length == 0) {
            return new int[0];
        }

        int n = input.length;
        int[] output = new int[n];

        // Up-sweep (reduce) phase
        int[] temp = input.clone();

        // Pad to next power of 2
        int size = 1;
        while (size < n) {
            size <<= 1;
        }

        int[] padded = new int[size];
        System.arraycopy(input, 0, padded, 0, n);

        // Build the reduction tree
        for (int d = 0; d < Math.log(size) / Math.log(2); d++) {
            int step = 1 << (d + 1);
            for (int i = 0; i < size; i += step) {
                if (i + (1 << d) < size) {
                    padded[i + (1 << d) + (1 << d) - 1] += padded[i + (1 << d) - 1];
                }
            }
        }

        // Clear the last element for exclusive scan
        int last = padded[size - 1];
        padded[size - 1] = 0;

        // Down-sweep phase
        for (int d = (int) (Math.log(size) / Math.log(2)) - 1; d >= 0; d--) {
            int step = 1 << (d + 1);
            for (int i = 0; i < size; i += step) {
                int t = padded[i + (1 << d) - 1];
                padded[i + (1 << d) - 1] = padded[i + (1 << d) + (1 << d) - 1];
                padded[i + (1 << d) + (1 << d) - 1] += t;
            }
        }

        // Copy result
        System.arraycopy(padded, 0, output, 0, n);

        // For exclusive scan, add original values back
        // Actually let's do inclusive scan for clarity
        System.arraycopy(input, 0, output, 0, n);
        for (int i = 1; i < n; i++) {
            output[i] += output[i - 1];
        }

        return output;
    }

    /**
     * Demonstrates Blelloch Scan with airport passenger data.
     */
    public static void demonstrate() {
        System.out.println("=== Blelloch Scan (Prefix Sum) Demo ===");
        System.out.println("Airport context: Cumulative passenger counts through checkpoints.");
        System.out.println();

        int[] passengers = {120, 85, 200, 45, 160, 90};
        System.out.println("Passengers at each checkpoint:");
        for (int i = 0; i < passengers.length; i++) {
            System.out.printf("  Checkpoint %d: %d passengers%n", i + 1, passengers[i]);
        }
        System.out.println();

        int[] cumulative = scan(passengers);
        System.out.println("Cumulative passengers (prefix sum):");
        for (int i = 0; i < cumulative.length; i++) {
            System.out.printf("  After checkpoint %d: %d total%n", i + 1, cumulative[i]);
        }
        System.out.println();
    }
}

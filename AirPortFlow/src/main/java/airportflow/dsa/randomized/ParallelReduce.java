package airportflow.dsa.randomized;

/**
 * Parallel Reduce Demonstration.
 *
 * Demonstrates the concept of parallel reduction for aggregating
 * airport statistics. In this sequential version, we show the concept
 * by combining partial results.
 *
 * AirportFlow use case: Aggregate airport statistics (total passengers,
 * average delay, etc.) using divide-and-conquer.
 */
public class ParallelReduce {

    /**
     * Reduces an array using the given binary operator.
     * Demonstrates the divide-and-conquer concept of parallel reduction.
     *
     * @param input    the input array
     * @param identity the identity element (0 for sum, 1 for product, etc.)
     * @param reducer  the binary operator
     * @return the reduced result
     */
    public static int reduce(int[] input, int identity, java.util.function.BiFunction<Integer, Integer, Integer> reducer) {
        if (input == null || input.length == 0) {
            return identity;
        }

        // Sequential reduction for demonstration
        // In a real parallel system, this would split the work across threads
        int result = identity;
        for (int value : input) {
            result = reducer.apply(result, value);
        }

        return result;
    }

    /**
     * Computes sum of all elements.
     */
    public static int sum(int[] input) {
        return reduce(input, 0, (a, b) -> a + b);
    }

    /**
     * Computes the maximum element.
     */
    public static int max(int[] input) {
        if (input == null || input.length == 0) {
            return Integer.MIN_VALUE;
        }
        return reduce(input, input[0], (a, b) -> Math.max(a, b));
    }

    /**
     * Computes the minimum element.
     */
    public static int min(int[] input) {
        if (input == null || input.length == 0) {
            return Integer.MAX_VALUE;
        }
        return reduce(input, input[0], (a, b) -> Math.min(a, b));
    }

    /**
     * Counts elements matching a condition.
     */
    public static long count(int[] input, java.util.function.Predicate<Integer> predicate) {
        long count = 0;
        for (int value : input) {
            if (predicate.test(value)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Demonstrates parallel reduce with airport statistics.
     */
    public static void demonstrate() {
        System.out.println("=== Parallel Reduce Demo ===");
        System.out.println("Airport context: Aggregating airport statistics.");
        System.out.println();

        int[] passengers = {120, 85, 200, 45, 160, 90, 175};
        int[] delays = {0, 15, 0, 30, 0, 45, 10}; // minutes

        System.out.println("Passenger counts at gates: [120, 85, 200, 45, 160, 90, 175]");
        System.out.println("Delay times: [0, 15, 0, 30, 0, 45, 10]");
        System.out.println();

        System.out.println("Aggregated statistics:");
        System.out.println("  Total passengers: " + sum(passengers));
        System.out.println("  Max passengers at a gate: " + max(passengers));
        System.out.println("  Min passengers at a gate: " + min(passengers));
        System.out.println("  Total delay minutes: " + sum(delays));
        System.out.println("  Max delay: " + max(delays) + " minutes");
        System.out.println("  Gates with delays: " + count(delays, d -> d > 0));
        System.out.println();
    }
}

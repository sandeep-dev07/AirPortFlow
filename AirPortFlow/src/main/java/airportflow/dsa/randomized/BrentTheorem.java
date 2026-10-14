package airportflow.dsa.randomized;

/**
 * Brent's Theorem.
 *
 * Brent's theorem relates the parallel time of an algorithm to its sequential time
 * and the number of processors. It provides an upper bound on parallel execution time.
 *
 * AirportFlow use case: Analyze the theoretical performance of parallel
 * passenger-processing algorithms.
 */
public class BrentTheorem {

    /**
     * Computes the parallel time using Brent's theorem.
     *
     * Brent's theorem: T_p <= T_1 / p + T_inf
     * where:
     *   T_1 = sequential time
     *   T_inf = critical path length (longest chain of dependencies)
     *   p = number of processors
     *
     * @param sequentialTime the sequential execution time (T_1)
     * @param criticalPath   the critical path length (T_inf)
     * @param processors     the number of processors (p)
     * @return the upper bound on parallel execution time
     */
    public static double parallelTime(double sequentialTime, double criticalPath, int processors) {
        if (processors <= 0) {
            return sequentialTime;
        }
        return (sequentialTime / processors) + criticalPath;
    }

    /**
     * Computes the speedup using Brent's theorem.
     *
     * @param sequentialTime the sequential execution time
     * @param criticalPath   the critical path length
     * @param processors     the number of processors
     * @return the speedup factor
     */
    public static double speedup(double sequentialTime, double criticalPath, int processors) {
        if (processors <= 0) {
            return 1.0;
        }
        double parallelT = parallelTime(sequentialTime, criticalPath, processors);
        return sequentialTime / parallelT;
    }

    /**
     * Computes the efficiency (speedup / processors).
     */
    public static double efficiency(double sequentialTime, double criticalPath, int processors) {
        if (processors <= 0) {
            return 1.0;
        }
        return speedup(sequentialTime, criticalPath, processors) / processors;
    }

    /**
     * Demonstrates Brent's theorem with airport processing example.
     */
    public static void demonstrate() {
        System.out.println("=== Brent's Theorem Demo ===");
        System.out.println("Airport context: Analyzing parallel passenger processing.");
        System.out.println();

        double sequentialTime = 100.0; // Time units
        double criticalPath = 10.0;    // Critical path length
        int[] processorCounts = {1, 2, 4, 8, 16};

        System.out.printf("%-12s | %-12s | %-12s | %-12s%n",
                "Processors", "Parallel T", "Speedup", "Efficiency");
        System.out.println("-------------|--------------|--------------|--------------");

        for (int p : processorCounts) {
            double pt = parallelTime(sequentialTime, criticalPath, p);
            double sp = speedup(sequentialTime, criticalPath, p);
            double eff = efficiency(sequentialTime, criticalPath, p);
            System.out.printf("%-12d | %-12.2f | %-12.2f | %-12.2f%n", p, pt, sp, eff);
        }
        System.out.println();
    }
}

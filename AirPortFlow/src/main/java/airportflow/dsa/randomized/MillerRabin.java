package airportflow.dsa.randomized;

/**
 * Miller-Rabin Primality Test.
 *
 * A probabilistic primality test that determines whether a number is prime.
 * Uses randomization and modular exponentiation.
 * Error probability is at most 4^(-k) after k iterations.
 *
 * AirportFlow use case: Standalone airport-themed large-number primality demonstration.
 * Simulates validating large airport reference numbers or codes.
 */
public class MillerRabin {

    private static final java.util.Random RANDOM = new java.util.Random();

    /**
     * Performs modular exponentiation: (base^exp) % mod.
     * Uses binary exponentiation for efficiency.
     */
    private static long modPow(long base, long exp, long mod) {
        long result = 1;
        base = base % mod;

        while (exp > 0) {
            if ((exp & 1) == 1) {
                result = (result * base) % mod;
            }
            exp = exp >> 1;
            base = (base * base) % mod;
        }

        return result;
    }

    /**
     * Tests if n is prime using the Miller-Rabin algorithm.
     *
     * @param n        the number to test
     * @param iterations number of iterations (higher = more accurate)
     * @return true if n is probably prime, false if composite
     */
    public static boolean isPrime(long n, int iterations) {
        if (n < 2) {
            return false;
        }
        if (n == 2 || n == 3) {
            return true;
        }
        if (n % 2 == 0) {
            return false;
        }

        // Write n-1 as d * 2^r
        long d = n - 1;
        int r = 0;
        while (d % 2 == 0) {
            d /= 2;
            r++;
        }

        // Witness loop
        for (int i = 0; i < iterations; i++) {
            long a = RANDOM.nextLong() % (n - 3) + 2; // Random in [2, n-2]
            long x = modPow(a, d, n);

            if (x == 1 || x == n - 1) {
                continue;
            }

            boolean composite = true;
            for (int j = 0; j < r - 1; j++) {
                x = modPow(x, 2, n);
                if (x == n - 1) {
                    composite = false;
                    break;
                }
            }

            if (composite) {
                return false; // Definitely composite
            }
        }

        return true; // Probably prime
    }

    /**
     * Tests primality with default iterations (10).
     */
    public static boolean isPrime(long n) {
        return isPrime(n, 10);
    }

    /**
     * Demonstrates Miller-Rabin with airport-themed large numbers.
     */
    public static void demonstrate() {
        System.out.println("=== Miller-Rabin Primality Test Demo ===");
        System.out.println("Airport context: Validating large airport reference numbers.");
        System.out.println();

        long[] numbers = {
                1000003L,
                1000033L,
                1000037L,
                1000000007L,
                999999937L,
                999999929L,
                1234567891L,
                1234567897L
        };

        System.out.println("Testing airport reference numbers:");
        System.out.println();
        System.out.printf("%-15s | %-10s | %s%n", "Number", "Prime?", "Note");
        System.out.println("-----------------|------------|------------------");

        for (long num : numbers) {
            boolean prime = isPrime(num);
            String note = prime ? "Likely prime" : "Composite";
            System.out.printf("%-15d | %-10s | %s%n", num, prime, note);
        }
        System.out.println();
    }
}

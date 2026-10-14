package airportflow.dsa.dp;

/**
 * Bitmask DP for Resource Selection Optimization.
 *
 * Uses bitmask dynamic programming to evaluate all subsets of a small set
 * of resources and find the optimal selection under constraints.
 *
 * AirportFlow use case: Select the best subset of gates/resources to maximize
 * capacity or minimize cost, given a small number of gates/resources.
 *
 * Time complexity: O(n * 2^n) — suitable only for small n (up to ~15).
 */
public class BitmaskDP {

    /**
     * Represents a resource (gate) with capacity and cost.
     */
    public static class Resource {
        public String id;
        public int capacity;
        public int cost;

        public Resource(String id, int capacity, int cost) {
            this.id = id;
            this.capacity = capacity;
            this.cost = cost;
        }

        @Override
        public String toString() {
            return String.format("%s (capacity=%d, cost=%d)", id, capacity, cost);
        }
    }

    /**
     * Finds the optimal subset of resources that maximizes total capacity
     * while keeping total cost within a budget.
     *
     * @param resources array of available resources
     * @param budget    maximum total cost allowed
     * @return the best subset as a list of selected resources
     */
    public static java.util.List<Resource> selectOptimalSubset(Resource[] resources, int budget) {
        int n = resources.length;
        if (n == 0 || n > 20) {
            return new java.util.ArrayList<>();
        }

        int maxMask = 1 << n;
        int bestMask = 0;
        int bestCapacity = 0;
        int bestCost = 0;

        // Try all subsets using bitmask
        for (int mask = 1; mask < maxMask; mask++) {
            int totalCapacity = 0;
            int totalCost = 0;

            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    totalCapacity += resources[i].capacity;
                    totalCost += resources[i].cost;
                }
            }

            // Check if this subset is valid and better than current best
            if (totalCost <= budget && totalCapacity > bestCapacity) {
                bestCapacity = totalCapacity;
                bestCost = totalCost;
                bestMask = mask;
            } else if (totalCost <= budget && totalCapacity == bestCapacity && totalCost < bestCost) {
                bestCost = totalCost;
                bestMask = mask;
            }
        }

        // Build result
        java.util.List<Resource> result = new java.util.ArrayList<>();
        for (int i = 0; i < n; i++) {
            if ((bestMask & (1 << i)) != 0) {
                result.add(resources[i]);
            }
        }

        return result;
    }

    /**
     * Calculates total capacity of a list of resources.
     */
    public static int totalCapacity(java.util.List<Resource> resources) {
        int sum = 0;
        for (Resource r : resources) {
            sum += r.capacity;
        }
        return sum;
    }

    /**
     * Calculates total cost of a list of resources.
     */
    public static int totalCost(java.util.List<Resource> resources) {
        int sum = 0;
        for (Resource r : resources) {
            sum += r.cost;
        }
        return sum;
    }
}

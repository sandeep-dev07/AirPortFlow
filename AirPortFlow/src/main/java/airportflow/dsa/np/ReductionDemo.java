package airportflow.dsa.np;

import airportflow.model.Gate;

/**
 * Reduction Demo: Scheduling -> SAT -> 3-SAT.
 *
 * Demonstrates how an airport gate scheduling problem can be reduced
 * to a 3-SAT problem for academic understanding.
 *
 * AirportFlow use case: Show that gate assignment constraints can be
 * expressed as boolean formulas suitable for SAT solving.
 */
public class ReductionDemo {

    /**
     * Represents a gate assignment variable.
     */
    public static class GateAssignment {
        public String flightId;
        public String gateId;
        public String timeSlot;

        public GateAssignment(String flightId, String gateId, String timeSlot) {
            this.flightId = flightId;
            this.gateId = gateId;
            this.timeSlot = timeSlot;
        }

        @Override
        public String toString() {
            return flightId + "->" + gateId + " @" + timeSlot;
        }
    }

    /**
     * Demonstrates the reduction from gate scheduling to 3-SAT.
     */
    public static void demonstrateGateSchedulingReduction() {
        System.out.println("=== Reduction Demo: Gate Scheduling -> 3-SAT ===");
        System.out.println("Airport context: Assigning flights to gates without conflicts.");
        System.out.println();

        // Example: 2 flights, 2 gates, 2 time slots
        System.out.println("Scenario:");
        System.out.println("  Flights: F1, F2");
        System.out.println("  Gates: G1, G2");
        System.out.println("  Time Slots: T1, T2");
        System.out.println();

        System.out.println("Variables (boolean):");
        System.out.println("  x_F1_G1_T1 = true means Flight F1 is assigned to Gate G1 at T1");
        System.out.println("  x_F1_G1_T2 = true means Flight F1 is assigned to Gate G1 at T2");
        System.out.println("  ... (and so on for all combinations)");
        System.out.println();

        System.out.println("Constraints (converted to 3-CNF clauses):");
        System.out.println("  1. Each flight must be at exactly one gate at exactly one time:");
        System.out.println("     (x_F1_G1_T1 OR x_F1_G1_T2 OR x_F1_G2_T1 OR x_F1_G2_T2)");
        System.out.println("     AND ... (mutual exclusion clauses)");
        System.out.println();
        System.out.println("  2. No two flights can use the same gate at the same time:");
        System.out.println("     (NOT x_F1_G1_T1 OR NOT x_F2_G1_T1) AND ...");
        System.out.println();
        System.out.println("  3. Each gate-time pair can have at most one flight:");
        System.out.println("     (NOT x_F1_G1_T1 OR NOT x_F2_G1_T1) AND ...");
        System.out.println();

        System.out.println("This reduction shows that airport scheduling problems can be");
        System.out.println("expressed as 3-SAT formulas, demonstrating NP-completeness.");
        System.out.println();
    }

    /**
     * Creates a simple 3-SAT instance for demonstration.
     */
    public static SATSolver.Clause[] createDemoInstance() {
        // Variables: 1=F1 at G1, 2=F1 at G2, 3=F2 at G1, 4=F2 at G2
        // Example: 3-SAT with 4 variables, 3 clauses
        return new SATSolver.Clause[]{
                new SATSolver.Clause(1, 2, -3),     // (x1 OR x2 OR NOT x3)
                new SATSolver.Clause(-1, 3, 4),     // (NOT x1 OR x3 OR x4)
                new SATSolver.Clause(-2, -3, -4)    // (NOT x2 OR NOT x3 OR NOT x4)
        };
    }

    /**
     * Demonstrates solving the demo instance.
     */
    public static void demonstrateSolving() {
        System.out.println("=== Solving Demo 3-SAT Instance ===");
        System.out.println();

        SATSolver.Clause[] clauses = createDemoInstance();

        System.out.println("3-SAT Formula:");
        System.out.println("  (x1 OR x2 OR NOT x3) AND");
        System.out.println("  (NOT x1 OR x3 OR x4) AND");
        System.out.println("  (NOT x2 OR NOT x3 OR NOT x4)");
        System.out.println();

        SATSolver.Assignment solution = SATSolver.solve(clauses, 4);

        if (solution != null) {
            System.out.println("Satisfiable! Solution:");
            System.out.print("  x1=" + solution.get(0));
            System.out.print(", x2=" + solution.get(1));
            System.out.print(", x3=" + solution.get(2));
            System.out.print(", x4=" + solution.get(3));
            System.out.println();
        } else {
            System.out.println("Formula is unsatisfiable.");
        }
        System.out.println();
    }
}

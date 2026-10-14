package airportflow.dsa.np;

/**
 * SAT Solver Demonstration.
 *
 * A simple SAT (Boolean Satisfiability) solver that demonstrates the concept
 * of solving constraint satisfaction problems.
 * This implementation handles 3-SAT instances in CNF form.
 *
 * AirportFlow use case: Demonstrate airport scheduling/resource constraints
 * as a SAT problem. Small instances only — suitable for academic demonstration.
 */
public class SATSolver {

    /**
     * Represents a clause in 3-CNF (disjunction of up to 3 literals).
     */
    public static class Clause {
        public int[] literals; // Positive = variable true, Negative = variable false

        public Clause(int... literals) {
            this.literals = literals;
        }
    }

    /**
     * Represents an assignment of boolean values to variables.
     */
    public static class Assignment {
        public boolean[] values;

        public Assignment(int varCount) {
            this.values = new boolean[varCount];
        }

        public void set(int varIndex, boolean value) {
            values[varIndex] = value;
        }

        public boolean get(int varIndex) {
            return values[varIndex];
        }
    }

    /**
     * Evaluates a clause given an assignment.
     */
    public static boolean evaluateClause(Clause clause, Assignment assignment) {
        for (int literal : clause.literals) {
            int varIndex = Math.abs(literal) - 1; // 1-indexed to 0-indexed
            boolean value = assignment.get(varIndex);
            if (literal > 0 && value) {
                return true;
            }
            if (literal < 0 && !value) {
                return true;
            }
        }
        return false;
    }

    /**
     * Evaluates a 3-CNF formula given an assignment.
     */
    public static boolean evaluateFormula(Clause[] clauses, Assignment assignment) {
        for (Clause clause : clauses) {
            if (!evaluateClause(clause, assignment)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Brute-force SAT solver (exhaustive search).
     * Only suitable for small instances (up to ~20 variables).
     */
    public static Assignment solve(Clause[] clauses, int varCount) {
        int totalAssignments = 1 << varCount;

        for (int mask = 0; mask < totalAssignments; mask++) {
            Assignment assignment = new Assignment(varCount);

            for (int i = 0; i < varCount; i++) {
                assignment.set(i, ((mask >> i) & 1) == 1);
            }

            if (evaluateFormula(clauses, assignment)) {
                return assignment;
            }
        }

        return null; // Unsatisfiable
    }

    /**
     * Counts the number of satisfying assignments.
     */
    public static int countSatisfying(Clause[] clauses, int varCount) {
        int totalAssignments = 1 << varCount;
        int count = 0;

        for (int mask = 0; mask < totalAssignments; mask++) {
            Assignment assignment = new Assignment(varCount);

            for (int i = 0; i < varCount; i++) {
                assignment.set(i, ((mask >> i) & 1) == 1);
            }

            if (evaluateFormula(clauses, assignment)) {
                count++;
            }
        }

        return count;
    }
}

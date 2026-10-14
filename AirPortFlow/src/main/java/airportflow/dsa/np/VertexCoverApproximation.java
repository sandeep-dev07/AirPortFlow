package airportflow.dsa.np;

import java.util.ArrayList;
import java.util.List;

/**
 * Vertex Cover Approximation Algorithm (2-approximation).
 *
 * Given a graph, finds a vertex cover that is at most twice the size
 * of the optimal vertex cover using a greedy approach.
 *
 * AirportFlow use case: Select minimum set of gates/resources to cover
 * all flight operations. Uses approximation since exact solution is NP-hard.
 */
public class VertexCoverApproximation {

    /**
     * Represents an edge in the graph.
     */
    public static class Edge {
        public int u;
        public int v;

        public Edge(int u, int v) {
            this.u = u;
            this.v = v;
        }
    }

    /**
     * Finds an approximate vertex cover using the greedy 2-approximation algorithm.
     *
     * @param edges list of edges in the graph
     * @param vertexCount number of vertices
     * @return list of vertices in the vertex cover
     */
    public static List<Integer> findVertexCover(List<Edge> edges, int vertexCount) {
        List<Integer> cover = new ArrayList<>();
        List<Edge> remainingEdges = new ArrayList<>(edges);

        while (!remainingEdges.isEmpty()) {
            // Pick an arbitrary edge (u, v)
            Edge edge = remainingEdges.get(0);
            int u = edge.u;
            int v = edge.v;

            // Add both endpoints to the cover
            cover.add(u);
            cover.add(v);

            // Remove all edges covered by u or v
            remainingEdges.removeIf(e -> e.u == u || e.v == u || e.u == v || e.v == v);
        }

        // Remove duplicates
        java.util.Set<Integer> uniqueCover = new java.util.LinkedHashSet<>(cover);
        return new ArrayList<>(uniqueCover);
    }

    /**
     * Finds an improved vertex cover by always picking the vertex
     * that covers the most uncovered edges.
     */
    public static List<Integer> findGreedyVertexCover(List<Edge> edges, int vertexCount) {
        List<Integer> cover = new ArrayList<>();
        List<Edge> remainingEdges = new ArrayList<>(edges);

        while (!remainingEdges.isEmpty()) {
            // Count coverage for each vertex
            int[] coverage = new int[vertexCount];
            for (Edge e : remainingEdges) {
                coverage[e.u]++;
                coverage[e.v]++;
            }

            // Find vertex with maximum coverage
            int bestVertex = 0;
            for (int i = 1; i < vertexCount; i++) {
                if (coverage[i] > coverage[bestVertex]) {
                    bestVertex = i;
                }
            }

            cover.add(bestVertex);

            // Remove all edges covered by bestVertex
            java.util.Iterator<Edge> it = remainingEdges.iterator();
            while (it.hasNext()) {
                Edge e = it.next();
                if (e.u == bestVertex || e.v == bestVertex) {
                    it.remove();
                }
            }
        }

        return cover;
    }

    /**
     * Demonstrates vertex cover on an airport-themed graph.
     */
    public static void demonstrate() {
        System.out.println("=== Vertex Cover Approximation Demo ===");
        System.out.println("Airport context: Selecting minimum resources to cover all operations.");
        System.out.println();

        // Airport graph: vertices = gates/resources, edges = shared operations
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(0, 1)); // Gate 0 and Gate 1 share an operation
        edges.add(new Edge(0, 2));
        edges.add(new Edge(1, 3));
        edges.add(new Edge(2, 3));
        edges.add(new Edge(3, 4));

        System.out.println("Airport resource graph (5 resources, 5 shared operations):");
        for (Edge e : edges) {
            System.out.println("  Resource " + e.u + " -- Resource " + e.v);
        }
        System.out.println();

        List<Integer> cover1 = findVertexCover(edges, 5);
        List<Integer> cover2 = findGreedyVertexCover(edges, 5);

        System.out.println("Greedy Vertex Cover (pick arbitrary edge):");
        System.out.println("  Selected resources: " + cover1);
        System.out.println("  Size: " + cover1.size());
        System.out.println();

        System.out.println("Greedy Vertex Cover (max coverage):");
        System.out.println("  Selected resources: " + cover2);
        System.out.println("  Size: " + cover2.size());
        System.out.println();
    }
}

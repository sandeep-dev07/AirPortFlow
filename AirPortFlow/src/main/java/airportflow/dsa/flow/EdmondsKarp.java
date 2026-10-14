package airportflow.dsa.flow;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Edmonds-Karp Maximum Flow Algorithm.
 *
 * An implementation of Ford-Fulkerson using BFS (Edmonds-Karp).
 * Guarantees O(V * E^2) time complexity.
 *
 * AirportFlow use case: Model passenger/cargo flow through airport facilities
 * and compute maximum throughput with guaranteed performance.
 */
public class EdmondsKarp {

    private int[][] capacity;
    private int[][] flow;
    private int vertexCount;

    public EdmondsKarp(int vertexCount) {
        this.vertexCount = vertexCount;
        this.capacity = new int[vertexCount][vertexCount];
        this.flow = new int[vertexCount][vertexCount];
    }

    /**
     * Adds a directed edge with given capacity.
     */
    public void addEdge(int from, int to, int cap) {
        capacity[from][to] += cap;
    }

    /**
     * Computes the maximum flow from source to sink using BFS augmenting paths.
     */
    public int maxFlow(int source, int sink) {
        int maxFlow = 0;

        // Initialize flow matrix
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                flow[i][j] = 0;
            }
        }

        // BFS to find augmenting paths
        int[] parent = new int[vertexCount];

        while (bfs(source, sink, parent)) {
            int pathFlow = Integer.MAX_VALUE;

            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, capacity[u][v] - flow[u][v]);
            }

            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                flow[u][v] += pathFlow;
                flow[v][u] -= pathFlow;
            }

            maxFlow += pathFlow;
        }

        return maxFlow;
    }

    /**
     * BFS to find augmenting path and populate parent array.
     */
    private boolean bfs(int source, int sink, int[] parent) {
        boolean[] visited = new boolean[vertexCount];
        Queue<Integer> queue = new LinkedList<>();

        queue.add(source);
        visited[source] = true;
        parent[source] = -1;

        while (!queue.isEmpty()) {
            int u = queue.poll();

            for (int v = 0; v < vertexCount; v++) {
                if (!visited[v] && capacity[u][v] - flow[u][v] > 0) {
                    queue.add(v);
                    parent[v] = u;
                    visited[v] = true;

                    if (v == sink) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Returns the residual capacity graph.
     */
    public int[][] getResidualCapacity() {
        int[][] residual = new int[vertexCount][vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                residual[i][j] = capacity[i][j] - flow[i][j];
            }
        }
        return residual;
    }

    /**
     * Computes the minimum cut after max flow.
     */
    public java.util.List<Integer> minCut(int source) {
        boolean[] visited = new boolean[vertexCount];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;

        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v = 0; v < vertexCount; v++) {
                if (!visited[v] && capacity[u][v] - flow[u][v] > 0) {
                    visited[v] = true;
                    queue.add(v);
                }
            }
        }

        java.util.List<Integer> reachable = new ArrayList<>();
        for (int i = 0; i < vertexCount; i++) {
            if (visited[i]) {
                reachable.add(i);
            }
        }

        return reachable;
    }
}

package airportflow.dsa.flow;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Ford-Fulkerson Maximum Flow Algorithm.
 *
 * Finds the maximum flow in a flow network by repeatedly finding augmenting paths
 * using DFS and augmenting flow along them.
 * Time complexity: O(E * maxFlow) — can be slow for large capacities.
 *
 * AirportFlow use case: Model passenger/cargo flow through airport facilities
 * and compute maximum throughput.
 */
public class FordFulkerson {

    private int[][] capacity;
    private int[][] flow;
    private int vertexCount;
    private int[] parent;

    public FordFulkerson(int vertexCount) {
        this.vertexCount = vertexCount;
        this.capacity = new int[vertexCount][vertexCount];
        this.flow = new int[vertexCount][vertexCount];
        this.parent = new int[vertexCount];
    }

    /**
     * Adds a directed edge with given capacity to the network.
     */
    public void addEdge(int from, int to, int cap) {
        capacity[from][to] += cap;
    }

    /**
     * Computes the maximum flow from source to sink.
     */
    public int maxFlow(int source, int sink) {
        int maxFlow = 0;

        // Initialize flow matrix
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                flow[i][j] = 0;
            }
        }

        // Find augmenting paths
        while (dfs(source, sink)) {
            int pathFlow = Integer.MAX_VALUE;

            // Find bottleneck capacity on the path
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, capacity[u][v] - flow[u][v]);
            }

            // Augment flow
            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                flow[u][v] += pathFlow;
                flow[v][u] -= pathFlow; // Reverse edge
            }

            maxFlow += pathFlow;
        }

        return maxFlow;
    }

    /**
     * DFS to find an augmenting path from source to sink.
     */
    private boolean dfs(int source, int sink) {
        boolean[] visited = new boolean[vertexCount];
        return dfsRecursive(source, sink, visited);
    }

    private boolean dfsRecursive(int u, int sink, boolean[] visited) {
        visited[u] = true;

        if (u == sink) {
            return true;
        }

        for (int v = 0; v < vertexCount; v++) {
            if (!visited[v] && capacity[u][v] - flow[u][v] > 0) {
                parent[v] = u;
                if (dfsRecursive(v, sink, visited)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Returns the flow matrix after computation.
     */
    public int[][] getFlowMatrix() {
        int[][] result = new int[vertexCount][vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            System.arraycopy(flow[i], 0, result[i], 0, vertexCount);
        }
        return result;
    }

    /**
     * Computes the minimum cut using BFS from source after max flow.
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

package airportflow.dsa.flow;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Bipartite Matching Algorithm.
 *
 * Finds the maximum matching in a bipartite graph.
 * Uses BFS-based approach (Hopcroft-Karp inspired).
 *
 * AirportFlow use case: Assign flights to compatible available gates
 * to maximize the number of matched flights.
 */
public class BipartiteMatching {

    private int[][] graph; // Adjacency matrix: left side (flights) -> right side (gates)
    private int leftSize;
    private int rightSize;
    private int[] matchR; // Which left vertex is matched to right vertex j
    private int[] matchL; // Which right vertex is matched to left vertex i
    private int[] dist;

    public BipartiteMatching(int leftSize, int rightSize) {
        this.leftSize = leftSize;
        this.rightSize = rightSize;
        this.graph = new int[leftSize][rightSize];
        this.matchR = new int[rightSize];
        this.matchL = new int[leftSize];
        this.dist = new int[leftSize];
    }

    /**
     * Adds an edge between left vertex u and right vertex v.
     */
    public void addEdge(int u, int v) {
        graph[u][v] = 1;
    }

    /**
     * Computes the maximum bipartite matching.
     *
     * @return the size of the maximum matching
     */
    public int maxMatching() {
        // Initialize matches
        for (int i = 0; i < rightSize; i++) {
            matchR[i] = -1;
        }
        for (int i = 0; i < leftSize; i++) {
            matchL[i] = -1;
        }

        int result = 0;

        // Try to find augmenting paths
        while (bfs()) {
            for (int u = 0; u < leftSize; u++) {
                if (matchL[u] == -1 && dfs(u)) {
                    result++;
                }
            }
        }

        return result;
    }

    /**
     * BFS to find augmenting paths.
     */
    private boolean bfs() {
        Queue<Integer> queue = new LinkedList<>();

        for (int u = 0; u < leftSize; u++) {
            if (matchL[u] == -1) {
                dist[u] = 0;
                queue.add(u);
            } else {
                dist[u] = -1;
            }
        }

        boolean found = false;

        while (!queue.isEmpty()) {
            int u = queue.poll();

            for (int v = 0; v < rightSize; v++) {
                if (graph[u][v] == 1) {
                    if (matchR[v] != -1 && dist[matchR[v]] == -1) {
                        dist[matchR[v]] = dist[u] + 1;
                        queue.add(matchR[v]);
                    }
                    if (matchR[v] == -1) {
                        found = true;
                    }
                }
            }
        }

        return found;
    }

    /**
     * DFS to find augmenting path from left vertex u.
     */
    private boolean dfs(int u) {
        for (int v = 0; v < rightSize; v++) {
            if (graph[u][v] == 1) {
                if (matchR[v] == -1 || (dist[matchR[v]] == dist[u] + 1 && dfs(matchR[v]))) {
                    matchL[u] = v;
                    matchR[v] = u;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns the matching: left vertex i matched to right vertex matchL[i].
     */
    public int[] getLeftMatching() {
        return matchL.clone();
    }

    /**
     * Returns the matching: right vertex j matched to left vertex matchR[j].
     */
    public int[] getRightMatching() {
        return matchR.clone();
    }
}

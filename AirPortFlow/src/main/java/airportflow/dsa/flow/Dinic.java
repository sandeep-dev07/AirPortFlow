package airportflow.dsa.flow;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Dinic's Maximum Flow Algorithm.
 *
 * An efficient maximum flow algorithm using level graph and blocking flow.
 * Time complexity: O(V^2 * E).
 *
 * AirportFlow use case: Model passenger/cargo flow through airport facilities
 * with efficient computation for larger networks.
 */
public class Dinic {

    private int[][] capacity;
    private int[][] flow;
    private int vertexCount;
    private int[] level;
    private int[] ptr;

    public Dinic(int vertexCount) {
        this.vertexCount = vertexCount;
        this.capacity = new int[vertexCount][vertexCount];
        this.flow = new int[vertexCount][vertexCount];
        this.level = new int[vertexCount];
        this.ptr = new int[vertexCount];
    }

    /**
     * Adds a directed edge with given capacity.
     */
    public void addEdge(int from, int to, int cap) {
        capacity[from][to] += cap;
    }

    /**
     * Computes maximum flow using Dinic's algorithm.
     */
    public int maxFlow(int source, int sink) {
        int maxFlow = 0;

        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                flow[i][j] = 0;
            }
        }

        while (bfs(source, sink)) {
            System.arraycopy(new int[vertexCount], 0, ptr, 0, vertexCount);

            while (true) {
                int pushed = dfs(source, sink, Integer.MAX_VALUE);
                if (pushed == 0) {
                    break;
                }
                maxFlow += pushed;
            }
        }

        return maxFlow;
    }

    private boolean bfs(int source, int sink) {
        for (int i = 0; i < vertexCount; i++) {
            level[i] = -1;
        }

        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        level[source] = 0;

        while (!queue.isEmpty() && level[sink] == -1) {
            int u = queue.poll();

            for (int v = 0; v < vertexCount; v++) {
                if (level[v] == -1 && flow[u][v] < capacity[u][v]) {
                    level[v] = level[u] + 1;
                    queue.add(v);
                }
            }
        }

        return level[sink] != -1;
    }

    private int dfs(int u, int sink, int pushed) {
        if (pushed == 0) {
            return 0;
        }

        if (u == sink) {
            return pushed;
        }

        for (int v = ptr[u]; v < vertexCount; v++) {
            ptr[u] = v;
            if (level[v] == level[u] + 1 && flow[u][v] < capacity[u][v]) {
                int tr = dfs(v, sink, Math.min(pushed, capacity[u][v] - flow[u][v]));
                if (tr > 0) {
                    flow[u][v] += tr;
                    flow[v][u] -= tr;
                    return tr;
                }
            }
        }

        return 0;
    }

    public int[][] getFlowMatrix() {
        int[][] result = new int[vertexCount][vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            System.arraycopy(flow[i], 0, result[i], 0, vertexCount);
        }
        return result;
    }
}

package airportflow.service;

import airportflow.dsa.flow.*;
import airportflow.model.Flight;
import airportflow.model.Gate;
import airportflow.repository.FlightRepository;
import airportflow.repository.GateRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service for analyzing passenger and capacity flow through airport facilities.
 * Uses network flow algorithms (Ford-Fulkerson, Edmonds-Karp, Dinic).
 */
public class FlowAnalysisService {

    // Airport network node indices
    public static final int TERMINAL = 0;
    public static final int SECURITY = 1;
    public static final int GATES = 2;
    public static final int BAGGAGE = 3;
    public static final int EXIT = 4;

    private FlightRepository flightRepository;
    private GateRepository gateRepository;

    public FlowAnalysisService(FlightRepository flightRepository, GateRepository gateRepository) {
        this.flightRepository = flightRepository;
        this.gateRepository = gateRepository;
    }

    /**
     * Builds a sample airport flow network:
     * Terminal -> Security -> Gates -> Baggage -> Exit
     */
    public void buildSampleNetwork(EdmondsKarp flowNetwork) {
        flowNetwork.addEdge(TERMINAL, SECURITY, 500);
        flowNetwork.addEdge(SECURITY, GATES, 400);
        flowNetwork.addEdge(GATES, BAGGAGE, 350);
        flowNetwork.addEdge(BAGGAGE, EXIT, 300);
        flowNetwork.addEdge(TERMINAL, GATES, 100);
        flowNetwork.addEdge(SECURITY, BAGGAGE, 50);
    }

    /**
     * Builds a sample airport flow network for Ford-Fulkerson.
     */
    public void buildSampleNetwork(FordFulkerson flowNetwork) {
        flowNetwork.addEdge(TERMINAL, SECURITY, 500);
        flowNetwork.addEdge(SECURITY, GATES, 400);
        flowNetwork.addEdge(GATES, BAGGAGE, 350);
        flowNetwork.addEdge(BAGGAGE, EXIT, 300);
        flowNetwork.addEdge(TERMINAL, GATES, 100);
        flowNetwork.addEdge(SECURITY, BAGGAGE, 50);
    }

    /**
     * Builds a sample airport flow network for Dinic.
     */
    public void buildSampleNetwork(Dinic flowNetwork) {
        flowNetwork.addEdge(TERMINAL, SECURITY, 500);
        flowNetwork.addEdge(SECURITY, GATES, 400);
        flowNetwork.addEdge(GATES, BAGGAGE, 350);
        flowNetwork.addEdge(BAGGAGE, EXIT, 300);
        flowNetwork.addEdge(TERMINAL, GATES, 100);
        flowNetwork.addEdge(SECURITY, BAGGAGE, 50);
    }

    /**
     * Computes max flow using Ford-Fulkerson.
     */
    public int computeMaxFlowFordFulkerson() {
        FordFulkerson ff = new FordFulkerson(5);
        buildSampleNetwork(ff);
        return ff.maxFlow(TERMINAL, EXIT);
    }

    /**
     * Computes max flow using Edmonds-Karp.
     */
    public int computeMaxFlowEdmondsKarp() {
        EdmondsKarp ek = new EdmondsKarp(5);
        buildSampleNetwork(ek);
        return ek.maxFlow(TERMINAL, EXIT);
    }

    /**
     * Computes max flow using Dinic.
     */
    public int computeMaxFlowDinic() {
        Dinic dinic = new Dinic(5);
        buildSampleNetwork(dinic);
        return dinic.maxFlow(TERMINAL, EXIT);
    }

    /**
     * Finds the minimum cut using Edmonds-Karp.
     */
    public List<Integer> findMinCut() {
        EdmondsKarp ek = new EdmondsKarp(5);
        buildSampleNetwork(ek);
        ek.maxFlow(TERMINAL, EXIT);
        return ek.minCut(TERMINAL);
    }

    /**
     * Returns a description of the airport network.
     */
    public String getNetworkDescription() {
        return "Airport Flow Network:\n" +
                "  Terminal (Source) -> Security: 500 capacity\n" +
                "  Terminal (Source) -> Gates (VIP): 100 capacity\n" +
                "  Security -> Gates: 400 capacity\n" +
                "  Security -> Baggage: 50 capacity\n" +
                "  Gates -> Baggage: 350 capacity\n" +
                "  Baggage -> Exit (Sink): 300 capacity\n" +
                "\n" +
                "This network models passenger flow from Terminal entry\n" +
                "through Security checkpoints, to Gates, Baggage claim,\n" +
                "and finally to Exit.";
    }

    /**
     * Returns a readable name for a node index.
     */
    public String getNodeName(int node) {
        switch (node) {
            case TERMINAL: return "Terminal";
            case SECURITY: return "Security";
            case GATES: return "Gates";
            case BAGGAGE: return "Baggage";
            case EXIT: return "Exit";
            default: return "Node " + node;
        }
    }
}

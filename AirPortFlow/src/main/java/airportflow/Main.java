package airportflow;

import airportflow.dsa.dp.BitmaskDP;
import airportflow.dsa.dp.MatrixChain;
import airportflow.dsa.dp.OptimalBST;
import airportflow.dsa.flow.BipartiteMatching;
import airportflow.dsa.flow.Dinic;
import airportflow.dsa.flow.EdmondsKarp;
import airportflow.dsa.flow.FordFulkerson;
import airportflow.dsa.np.ReductionDemo;
import airportflow.dsa.np.SATSolver;
import airportflow.dsa.np.VertexCoverApproximation;
import airportflow.dsa.randomized.BlellochScan;
import airportflow.dsa.randomized.BrentTheorem;
import airportflow.dsa.randomized.MillerRabin;
import airportflow.dsa.randomized.ParallelReduce;
import airportflow.dsa.randomized.ReservoirSampling;
import airportflow.dsa.string.AhoCorasick;
import airportflow.dsa.string.KMP;
import airportflow.dsa.string.RabinKarp;
import airportflow.dsa.string.ZFunction;
import airportflow.dsa.suffix.LCPKasai;
import airportflow.dsa.suffix.SAIS;
import airportflow.dsa.suffix.SuffixArray;
import airportflow.model.*;
import airportflow.repository.EventRepository;
import airportflow.repository.FlightRepository;
import airportflow.repository.GateRepository;
import airportflow.service.EventService;
import airportflow.service.FlightService;
import airportflow.service.FlowAnalysisService;
import airportflow.service.GateAssignmentService;
import airportflow.service.OptimizationService;
import airportflow.service.SearchService;
import airportflow.util.InputValidator;
import airportflow.util.SampleData;

import java.util.*;

/**
 * AirportFlow - DSA-3 Hackathon Project
 *
 * Main application class with a console-based menu system.
 * Demonstrates DSA algorithms in the context of airport operations.
 */
public class Main {

    private static FlightRepository flightRepository;
    private static GateRepository gateRepository;
    private static EventRepository eventRepository;
    private static FlightService flightService;
    private static SearchService searchService;
    private static GateAssignmentService gateAssignmentService;
    private static FlowAnalysisService flowAnalysisService;
    private static OptimizationService optimizationService;
    private static EventService eventService;
    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);

        // Initialize repositories
        flightRepository = new FlightRepository();
        gateRepository = new GateRepository();
        eventRepository = new EventRepository();

        // Load sample data
        SampleData.loadSampleData(flightRepository, gateRepository, eventRepository);

        // Initialize services
        flightService = new FlightService(flightRepository, gateRepository);
        searchService = new SearchService(flightRepository);
        gateAssignmentService = new GateAssignmentService(flightRepository, gateRepository);
        flowAnalysisService = new FlowAnalysisService(flightRepository, gateRepository);
        optimizationService = new OptimizationService(gateRepository, flightRepository);
        eventService = new EventService(eventRepository, flightRepository);

        // Display welcome and run menu
        displayWelcome();
        runMainMenu();
    }

    private static void displayWelcome() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("        Welcome to AirportFlow          ");
        System.out.println("========================================");
        System.out.println("AirportFlow - DSA-3 Hackathon Project");
        System.out.println("Demonstrating DSA algorithms in airport operations.");
        System.out.println();
    }

    private static void runMainMenu() {
        boolean running = true;

        while (running) {
            displayMenu();
            int choice = InputValidator.readInt("Enter your choice: ", 1, 13);

            switch (choice) {
                case 1:
                    handleViewFlights();
                    break;
                case 2:
                    handleSearchFlight();
                    break;
                case 3:
                    handleSearchStringAlgorithms();
                    break;
                case 4:
                    handleCorrectInput();
                    break;
                case 5:
                    handleAssignGates();
                    break;
                case 6:
                    handleFlowAnalysis();
                    break;
                case 7:
                    handleResourceOptimization();
                    break;
                case 8:
                    handleSortFlights();
                    break;
                case 9:
                    handleEventProcessing();
                    break;
                case 10:
                    handleSuffixStructures();
                    break;
                case 11:
                    handleNPAlgorithms();
                    break;
                case 12:
                    handleRandomizedAlgorithms();
                    break;
                default:
                    running = false;
                    System.out.println();
                    System.out.println("========================================");
                    System.out.println("   Thank you for using AirportFlow!    ");
                    System.out.println("========================================");
                    break;
            }
        }
    }

    private static void displayMenu() {
        System.out.println();
        System.out.println("========== AIRPORTFLOW MENU ==========");
        System.out.println(" 1. View All Flights");
        System.out.println(" 2. Search Flight");
        System.out.println(" 3. String Algorithm Demos (KMP, Rabin-Karp, Aho-Corasick, Z-Function)");
        System.out.println(" 4. Correct Airport Input (Levenshtein / Damerau-Levenshtein)");
        System.out.println(" 5. Assign Flights to Gates (Bipartite Matching)");
        System.out.println(" 6. Passenger Flow Analysis (Ford-Fulkerson, Edmonds-Karp, Dinic)");
        System.out.println(" 7. Resource Optimization (Bitmask DP, Matrix-Chain, Optimal BST)");
        System.out.println(" 8. Sort Flights (Randomized QuickSort)");
        System.out.println(" 9. Airport Event Processing (Reservoir Sampling)");
        System.out.println("10. Suffix Structures (Suffix Array, LCP/Kasai, SA-IS)");
        System.out.println("11. NP-Completeness Demo (SAT/3-SAT, Vertex Cover Approximation)");
        System.out.println("12. Randomized/Parallel Algorithms (Miller-Rabin, Blelloch Scan, Parallel Reduce)");
        System.out.println("13. Exit");
        System.out.println("========================================");
    }

    private static void handleViewFlights() {
        System.out.println();
        System.out.println("=== View All Flights ===");
        List<Flight> flights = flightService.getAllFlights();
        if (flights.isEmpty()) {
            System.out.println("No flights available.");
            return;
        }
        System.out.println();
        System.out.printf("%-10s | %-12s | %-8s | %-8s | %-12s | %-10s | %-10s | %-6s%n",
                "Flight", "Airline", "Source", "Dest", "Time", "Status", "Passengers", "Gate");
        System.out.println("-----------|--------------|----------|----------|--------------|------------|------------|--------");
        for (Flight f : flights) {
            System.out.println(f.toString());
        }
        System.out.println();
        System.out.println("Total flights: " + flights.size());
    }

    private static void handleSearchFlight() {
        System.out.println();
        System.out.println("=== Search Flight ===");
        System.out.println("Search by:");
        System.out.println("  1. Flight Number (KMP)");
        System.out.println("  2. Airline Name (Rabin-Karp)");
        System.out.println("  3. Route/City (Z-Function)");
        int choice = InputValidator.readInt("Enter choice: ", 1, 3);
        String query = InputValidator.readString("Enter search term: ");

        List<Flight> results;
        switch (choice) {
            case 1:
                results = searchService.searchByFlightNumber(query);
                break;
            case 2:
                results = searchService.searchByAirline(query);
                break;
            case 3:
                results = searchService.searchByRoute(query);
                break;
            default:
                return;
        }

        if (results.isEmpty()) {
            System.out.println("No flights found matching: " + query);
        } else {
            System.out.println();
            System.out.println("Found " + results.size() + " flight(s):");
            for (Flight f : results) {
                System.out.println("  " + f);
            }
        }
    }

    private static void handleSearchStringAlgorithms() {
        System.out.println();
        System.out.println("=== String Algorithm Demonstrations ===");

        String text = "Flight AI101 departed at 08:00. Flight 6E202 is delayed. " +
                "Security alert for SG303. AI404 boarding now. 6E505 on time.";

        System.out.println("Sample airport text:");
        System.out.println("\"" + text + "\"");
        System.out.println();

        // KMP Demo
        System.out.println("--- KMP Search ---");
        String kmpPattern = InputValidator.readString("Enter pattern to search (KMP): ");
        List<Integer> kmpResults = KMP.search(text, kmpPattern);
        if (kmpResults.isEmpty()) {
            System.out.println("Pattern not found.");
        } else {
            System.out.println("Found at positions: " + kmpResults);
        }
        System.out.println();

        // Rabin-Karp Demo
        System.out.println("--- Rabin-Karp Search ---");
        String rkPattern = InputValidator.readString("Enter pattern to search (Rabin-Karp): ");
        List<Integer> rkResults = RabinKarp.search(text, rkPattern);
        if (rkResults.isEmpty()) {
            System.out.println("Pattern not found.");
        } else {
            System.out.println("Found at positions: " + rkResults);
        }
        System.out.println();

        // Z-Function Demo
        System.out.println("--- Z-Function Demo ---");
        System.out.println("Z-Function for \"" + text + "\":");
        int[] z = ZFunction.compute(text);
        System.out.println("Z-array: " + Arrays.toString(z));
        String lrs = ZFunction.longestRepeatedSubstring(text);
        System.out.println("Longest repeated substring: \"" + lrs + "\"");
        System.out.println();

        // Aho-Corasick Demo
        System.out.println("--- Aho-Corasick Multi-Pattern Search ---");
        String[] patterns = {"AI101", "6E202", "DELAY", "BOARDING"};
        System.out.println("Patterns: " + Arrays.toString(patterns));
        List<AhoCorasick.MatchResult> acResults = searchService.searchMultiplePatterns(text, patterns);
        if (acResults.isEmpty()) {
            System.out.println("No matches found.");
        } else {
            for (AhoCorasick.MatchResult result : acResults) {
                System.out.println("  " + result);
            }
        }
        System.out.println();
    }

    private static void handleCorrectInput() {
        System.out.println();
        System.out.println("=== Input Correction (Edit Distance) ===");
        String input = InputValidator.readString("Enter an airport name (possibly misspelled): ");

        String[] airports = {
                "Delhi", "Mumbai", "Bangalore", "Chennai", "Kolkata",
                "Hyderabad", "Goa", "Pune", "Nagpur", "Ahmedabad"
        };

        String levResult = optimizationService.correctAirportName(input);
        String damResult = optimizationService.correctAirportNameDamerau(input);
        int levDist = optimizationService.getLevenshteinDistance(input, levResult);
        int damDist = optimizationService.getDamerauLevenshteinDistance(input, damResult);

        System.out.println();
        System.out.println("Input: \"" + input + "\"");
        System.out.println();
        System.out.println("Levenshtein Distance Correction:");
        System.out.println("  Closest match: \"" + levResult + "\"");
        System.out.println("  Edit distance: " + levDist);
        System.out.println();
        System.out.println("Damerau-Levenshtein Distance Correction:");
        System.out.println("  Closest match: \"" + damResult + "\"");
        System.out.println("  Edit distance: " + damDist);
        System.out.println();

        System.out.println("All valid airports: " + Arrays.toString(airports));
    }

    private static void handleAssignGates() {
        System.out.println();
        System.out.println("=== Flight-to-Gate Assignment (Bipartite Matching) ===");
        System.out.println();

        List<Gate> availableGates = gateAssignmentService.getAvailableGates();
        System.out.println("Available Gates:");
        for (Gate g : availableGates) {
            System.out.println("  " + g);
        }
        System.out.println();

        System.out.println("Running Bipartite Matching algorithm...");
        Map<String, String> assignments = gateAssignmentService.assignFlightsToGates();

        if (assignments.isEmpty()) {
            System.out.println("No flights were assigned to gates.");
        } else {
            System.out.println();
            System.out.println("Flight-Gate Assignments:");
            System.out.println("  Flight     | Gate | Status");
            System.out.println("  -----------|------|--------");
            for (Map.Entry<String, String> entry : assignments.entrySet()) {
                System.out.printf("  %-10s | %-4s | Assigned%n", entry.getKey(), entry.getValue());
            }
        }
        System.out.println();

        System.out.println("Updated Flight List:");
        List<Flight> flights = flightService.getAllFlights();
        for (Flight f : flights) {
            System.out.println("  " + f);
        }
    }

    private static void handleFlowAnalysis() {
        System.out.println();
        System.out.println("=== Passenger Flow Analysis ===");
        System.out.println();
        System.out.println(flowAnalysisService.getNetworkDescription());
        System.out.println();

        System.out.println("Computing maximum flows...");
        System.out.println();

        int ffFlow = flowAnalysisService.computeMaxFlowFordFulkerson();
        System.out.println("Ford-Fulkerson Max Flow: " + ffFlow);

        int ekFlow = flowAnalysisService.computeMaxFlowEdmondsKarp();
        System.out.println("Edmonds-Karp Max Flow:   " + ekFlow);

        int dinicFlow = flowAnalysisService.computeMaxFlowDinic();
        System.out.println("Dinic Max Flow:          " + dinicFlow);
        System.out.println();

        List<Integer> minCut = flowAnalysisService.findMinCut();
        System.out.println("Min-Cut (reachable from Terminal):");
        for (int node : minCut) {
            System.out.println("  - " + flowAnalysisService.getNodeName(node));
        }
        System.out.println();

        System.out.println("Interpretation:");
        System.out.println("  The max flow represents the maximum passenger throughput");
        System.out.println("  from Terminal to Exit. The min-cut identifies the bottleneck");
        System.out.println("  (critical capacity constraints) in the airport network.");
    }

    private static void handleResourceOptimization() {
        System.out.println();
        System.out.println("=== Resource Optimization ===");
        System.out.println();

        System.out.println("1. Bitmask DP - Optimal Gate Selection");
        System.out.println("2. Matrix-Chain Multiplication Demo");
        System.out.println("3. Optimal BST Demo");
        int choice = InputValidator.readInt("Enter choice: ", 1, 3);

        switch (choice) {
            case 1:
                handleBitmaskDP();
                break;
            case 2:
                MatrixChain.demonstrate();
                break;
            case 3:
                OptimalBST.demonstrate();
                break;
        }
    }

    private static void handleBitmaskDP() {
        System.out.println();
        System.out.println("=== Bitmask DP - Optimal Gate/Resource Selection ===");
        System.out.println("Select the best subset of gates/resources to maximize capacity");
        System.out.println("under a budget constraint.");
        System.out.println();

        int budget = InputValidator.readInt("Enter budget: ", 1, 10000);

        List<BitmaskDP.Resource> optimal = optimizationService.optimizeGateSelection(budget);

        System.out.println();
        System.out.println("Optimal resource selection (Budget: " + budget + "):");
        if (optimal.isEmpty()) {
            System.out.println("  No resources selected (budget too low).");
        } else {
            System.out.println("  Selected Resources:");
            for (BitmaskDP.Resource r : optimal) {
                System.out.printf("  - %-6s: Capacity=%d, Cost=%d%n", r.id, r.capacity, r.cost);
            }
            System.out.println();
            System.out.println("  Total Capacity: " + BitmaskDP.totalCapacity(optimal));
            System.out.println("  Total Cost:     " + BitmaskDP.totalCost(optimal));
        }
        System.out.println();
    }

    private static void handleSortFlights() {
        System.out.println();
        System.out.println("=== Sort Flights (Randomized QuickSort) ===");
        System.out.println("Sort by:");
        System.out.println("  1. Flight Number");
        System.out.println("  2. Scheduled Time");
        System.out.println("  3. Passenger Count");
        int choice = InputValidator.readInt("Enter choice: ", 1, 3);

        String sortBy;
        switch (choice) {
            case 1: sortBy = "flightnumber"; break;
            case 2: sortBy = "time"; break;
            case 3: sortBy = "passengers"; break;
            default: return;
        }

        List<Flight> sorted = flightService.sortFlights(sortBy);

        System.out.println();
        System.out.println("Sorted Flights:");
        System.out.printf("%-10s | %-12s | %-8s | %-8s | %-12s | %-10s | %-10s | %-6s%n",
                "Flight", "Airline", "Source", "Dest", "Time", "Status", "Passengers", "Gate");
        System.out.println("-----------|--------------|----------|----------|--------------|------------|------------|--------");
        for (Flight f : sorted) {
            System.out.println(f.toString());
        }
    }

    private static void handleEventProcessing() {
        System.out.println();
        System.out.println("=== Airport Event Processing ===");
        System.out.println();

        System.out.println("All Events:");
        List<AirportEvent> events = eventService.getAllEvents();
        for (AirportEvent e : events) {
            System.out.println("  " + e);
        }
        System.out.println();
        System.out.println("Total events: " + events.size());
        System.out.println();

        eventService.demonstrateReservoirSampling();
    }

    private static void handleSuffixStructures() {
        System.out.println();
        System.out.println("=== Suffix Structures Demo ===");
        System.out.println();

        String text = "airportflightairportflightflow";
        System.out.println("Sample airport text: \"" + text + "\"");
        System.out.println();

        // Suffix Array
        System.out.println("--- Suffix Array ---");
        SuffixArray sa = new SuffixArray(text);
        int[] suffixArray = sa.getSuffixArray();
        System.out.println("Suffix Array indices:");
        System.out.print("[");
        for (int i = 0; i < suffixArray.length; i++) {
            System.out.print(suffixArray[i]);
            if (i < suffixArray.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
        System.out.println();

        String pattern = InputValidator.readString("Enter substring to search (or press Enter for 'flight'): ");
        if (pattern.isEmpty()) {
            pattern = "flight";
        }

        List<Integer> saResults = sa.search(pattern);
        if (saResults.isEmpty()) {
            System.out.println("Pattern \"" + pattern + "\" not found.");
        } else {
            System.out.println("Pattern \"" + pattern + "\" found at positions: " + saResults);
        }
        System.out.println();

        // LCP/Kasai
        System.out.println("--- LCP/Kasai Algorithm ---");
        int[] lcp = LCPKasai.computeLCP(text, suffixArray);
        System.out.println("LCP Array: " + Arrays.toString(lcp));
        System.out.println();

        // SA-IS
        System.out.println("--- SA-IS Construction Demo ---");
        SAIS.demonstrate("airport");
        System.out.println();
    }

    private static void handleNPAlgorithms() {
        System.out.println();
        System.out.println("=== NP-Completeness Demo ===");
        System.out.println();

        System.out.println("1. SAT/3-SAT Solver");
        System.out.println("2. Reduction Demo (Gate Scheduling -> 3-SAT)");
        System.out.println("3. Vertex Cover Approximation");
        int choice = InputValidator.readInt("Enter choice: ", 1, 3);

        switch (choice) {
            case 1:
                SATSolver.Clause[] clauses = ReductionDemo.createDemoInstance();
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
                    System.out.println("Unsatisfiable.");
                }
                break;
            case 2:
                ReductionDemo.demonstrateGateSchedulingReduction();
                ReductionDemo.demonstrateSolving();
                break;
            case 3:
                VertexCoverApproximation.demonstrate();
                break;
        }
    }

    private static void handleRandomizedAlgorithms() {
        System.out.println();
        System.out.println("=== Randomized/Parallel Algorithms Demo ===");
        System.out.println();

        System.out.println("1. Miller-Rabin Primality Test");
        System.out.println("2. Blelloch Scan (Prefix Sum)");
        System.out.println("3. Parallel Reduce");
        System.out.println("4. Brent's Theorem");
        int choice = InputValidator.readInt("Enter choice: ", 1, 4);

        switch (choice) {
            case 1:
                eventService.demonstrateMillerRabin();
                break;
            case 2:
                eventService.demonstrateBlellochScan();
                break;
            case 3:
                eventService.demonstrateParallelReduce();
                break;
            case 4:
                eventService.demonstrateBrentTheorem();
                break;
        }
    }
}

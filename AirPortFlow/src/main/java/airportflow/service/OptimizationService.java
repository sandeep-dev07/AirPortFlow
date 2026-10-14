package airportflow.service;

import airportflow.dsa.dp.BitmaskDP;
import airportflow.dsa.dp.DamerauLevenshtein;
import airportflow.dsa.dp.LevenshteinDistance;
import airportflow.model.AirportResource;
import airportflow.repository.FlightRepository;
import airportflow.repository.GateRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for optimizing airport resources.
 * Uses DP algorithms (Bitmask DP, Matrix-Chain, Optimal BST).
 */
public class OptimizationService {

    private GateRepository gateRepository;
    private FlightRepository flightRepository;

    public OptimizationService(GateRepository gateRepository, FlightRepository flightRepository) {
        this.gateRepository = gateRepository;
        this.flightRepository = flightRepository;
    }

    /**
     * Finds the optimal subset of gates to maximize capacity within a budget.
     */
    public List<BitmaskDP.Resource> optimizeGateSelection(int budget) {
        List<airportflow.model.Gate> allGates = gateRepository.getAllGates();

        BitmaskDP.Resource[] resources = new BitmaskDP.Resource[allGates.size()];
        for (int i = 0; i < allGates.size(); i++) {
            airportflow.model.Gate gate = allGates.get(i);
            int capacity = gate.getGateType().equals("International") ? 300 : 200;
            int cost = 50 + i * 30;
            resources[i] = new BitmaskDP.Resource(gate.getGateId(), capacity, cost);
        }

        return BitmaskDP.selectOptimalSubset(resources, budget);
    }

    /**
     * Finds the closest matching airport name using Levenshtein distance.
     */
    public String correctAirportName(String input) {
        String[] airports = {
                "Delhi", "Mumbai", "Bangalore", "Chennai", "Kolkata",
                "Hyderabad", "Goa", "Pune", "Nagpur", "Ahmedabad"
        };
        return LevenshteinDistance.findClosest(input, airports);
    }

    /**
     * Finds the closest matching airport name using Damerau-Levenshtein distance.
     */
    public String correctAirportNameDamerau(String input) {
        String[] airports = {
                "Delhi", "Mumbai", "Bangalore", "Chennai", "Kolkata",
                "Hyderabad", "Goa", "Pune", "Nagpur", "Ahmedabad"
        };
        return DamerauLevenshtein.findClosest(input, airports);
    }

    /**
     * Returns the edit distance between two airport names.
     */
    public int getLevenshteinDistance(String s1, String s2) {
        return LevenshteinDistance.compute(s1, s2);
    }

    /**
     * Returns the Damerau-Levenshtein distance between two airport names.
     */
    public int getDamerauLevenshteinDistance(String s1, String s2) {
        return DamerauLevenshtein.compute(s1, s2);
    }
}

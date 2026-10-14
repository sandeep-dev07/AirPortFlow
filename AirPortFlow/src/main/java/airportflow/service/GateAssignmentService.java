package airportflow.service;

import airportflow.dsa.flow.BipartiteMatching;
import airportflow.model.Flight;
import airportflow.model.Gate;
import airportflow.repository.FlightRepository;
import airportflow.repository.GateRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service for assigning flights to gates.
 * Uses Bipartite Matching algorithm.
 */
public class GateAssignmentService {

    private FlightRepository flightRepository;
    private GateRepository gateRepository;

    public GateAssignmentService(FlightRepository flightRepository, GateRepository gateRepository) {
        this.flightRepository = flightRepository;
        this.gateRepository = gateRepository;
    }

    /**
     * Assigns flights to gates using Bipartite Matching.
     *
     * @return a map from flight number to assigned gate ID
     */
    public Map<String, String> assignFlightsToGates() {
        List<Flight> allFlights = flightRepository.getAllFlights();
        List<Gate> availableGates = gateRepository.findAvailableGates();

        // Filter flights that need assignment
        List<Flight> unassignedFlights = new ArrayList<>();
        for (Flight f : allFlights) {
            if (f.getAssignedGate() == null || f.getAssignedGate().equals("Unassigned")) {
                unassignedFlights.add(f);
            }
        }

        // Also include flights that could be reassigned
        List<Flight> assignableFlights = new ArrayList<>();
        for (Flight f : allFlights) {
            if (!f.getStatus().equals("Cancelled")) {
                assignableFlights.add(f);
            }
        }

        int leftSize = assignableFlights.size();
        int rightSize = availableGates.size();

        if (leftSize == 0 || rightSize == 0) {
            return new HashMap<>();
        }

        BipartiteMatching matching = new BipartiteMatching(leftSize, rightSize);

        // Build bipartite graph based on compatibility
        for (int i = 0; i < leftSize; i++) {
            Flight flight = assignableFlights.get(i);
            for (int j = 0; j < rightSize; j++) {
                Gate gate = availableGates.get(j);
                if (isCompatible(flight, gate)) {
                    matching.addEdge(i, j);
                }
            }
        }

        int matchCount = matching.maxMatching();
        int[] leftMatch = matching.getLeftMatching();

        // Build result map
        Map<String, String> assignments = new HashMap<>();
        for (int i = 0; i < leftSize; i++) {
            if (leftMatch[i] != -1) {
                Flight flight = assignableFlights.get(i);
                Gate gate = availableGates.get(leftMatch[i]);
                assignments.put(flight.getFlightNumber(), gate.getGateId());

                // Update the flight's assigned gate
                flight.setAssignedGate(gate.getGateId());
            }
        }

        return assignments;
    }

    /**
     * Checks if a flight is compatible with a gate.
     */
    private boolean isCompatible(Flight flight, Gate gate) {
        if (!gate.isAvailable()) {
            return false;
        }

        // Simple compatibility: domestic flights -> domestic gates, international -> international
        boolean isInternational = flight.getDestination().equals("London")
                || flight.getDestination().equals("Dubai")
                || flight.getDestination().equals("Singapore");

        if (isInternational) {
            return gate.getGateType().equals("International");
        } else {
            return gate.getGateType().equals("Domestic") || gate.getGateType().equals("International");
        }
    }

    /**
     * Returns all flights.
     */
    public List<Flight> getAllFlights() {
        return flightRepository.getAllFlights();
    }

    /**
     * Returns all available gates.
     */
    public List<Gate> getAvailableGates() {
        return gateRepository.findAvailableGates();
    }
}

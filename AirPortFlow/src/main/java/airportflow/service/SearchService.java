package airportflow.service;

import airportflow.dsa.string.AhoCorasick;
import airportflow.dsa.string.KMP;
import airportflow.dsa.string.RabinKarp;
import airportflow.dsa.string.ZFunction;
import airportflow.model.Flight;
import airportflow.repository.FlightRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for searching flights and airport-related text.
 * Uses DSA string algorithms (KMP, Rabin-Karp, Z-Function, Aho-Corasick).
 */
public class SearchService {

    private FlightRepository flightRepository;

    public SearchService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    /**
     * Searches for flights by flight number using KMP.
     */
    public List<Flight> searchByFlightNumber(String query) {
        List<Flight> results = new ArrayList<>();
        List<Flight> allFlights = flightRepository.getAllFlights();

        for (Flight flight : allFlights) {
            if (KMP.contains(flight.getFlightNumber(), query)) {
                results.add(flight);
            }
        }

        return results;
    }

    /**
     * Searches for flights by airline using Rabin-Karp.
     */
    public List<Flight> searchByAirline(String query) {
        List<Flight> results = new ArrayList<>();
        List<Flight> allFlights = flightRepository.getAllFlights();

        for (Flight flight : allFlights) {
            if (RabinKarp.contains(flight.getAirline(), query)) {
                results.add(flight);
            }
        }

        return results;
    }

    /**
     * Searches for flights by source or destination using Z-Function.
     */
    public List<Flight> searchByRoute(String query) {
        List<Flight> results = new ArrayList<>();
        List<Flight> allFlights = flightRepository.getAllFlights();

        for (Flight flight : allFlights) {
            if (ZFunction.search(flight.getSource(), query).size() > 0 ||
                ZFunction.search(flight.getDestination(), query).size() > 0) {
                results.add(flight);
            }
        }

        return results;
    }

    /**
     * Searches for multiple patterns in flight data using Aho-Corasick.
     */
    public List<AhoCorasick.MatchResult> searchMultiplePatterns(String text, String[] patterns) {
        AhoCorasick ac = new AhoCorasick();
        for (int i = 0; i < patterns.length; i++) {
            ac.addPattern(patterns[i], i);
        }
        ac.buildFailureLinks();
        return ac.search(text, patterns);
    }

    /**
     * Returns all flights for display.
     */
    public List<Flight> getAllFlights() {
        return flightRepository.getAllFlights();
    }
}

package airportflow.service;

import airportflow.model.Flight;
import airportflow.model.Gate;
import airportflow.repository.FlightRepository;
import airportflow.repository.GateRepository;
import airportflow.dsa.randomized.RandomizedQuickSort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Service for managing flights and gates.
 * Handles CRUD operations and display logic.
 */
public class FlightService {

    private FlightRepository flightRepository;
    private GateRepository gateRepository;

    public FlightService(FlightRepository flightRepository, GateRepository gateRepository) {
        this.flightRepository = flightRepository;
        this.gateRepository = gateRepository;
    }

    /**
     * Returns all flights.
     */
    public List<Flight> getAllFlights() {
        return flightRepository.getAllFlights();
    }

    /**
     * Finds a flight by flight number.
     */
    public Flight findFlight(String flightNumber) {
        return flightRepository.findByFlightNumber(flightNumber);
    }

    /**
     * Returns all gates.
     */
    public List<Gate> getAllGates() {
        return gateRepository.getAllGates();
    }

    /**
     * Sorts flights by the given field using Randomized QuickSort.
     */
    public List<Flight> sortFlights(String sortBy) {
        List<Flight> flights = flightRepository.getAllFlights();

        switch (sortBy.toLowerCase()) {
            case "flightnumber":
            case "number":
                // Sort by flight number
                Flight[] byNumber = new Flight[flights.size()];
                flights.toArray(byNumber);
                RandomizedQuickSort.sort(byNumber, (f1, f2) -> f1.getFlightNumber().compareTo(f2.getFlightNumber()));
                List<Flight> result1 = new ArrayList<>();
                Collections.addAll(result1, byNumber);
                return result1;

            case "time":
            case "scheduledtime":
                // Sort by scheduled time
                Flight[] byTime = new Flight[flights.size()];
                flights.toArray(byTime);
                RandomizedQuickSort.sort(byTime, (f1, f2) -> f1.getScheduledTime().compareTo(f2.getScheduledTime()));
                List<Flight> result2 = new ArrayList<>();
                Collections.addAll(result2, byTime);
                return result2;

            case "passengers":
            case "passengercount":
                // Sort by passenger count (descending)
                Flight[] byPassengers = new Flight[flights.size()];
                flights.toArray(byPassengers);
                RandomizedQuickSort.sort(byPassengers, (f1, f2) -> Integer.compare(f2.getPassengerCount(), f1.getPassengerCount()));
                List<Flight> result3 = new ArrayList<>();
                Collections.addAll(result3, byPassengers);
                return result3;

            default:
                return flights;
        }
    }
}

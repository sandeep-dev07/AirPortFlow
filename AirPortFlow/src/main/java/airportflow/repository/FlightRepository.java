package airportflow.repository;

import airportflow.model.Flight;
import java.util.ArrayList;
import java.util.List;

/**
 * In-memory repository for Flight records.
 * Uses Java collections for data storage (no database).
 */
public class FlightRepository {

    private List<Flight> flights;

    public FlightRepository() {
        this.flights = new ArrayList<>();
    }

    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    public List<Flight> getAllFlights() {
        return new ArrayList<>(flights);
    }

    public Flight findByFlightNumber(String flightNumber) {
        for (Flight f : flights) {
            if (f.getFlightNumber().equalsIgnoreCase(flightNumber)) {
                return f;
            }
        }
        return null;
    }

    public void clear() {
        flights.clear();
    }

    public int size() {
        return flights.size();
    }
}

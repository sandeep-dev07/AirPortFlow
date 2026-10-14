package airportflow.model;

/**
 * Represents a passenger in the airport system.
 */
public class Passenger {

    private String passengerId;
    private String name;
    private String flightNumber;

    public Passenger(String passengerId, String name, String flightNumber) {
        this.passengerId = passengerId;
        this.name = name;
        this.flightNumber = flightNumber;
    }

    public String getPassengerId() {
        return passengerId;
    }

    public String getName() {
        return name;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    @Override
    public String toString() {
        return String.format("%-8s | %-20s | Flight: %s",
                passengerId, name, flightNumber);
    }
}

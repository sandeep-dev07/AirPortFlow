package airportflow.model;

/**
 * Represents a flight in the airport system.
 */
public class Flight {

    private String flightNumber;
    private String airline;
    private String source;
    private String destination;
    private String scheduledTime;
    private String status;
    private int passengerCount;
    private String assignedGate;

    public Flight(String flightNumber, String airline, String source,
                  String destination, String scheduledTime,
                  String status, int passengerCount, String assignedGate) {
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.source = source;
        this.destination = destination;
        this.scheduledTime = scheduledTime;
        this.status = status;
        this.passengerCount = passengerCount;
        this.assignedGate = assignedGate;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getAirline() {
        return airline;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String getScheduledTime() {
        return scheduledTime;
    }

    public String getStatus() {
        return status;
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    public String getAssignedGate() {
        return assignedGate;
    }

    public void setAssignedGate(String assignedGate) {
        this.assignedGate = assignedGate;
    }

    @Override
    public String toString() {
        return String.format("%-10s | %-12s | %-8s -> %-8s | %-12s | %-10s | Passengers: %3d | Gate: %s",
                flightNumber, airline, source, destination,
                scheduledTime, status, passengerCount,
                assignedGate != null ? assignedGate : "Unassigned");
    }
}

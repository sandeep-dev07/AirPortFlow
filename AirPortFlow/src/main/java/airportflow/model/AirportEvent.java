package airportflow.model;

/**
 * Represents an event occurring at the airport.
 * Events simulate continuous airport activity such as
 * flight updates, gate changes, security alerts, etc.
 */
public class AirportEvent {

    private String eventId;
    private String flightNumber;
    private String eventType;
    private long timestamp;
    private String description;

    public AirportEvent(String eventId, String flightNumber,
                        String eventType, long timestamp, String description) {
        this.eventId = eventId;
        this.flightNumber = flightNumber;
        this.eventType = eventType;
        this.timestamp = timestamp;
        this.description = description;
    }

    public String getEventId() {
        return eventId;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getEventType() {
        return eventType;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | Flight: %-8s | %s",
                eventId, eventType, flightNumber, description);
    }
}

package airportflow.repository;

import airportflow.model.AirportEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * In-memory repository for AirportEvent records.
 */
public class EventRepository {

    private List<AirportEvent> events;

    public EventRepository() {
        this.events = new ArrayList<>();
    }

    public void addEvent(AirportEvent event) {
        events.add(event);
    }

    public List<AirportEvent> getAllEvents() {
        return new ArrayList<>(events);
    }

    public void clear() {
        events.clear();
    }

    public int size() {
        return events.size();
    }
}

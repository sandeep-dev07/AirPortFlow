package airportflow.service;

import airportflow.dsa.randomized.BlellochScan;
import airportflow.dsa.randomized.BrentTheorem;
import airportflow.dsa.randomized.MillerRabin;
import airportflow.dsa.randomized.ParallelReduce;
import airportflow.dsa.randomized.ReservoirSampling;
import airportflow.model.AirportEvent;
import airportflow.repository.EventRepository;
import airportflow.repository.FlightRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for processing airport events and demonstrating randomized/parallel algorithms.
 */
public class EventService {

    private EventRepository eventRepository;
    private FlightRepository flightRepository;
    private ReservoirSampling reservoirSampling;

    public EventService(EventRepository eventRepository, FlightRepository flightRepository) {
        this.eventRepository = eventRepository;
        this.flightRepository = flightRepository;
        this.reservoirSampling = new ReservoirSampling(5); // Keep sample of 5 events
    }

    /**
     * Adds a new event to the repository and updates the reservoir sample.
     */
    public void addEvent(AirportEvent event) {
        eventRepository.addEvent(event);
        reservoirSampling.sample(event);
    }

    /**
     * Returns all events.
     */
    public List<AirportEvent> getAllEvents() {
        return eventRepository.getAllEvents();
    }

    /**
     * Returns the current reservoir sample of events.
     */
    public List<Object> getSampleEvents() {
        return reservoirSampling.getSample();
    }

    /**
     * Demonstrates Reservoir Sampling with simulated event stream.
     */
    public void demonstrateReservoirSampling() {
        System.out.println("=== Reservoir Sampling Demo ===");
        System.out.println("Airport context: Maintaining a representative sample from event stream.");
        System.out.println();

        List<AirportEvent> allEvents = eventRepository.getAllEvents();

        // Process all events through the reservoir
        reservoirSampling.clear();
        reservoirSampling.sampleAll(allEvents);

        System.out.println("Total events processed: " + reservoirSampling.getCount());
        System.out.println("Sample size: " + reservoirSampling.getSampleSize());
        System.out.println("Sampled events:");
        List<Object> sample = reservoirSampling.getSample();
        for (Object obj : sample) {
            if (obj instanceof AirportEvent) {
                AirportEvent event = (AirportEvent) obj;
                System.out.println("  [" + event.getEventId() + "] " + event.getEventType());
            }
        }
        System.out.println();
    }

    /**
     * Demonstrates Miller-Rabin primality test.
     */
    public void demonstrateMillerRabin() {
        MillerRabin.demonstrate();
    }

    /**
     * Demonstrates Blelloch Scan with airport data.
     */
    public void demonstrateBlellochScan() {
        BlellochScan.demonstrate();
    }

    /**
     * Demonstrates Parallel Reduce with airport statistics.
     */
    public void demonstrateParallelReduce() {
        ParallelReduce.demonstrate();
    }

    /**
     * Demonstrates Brent's Theorem.
     */
    public void demonstrateBrentTheorem() {
        BrentTheorem.demonstrate();
    }
}

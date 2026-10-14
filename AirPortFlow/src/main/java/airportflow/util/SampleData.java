package airportflow.util;

import airportflow.model.AirportEvent;
import airportflow.model.Flight;
import airportflow.model.Gate;
import airportflow.model.Passenger;
import airportflow.repository.EventRepository;
import airportflow.repository.FlightRepository;
import airportflow.repository.GateRepository;

/**
 * Provides fixed sample data for the AirportFlow application.
 * Creates realistic airport data for demonstration purposes.
 */
public class SampleData {

    /**
     * Initializes the repositories with fixed sample data.
     *
     * @param flightRepo  the flight repository to populate
     * @param gateRepo    the gate repository to populate
     * @param eventRepo   the event repository to populate
     */
    public static void loadSampleData(FlightRepository flightRepo,
                                      GateRepository gateRepo,
                                      EventRepository eventRepo) {
        // Sample flights (10 flights)
        flightRepo.addFlight(new Flight("AI101", "Air India", "Delhi", "Mumbai",
                "08:00", "On Time", 180, "G1"));
        flightRepo.addFlight(new Flight("6E202", "IndiGo", "Bangalore", "Chennai",
                "09:15", "Delayed", 150, "G2"));
        flightRepo.addFlight(new Flight("SG303", "SpiceJet", "Kolkata", "Hyderabad",
                "10:30", "On Time", 120, null));
        flightRepo.addFlight(new Flight("AI404", "Air India", "Mumbai", "Delhi",
                "11:45", "Boarding", 200, "G3"));
        flightRepo.addFlight(new Flight("6E505", "IndiGo", "Chennai", "Bangalore",
                "13:00", "On Time", 160, "G4"));
        flightRepo.addFlight(new Flight("SG606", "SpiceJet", "Hyderabad", "Kolkata",
                "14:20", "Cancelled", 0, null));
        flightRepo.addFlight(new Flight("AI707", "Air India", "Delhi", "Goa",
                "15:35", "On Time", 175, "G5"));
        flightRepo.addFlight(new Flight("6E808", "IndiGo", "Pune", "Nagpur",
                "16:50", "Delayed", 130, null));
        flightRepo.addFlight(new Flight("SG909", "SpiceJet", "Goa", "Delhi",
                "18:05", "On Time", 140, "G6"));
        flightRepo.addFlight(new Flight("AI010", "Air India", "Mumbai", "Bangalore",
                "19:30", "Scheduled", 190, "G7"));

        // Sample gates (7 gates)
        gateRepo.addGate(new Gate("G1", "1", true, "Domestic", "Domestic"));
        gateRepo.addGate(new Gate("G2", "1", true, "Domestic", "Domestic"));
        gateRepo.addGate(new Gate("G3", "2", true, "International", "International"));
        gateRepo.addGate(new Gate("G4", "2", true, "Domestic", "Domestic"));
        gateRepo.addGate(new Gate("G5", "1", true, "Domestic", "Domestic"));
        gateRepo.addGate(new Gate("G6", "3", false, "International", "International"));
        gateRepo.addGate(new Gate("G7", "1", true, "Domestic", "Domestic"));

        // Sample airport events (12 events)
        long baseTime = System.currentTimeMillis() / 1000;
        eventRepo.addEvent(new AirportEvent("EVT001", "AI101", "DEPARTURE",
                baseTime, "Flight AI101 departed on time"));
        eventRepo.addEvent(new AirportEvent("EVT002", "6E202", "DELAY",
                baseTime + 300, "Flight 6E202 delayed by 30 minutes"));
        eventRepo.addEvent(new AirportEvent("EVT003", "SG303", "ARRIVAL",
                baseTime + 600, "Flight SG303 arrived at gate G2"));
        eventRepo.addEvent(new AirportEvent("EVT004", "AI404", "BOARDING",
                baseTime + 900, "Boarding started for AI404"));
        eventRepo.addEvent(new AirportEvent("EVT005", "6E505", "GATE_CHANGE",
                baseTime + 1200, "Gate changed from G4 to G5"));
        eventRepo.addEvent(new AirportEvent("EVT006", "SG606", "CANCELLATION",
                baseTime + 1500, "Flight SG606 cancelled due to weather"));
        eventRepo.addEvent(new AirportEvent("EVT007", "AI707", "DEPARTURE",
                baseTime + 1800, "Flight AI707 departed from G5"));
        eventRepo.addEvent(new AirportEvent("EVT008", "6E808", "DELAY",
                baseTime + 2100, "Flight 6E808 delayed by 45 minutes"));
        eventRepo.addEvent(new AirportEvent("EVT009", "SG909", "ARRIVAL",
                baseTime + 2400, "Flight SG909 landed safely"));
        eventRepo.addEvent(new AirportEvent("EVT010", "AI010", "SCHEDULED",
                baseTime + 2700, "Flight AI010 scheduled for evening"));
        eventRepo.addEvent(new AirportEvent("EVT011", "AI101", "SECURITY",
                baseTime + 3000, "Security checkpoint cleared for AI101"));
        eventRepo.addEvent(new AirportEvent("EVT012", "6E202", "BAGGAGE",
                baseTime + 3300, "Baggage belt activated for 6E202"));
    }

    /**
     * Returns a list of sample passengers.
     */
    public static java.util.List<Passenger> getSamplePassengers() {
        java.util.List<Passenger> passengers = new java.util.ArrayList<>();
        passengers.add(new Passenger("P001", "Rahul Sharma", "AI101"));
        passengers.add(new Passenger("P002", "Priya Patel", "AI101"));
        passengers.add(new Passenger("P003", "Amit Kumar", "6E202"));
        passengers.add(new Passenger("P004", "Sneha Reddy", "SG303"));
        passengers.add(new Passenger("P005", "Vikram Singh", "AI404"));
        passengers.add(new Passenger("P006", "Anjali Gupta", "6E505"));
        passengers.add(new Passenger("P007", "Rohan Mehta", "AI707"));
        passengers.add(new Passenger("P008", "Kavya Nair", "SG909"));
        return passengers;
    }
}

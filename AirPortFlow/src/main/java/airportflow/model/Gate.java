package airportflow.model;

/**
 * Represents a gate at the airport.
 */
public class Gate {

    private String gateId;
    private String terminal;
    private boolean available;
    private String gateType;
    private String compatibleFlightType;

    public Gate(String gateId, String terminal, boolean available,
                String gateType, String compatibleFlightType) {
        this.gateId = gateId;
        this.terminal = terminal;
        this.available = available;
        this.gateType = gateType;
        this.compatibleFlightType = compatibleFlightType;
    }

    public String getGateId() {
        return gateId;
    }

    public String getTerminal() {
        return terminal;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getGateType() {
        return gateType;
    }

    public String getCompatibleFlightType() {
        return compatibleFlightType;
    }

    @Override
    public String toString() {
        return String.format("%-6s | Terminal %-2s | %-10s | %-12s | %s",
                gateId, terminal, gateType,
                compatibleFlightType,
                available ? "Available" : "Occupied");
    }
}

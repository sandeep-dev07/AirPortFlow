package airportflow.repository;

import airportflow.model.Gate;
import java.util.ArrayList;
import java.util.List;

/**
 * In-memory repository for Gate records.
 */
public class GateRepository {

    private List<Gate> gates;

    public GateRepository() {
        this.gates = new ArrayList<>();
    }

    public void addGate(Gate gate) {
        gates.add(gate);
    }

    public List<Gate> getAllGates() {
        return new ArrayList<>(gates);
    }

    public Gate findByGateId(String gateId) {
        for (Gate g : gates) {
            if (g.getGateId().equalsIgnoreCase(gateId)) {
                return g;
            }
        }
        return null;
    }

    public List<Gate> findAvailableGates() {
        List<Gate> available = new ArrayList<>();
        for (Gate g : gates) {
            if (g.isAvailable()) {
                available.add(g);
            }
        }
        return available;
    }

    public void clear() {
        gates.clear();
    }

    public int size() {
        return gates.size();
    }
}

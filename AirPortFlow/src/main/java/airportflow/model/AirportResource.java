package airportflow.model;

/**
 * Represents a general airport resource (e.g., runway, baggage belt,
 * security lane). Used in resource optimization demonstrations.
 */
public class AirportResource {

    private String resourceId;
    private String resourceType;
    private int capacity;
    private boolean available;
    private int cost; // Cost or priority for optimization

    public AirportResource(String resourceId, String resourceType,
                           int capacity, boolean available, int cost) {
        this.resourceId = resourceId;
        this.resourceType = resourceType;
        this.capacity = capacity;
        this.available = available;
        this.cost = cost;
    }

    public String getResourceId() {
        return resourceId;
    }

    public String getResourceType() {
        return resourceType;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isAvailable() {
        return available;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return String.format("%-6s | %-15s | Capacity: %3d | Available: %-5s | Cost: %d",
                resourceId, resourceType, capacity,
                available ? "Yes" : "No", cost);
    }
}

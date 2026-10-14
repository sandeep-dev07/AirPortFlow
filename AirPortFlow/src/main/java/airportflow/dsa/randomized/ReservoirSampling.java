package airportflow.dsa.randomized;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Reservoir Sampling Algorithm.
 *
 * Maintains a representative random sample from a stream of unknown length.
 * Each element has an equal probability of being in the sample.
 * Time complexity: O(1) per element.
 *
 * AirportFlow use case: Maintain a representative sample from continuous
 * airport events without storing all events in memory.
 */
public class ReservoirSampling {

    private static final Random RANDOM = new Random();

    private Object[] reservoir;
    private int sampleSize;
    private int count; // Total items seen

    /**
     * Creates a reservoir sampler.
     *
     * @param sampleSize the size of the reservoir sample
     */
    public ReservoirSampling(int sampleSize) {
        this.sampleSize = sampleSize;
        this.reservoir = new Object[sampleSize];
        this.count = 0;
    }

    /**
     * Processes a new item from the stream.
     * Each item has equal probability of being kept in the reservoir.
     */
    public void sample(Object item) {
        count++;

        if (count <= sampleSize) {
            // Fill the reservoir initially
            reservoir[count - 1] = item;
        } else {
            // Randomly decide whether to include the new item
            int j = RANDOM.nextInt(count);
            if (j < sampleSize) {
                reservoir[j] = item;
            }
        }
    }

    /**
     * Processes a batch of items.
     */
    public void sampleAll(List<?> items) {
        for (Object item : items) {
            sample(item);
        }
    }

    /**
     * Returns the current reservoir sample.
     */
    public List<Object> getSample() {
        List<Object> result = new ArrayList<>();
        for (int i = 0; i < Math.min(count, sampleSize); i++) {
            if (reservoir[i] != null) {
                result.add(reservoir[i]);
            }
        }
        return result;
    }

    /**
     * Returns the total number of items processed.
     */
    public int getCount() {
        return count;
    }

    /**
     * Returns the sample size.
     */
    public int getSampleSize() {
        return sampleSize;
    }

    /**
     * Clears the reservoir.
     */
    public void clear() {
        reservoir = new Object[sampleSize];
        count = 0;
    }
}

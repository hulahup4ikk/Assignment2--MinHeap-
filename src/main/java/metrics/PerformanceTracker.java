package metrics;

public class PerformanceTracker {

    private long comparisons = 0;
    private long swaps = 0;
    private long accesses = 0;
    private long allocations = 0; // ← добавили

    public void incrementComparisons() { comparisons++; }
    public void incrementSwaps() { swaps++; }
    public void incrementAccesses() { accesses++; }
    public void incrementAllocations() { allocations++; } // ← добавили

    public long getComparisons() { return comparisons; }
    public long getSwaps() { return swaps; }
    public long getAccesses() { return accesses; }
    public long getAllocations() { return allocations; } // ← добавили

    public void reset() {
        comparisons = swaps = accesses = allocations = 0;
    }

    @Override
    public String toString() {
        return String.format(
                "Comparisons=%d | Swaps=%d | Accesses=%d | Allocations=%d",
                comparisons, swaps, accesses, allocations
        );
    }
}

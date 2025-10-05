package metrics;

public class PerformanceTracker {
    private long comparisons = 0;
    private long swaps = 0;
    private long accesses = 0;

    public void incrementComparisons() { comparisons++; }
    public void incrementSwaps() { swaps++; }
    public void incrementAccesses() { accesses++; }

    public long getComparisons() { return comparisons; }
    public long getSwaps() { return swaps; }
    public long getAccesses() { return accesses; }

    public void reset() {
        comparisons = swaps = accesses = 0;
    }

    @Override
    public String toString() {
        return String.format("Comparisons=%d | Swaps=%d | Accesses=%d",
                comparisons, swaps, accesses);
    }
}

package cli;

import algorithms.MyMinHeap;
import metrics.PerformanceTracker;
import java.util.Random;
import util.CSVExporter;

public class BenchmarkRunner {
    public static void main(String[] args) {
        PerformanceTracker tracker = new PerformanceTracker();
        MyMinHeap<Integer> heap = new MyMinHeap<>(tracker);
        Random rand = new Random();

        int[] sizes = {100, 1000, 10000, 100000};

        System.out.println("=== Benchmarking MinHeap ===");

        for (int n : sizes) {
            tracker.reset();

            for (int i = 0; i < n; i++)
                heap.insert(rand.nextInt(1_000_000));

            long start = System.nanoTime();
            while (!heap.isEmpty())
                heap.extractMin();
            long end = System.nanoTime();

            double ms = (end - start) / 1e6;

            System.out.printf("n=%d | time=%.3f ms | %s%n", n, ms, tracker);

            CSVExporter.saveResult(
                    n,                        // array size
                    ms,                       // execution time
                    tracker.getComparisons(), // number of comparisons
                    tracker.getSwaps(),       // number of exchanges
                    tracker.getAccesses(),    // array accesses
                    "MinHeap"                 // algorithm name
            );
        }

        System.out.println("\n All results are saved in results.csv");
    }
}

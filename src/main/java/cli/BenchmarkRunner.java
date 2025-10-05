package cli;

import algorithms.MyMinHeap;
import metrics.PerformanceTracker;
import java.util.Random;

public class BenchmarkRunner {
    public static void main(String[] args) {
        PerformanceTracker tracker = new PerformanceTracker();
        MyMinHeap<Integer> heap = new MyMinHeap<>(tracker);
        Random rand = new Random();

        int[] sizes = {100, 1000, 10000, 100000};

        for (int n : sizes) {
            tracker.reset();
            for (int i = 0; i < n; i++)
                heap.insert(rand.nextInt(1_000_000));

            long start = System.nanoTime();
            while (!heap.isEmpty()) heap.extractMin();
            long end = System.nanoTime();

            double ms = (end - start) / 1e6;
            System.out.printf("n=%d | time=%.3f ms | %s%n", n, ms, tracker);
        }
    }
}

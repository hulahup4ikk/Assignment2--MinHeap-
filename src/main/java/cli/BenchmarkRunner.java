package cli;

import algorithms.MyMinHeap;
import metrics.PerformanceTracker;
import util.CSVExporter;

import java.util.Arrays;
import java.util.Random;


public class BenchmarkRunner {

    private static final int[] SIZES = {100, 1000, 10000, 100000};
    private static final int RUNS_PER_SIZE = 5;

    public static void main(String[] args) {
        Random rand = new Random();

        System.out.println("===== MIN-HEAP SCALABILITY BENCHMARK =====");
        System.out.printf("%-10s %-15s %-12s %-20s%n", "n", "Input Type", "Avg Time (ms)", "Memory Used (MB)");
        System.out.println("-------------------------------------------------------------");

        for (int n : SIZES) {
            int[] randomArr = generateRandomArray(n, rand);
            int[] sortedArr = generateSortedArray(n);
            int[] reverseArr = generateReverseArray(n);
            int[] nearlyArr = generateNearlySortedArray(n, rand);

            runBenchmarkForType(n, randomArr, "random");
            runBenchmarkForType(n, sortedArr, "sorted");
            runBenchmarkForType(n, reverseArr, "reverse");
            runBenchmarkForType(n, nearlyArr, "nearly-sorted");
        }

        System.out.println("\nResults saved to results.csv");
    }

    // === Benchmark for one dataset type ===
    private static void runBenchmarkForType(int n, int[] data, String type) {
        PerformanceTracker tracker = new PerformanceTracker();
        double totalTime = 0;
        double totalMemoryMB = 0;

        for (int run = 0; run < RUNS_PER_SIZE; run++) {
            tracker.reset();
            MyMinHeap<Integer> heap = new MyMinHeap<>(tracker);
            Runtime rt = Runtime.getRuntime();

            // memory before
            System.gc();
            long beforeMem = rt.totalMemory() - rt.freeMemory();

            // insert all
            for (int x : data) heap.insert(x);

            long start = System.nanoTime();
            while (!heap.isEmpty()) heap.extractMin();
            long end = System.nanoTime();

            // memory after
            long afterMem = rt.totalMemory() - rt.freeMemory();
            double usedMB = Math.max(0, (afterMem - beforeMem) / 1e6);
            totalMemoryMB += usedMB;

            double ms = (end - start) / 1e6;
            totalTime += ms;
        }

        double avgTime = totalTime / RUNS_PER_SIZE;
        double avgMem = totalMemoryMB / RUNS_PER_SIZE;

        System.out.printf("%-10d %-15s %-12.3f %-20.2f%n", n, type, avgTime, avgMem);

        CSVExporter.saveResult(
                n,
                avgTime,
                tracker.getComparisons(),
                tracker.getSwaps(),
                tracker.getAccesses(),
                tracker.getAllocations(),
                avgMem,
                type
        );
    }

    // === Generators ===

    private static int[] generateRandomArray(int n, Random rand) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = rand.nextInt(1_000_000);
        return arr;
    }

    private static int[] generateSortedArray(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = i;
        return arr;
    }

    private static int[] generateReverseArray(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = n - i;
        return arr;
    }

    private static int[] generateNearlySortedArray(int n, Random rand) {
        int[] arr = generateSortedArray(n);
        int swaps = Math.max(1, n / 10); // 10% случайных перестановок
        for (int i = 0; i < swaps; i++) {
            int a = rand.nextInt(n);
            int b = rand.nextInt(n);
            int tmp = arr[a];
            arr[a] = arr[b];
            arr[b] = tmp;
        }
        return arr;
    }
}

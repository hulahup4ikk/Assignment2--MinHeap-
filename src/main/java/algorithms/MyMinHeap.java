package algorithms;

import java.util.ArrayList;
import metrics.PerformanceTracker;


public class MyMinHeap<T extends Comparable<T>> {
    private final ArrayList<T> heap = new ArrayList<>();
    private final PerformanceTracker tracker;

    public MyMinHeap(PerformanceTracker tracker) {
        this.tracker = tracker;
    }

    public void insert(T value) {
        int oldCapacity = heap.size();
        heap.add(value);
        tracker.incrementAccesses();

        if (heap.size() > oldCapacity && heap.size() % 2 == 0)
            tracker.incrementAllocations();

        heapifyUp(heap.size() - 1);
    }

    public T extractMin() {
        if (heap.isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }
        T min = heap.get(0);
        tracker.incrementAccesses(); // read root

        T last = heap.remove(heap.size() - 1);
        tracker.incrementAccesses(); // remove read+write (amortized as 1)

        if (!heap.isEmpty()) {
            heap.set(0, last);
            tracker.incrementAccesses(); // write root
            heapifyDown(0);
        }
        return min;
    }

    public void decreaseKey(int index, T newValue) {
        if (index < 0 || index >= heap.size()) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        if (newValue == null) {
            throw new IllegalArgumentException("New value must not be null");
        }

        tracker.incrementAccesses();
        T oldValue = heap.get(index);

        tracker.incrementComparisons();
        if (newValue.compareTo(oldValue) > 0) {
            throw new IllegalArgumentException(
                    "New value (" + newValue + ") must not be greater than current (" + oldValue + ")");
        }

        heap.set(index, newValue);
        tracker.incrementAccesses();

        heapifyUp(index);
    }


    public void merge(MyMinHeap<T> other) {
        if (other == null) {
            throw new IllegalArgumentException("Cannot merge with null heap");
        }
        if (other == this) {
            throw new IllegalArgumentException("Cannot merge heap with itself");
        }

        heap.addAll(other.heap);
        tracker.incrementAccesses();
        tracker.incrementAllocations();

        buildHeap();
    }

    private void heapifyUp(int i) {
        while (i > 0) {
            int p = (i - 1) / 2;

            T cur = heap.get(i);
            T par = heap.get(p);
            tracker.incrementAccesses();
            tracker.incrementAccesses();

            tracker.incrementComparisons();
            if (cur.compareTo(par) >= 0) break;

            swap(i, p);
            i = p;
        }
    }

    private void heapifyDown(int i) {
        int n = heap.size();
        while (true) {
            int l = 2 * i + 1;
            int r = 2 * i + 2;
            int smallest = i;
            T smallestVal = heap.get(i);
            tracker.incrementAccesses();

            if (l < n) {
                T left = heap.get(l);
                tracker.incrementAccesses();
                tracker.incrementComparisons();
                if (left.compareTo(smallestVal) < 0) {
                    smallest = l;
                    smallestVal = left;
                }
            }

            if (r < n) {
                T right = heap.get(r);
                tracker.incrementAccesses();
                tracker.incrementComparisons();
                if (right.compareTo(smallestVal) < 0) {
                    smallest = r;
                    smallestVal = right;
                }
            }

            if (smallest == i) break;

            swap(i, smallest);
            i = smallest;
        }
    }

    private void swap(int i, int j) {
        tracker.incrementSwaps();
        T tmp = heap.get(i);
        T b   = heap.get(j);
        tracker.incrementAccesses(); // read i
        tracker.incrementAccesses(); // read j
        heap.set(i, b);
        heap.set(j, tmp);
        tracker.incrementAccesses(); // write i
        tracker.incrementAccesses(); // write j
    }

    public void buildHeap() {
        for (int i = heap.size() / 2 - 1; i >= 0; i--) {
            heapifyDown(i);
        }
    }


    public boolean isEmpty() { return heap.isEmpty(); }
    public int size() { return heap.size(); }
}

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
        heap.add(value);
        tracker.incrementAccesses(); // add write
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

        for (T item : other.heap) {
            if (item != null) {
                insert(item);
            }
        }
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

            if (l < n) {
                T left = heap.get(l);
                T smv  = heap.get(smallest);
                tracker.incrementAccesses();
                tracker.incrementAccesses();
                tracker.incrementComparisons();
                if (left.compareTo(smv) < 0) smallest = l;
            }
            if (r < n) {
                T right = heap.get(r);
                T smv   = heap.get(smallest);
                tracker.incrementAccesses();
                tracker.incrementAccesses();
                tracker.incrementComparisons();
                if (right.compareTo(smv) < 0) smallest = r;
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

    public boolean isEmpty() { return heap.isEmpty(); }
    public int size() { return heap.size(); }
    public ArrayList<T> getHeap() { return heap; } // for tests/debug
}

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
        tracker.incrementAccesses();
        heapifyUp(heap.size() - 1);
    }

    public T extractMin() {
        if (heap.isEmpty()) throw new IllegalStateException("Heap is empty");
        T min = heap.get(0);
        tracker.incrementAccesses();

        T last = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, last);
            heapifyDown(0);
        }
        return min;
    }

    public void decreaseKey(int index, T newValue) {
        if (newValue.compareTo(heap.get(index)) > 0)
            throw new IllegalArgumentException("New key is greater than current key");
        heap.set(index, newValue);
        heapifyUp(index);
    }

    public void merge(MyMinHeap<T> other) {
        for (T item : other.heap) {
            insert(item);
        }
    }

    private void heapifyUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            tracker.incrementComparisons();
            if (heap.get(index).compareTo(heap.get(parent)) >= 0) break;
            swap(index, parent);
            index = parent;
        }
    }

    private void heapifyDown(int index) {
        int size = heap.size();
        while (true) {
            int left = 2 * index + 1, right = 2 * index + 2, smallest = index;

            if (left < size && heap.get(left).compareTo(heap.get(smallest)) < 0)
                smallest = left;
            if (right < size && heap.get(right).compareTo(heap.get(smallest)) < 0)
                smallest = right;

            if (smallest == index) break;
            swap(index, smallest);
            index = smallest;
        }
    }

    private void swap(int i, int j) {
        tracker.incrementSwaps();
        T tmp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, tmp);
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public int size() {
        return heap.size();
    }

    public ArrayList<T> getHeap() {
        return heap;
    }
}

package algorithms;

import metrics.PerformanceTracker;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MyMinHeapTest {

    @Test
    void testInsertExtract() {
        PerformanceTracker tracker = new PerformanceTracker();
        MyMinHeap<Integer> heap = new MyMinHeap<>(tracker);
        heap.insert(5);
        heap.insert(2);
        heap.insert(8);
        assertEquals(2, heap.extractMin());
        assertEquals(2, tracker.getComparisons());
    }

    @Test
    void testDecreaseKey() {
        PerformanceTracker tracker = new PerformanceTracker();
        MyMinHeap<Integer> heap = new MyMinHeap<>(tracker);
        heap.insert(10);
        heap.insert(20);
        heap.decreaseKey(1, 5);
        assertEquals(5, heap.extractMin());
    }

    @Test
    void testEmptyHeap() {
        PerformanceTracker tracker = new PerformanceTracker();
        MyMinHeap<Integer> heap = new MyMinHeap<>(tracker);
        assertTrue(heap.isEmpty());
        assertThrows(IllegalStateException.class, heap::extractMin);
    }
}
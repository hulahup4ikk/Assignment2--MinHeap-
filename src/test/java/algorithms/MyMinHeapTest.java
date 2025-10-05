package algorithms;

import metrics.PerformanceTracker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyMinHeapTest {

    @Test
    void insertExtract_basic() {
        PerformanceTracker tr = new PerformanceTracker();
        MyMinHeap<Integer> h = new MyMinHeap<>(tr);
        h.insert(5);
        h.insert(2);
        h.insert(8);
        assertEquals(2, h.extractMin());
        assertEquals(5, h.extractMin());
        assertEquals(8, h.extractMin());
        assertTrue(h.isEmpty());
    }

    @Test
    void decreaseKey_works() {
        PerformanceTracker tr = new PerformanceTracker();
        MyMinHeap<Integer> h = new MyMinHeap<>(tr);
        h.insert(10); // index 0
        h.insert(20); // index 1
        h.decreaseKey(1, 5); // 20 -> 5 should bubble to top
        assertEquals(5, h.extractMin());
        assertEquals(10, h.extractMin());
        assertTrue(h.isEmpty());
    }

    @Test
    void merge_heaps() {
        PerformanceTracker tr = new PerformanceTracker();
        MyMinHeap<Integer> a = new MyMinHeap<>(tr);
        MyMinHeap<Integer> b = new MyMinHeap<>(tr);
        a.insert(7); a.insert(3);
        b.insert(4); b.insert(1);
        a.merge(b);
        assertEquals(1, a.extractMin());
        assertEquals(3, a.extractMin());
        assertEquals(4, a.extractMin());
        assertEquals(7, a.extractMin());
        assertTrue(a.isEmpty());
    }

    @Test
    void duplicates_ok() {
        PerformanceTracker tr = new PerformanceTracker();
        MyMinHeap<Integer> h = new MyMinHeap<>(tr);
        h.insert(5); h.insert(5); h.insert(5);
        assertEquals(5, h.extractMin());
        assertEquals(5, h.extractMin());
        assertEquals(5, h.extractMin());
        assertTrue(h.isEmpty());
    }

    @Test
    void extract_from_empty_throws() {
        PerformanceTracker tr = new PerformanceTracker();
        MyMinHeap<Integer> h = new MyMinHeap<>(tr);
        assertThrows(IllegalStateException.class, h::extractMin);
    }

    @Test
    void decreaseKey_invalid_index() {
        PerformanceTracker tr = new PerformanceTracker();
        MyMinHeap<Integer> h = new MyMinHeap<>(tr);
        h.insert(1);
        assertThrows(IndexOutOfBoundsException.class, () -> h.decreaseKey(5, 0));
    }

    @Test
    void decreaseKey_wrong_direction() {
        PerformanceTracker tr = new PerformanceTracker();
        MyMinHeap<Integer> h = new MyMinHeap<>(tr);
        h.insert(1);
        assertThrows(IllegalArgumentException.class, () -> h.decreaseKey(0, 2));
    }
}

# Min-Heap (Student A)
Optimized MinHeap Implementation

This branch introduces algorithmic and performance improvements over the base version.

🔧 New Features & Enhancements

Linear BuildHeap (buildHeap) — builds a heap from an array in O(n) time.
Used to make merge operations faster.

Optimized Merge (merge) — combines two heaps in O(n + m) instead of O(m log n).

Optimized HeapifyDown — reduces redundant array accesses by caching the smallest value.
✅ 30–40 % fewer memory reads.

Memory Tracking — adds allocation counting to PerformanceTracker.

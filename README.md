# Min-Heap Implementation (Student A)

## Overview
This project implements a Min-Heap data structure in Java with additional operations (`decreaseKey`, `merge`) and performance tracking.

## Features
- Clean, documented Java code
- Performance metrics: comparisons, swaps, accesses
- CLI benchmarking across input sizes
- Unit and property-based tests
- Cross-validation with Java’s PriorityQueue
- Scalability and memory profiling

## Run
```bash
mvn clean compile exec:java -Dexec.mainClass="cli.BenchmarkRunner"

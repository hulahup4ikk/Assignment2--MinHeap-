package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CSVExporter {
    private static final String FILE_NAME = "results.csv";
    private static boolean headerWritten = false;

    public static void saveResult(int n, double timeMs, long comparisons,
                                  long swaps, long accesses, long allocations,
                                  double memoryMB, String inputType) {
        String fileName = "results.csv";
        boolean fileExists = new File(fileName).exists();

        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName, true))) {
            if (!fileExists) {
                pw.println("n,inputType,timeMs,comparisons,swaps,accesses,allocations,memoryMB");
            }
            pw.printf("%d,%s,%.3f,%d,%d,%d,%d,%.2f%n",
                    n, inputType, timeMs, comparisons, swaps, accesses, allocations, memoryMB);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



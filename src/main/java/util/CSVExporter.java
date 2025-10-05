package util;

import java.io.FileWriter;
import java.io.IOException;

public class CSVExporter {
    private static final String FILE_NAME = "results.csv";
    private static boolean headerWritten = false;

    public static void saveResult(int n, double timeMs, long comps, long swaps, long accesses, String algorithm) {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {

            if (!headerWritten) {
                writer.write("Algorithm,ArraySize,Time(ms),Comparisons,Swaps,Accesses\n");
                headerWritten = true;
            }

            writer.write(String.format("%s,%d,%.4f,%d,%d,%d\n",
                    algorithm, n, timeMs, comps, swaps, accesses));

        } catch (IOException e) {
            System.err.println("Error when writing to CSV: " + e.getMessage());
        }
    }
}

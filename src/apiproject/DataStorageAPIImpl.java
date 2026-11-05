
package apiproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataStorageAPIImpl implements DataStorageAPI {

    private final String outputDestination;
    private boolean lastWriteSuccessful;

    public DataStorageAPIImpl() {
        this("output.txt");
    }

    public DataStorageAPIImpl(String outputDestination) {
        // An invalid destination is recorded as a failed write.
        this.outputDestination = outputDestination;
        this.lastWriteSuccessful = false;
    }

    @Override
    public List<Integer> readInputData(String input) {
        List<Integer> numbers = new ArrayList<>();

        // Validate the input file path.
        if (input == null || input.isBlank()) {
            return null;
        }

        try (Scanner scanner = new Scanner(new File(input))) {
            while (scanner.hasNext()) {
                if (!scanner.hasNextInt()) {
                    return null;
                }

                numbers.add(scanner.nextInt());
            }
        } catch (FileNotFoundException | SecurityException e) {
            return null;
        }

        return numbers;
    }

    @Override
    public void writeOutputData(String output) {
        lastWriteSuccessful = false;

        // Validate the output and destination.
        if (output == null
                || outputDestination == null
                || outputDestination.isBlank()) {
            return;
        }

        try (PrintWriter writer =
                new PrintWriter(outputDestination)) {
            writer.println(output);

            if (!writer.checkError()) {
                lastWriteSuccessful = true;
            }
        } catch (FileNotFoundException | SecurityException e) {
            lastWriteSuccessful = false;
        }
    }

    // Allows the coordinator to inspect the last write result.
    public boolean wasLastWriteSuccessful() {
        return lastWriteSuccessful;
    }
}

package apiproject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataStorageAPIImpl implements DataStorageAPI {

    private final String outputDestination;
    
    public DataStorageAPIImpl() {
    this.outputDestination = "output.txt";
}

    public DataStorageAPIImpl(String outputDestination) {
        this.outputDestination = outputDestination;
    }

    @Override
    public List<Integer> readInputData(String input) {
        List<Integer> numbers = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(input))) {
            while (scanner.hasNextInt()) {
                numbers.add(scanner.nextInt());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(
                    "Could not find input file: " + input, e);
        }

        return numbers;
    }

    @Override
    public void writeOutputData(String output) {
        try (PrintWriter writer = new PrintWriter(outputDestination)) {
            writer.println(output);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(
                    "Could not write to output file: "
                    + outputDestination, e);
        }
    }
}
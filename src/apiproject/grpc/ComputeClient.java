
package apiproject.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ComputeClient {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {

            System.out.println("=== Factor Computation Client ===");
            System.out.println("1. Use an input file");
            System.out.println("2. Type numbers directly");
            System.out.print("Choose option (1 or 2): ");

            String choice = scanner.nextLine().trim();
            String input;

            if ("1".equals(choice)) {
                System.out.print("Input file path: ");
                input = scanner.nextLine().trim();

            } else if ("2".equals(choice)) {
                System.out.print("Enter positive integers separated by spaces: ");
                String numbers = scanner.nextLine().trim();

                if (numbers.isBlank()) {
                    System.out.println("No numbers provided.");
                    return;
                }

                String[] values = numbers.split("\\s+");

                for (String value : values) {
                    try {
                        if (Integer.parseInt(value) <= 0) {
                            System.out.println("All numbers must be positive.");
                            return;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number: " + value);
                        return;
                    }
                }

                // The existing storage API expects a file path.
                // Create a temporary input file for typed numbers.
                try {
                    java.nio.file.Path tempFile =
                            java.nio.file.Files.createTempFile(
                                    "cp7-input-", ".txt");

                    java.nio.file.Files.writeString(tempFile, numbers);
                    tempFile.toFile().deleteOnExit();

                    input = tempFile.toAbsolutePath().toString();

                } catch (java.io.IOException e) {
                    System.out.println("Unable to prepare input data.");
                    return;
                }

            } else {
                System.out.println("Invalid option.");
                return;
            }

            System.out.print("Output file path: ");
            String output = scanner.nextLine().trim();

            System.out.print("Output delimiter (default comma): ");
            String delimiter = scanner.nextLine();

            if (delimiter.isEmpty()) {
                delimiter = ",";
            }

            ManagedChannel channel =
                    ManagedChannelBuilder.forAddress("localhost", 50051)
                            .usePlaintext()
                            .build();

            try {
                ComputeServiceGrpc.ComputeServiceBlockingStub stub =
                        ComputeServiceGrpc.newBlockingStub(channel)
                                .withDeadlineAfter(60, TimeUnit.SECONDS);

                JobResponse response = stub.configureJob(
                        JobRequest.newBuilder()
                                .setInputSource(input)
                                .setOutputDestination(output)
                                .setDelimiter(delimiter)
                                .build());

                System.out.println(response.getSuccess()
                        ? "Job completed successfully!"
                        : "Job failed: " + response.getMessage());

            } catch (StatusRuntimeException e) {
                System.out.println("Network error: " + e.getStatus());
            } finally {
                channel.shutdown();
            }
        }
    }
}

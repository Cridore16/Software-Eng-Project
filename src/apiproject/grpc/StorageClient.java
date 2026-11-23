package apiproject.grpc;

import apiproject.DataStorageAPI;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class StorageClient implements DataStorageAPI, AutoCloseable {

    private final ManagedChannel channel;
    private final StorageServiceGrpc.StorageServiceBlockingStub stub;
    private String outputDestination;
    private boolean lastWriteSuccessful;

    public StorageClient(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        stub = StorageServiceGrpc.newBlockingStub(channel)
                .withDeadlineAfter(30, TimeUnit.SECONDS);
    }

    public void setOutputDestination(String destination) {
        this.outputDestination = destination;
    }

    @Override
    public List<Integer> readInputData(String input) {
        try {
            ReadResponse response = stub.readInputData(
                    ReadRequest.newBuilder()
                            .setInput(input)
                            .build());
            return response.getNumbersList();
        } catch (io.grpc.StatusRuntimeException e) {
            return null;
        }
    }

    @Override
    public void writeOutputData(String output) {
        lastWriteSuccessful = false;

        if (outputDestination == null || outputDestination.isBlank()
                || output == null) {
            return;
        }

        try {
            WriteResponse response = stub.writeOutputData(
                    WriteRequest.newBuilder()
                            .setOutput(output)
                            .setDestination(outputDestination)
                            .build());

            lastWriteSuccessful = response.getSuccess();
        } catch (io.grpc.StatusRuntimeException e) {
            lastWriteSuccessful = false;
        }
    }

    public boolean wasLastWriteSuccessful() {
        return lastWriteSuccessful;
    }

    @Override
    public void close() {
        channel.shutdown();
    }
}

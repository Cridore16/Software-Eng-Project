package apiproject;

import apiproject.grpc.JobRequest;
import apiproject.grpc.JobResponse;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Checkpoint7RegressionTest {

    @Test
    public void testInMemoryComputation() {
        List<Integer> inputs = List.of(6, 12, 15);
        List<String> outputs = new ArrayList<>();

        InMemoryDataStore storage = new InMemoryDataStore(
                new InputConfig(inputs),
                new OutputConfig(outputs));

        UserComputeEngineAPIImpl engine =
                new UserComputeEngineAPIImpl(
                        storage, new ComputationAPIImpl());

        engine.configureJob("IN_MEMORY", "output.txt", ';');

        assertEquals("SUCCESS", engine.getLastJobStatus());
        assertEquals(
                List.of("1,2,3,6;1,2,3,4,6,12;1,3,5,15"),
                outputs);
    }

    @Test
    public void testTypedNumbersProtobuf() throws Exception {
        JobRequest request = JobRequest.newBuilder()
                .setUseTypedNumbers(true)
                .addAllNumbers(List.of(8, 20, 25))
                .setOutputDestination("output.txt")
                .setDelimiter(";")
                .build();

        JobRequest decoded =
                JobRequest.parseFrom(request.toByteArray());

        assertTrue(decoded.getUseTypedNumbers());
        assertEquals(List.of(8, 20, 25),
                decoded.getNumbersList());
        assertEquals("output.txt",
                decoded.getOutputDestination());
        assertEquals(";", decoded.getDelimiter());
    }

    @Test
    public void testFileInputProtobuf() throws Exception {
        JobRequest request = JobRequest.newBuilder()
                .setInputSource("input.txt")
                .setOutputDestination("output.txt")
                .setDelimiter(",")
                .build();

        JobRequest decoded =
                JobRequest.parseFrom(request.toByteArray());

        assertFalse(decoded.getUseTypedNumbers());
        assertEquals("input.txt", decoded.getInputSource());
        assertEquals(0, decoded.getNumbersCount());
    }

    @Test
    public void testInvalidComputationInput() {
        List<String> outputs = new ArrayList<>();

        InMemoryDataStore storage = new InMemoryDataStore(
                new InputConfig(List.of(-5)),
                new OutputConfig(outputs));

        UserComputeEngineAPIImpl engine =
                new UserComputeEngineAPIImpl(
                        storage, new ComputationAPIImpl());

        engine.configureJob("IN_MEMORY", "output.txt", ';');

        assertEquals("COMPUTATION_ERROR",
                engine.getLastJobStatus());
        assertTrue(outputs.isEmpty());
    }

    @Test
    public void testFailureResponseProtobuf() throws Exception {
        JobResponse response = JobResponse.newBuilder()
                .setSuccess(false)
                .setMessage("WRITE_ERROR")
                .build();

        JobResponse decoded =
                JobResponse.parseFrom(response.toByteArray());

        assertFalse(decoded.getSuccess());
        assertEquals("WRITE_ERROR", decoded.getMessage());
    }
}

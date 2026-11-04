
package apiproject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

public class ComputeEngineIntegrationTest {

    @Test
    public void testComputeEngineIntegration() {

        List<Integer> inputData =
                Arrays.asList(1, 10, 25);

        List<String> outputData =
                new ArrayList<>();

        InputConfig inputConfig =
                new InputConfig(inputData);

        OutputConfig outputConfig =
                new OutputConfig(outputData);

        InMemoryDataStore dataStore =
                new InMemoryDataStore(
                        inputConfig,
                        outputConfig);

        ComputationAPIImpl computation =
                new ComputationAPIImpl();

        UserComputeEngineAPIImpl computeEngine =
                new UserComputeEngineAPIImpl(
                        dataStore,
                        computation);

        List<Integer> inputs =
                dataStore.readInputData("");

        assertEquals(inputData, inputs);
        assertNotNull(dataStore);
        assertNotNull(computation);
        assertNotNull(computeEngine);
    }
    
    @Test
    public void testStorageExceptionHandled() {

        // Create a storage component that deliberately fails.
        DataStorageAPI failingStorage = new DataStorageAPI() {

            @Override
            public List<Integer> readInputData(String input) {
                throw new RuntimeException("Simulated storage failure");
            }

            @Override
            public void writeOutputData(String output) {
                // No writing needed for this test.
            }
        };

        ComputationAPIImpl computation =
                new ComputationAPIImpl();

        UserComputeEngineAPIImpl computeEngine =
                new UserComputeEngineAPIImpl(
                        failingStorage,
                        computation);

        // The coordinator should catch the storage exception.
        computeEngine.configureJob(
                "input.txt",
                "output.txt",
                ',');

        // Verify the exception became an error status.
        assertEquals("ERROR", computeEngine.getLastJobStatus());
    }

}

package apiproject;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
}
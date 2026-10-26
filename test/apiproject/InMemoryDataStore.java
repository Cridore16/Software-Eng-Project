package apiproject;

import java.util.List;

public class InMemoryDataStore implements DataStorageAPI {

    private InputConfig inputConfig;
    private OutputConfig outputConfig;

    public InMemoryDataStore(
            InputConfig inputConfig,
            OutputConfig outputConfig) {

        this.inputConfig = inputConfig;
        this.outputConfig = outputConfig;
    }

    @Override
    public List<Integer> readInputData(String input) {
        return inputConfig.getInputData();
    }

    public void writeOutputData(String output) {
        outputConfig.getOutputData().add(output);
    }
}
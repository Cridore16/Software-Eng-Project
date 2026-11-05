package apiproject;

import java.util.List;

public class OutputConfig {

    private List<String> outputData;

    public OutputConfig(List<String> outputData) {
        this.outputData = outputData;
    }

    public List<String> getOutputData() {
        return outputData;
    }
}
package apiproject;

import java.util.List;

public class UserComputeEngineAPIImpl implements UserComputeEngineAPI {

    private final DataStorageAPI dataStorage;
    private final ComputationAPI computation;

    public UserComputeEngineAPIImpl(
            DataStorageAPI dataStorage,
            ComputationAPI computation) {

        this.dataStorage = dataStorage;
        this.computation = computation;
    }

    @Override
    public void configureJob(
            String inputSource,
            String outputDestination,
            char delimiter) {

        List<Integer> inputs =
                dataStorage.readInputData(inputSource);

        StringBuilder results = new StringBuilder();

        for (int input : inputs) {

            String result =
                    computation.computeResult(input);

            if (results.length() > 0) {
                results.append(delimiter);
            }

            results.append(result);
        }

        dataStorage.writeOutputData(results.toString());
    }
}
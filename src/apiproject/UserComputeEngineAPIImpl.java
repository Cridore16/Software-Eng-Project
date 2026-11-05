
package apiproject;

import java.util.List;

public class UserComputeEngineAPIImpl implements UserComputeEngineAPI {

    private final DataStorageAPI dataStorage;
    private final ComputationAPI computation;

    private String lastJobStatus = "NOT_STARTED";

    public UserComputeEngineAPIImpl(
            DataStorageAPI dataStorage,
            ComputationAPI computation) {

        if (dataStorage == null || computation == null) {
            throw new IllegalArgumentException(
                    "Dependencies cannot be null");
        }

        this.dataStorage = dataStorage;
        this.computation = computation;
    }

    @Override
    public void configureJob(
            String inputSource,
            String outputDestination,
            char delimiter) {

        lastJobStatus = "ERROR";

        // Validate the job configuration.
        if (inputSource == null || inputSource.isBlank()
                || outputDestination == null
                || outputDestination.isBlank()
                || delimiter == '\0') {
            lastJobStatus = "INVALID_INPUT";
            return;
        }

        try {
            List<Integer> inputs =
                    dataStorage.readInputData(inputSource);

            if (inputs == null) {
                lastJobStatus = "READ_ERROR";
                return;
            }

            StringBuilder results = new StringBuilder();

            for (int input : inputs) {
                String result = computation.computeResult(input);

                if (result == null || result.startsWith("ERROR:")) {
                    lastJobStatus = "COMPUTATION_ERROR";
                    return;
                }

                if (results.length() > 0) {
                    results.append(delimiter);
                }

                results.append(result);
            }

            dataStorage.writeOutputData(results.toString());

            // Check the result when using our file-based storage.
            if (dataStorage instanceof DataStorageAPIImpl) {
                DataStorageAPIImpl storage =
                        (DataStorageAPIImpl) dataStorage;

                if (!storage.wasLastWriteSuccessful()) {
                    lastJobStatus = "WRITE_ERROR";
                    return;
                }
            }

            lastJobStatus = "SUCCESS";

        } catch (RuntimeException e) {
            // Prevent component exceptions from crossing the
            // network boundary.
            lastJobStatus = "ERROR";
        }
    }

    // Returns the result of the most recent job.
    public String getLastJobStatus() {
        return lastJobStatus;
    }
}


package apiproject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultiThreadedUserComputeEngineAPIImpl
        implements UserComputeEngineAPI {

    private static final int THREAD_COUNT = 4;

    private final DataStorageAPI dataStorage;
    private final ComputationAPI computation;

    public MultiThreadedUserComputeEngineAPIImpl(
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

        if (inputSource == null || inputSource.isBlank()
                || outputDestination == null
                || outputDestination.isBlank()
                || delimiter == '\0') {
            return;
        }

        ExecutorService executor =
                Executors.newFixedThreadPool(THREAD_COUNT);

        try {
            List<Integer> inputs =
                    dataStorage.readInputData(inputSource);

            if (inputs == null) {
                return;
            }

            List<Future<String>> futures = new ArrayList<>();

            for (int input : inputs) {
                final int value = input;

                futures.add(executor.submit(
                        () -> computation.computeResult(value)));
            }

            StringBuilder results = new StringBuilder();

            for (Future<String> future : futures) {
                String result = future.get();

                if (result == null || result.startsWith("ERROR:")) {
                    return;
                }

                if (results.length() > 0) {
                    results.append(delimiter);
                }

                results.append(result);
            }

            DataStorageAPI outputStorage = dataStorage;

            if (dataStorage instanceof DataStorageAPIImpl) {
                outputStorage =
                        new DataStorageAPIImpl(outputDestination);
            }

            outputStorage.writeOutputData(results.toString());

            if (outputStorage instanceof DataStorageAPIImpl) {
                DataStorageAPIImpl storage =
                        (DataStorageAPIImpl) outputStorage;

                if (!storage.wasLastWriteSuccessful()) {
                    return;
                }
            }

        } catch (Exception e) {
            Thread.currentThread().interrupt();
        } finally {
            executor.shutdown();
        }
    }
}

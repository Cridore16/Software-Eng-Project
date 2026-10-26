package apiproject;

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
    }
}
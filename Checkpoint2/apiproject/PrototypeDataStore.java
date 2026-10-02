public class PrototypeDataStore {
    public void prototype(DataStore dataStore) {
        // store some data
        DataStorageResponse storeResponse = dataStore.storeData(new DataStorageRequest());
        // retrieve the data
        DataLoadResponse loadResponse = dataStore.loadData(storeResponse.getDataKey());
    }

    public interface DataStorageRequest {
        // TBD
    }

    public interface DataStorageResponse {
        DataStorageKey getDataKey();
    }

    public interface DataStorageKey {
        // TBD
    }

    public interface DataLoadResponse {
        // TBD
    }
}

public interface DataStore {
    DataStorageResponse storeData(DataStorageRequest request);

    DataLoadResponse loadData(DataStorageKey key);

}

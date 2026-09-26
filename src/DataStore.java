public interface DataStore {
DataStorageResponse storeData(DataStorageRequest request);
DataLoadResponse loadData(DataStorageKey key);
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
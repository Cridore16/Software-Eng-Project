package apiproject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

public class TestDataStorageAPI {

    @Test
    public void testDataStorageAPI() {

        DataStorageAPI dependency = Mockito.mock(DataStorageAPI.class);

        DataStorageAPIImpl api = new DataStorageAPIImpl();

        assertNotNull(api);
    }
}
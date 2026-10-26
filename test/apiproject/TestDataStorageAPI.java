
package apiproject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestDataStorageAPI {

    @Test
    public void testDataStorageAPI() {
        DataStorageAPIImpl api =
                new DataStorageAPIImpl();

        assertNotNull(api);
    }
}

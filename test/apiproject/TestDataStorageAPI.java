
package apiproject;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

public class TestDataStorageAPI {

    @Test
    public void testDataStorageAPI() {
        DataStorageAPIImpl api =
                new DataStorageAPIImpl();

        assertNotNull(api);
    }
}

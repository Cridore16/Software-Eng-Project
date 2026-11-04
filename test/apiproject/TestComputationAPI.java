
package apiproject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestComputationAPI {

    @Test
    public void testComputationAPI() {
        ComputationAPIImpl api =
                new ComputationAPIImpl();

        assertNotNull(api);
    }
}

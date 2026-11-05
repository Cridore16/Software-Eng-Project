
package apiproject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestComputationAPI {

    @Test
    public void testComputationAPI() {
        ComputationAPIImpl api = new ComputationAPIImpl();

        assertNotNull(api);
    }

    @Test
    public void testValidInput() {
        ComputationAPIImpl api = new ComputationAPIImpl();

        String result = api.computeResult(6);

        assertEquals("1,2,3,6", result);
    }

    @Test
    public void testInvalidInput() {
        ComputationAPIImpl api = new ComputationAPIImpl();

        assertEquals(
            "ERROR: Input must be a positive integer",
            api.computeResult(0)
        );

        assertEquals(
            "ERROR: Input must be a positive integer",
            api.computeResult(-5)
        );
    }
}

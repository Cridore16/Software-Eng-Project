
package apiproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

public class TestComputationAPI {

    @Test
    public void testComputationAPI() {
        ComputationAPIImpl api = new ComputationAPIImpl();
        assertNotNull(api);
    }

    @Test
    public void testValidInput() {
        ComputationAPIImpl api = new ComputationAPIImpl();
        assertEquals("1,2,3,6", api.computeResult(6));
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

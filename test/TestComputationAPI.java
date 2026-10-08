package apiproject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

public class TestComputationAPI {

    @Test
    public void testComputationAPI() {

        ComputationAPI dependency = Mockito.mock(ComputationAPI.class);

        ComputationAPIImpl api = new ComputationAPIImpl();

        assertNotNull(api);
    }
}
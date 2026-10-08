package apiproject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

public class TestUserComputeEngineAPI {

    @Test
    public void testUserComputeEngineAPI() {

        UserComputeEngineAPI dependency =
                Mockito.mock(UserComputeEngineAPI.class);

        UserComputeEngineAPIImpl api =
                new UserComputeEngineAPIImpl();

        assertNotNull(api);
    }
}
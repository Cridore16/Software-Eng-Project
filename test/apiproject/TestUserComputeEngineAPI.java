package apiproject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.mockito.Mockito;

public class TestUserComputeEngineAPI {

    @Test
    public void testUserComputeEngineAPI() {

        DataStorageAPI dataStorage =
                Mockito.mock(DataStorageAPI.class);

        ComputationAPI computation =
                Mockito.mock(ComputationAPI.class);

        UserComputeEngineAPIImpl api =
                new UserComputeEngineAPIImpl(dataStorage, computation);

        assertNotNull(api);
    }
}

package project.threadHarness;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import apiproject.ComputationAPIImpl;
import apiproject.DataStorageAPIImpl;
import apiproject.MultiThreadedUserComputeEngineAPIImpl;

public class ParallelOutputVerificationTest {

    @Test
    public void verifiesCorrectOutputAndOrder() throws Exception {
        Path output = Files.createTempFile("cp6-output-", ".txt");

        try {
            MultiThreadedUserComputeEngineAPIImpl engine =
                    new MultiThreadedUserComputeEngineAPIImpl(
                            new DataStorageAPIImpl(),
                            new ComputationAPIImpl()
                    );

            engine.configureJob(
                    "test/testInputFile.test",
                    output.toString(),
                    '|'
            );

            String expected =
                    "1|1,3,5,15|1,2,5,10|1,5|1,2|1,3|1,2,4,8";

            String actual = Files.readString(output).trim();

            assertEquals(expected, actual);
        } finally {
            Files.deleteIfExists(output);
        }
    }
}

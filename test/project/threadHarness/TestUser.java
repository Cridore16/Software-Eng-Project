package project.threadHarness;

import java.io.File;

import apiproject.UserComputeEngineAPI;

public class TestUser {

    private final UserComputeEngineAPI coordinator;

    public TestUser(UserComputeEngineAPI coordinator) {
        this.coordinator = coordinator;
    }

    public void run(String outputPath) {
        char delimiter = ';';

        String inputPath = "test" + File.separatorChar
                + "testInputFile.test";

        coordinator.configureJob(inputPath, outputPath, delimiter);
    }
}
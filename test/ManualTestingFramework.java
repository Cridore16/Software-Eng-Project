package project.checkpointtests;

import apiproject.ComputationAPI;
import apiproject.ComputationAPIImpl;
import apiproject.DataStorageAPI;
import apiproject.DataStorageAPIImpl;
import apiproject.UserComputeEngineAPI;
import apiproject.UserComputeEngineAPIImpl;

public class ManualTestingFramework {

    public static final String INPUT = "manualTestInput.txt";
    public static final String OUTPUT = "manualTestOutput.txt";

    public static void main(String[] args) {

        ComputationAPI computation = new ComputationAPIImpl();

        DataStorageAPI dataStorage =
                new DataStorageAPIImpl(OUTPUT);

        UserComputeEngineAPI computeEngine =
                new UserComputeEngineAPIImpl(
                        dataStorage,
                        computation);

        computeEngine.configureJob(
                INPUT,
                OUTPUT,
                ',');
    }
}
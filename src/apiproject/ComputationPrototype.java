package apiproject;
import project.annotations.ConceptualAPIPrototype;
import project.annotations.ComputaitionAPI;


public class ComputationPrototype {

    @ConceptualAPIPrototype
    public void prototypeComputation(ComputationAPI api) {

        int mockInput = 6;

        String mockResult =
            api.computeResult(mockInput);
    }
}
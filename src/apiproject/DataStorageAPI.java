package apiproject;
import project.annotations.ProcessAPI;
import java.util.List;

@ProcessAPI
public interface DataStorageAPI{
    List<Integer> readInputData(String input);

}
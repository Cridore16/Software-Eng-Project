
package apiproject;

public class ComputationAPIImpl implements ComputationAPI {

    public ComputationAPIImpl() {
    }

    @Override
    public String computeResult(int input) {
        StringBuilder result = new StringBuilder();

        for (int i = 1; i <= input; i++) {
            if (input % i == 0) {
                if (result.length() > 0) {
                    result.append(",");
                }

                result.append(i);
            }
        }

        return result.toString();
    }
}
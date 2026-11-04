

package apiproject;

public class ComputationAPIImpl implements ComputationAPI {

    public ComputationAPIImpl() {
    }

    @Override
    public String computeResult(int input) {

        // Only positive integers are valid for this computation.
        if (input <= 0) {
            return "ERROR: Input must be a positive integer";
        }

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

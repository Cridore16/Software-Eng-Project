public interface LoginResponse{
   LoginResponseCode getResponseCode();
UserIdentifier getUserIdentifier();
}
public interface UserIdentifier {
// TBD
}
public enum LoginResponseCode{
    SUCCESS(true);
    FAILURE(false);
    UNKNOWN_USERNAME (false);

    priavte boolean success;
    priavte LoginResppnseCode(boolean success){
        this.success = success;
    }
    public boolean success(){
        return success;
    }
}

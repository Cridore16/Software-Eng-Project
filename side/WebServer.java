package apiproject;

public interface WebServer {
    LoginResponse login(LoginRequest loginRequest);

    ProfileLoadResponse loadProfile(UserIdentifier user);

    ProfileChangeResponse changeProfile(UserIdentifier user, ProfileChangeRequest request);

    LogoutResponse logout(UserIdentifier user);

}
public enum LoginResponseCode{
    SUCCESS(true),
    FAILURE(false),
    UNKNOWN_USERNAME (false);

    private boolean success;
    private LoginResponseCode(boolean success){
        this.success = success;
    }
    public boolean success(){
        return success;
    }
}

package PrototypeSomeApi;

public interface WebServer {
    LoginResponse login(LoginRequest loginRequest);

    ProfileLoadResponse loadProfile(UserIdentifier user);

    ProfileChangeResponse changeProfile(UserIdentifier user, ProfileChangeRequest request);

    LogoutResponse logout(UserIdentifier user);

}

package apiproject;

public class PrototypeSomeApi {

    public void testWebServer(WebServer server) {

        LoginResponse loginResponse = server.login(new LoginRequest());

        // load their profile
        if (loginResponse.getResponseCode().success()) {
            // etc
        }
    }
}

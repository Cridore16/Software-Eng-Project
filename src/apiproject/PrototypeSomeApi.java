public class PrototypeSomeApi {
public void testWebServer(WebServer server) {
LoginResponse loginResponse = server.login(new LoginRequest());
// load their profile
if (loginResponse.getResponseCode().success()) {
// etc
}

}
public default int calculateValue(int input) {
return calculateValue(input, DEFAULT_NUM_THREADS);
}
public int calculateValue(int input, int nThreads);
}

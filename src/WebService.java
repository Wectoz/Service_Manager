public class WebService extends Service {
    private String port;

    public WebService(String name, String version, Environment environment, ServiceStatus status, String port) {
        super(name, version, environment, status);
        this.port = port;
    }
}

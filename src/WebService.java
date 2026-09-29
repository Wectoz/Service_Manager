public class WebService extends Service implements Deployable {
    private String port;

    public WebService(String name, String version, Environment environment, ServiceStatus status, String port) {
        super(name, version, environment, status);
        this.port = port;
    }
    @Override
    public void start() {
        super.start();
    }
    @Override
    public String getInfo() {
        return super.getInfo() + "\n" +
                "Port: " + port;
    }
    @Override
    public void deploy(String newVersion) {
        setVersion(newVersion);
    }

}

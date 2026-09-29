public class Service {
    private String name;
    private String version;
    private Environment environment;
    private ServiceStatus status;

    public Service(String name, String version, Environment environment, ServiceStatus status) {
        this.name = name;
        this.version = version;
        this.environment = environment;
        this.status = status;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public void start() {
        status = ServiceStatus.RUNNING;

    }
public void stop() {
        status = ServiceStatus.STOPPED;

}
public String getInfo() {
        return "Name: " + name + "\n" +
                "Version: " + version + "\n" +
                "Environment: " + environment + "\n" +
                "Status: " + status;

}
}

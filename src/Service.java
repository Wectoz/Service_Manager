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
    public void start() {

    }
public void stop() {

}
public String getInfo() {

}
}

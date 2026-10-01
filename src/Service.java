// Base class for all services in the system
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

    public ServiceStatus getStatus() {
        return status;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public String getVersion() {
        return version;
    }

    public String getName() {
        return name;
    }

    // Need this for when we deploy a new version
    public void setVersion(String version) {
        this.version = version;
    }
    
    // Set service status to RUNNING
    public void start() {
        status = ServiceStatus.RUNNING;
    }
    
    // Set service status to STOPPED
    public void stop() {
        status = ServiceStatus.STOPPED;
    }
    
    // Returns a formatted string with all the service details
    public String getInfo() {
        return "Name: " + name + "\n" +
                "Version: " + version + "\n" +
                "Environment: " + environment + "\n" +
                "Status: " + status;
    }
}

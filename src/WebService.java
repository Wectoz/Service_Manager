// Web service that runs on a specific port
public class WebService extends Service implements Deployable {
    private String port;

    public WebService(String name, String version, Environment environment, ServiceStatus status, String port) {
        super(name, version, environment, status);
        validatePort(port);
        this.port = port;
    }
    
    // Make sure the port is actually valid
    private void validatePort(String port) {
        try {
            int portNum = Integer.parseInt(port);
            if (portNum < 1 || portNum > 65535) {
                throw new IllegalArgumentException("Invalid port number: " + port + ". Port must be between 1 and 65535.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid port format: " + port + ". Port must be a number.");
        }
    }
    
    @Override
    // Start web service with port-specific logging
    public void start() {
        System.out.println("Starting on port " + port);
        super.start();
        System.out.println("Listening on port " + port);
    }
    
    @Override
    // Return service info including port
    public String getInfo() {
        return super.getInfo() + "\n" +
                "Port: " + port;
    }
    
    @Override
    // Deploy new version by updating version field
    public void deploy(String newVersion) {
        setVersion(newVersion);
    }
    
    // Some sample web services to load on startup
    public static WebService[] getPredefinedServices() {
        return new WebService[] {
            new WebService("API Gateway", "1.0.0", Environment.PRODUCTION, ServiceStatus.RUNNING, "8080"),
            new WebService("Auth Service", "2.1.0", Environment.PRODUCTION, ServiceStatus.RUNNING, "8081"),
            new WebService("User Service", "1.5.0", Environment.STAGING, ServiceStatus.STOPPED, "8082"),
            new WebService("Payment Gateway", "3.0.0", Environment.PRODUCTION, ServiceStatus.RUNNING, "8443"),
            new WebService("Notification Service", "1.2.0", Environment.DEVELOPMENT, ServiceStatus.STOPPED, "8083")
        };
    }
}

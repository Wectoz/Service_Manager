// Database service with a specific database type (PostgreSQL, MySQL, etc.)
public class DatabaseService extends Service {
    private String databaseType;

    public DatabaseService(String name, String version, Environment environment, ServiceStatus status, String databaseType) {
        super(name, version, environment, status);
        this.databaseType = databaseType;
    }
    @Override
    public void start() {
        System.out.println("Starting " + databaseType + " database");
        super.start();
        System.out.println(databaseType + " database ready");
    }
    @Override
    public String getInfo() {
        return super.getInfo() + "\n" +
                "Database Type: " + databaseType;
    }
    
    // Some sample database services to load on startup
    public static DatabaseService[] getPredefinedServices() {
        return new DatabaseService[] {
            new DatabaseService("User Database", "2.1.0", Environment.PRODUCTION, ServiceStatus.RUNNING, "PostgreSQL"),
            new DatabaseService("Product Database", "1.8.0", Environment.PRODUCTION, ServiceStatus.RUNNING, "MySQL"),
            new DatabaseService("Cache Database", "1.0.0", Environment.PRODUCTION, ServiceStatus.RUNNING, "Redis"),
            new DatabaseService("Analytics Database", "1.5.0", Environment.STAGING, ServiceStatus.STOPPED, "MongoDB"),
            new DatabaseService("Log Database", "1.2.0", Environment.DEVELOPMENT, ServiceStatus.STOPPED, "Elasticsearch")
        };
    }
}

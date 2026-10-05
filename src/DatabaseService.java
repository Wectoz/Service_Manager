// Database service with a specific database type (PostgreSQL, MySQL, etc.)
public class DatabaseService extends Service implements Deployable {
    private final String databaseType;

    public DatabaseService(String name, String version, Environment environment, ServiceStatus status, String databaseType) {
        super(name, version, environment, status);
        validateDatabaseType(databaseType);
        this.databaseType = databaseType;
    }

    // Validate that database type is one of the allowed values
    private void validateDatabaseType(String databaseType) {
        if (databaseType == null || databaseType.trim().isEmpty()) {
            throw new IllegalArgumentException("Database type cannot be empty.");
        }
        String normalized = databaseType.trim();
        java.util.Set<String> validTypes = java.util.Set.of("PostgreSQL", "MySQL", "Redis", "MongoDB", "Elasticsearch");
        if (!validTypes.contains(normalized)) {
            throw new IllegalArgumentException("Invalid database type: " + databaseType + ". Must be one of: PostgreSQL, MySQL, Redis, MongoDB, Elasticsearch");
        }
    }
    
    @Override
    // Start database with database-type-specific logging
    public void start() {
        System.out.println("Starting " + databaseType + " database");
        super.start();
        System.out.println(databaseType + " database ready");
    }
    
    @Override
    // Return service info including database type
    public String getInfo() {
        return super.getInfo() + "\n" +
                "Database Type: " + databaseType;
    }

    @Override
    // Deploy new version by updating version field
    public void deploy(String newVersion) {
        setVersion(newVersion);
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

public class DatabaseService extends Service {
    private String databaseType;

    public DatabaseService(String name, String version, Environment environment, ServiceStatus status, String databaseType) {
        super(name, version, environment, status);
        this.databaseType = databaseType;
    }
}

public class DatabaseService extends Service {
    private String databaseType;

    public DatabaseService(String name, String version, Environment environment, ServiceStatus status, String databaseType) {
        super(name, version, environment, status);
        this.databaseType = databaseType;
    }
    @Override
    public void start() {
        super.start();
    }
    @Override
    public String getInfo() {
        return super.getInfo() + "\n" +
                "Database Type: " + databaseType;
    }
}

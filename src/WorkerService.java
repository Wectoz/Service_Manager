public class WorkerService extends Service implements Deployable {
    private String jobType;

    public WorkerService(String name, String version, Environment environment, ServiceStatus status, String jobType) {
        super(name, version, environment, status);
        this.jobType = jobType;
    }
    @Override
    public void start() {
        super.start();
    }
    @Override
    public String getInfo() {
        return super.getInfo() + "\n" +
                "Job Type: " + jobType;
    }
    @Override
    public void deploy(String newVersion) {
        setVersion(newVersion);
    }
    
    public static WorkerService[] getPredefinedServices() {
        return new WorkerService[] {
            new WorkerService("Email Worker", "1.5.0", Environment.PRODUCTION, ServiceStatus.RUNNING, "EmailJob"),
            new WorkerService("Report Generator", "2.0.0", Environment.PRODUCTION, ServiceStatus.RUNNING, "ReportJob"),
            new WorkerService("Data Cleanup", "1.0.0", Environment.STAGING, ServiceStatus.STOPPED, "CleanupJob"),
            new WorkerService("Backup Worker", "1.3.0", Environment.PRODUCTION, ServiceStatus.RUNNING, "BackupJob"),
            new WorkerService("Notification Worker", "1.1.0", Environment.DEVELOPMENT, ServiceStatus.STOPPED, "NotificationJob")
        };
    }

}

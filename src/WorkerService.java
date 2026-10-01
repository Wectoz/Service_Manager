// Background worker service that handles specific job types
public class WorkerService extends Service implements Deployable {
    private String jobType;

    public WorkerService(String name, String version, Environment environment, ServiceStatus status, String jobType) {
        super(name, version, environment, status);
        this.jobType = jobType;
    }
    @Override
    public void start() {
        System.out.println("Starting " + jobType + " worker");
        super.start();
        System.out.println(jobType + " worker running");
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
    
    // Some sample worker services to load on startup
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

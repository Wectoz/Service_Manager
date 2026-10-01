// Background worker service that handles specific job types
public class WorkerService extends Service implements Deployable {
    private String jobType;

    public WorkerService(String name, String version, Environment environment, ServiceStatus status, String jobType) {
        super(name, version, environment, status);
        validateJobType(jobType);
        this.jobType = jobType;
    }

    // Validate that job type is one of the allowed values
    private void validateJobType(String jobType) {
        if (jobType == null || jobType.trim().isEmpty()) {
            throw new IllegalArgumentException("Job type cannot be empty.");
        }
        String normalized = jobType.trim();
        java.util.Set<String> validTypes = java.util.Set.of("EmailJob", "ReportJob", "CleanupJob", "BackupJob", "NotificationJob");
        if (!validTypes.contains(normalized)) {
            throw new IllegalArgumentException("Invalid job type: " + jobType + ". Must be one of: EmailJob, ReportJob, CleanupJob, BackupJob, NotificationJob");
        }
    }
    
    @Override
    // Start worker with job-type-specific logging
    public void start() {
        System.out.println("Starting " + jobType + " worker");
        super.start();
        System.out.println(jobType + " worker running");
    }
    
    @Override
    // Return service info including job type
    public String getInfo() {
        return super.getInfo() + "\n" +
                "Job Type: " + jobType;
    }
    
    @Override
    // Deploy new version by updating version field
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

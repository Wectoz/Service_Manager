public class WorkerService extends Service {
    private String jobType;

    public WorkerService(String name, String version, Environment environment, ServiceStatus status, String jobType) {
        super(name, version, environment, status);
        this.jobType = jobType;
    }

}

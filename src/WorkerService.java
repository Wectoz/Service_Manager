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

}

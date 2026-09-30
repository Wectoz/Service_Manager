// Entry point for the service manager application
public class Main {
    public static void main(String[] args) {
        ServiceManager manager = new ServiceManager();
        
        // Load some sample services so the app isn't empty on startup
        for (WebService ws : WebService.getPredefinedServices()) {
            manager.addService(ws);
        }
        for (DatabaseService db : DatabaseService.getPredefinedServices()) {
            manager.addService(db);
        }
        for (WorkerService worker : WorkerService.getPredefinedServices()) {
            manager.addService(worker);
        }
        
        // Fire up the console interface
        ConsoleUI ui = new ConsoleUI(manager);
        ui.start();
    }
}

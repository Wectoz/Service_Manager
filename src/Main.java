public class Main {
    public static void main(String[] args) {
        ServiceManager manager = new ServiceManager();
        
        // Add predefined services
        for (WebService ws : WebService.getPredefinedServices()) {
            manager.addService(ws);
        }
        for (DatabaseService db : DatabaseService.getPredefinedServices()) {
            manager.addService(db);
        }
        for (WorkerService worker : WorkerService.getPredefinedServices()) {
            manager.addService(worker);
        }
        
        // Start the console UI
        ConsoleUI ui = new ConsoleUI(manager);
        ui.start();
    }
}

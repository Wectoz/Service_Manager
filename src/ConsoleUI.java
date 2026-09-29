import java.util.Scanner;

public class ConsoleUI {
    private ServiceManager manager;
    private Scanner scanner;

    public ConsoleUI(ServiceManager manager) {
        this.manager = manager;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;
        
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    addService();
                    break;
                case 2:
                    removeService();
                    break;
                case 3:
                    searchService();
                    break;
                case 4:
                    displayAllServices();
                    break;
                case 5:
                    startService();
                    break;
                case 6:
                    stopService();
                    break;
                case 7:
                    deployService();
                    break;
                case 8:
                    displayServicesByEnvironment();
                    break;
                case 9:
                    displayStatistics();
                    break;
                case 0:
                    running = false;
                    System.out.println("Exiting Service Manager...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
        }
        scanner.close();
    }

    private void displayMenu() {
        System.out.println("=== Service Manager Menu ===");
        System.out.println("1. Add a new service");
        System.out.println("2. Remove a service");
        System.out.println("3. Search for a service by name");
        System.out.println("4. Display all registered services");
        System.out.println("5. Start a service");
        System.out.println("6. Stop a service");
        System.out.println("7. Deploy a new version of a service");
        System.out.println("8. Display services from a specific environment");
        System.out.println("9. Display service statistics");
        System.out.println("0. Exit");
    }

    private void addService() {
        System.out.println("\n--- Add Service ---");
        System.out.println("1. WebService");
        System.out.println("2. DatabaseService");
        System.out.println("3. WorkerService");
        int type = getIntInput("Select service type: ");
        
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter version: ");
        String version = scanner.nextLine();
        if (version.isEmpty()) {
            System.out.println("Version cannot be empty.");
            return;
        }
        if (!version.matches("\\d+(\\.\\d+)*")) {
            System.out.println("Version must contain only numbers (e.g., 1, 1.0, 2.5.1).");
            return;
        }
        
        System.out.println("Environments: DEVELOPMENT, STAGING, PRODUCTION");
        System.out.print("Enter environment: ");
        String envStr = scanner.nextLine();
        Environment environment = Environment.valueOf(envStr.toUpperCase());
        
        System.out.println("Status: RUNNING, STOPPED, FAILED");
        System.out.print("Enter status: ");
        String statusStr = scanner.nextLine();
        ServiceStatus status = ServiceStatus.valueOf(statusStr.toUpperCase());
        
        switch (type) {
            case 1:
                System.out.print("Enter port: ");
                String port = scanner.nextLine();
                manager.addService(new WebService(name, version, environment, status, port));
                break;
            case 2:
                System.out.print("Enter database type: ");
                String dbType = scanner.nextLine();
                manager.addService(new DatabaseService(name, version, environment, status, dbType));
                break;
            case 3:
                System.out.print("Enter job type: ");
                String jobType = scanner.nextLine();
                manager.addService(new WorkerService(name, version, environment, status, jobType));
                break;
            default:
                System.out.println("Invalid service type.");
                return;
        }
        System.out.println("Service added successfully.");
    }

    private void removeService() {
        System.out.println("\n--- Remove Service ---");
        System.out.print("Enter service name: ");
        String name = scanner.nextLine();
        manager.removeService(name);
    }

    private void searchService() {
        System.out.println("\n--- Search Service ---");
        System.out.print("Enter search keyword: ");
        String keyword = scanner.nextLine();
        if (keyword.isEmpty()) {
            System.out.println("Search keyword cannot be empty.");
        } else {
            boolean found = manager.searchServices(keyword);
            if (!found) {
                System.out.println("Could not find keyword: " + keyword);
            }
        }
    }

    private void displayAllServices() {
        System.out.println("\n--- All Services ---");
        for (Service service : manager.getServices()) {
            System.out.println(service.getInfo());
            System.out.println("---");
        }
    }

    private void startService() {
        System.out.println("\n--- Start Service ---");
        System.out.print("Enter service name: ");
        String name = scanner.nextLine();
        if (name.isEmpty()) {
            System.out.println("Service name cannot be empty.");
            return;
        }
        manager.startService(name);
    }

    private void stopService() {
        System.out.println("\n--- Stop Service ---");
        System.out.print("Enter service name: ");
        String name = scanner.nextLine();
        if (name.isEmpty()) {
            System.out.println("Service name cannot be empty.");
            return;
        }
        manager.stopService(name);
    }

    private void deployService() {
        System.out.println("\n--- Deploy Service ---");
        System.out.print("Enter service name: ");
        String name = scanner.nextLine();
        if (name.isEmpty()) {
            System.out.println("Service name cannot be empty.");
            return;
        }
        System.out.print("Enter new version: ");
        String version = scanner.nextLine();
        if (version.isEmpty()) {
            System.out.println("Service version cannot be empty.");
            return;
        }
        if (!version.matches("\\d+(\\.\\d+)*")) {
            System.out.println("Version must contain only numbers (e.g., 1, 1.0, 2.5.1).");
            return;
        }
        manager.deployService(name, version);
    }

    private void displayServicesByEnvironment() {
        System.out.println("\n--- Services by Environment ---");
        System.out.println("Environments: DEVELOPMENT, STAGING, PRODUCTION");
        System.out.print("Enter environment: ");
        String env = scanner.nextLine();
        manager.displayServicesInEnvironment(env.toUpperCase());
    }

    private void displayStatistics() {
        System.out.println("\n--- Service Statistics ---");
        manager.displayServiceStatistics();
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
}

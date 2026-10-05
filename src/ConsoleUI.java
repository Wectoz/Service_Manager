import java.util.List;
import java.util.Map;
import java.util.Scanner;

// Console interface for managing services
public class ConsoleUI {
    private static final String SERVICE_NAME_PROMPT = "Enter service name: ";
    private static final String SERVICE_NAME_EMPTY_ERROR = "Service name cannot be empty.";
    private final ServiceManager manager;
    private Scanner scanner;

    public ConsoleUI(ServiceManager manager) {
        this.manager = manager;
    }

    // Main menu loop - keeps running until user picks exit
    public void start() {
        try (Scanner scanner = new Scanner(System.in)) {
            this.scanner = scanner;
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
        }
    }

    // Display the main menu options
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

    // Add a new service - user picks type and enters details
    private void addService() {
        System.out.println("\n--- Add Service ---");
        System.out.println("1. WebService");
        System.out.println("2. DatabaseService");
        System.out.println("3. WorkerService");
        int type = getIntInput("Select service type: ");
        
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        if (name.isEmpty()) {
            System.out.println(SERVICE_NAME_EMPTY_ERROR);
            return;
        }

        System.out.print("Enter version: ");
        String version = scanner.nextLine();
        if (version.isEmpty()) {
            System.out.println("Version cannot be empty.");
            return;
        }
        if (!isValidVersion(version)) {
            System.out.println("Version must contain only numbers (e.g., 1, 1.0, 2.5.1).");
            return;
        }
        
        System.out.println("Environments: DEVELOPMENT, STAGING, PRODUCTION");
        System.out.print("Enter environment: ");
        String envStr = scanner.nextLine();
        Environment environment;
        try {
            environment = Environment.valueOf(envStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid environment. Must be DEVELOPMENT, STAGING, or PRODUCTION.");
            return;
        }
        
        System.out.println("Status: RUNNING, STOPPED, FAILED");
        System.out.print("Enter status: ");
        String statusStr = scanner.nextLine();
        ServiceStatus status;
        try {
            status = ServiceStatus.valueOf(statusStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid status. Must be RUNNING, STOPPED, or FAILED.");
            return;
        }
        
        Service newService = null;
        
        switch (type) {
            case 1:
                int port = getIntInput("Enter port: ");
                try {
                    newService = new WebService(name, version, environment, status, port);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid port: " + e.getMessage());
                    return;
                }
                break;
            case 2:
                System.out.print("Enter database type: ");
                String dbType = scanner.nextLine();
                if (dbType.isEmpty()) {
                    System.out.println("Database type cannot be empty.");
                    return;
                }
                newService = new DatabaseService(name, version, environment, status, dbType);
                break;
            case 3:
                System.out.print("Enter job type: ");
                String jobType = scanner.nextLine();
                if (jobType.isEmpty()) {
                    System.out.println("Job type cannot be empty.");
                    return;
                }
                newService = new WorkerService(name, version, environment, status, jobType);
                break;
            default:
                System.out.println("Invalid service type.");
                return;
        }
        
        if (manager.addService(newService)) {
            System.out.println(name + " added successfully.");
        } else {
            System.out.println("Service with name '" + name + "' already exists.");
        }
    }

    // Remove a service by name
    private void removeService() {
        System.out.println("\n--- Remove Service ---");
        System.out.print(SERVICE_NAME_PROMPT);
        String name = scanner.nextLine();
        if (manager.removeService(name)) {
            System.out.println(name + " removed successfully.");
        } else {
            System.out.println("Service not found: " + name);
        }
    }

    // Search for services by keyword
    private void searchService() {
        System.out.println("\n--- Search Service ---");
        System.out.print("Enter search keyword: ");
        String keyword = scanner.nextLine();
        if (keyword.isEmpty()) {
            System.out.println("Search keyword cannot be empty.");
        } else {
            List<String> results = manager.searchServices(keyword);
            if (results.isEmpty()) {
                System.out.println("Could not find keyword: " + keyword);
            } else {
                for (String serviceName : results) {
                    System.out.println(serviceName);
                }
            }
        }
    }

    // Display all registered services
    private void displayAllServices() {
        System.out.println("\n--- All Services ---");
        for (Service service : manager.getServices()) {
            System.out.println(service.getInfo());
            System.out.println("---");
        }
    }

    // Start a service by name
    private void startService() {
        System.out.println("\n--- Start Service ---");
        System.out.print(SERVICE_NAME_PROMPT);
        String name = scanner.nextLine();
        if (name.isEmpty()) {
            System.out.println(SERVICE_NAME_EMPTY_ERROR);
            return;
        }
        if (manager.startService(name)) {
            System.out.println(name + " started successfully.");
        } else {
            System.out.println("Service " + name + " is already running or not found.");
        }
    }

    // Stop a service by name
    private void stopService() {
        System.out.println("\n--- Stop Service ---");
        System.out.print(SERVICE_NAME_PROMPT);
        String name = scanner.nextLine();
        if (name.isEmpty()) {
            System.out.println(SERVICE_NAME_EMPTY_ERROR);
            return;
        }
        if (manager.stopService(name)) {
            System.out.println(name + " stopped successfully.");
        } else {
            System.out.println("Service " + name + " is already stopped or not found.");
        }
    }

    // Deploy a new version of a service
    private void deployService() {
        System.out.println("\n--- Deploy Service ---");
        System.out.print(SERVICE_NAME_PROMPT);
        String name = scanner.nextLine();
        if (name.isEmpty()) {
            System.out.println(SERVICE_NAME_EMPTY_ERROR);
            return;
        }
        System.out.print("Enter new version: ");
        String version = scanner.nextLine();
        if (version.isEmpty()) {
            System.out.println("Service version cannot be empty.");
            return;
        }
        if (!isValidVersion(version)) {
            System.out.println("Version must contain only numbers (e.g., 1, 1.0, 2.5.1).");
            return;
        }
        if (manager.deployService(name, version)) {
            System.out.println(name + " deployed successfully.");
        } else {
            System.out.println("Service " + name + " is not deployable or not found.");
        }
    }

    // Display services filtered by environment
    private void displayServicesByEnvironment() {
        System.out.println("\n--- Services by Environment ---");
        System.out.println("Environments: DEVELOPMENT, STAGING, PRODUCTION");
        System.out.print("Enter environment: ");
        String env = scanner.nextLine();
        try {
            List<Service> services = manager.getServicesByEnvironment(env.toUpperCase());
            if (services.isEmpty()) {
                System.out.println("No services found in environment: " + env);
            } else {
                for (Service service : services) {
                    System.out.println(service.getName());
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid environment: " + env);
        }
    }

    // Keep asking for input until we get a valid number
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

    // Version should be numbers separated by dots like 1.0 or 2.5.1
    // Max 3 segments, max 5 digits per segment, max 20 total characters
    private boolean isValidVersion(String version) {
        if (version == null || version.isEmpty()) {
            System.out.println("Service version cannot be empty.");
            return false;
        }
        if (version.length() > 20) {
            System.out.println("Version too long (max 20 characters).");
            return false;
        }
        return version.matches("^\\d{1,5}(\\.\\d{1,5}){0,2}$");
    }

    // Display service statistics
    private void displayStatistics() {
        System.out.println("\n--- Service Statistics ---");
        Map<ServiceStatus, Integer> stats = manager.getServiceStatistics();
        System.out.println("Running: " + stats.get(ServiceStatus.RUNNING));
        System.out.println("Stopped: " + stats.get(ServiceStatus.STOPPED));
        System.out.println("Failed: " + stats.get(ServiceStatus.FAILED));
        System.out.println("Total: " + (stats.get(ServiceStatus.RUNNING) + stats.get(ServiceStatus.STOPPED) + stats.get(ServiceStatus.FAILED)));
    }
}

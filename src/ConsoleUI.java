import java.util.List;
import java.util.Map;
import java.util.Scanner;

// Console interface for managing services
public class ConsoleUI {
    private static final String SERVICE_NAME_PROMPT = "Enter service name: ";
    private static final String SERVICE_NAME_EMPTY_ERROR = "Service name cannot be empty.";
    private final ServiceManager manager;
    private Scanner scanner;

    // Constructor - initialize with service manager
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
        System.out.println("1. WebService (web)");
        System.out.println("2. DatabaseService (db)");
        System.out.println("3. WorkerService (worker)");
        System.out.print("Enter service type: ");
        String typeInput = scanner.nextLine().toUpperCase();
        int type;

        // Match by number or prefix
        if (typeInput.equals("1") || typeInput.startsWith("W")) {
            type = 1;
        } else if (typeInput.equals("2") || typeInput.startsWith("D")) {
            type = 2;
        } else if (typeInput.equals("3") || typeInput.startsWith("WO")) {
            type = 3;
        } else {
            System.out.println("Invalid service type. Must be 1, 2, 3, web, db, or worker.");
            return;
        }

        // Get type-specific field first
        Integer port = null;
        String dbType = null;
        String jobType = null;

        switch (type) {
            case 1:
                System.out.println("\n--- Choose Port ---");
                System.out.println("Port must be between 1 and 65535");
                port = getIntInput("Enter port: ");
                try {
                    // Validate port by creating temporary service
                    new WebService("temp", "1.0", Environment.DEVELOPMENT, ServiceStatus.STOPPED, port);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid port: " + e.getMessage());
                    return;
                }
                break;
            case 2:
                System.out.println("\n--- Choose Database Type ---");
                System.out.println("1. PostgreSQL");
                System.out.println("2. MySQL");
                System.out.println("3. Redis");
                System.out.println("4. MongoDB");
                System.out.println("5. Elasticsearch");
                System.out.print("Enter database type: ");
                dbType = scanner.nextLine();
                if (dbType.isEmpty()) {
                    System.out.println("Database type cannot be empty.");
                    return;
                }
                // Match by number or prefix
                String dbTypeUpper = dbType.toUpperCase();
                if (dbTypeUpper.equals("1") || dbTypeUpper.startsWith("P")) {
                    dbType = "PostgreSQL";
                } else if (dbTypeUpper.equals("2") || dbTypeUpper.startsWith("M")) {
                    dbType = "MySQL";
                } else if (dbTypeUpper.equals("3") || dbTypeUpper.startsWith("R")) {
                    dbType = "Redis";
                } else if (dbTypeUpper.equals("4") || dbTypeUpper.startsWith("MO")) {
                    dbType = "MongoDB";
                } else if (dbTypeUpper.equals("5") || dbTypeUpper.startsWith("E")) {
                    dbType = "Elasticsearch";
                }
                break;
            case 3:
                System.out.println("\n--- Choose Job Type ---");
                System.out.println("1. EmailJob");
                System.out.println("2. ReportJob");
                System.out.println("3. CleanupJob");
                System.out.println("4. BackupJob");
                System.out.println("5. NotificationJob");
                System.out.print("Enter job type: ");
                jobType = scanner.nextLine();
                if (jobType.isEmpty()) {
                    System.out.println("Job type cannot be empty.");
                    return;
                }
                // Match by number or prefix
                String jobTypeUpper = jobType.toUpperCase();
                if (jobTypeUpper.equals("1") || jobTypeUpper.startsWith("E")) {
                    jobType = "EmailJob";
                } else if (jobTypeUpper.equals("2") || jobTypeUpper.startsWith("R")) {
                    jobType = "ReportJob";
                } else if (jobTypeUpper.equals("3") || jobTypeUpper.startsWith("C")) {
                    jobType = "CleanupJob";
                } else if (jobTypeUpper.equals("4") || jobTypeUpper.startsWith("B")) {
                    jobType = "BackupJob";
                } else if (jobTypeUpper.equals("5") || jobTypeUpper.startsWith("N")) {
                    jobType = "NotificationJob";
                }
                break;
        }

        // Now get common fields
        System.out.print(SERVICE_NAME_PROMPT);
        String name = scanner.nextLine();
        if (name.isEmpty()) {
            System.out.println(SERVICE_NAME_EMPTY_ERROR);
            return;
        }

        System.out.println("Version format: numbers separated by dots (e.g., 1, 1.0, 2.5.1)");
        System.out.print("Enter version: ");
        String version = scanner.nextLine();
        if (version.isEmpty()) {
            System.out.println("Version cannot be empty.");
            return;
        }
        // Validate version format
        if (!isValidVersion(version)) {
            System.out.println("Version must contain only numbers (e.g., 1, 1.0, 2.5.1).");
            return;
        }

        System.out.println("\n--- Choose Environment ---");
        System.out.println("1. DEVELOPMENT (dev)");
        System.out.println("2. STAGING (staging)");
        System.out.println("3. PRODUCTION (prod)");
        System.out.print("Enter environment: ");
        String envStr = scanner.nextLine().toUpperCase();
        Environment environment;
        try {
            // Match by number or prefix
            if (envStr.equals("1") || envStr.startsWith("D")) {
                environment = Environment.DEVELOPMENT;
            } else if (envStr.equals("2") || envStr.startsWith("S")) {
                environment = Environment.STAGING;
            } else if (envStr.equals("3") || envStr.startsWith("P")) {
                environment = Environment.PRODUCTION;
            } else {
                // Try exact match as fallback
                environment = Environment.valueOf(envStr);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid environment. Must be 1, 2, 3, DEVELOPMENT, STAGING, or PRODUCTION.");
            return;
        }

        System.out.println("\n--- Choose Status ---");
        System.out.println("1. RUNNING (run)");
        System.out.println("2. STOPPED (stop)");
        System.out.println("3. FAILED (fail)");
        System.out.print("Enter status: ");
        String statusStr = scanner.nextLine().toUpperCase();
        ServiceStatus status;
        try {
            // Match by number or prefix
            if (statusStr.equals("1") || statusStr.startsWith("R")) {
                status = ServiceStatus.RUNNING;
            } else if (statusStr.equals("2") || statusStr.startsWith("S")) {
                status = ServiceStatus.STOPPED;
            } else if (statusStr.equals("3") || statusStr.startsWith("F")) {
                status = ServiceStatus.FAILED;
            } else {
                // Try exact match as fallback
                status = ServiceStatus.valueOf(statusStr);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid status. Must be 1, 2, 3, RUNNING, STOPPED, or FAILED.");
            return;
        }

        // Create the service based on type
        Service newService = null;
        switch (type) {
            case 1:
                newService = new WebService(name, version, environment, status, port);
                break;
            case 2:
                newService = new DatabaseService(name, version, environment, status, dbType);
                break;
            case 3:
                newService = new WorkerService(name, version, environment, status, jobType);
                break;
        }

        // Add service to manager
        if (manager.addService(newService)) {
            System.out.println(name + " added successfully.");
        } else {
            System.out.println("Service with name '" + name + "' already exists.");
        }
    }

    // Remove a service by name
    private void removeService() {
        System.out.println("\n--- Remove Service ---");
        System.out.println("Existing services:");
        for (Service service : manager.getServices()) {
            System.out.println("- " + service.getName());
        }
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
        System.out.println("Existing services:");
        for (Service service : manager.getServices()) {
            System.out.println("- " + service.getName() + " (" + service.getStatus() + ")");
        }
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
        System.out.println("Existing services:");
        for (Service service : manager.getServices()) {
            System.out.println("- " + service.getName() + " (" + service.getStatus() + ")");
        }
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
        System.out.println("Existing services:");
        for (Service service : manager.getServices()) {
            System.out.println("- " + service.getName());
        }
        System.out.print(SERVICE_NAME_PROMPT);
        String name = scanner.nextLine();
        if (name.isEmpty()) {
            System.out.println(SERVICE_NAME_EMPTY_ERROR);
            return;
        }
        Service service = manager.getService(name);
        if (service == null) {
            System.out.println("Service not found: " + name);
            return;
        }
        String oldVersion = service.getVersion();
        System.out.println("Current version: " + oldVersion);
        System.out.println("Version format: numbers separated by dots (e.g., 1, 1.0, 2.5.1)");
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
            System.out.println(name + " deployed successfully from " + oldVersion + " to " + version + ".");
        } else {
            System.out.println("Service " + name + " is not deployable or not found.");
        }
    }

    // Display services filtered by environment
    private void displayServicesByEnvironment() {
        System.out.println("\n--- Services by Environment ---");
        System.out.println("1. DEVELOPMENT (dev)");
        System.out.println("2. STAGING (staging)");
        System.out.println("3. PRODUCTION (prod)");
        System.out.print("Enter environment: ");
        String env = scanner.nextLine().toUpperCase();
        try {
            // Match by number or prefix
            String envName;
            if (env.equals("1") || env.startsWith("D")) {
                envName = "DEVELOPMENT";
            } else if (env.equals("2") || env.startsWith("S")) {
                envName = "STAGING";
            } else if (env.equals("3") || env.startsWith("P")) {
                envName = "PRODUCTION";
            } else {
                envName = env;
            }
            // Get and display services in the selected environment
            List<Service> services = manager.getServicesByEnvironment(envName);
            if (services.isEmpty()) {
                System.out.println("No services found in environment: " + envName);
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
            return false;
        }
        if (version.length() > 20) {
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

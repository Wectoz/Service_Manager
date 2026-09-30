import java.util.ArrayList;
import java.util.List;

// Manages all the services in the system - add, remove, start, stop, deploy
public class ServiceManager {
    private List<Service> services;

    public ServiceManager() {
        services = new ArrayList<>();
    }
    
    public void addService(Service service) {
        services.add(service);
    }
    public boolean removeService(String serviceName) {
        try {
            Service service = findServiceByName(serviceName);
            services.remove(service);
            return true;
        } catch (ServiceNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    // Search for services by name (case-insensitive)
    public boolean searchServices(String keyword) {
        boolean found = false;
        String lowerKeyword = keyword.toLowerCase();
        for (Service service : services) {
            if (service.getName().toLowerCase().contains(lowerKeyword)) {
                System.out.println(service.getName());
                found = true;
            }
        }
        return found;
    }
    
    // Find a service by its name - throws if not found
    public Service findServiceByName(String name) throws ServiceNotFoundException {
        for (Service service : services) {
            if (service.getName().equalsIgnoreCase(name)) {
                return service;
            }
        }
        throw new ServiceNotFoundException("Service not found: " + name);
    }
    
    public List<Service> getServices() {
        return services;
    }
    public boolean startService(String serviceName) {
        try {
            Service service = findServiceByName(serviceName);
            service.start();
            return true;
        } catch (ServiceNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public boolean stopService(String serviceName) {
        try {
            Service service = findServiceByName(serviceName);
            service.stop();
            return true;
        } catch (ServiceNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    // Deploy a new version - only works for services that implement Deployable
    public boolean deployService(String serviceName, String newVersion) {
        try {
            Service service = findServiceByName(serviceName);
            if (service instanceof Deployable) {
                ((Deployable) service).deploy(newVersion);
                System.out.println("Deployed new version " + newVersion + " for service " + service.getName());
                return true;
            } else {
                System.out.println("Service " + serviceName + " is not deployable");
                return false;
            }
        } catch (ServiceNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    // Show all services in a specific environment
    public void displayServicesInEnvironment(String environment) {
        boolean found = false;
        for (Service service : services) {
            if (service.getEnvironment().toString().equals(environment)) {
                System.out.println(service.getName());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No services found in environment: " + environment);
        }
    }
    
    // Count how many services are in each status
    public void displayServiceStatistics() {
        int running = 0;
        int stopped = 0;
        int failed = 0;
        
        for (Service service : services) {
            switch (service.getStatus()) {
                case RUNNING:
                    running++;
                    break;
                case STOPPED:
                    stopped++;
                    break;
                case FAILED:
                    failed++;
                    break;
            }
        }
        
        System.out.println("Service Statistics:");
        System.out.println("Running: " + running);
        System.out.println("Stopped: " + stopped);
        System.out.println("Failed: " + failed);
        System.out.println("Total: " + services.size());
    }
}

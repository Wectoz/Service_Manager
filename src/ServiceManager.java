import java.util.*;

// Manages all the services in the system - add, remove, start, stop, deploy
public class ServiceManager {
    private final List<Service> services;

    public ServiceManager() {
        services = new ArrayList<>();
    }
    
    // Add a service if it doesn't already exist
    public boolean addService(Service service) {
        try {
            findServiceByName(service.getName());
            return false;
        } catch (ServiceNotFoundException e) {
            services.add(service);
            return true;
        }
    }
    
    // Remove a service by name
    public boolean removeService(String serviceName) {
        try {
            Service service = findServiceByName(serviceName);
            services.remove(service);
            return true;
        } catch (ServiceNotFoundException e) {
            return false;
        }
    }
    
    // Search for services by name (case-insensitive)
    public List<String> searchServices(String keyword) {
        List<String> results = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        for (Service service : services) {
            if (service.getName().toLowerCase().contains(lowerKeyword)) {
                results.add(service.getName());
            }
        }
        return results;
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
    
    // Return an unmodifiable list of all services
    public List<Service> getServices() {
        return Collections.unmodifiableList(services);
    }
    
    // Start a service by name
    public boolean startService(String serviceName) {
        try {
            Service service = findServiceByName(serviceName);
            if (service.getStatus() == ServiceStatus.RUNNING) {
                return false;
            }
            service.start();
            return true;
        } catch (ServiceNotFoundException e) {
            return false;
        }
    }
    
    // Stop a service by name
    public boolean stopService(String serviceName) {
        try {
            Service service = findServiceByName(serviceName);
            if (service.getStatus() == ServiceStatus.STOPPED) {
                return false;
            }
            service.stop();
            return true;
        } catch (ServiceNotFoundException e) {
            return false;
        }
    }
    
    // Deploy a new version - only works for services that implement Deployable
    public boolean deployService(String serviceName, String newVersion) {
        try {
            Service service = findServiceByName(serviceName);
            if (service instanceof Deployable) {
                ((Deployable) service).deploy(newVersion);
                return true;
            } else {
                return false;
            }
        } catch (ServiceNotFoundException e) {
            return false;
        }
    }
    // Show all services in a specific environment
    public List<Service> getServicesByEnvironment(String environment) {
        List<Service> servicesInEnvironment = new ArrayList<>();
        for (Service service : services) {
            if (service.getEnvironment().equals(Environment.valueOf(environment))) {
                servicesInEnvironment.add(service);
            }
        }
        return servicesInEnvironment;
    }
    
    // Count how many services are in each status
    public Map<ServiceStatus, Integer> getServiceStatistics() {
        Map<ServiceStatus, Integer> statusCount = new HashMap<>();
        statusCount.put(ServiceStatus.RUNNING, 0);
        statusCount.put(ServiceStatus.STOPPED, 0);
        statusCount.put(ServiceStatus.FAILED, 0);
        
        for (Service service : services) {
            statusCount.put(service.getStatus(), statusCount.get(service.getStatus()) + 1);
        }
        return statusCount;
    }
}

// Thrown when we try to find a service that doesn't exist
public class ServiceNotFoundException extends Exception{
    public ServiceNotFoundException(String message) {
        super(message);
    }
}

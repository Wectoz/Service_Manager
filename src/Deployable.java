// Services that can be deployed with new versions implement this
public interface Deployable {
    void deploy(String newVersion);
}

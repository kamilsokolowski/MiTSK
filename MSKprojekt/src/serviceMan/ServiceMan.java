package serviceMan;

/**
 * Created by Stanislaw on 08.05.2018.
 */
public class ServiceMan {
    private int idServiceMan;
    private int clientId;
    private int timeToClient;
    private boolean isAvailable;
    private static int numberOfServiceMan;

    public ServiceMan() {
        this.idServiceMan = numberOfServiceMan;
        this.timeToClient = -1;
        this.isAvailable = true;
        numberOfServiceMan++;
    }

    public int getIdServiceMan() {
        return idServiceMan;
    }

    public void setIdServiceMan(int idServiceMan) {
        this.idServiceMan = idServiceMan;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getTimeToClient() {
        return timeToClient;
    }

    public void setTimeToClient(int timeToClient) {
        this.timeToClient = timeToClient;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public static int getNumberOfServiceMan() {
        return numberOfServiceMan;
    }

    public static void setNumberOfServiceMan(int numberOfServiceMan) {
        ServiceMan.numberOfServiceMan = numberOfServiceMan;
    }

    public void applyObstructionDelay(int delay) {
        this.timeToClient += delay;
    }
}

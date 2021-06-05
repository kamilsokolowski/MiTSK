package client;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Stanislaw on 08.05.2018.
 */

public class Client {
    private int idClient;
    private int distanceToFirm;
    private boolean serviceCalled;
    private static int numberOfClients = 0;
    private Device myDevice;

    public Client() {
        this.idClient = numberOfClients;
        this.distanceToFirm = ThreadLocalRandom.current().nextInt(15, 30 + 1);
        this.serviceCalled = false;
        this.myDevice = new Device(this.idClient, true);
        numberOfClients++;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getDistanceToFirm() {
        return distanceToFirm;
    }

    public void setDistanceToFirm(int distanceToFirm) {
        this.distanceToFirm = distanceToFirm;
    }

    public boolean isServiceCalled() {
        return serviceCalled;
    }

    public void setServiceCalled(boolean serviceCalled) {
        this.serviceCalled = serviceCalled;
    }

    public void setDeviceOperational (boolean state) {
        this.myDevice.setOperational(state);
    }

    public boolean isDeviceOperational() {
        return this.myDevice.isOperational();
    }
}
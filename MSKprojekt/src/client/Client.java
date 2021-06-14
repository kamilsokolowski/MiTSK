package client;

import settigs.SimulationSettings;

import java.util.concurrent.ThreadLocalRandom;

/**
 * This class represents Client in simulation.
 */

public class Client {
    private int idClient;
    private int distanceToFirm;
    private boolean serviceCalled;
    private Device myDevice;

    /**
     * Creates Client with given id number. Id should be uniq among all clients.
     * During creation Client is assigned with distance to service firm as time required to arrive to Client.
     * Minimal distance to firm is given in SimulationSettings.minClientDistance and maximum distance
     * is given in SimulationSettings.maxClientDistance.
     * Client is associated with Device by Id number. It means Client with idClient = 0 operates Device
     * with idDevice = 0.
     */
    public Client(int id) {
        this.idClient = id;
        this.distanceToFirm = ThreadLocalRandom.current().nextInt(
                SimulationSettings.minClientDistance, SimulationSettings.maxClientDistance + 1
        );
        this.serviceCalled = false;
        this.myDevice = new Device(this.idClient, true);
    }
    /**
     * Set of public helper methods. Helper methods are getters and setters.
     * */
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
    /**
     * Override of toString method for easier displaying.
     * */
    @Override
    public String toString() {
        return "Client{" +
                "idClient=" + idClient +
                ", serviceCalled=" + serviceCalled +
                '}';
    }
}
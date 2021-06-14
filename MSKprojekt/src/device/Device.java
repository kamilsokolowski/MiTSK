package device;

import settigs.SimulationSettings;

import java.util.concurrent.ThreadLocalRandom;

public class Device {
    private int idDevice;
    private boolean isOperational;
    private int timeToFailure;

    public Device(int id) {
        this.idDevice = id;
        this.isOperational = true;
    }

    public int getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(int idDevice) {
        this.idDevice = idDevice;
    }

    public boolean isOperational() {
        return isOperational;
    }

    public void setOperational(boolean operational) {
        isOperational = operational;
    }

    public int getTimeToFailure() {
        return timeToFailure;
    }

    public void setTimeToFailure(int timeToFailure) {
        this.timeToFailure = timeToFailure + ThreadLocalRandom.current().nextInt(
                SimulationSettings.minDeviceTimeToFailure, SimulationSettings.maxDeviceTimeToFailure + 1
        );
    }

    @Override
    public String toString() {
        return "Device{" +
                "idDevice=" + idDevice +
                ", isOperational=" + isOperational +
                '}';
    }
}

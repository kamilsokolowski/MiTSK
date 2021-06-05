package device;

import java.util.concurrent.ThreadLocalRandom;

public class Device {
    private int idDevice;
    private boolean isOperational;
    private int timeToFailure;
    private static int numberOfDevices = 0;

    public Device() {
        this.idDevice = numberOfDevices;
        this.isOperational = true;
        numberOfDevices ++;
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
        this.timeToFailure = timeToFailure + ThreadLocalRandom.current().nextInt(10, 20 + 1);
    }

    public static int getNumberOfDevices() {
        return numberOfDevices;
    }

    public static void setNumberOfDevices(int numberOfDevices) {
        Device.numberOfDevices = numberOfDevices;
    }
}

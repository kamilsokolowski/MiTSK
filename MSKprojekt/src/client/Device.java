package client;

public class Device {
    private int deviceId;
    private boolean isOperational;

    public Device(int deviceId, boolean isOperational) {
        this.deviceId = deviceId;
        this.isOperational = isOperational;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public boolean isOperational() {
        return isOperational;
    }

    public void setOperational(boolean operational) {
        isOperational = operational;
    }
}

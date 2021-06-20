package settigs;

public class SimulationSettings {
    /**
     * IMPORTANT NOTE! numberOfClients and numberOfDevices MUST be equal.
     * */
    public static int numberOfClients = 8;
    public static int numberOfDevices = 8;
    public static int numberOfServiceMan = 3;

    public static int minClientDistance = 15;
    public static int maxClientDistance = 30;

    public static int minDeviceTimeToFailure = 10;
    public static int maxDeviceTimeToFailure = 20;

    public static int minDelayOccurrence = 10;
    public static int maxDelayOccurrence = 50;

    public static int minDelayTime = 5;
    public static int maxDelayTime = 15;
}

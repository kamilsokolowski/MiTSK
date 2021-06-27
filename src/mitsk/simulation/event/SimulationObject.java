package mitsk.simulation.event;

public abstract class SimulationObject {
    private Simulation sim;

    public SimulationObject(Simulation sim) {
        this.sim = sim;
    }

    public Simulation getSim() {
        return sim;
    }
}

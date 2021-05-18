package mitsk.simulation.discrete.step;

public abstract class SimulationObject {
    private Simulation sim;

    public SimulationObject(Simulation sim) { this.sim = sim; }

    public Simulation getSim() { return this.sim; }

    public abstract void timeAdvanced();
}

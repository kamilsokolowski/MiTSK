package mitsk.simulation.event;

public abstract class SimulationEvent implements Comparable <SimulationEvent>{
    protected SimulationObject simObj;
    protected Integer plannedTime;

    public SimulationEvent(SimulationObject simObj, int plannedTime) {
        this.simObj = simObj;
        this.plannedTime = plannedTime;
    }

    public abstract void handle();

    public int getPlannedTime() {
        return plannedTime;
    }

    @Override
    public int compareTo(SimulationEvent o) {
        return this.plannedTime.compareTo(o.plannedTime);
    }
}

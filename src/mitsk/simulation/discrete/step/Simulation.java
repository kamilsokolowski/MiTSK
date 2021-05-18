package mitsk.simulation.discrete.step;

import mitsk.simulation.discrete.shop.Checkout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Simulation {
    private int maxSimulationTime;
    private int step;
    private int simTime;
    private List<SimulationObject> objects;
    private List<SimulationObject> addedObjects;
    private List<SimulationObject> removedObjects;
    private List<Checkout> checkouts;

    public Simulation(int maxSimulationTime, int step) {
        this.maxSimulationTime = maxSimulationTime;
        this.step = step;
        this.simTime = 0;
        this.objects = new ArrayList<>();
        this.addedObjects = new ArrayList<>();
        this.removedObjects = new ArrayList<>();
        this.checkouts = new ArrayList<>();
    }

    public int getTime() { return this.simTime; }
    public void register(SimulationObject obj) { this.addedObjects.add(obj); }
    public void unregister(SimulationObject obj) { this.removedObjects.add(obj); }

    public void run() throws InterruptedException {
        while(this.simTime <= this.maxSimulationTime) {
            //Thread.sleep(1000);
            for (Iterator<SimulationObject> it = objects.iterator(); it.hasNext();) {
                SimulationObject next = it.next();
                next.timeAdvanced();
            }
            objects.addAll(addedObjects);
            for (Iterator<SimulationObject> it = removedObjects.iterator(); it.hasNext();) {
                SimulationObject next = it.next();
                objects.remove(next);
            }
            removedObjects.clear();
            addedObjects.clear();
            this.simTime += this.step;
        }
        this.simTime = -1;
    }

    public List<Checkout> getCheckouts() {
        return checkouts;
    }

    public void setCheckouts(Checkout checkout) {
        this.checkouts.add(checkout);
    }
}

package mitsk.simulation.event;

import mitsk.simulation.discrete.step.SimulationObject;
import mitsk.simulation.event.shop.ServiceClientEvent;
import mitsk.simulation.event.shop.SpawnClientsEvent;

import java.util.*;

public class Simulation {
    private int maxSimTime = 100;
    private int simTime = 0;

    private List<SimulationEvent> eventSchedule = new ArrayList<>();
    private List<SimulationEvent> eventToRegister = new ArrayList<>();
    private List<SimulationEvent> eventsToDelete = new ArrayList<>();

    public int getTime() { return simTime; }

    public void register(SimulationEvent simEvent) {
        this.eventToRegister.add(simEvent);
    }

    public void unregister(SimulationEvent simEvent) {
        this.eventsToDelete.add(simEvent);
    }

    public void run() {
        while(simTime <= maxSimTime) {
            Collections.sort(eventSchedule);
            for (Iterator<SimulationEvent> it = eventSchedule.iterator(); it.hasNext();) {
                SimulationEvent next = it.next();

                this.simTime = next.getPlannedTime();
                next.handle();

                this.eventsToDelete.add(next);
            }
            this.eventSchedule.removeAll(this.eventsToDelete);
            this.eventSchedule.addAll(this.eventToRegister);

            this.eventToRegister.clear();
            this.eventsToDelete.clear();
        }
    }
}

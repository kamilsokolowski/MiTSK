package mitsk;


import mitsk.simulation.event.Simulation;
import mitsk.simulation.event.SimulationObject;
import mitsk.simulation.event.shop.SimulationDummyObject;
import mitsk.simulation.event.shop.SpawnClientsEvent;

public class Main {

    public static void main(String[] args) {
        Simulation eventSim = new Simulation();
        SimulationObject simObj = new SimulationDummyObject(eventSim);
        eventSim.register(new SpawnClientsEvent(simObj,0));
        eventSim.run();
    }
}

package mitsk.simulation.event.shop;

import mitsk.generators.Range;
import mitsk.generators.Weighted;
import mitsk.simulation.event.SimulationEvent;
import mitsk.simulation.event.SimulationObject;

public class ClientEntersShopEvent extends SimulationEvent {

    private int clientId;

    public ClientEntersShopEvent(SimulationObject simObj, int plannedTime, int clientId) {
        super(simObj, plannedTime);
        this.clientId = clientId;
    }

    @Override
    public void handle() {
        System.out.println("Klient - " + this.clientId + " 1. udał się do kasy" );
        this.simObj.getSim().register(
                new ServiceClientEvent(
                        this.simObj,
                        (int) (simObj.getSim().getTime()) + 4,
                        this.clientId
                )
        );
    }
}

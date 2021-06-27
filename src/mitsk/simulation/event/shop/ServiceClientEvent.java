package mitsk.simulation.event.shop;

import mitsk.generators.Range;
import mitsk.generators.Weighted;
import mitsk.simulation.event.SimulationEvent;
import mitsk.simulation.event.SimulationObject;

public class ServiceClientEvent extends SimulationEvent {
    private int clientId;

    public ServiceClientEvent(SimulationObject simObj, int plannedTime , int clientId) {
        super(simObj, plannedTime);
        this.clientId = clientId;
    }

    @Override
    public void handle() {
        Range t0 = new Range(1, 2, 50);
        Range t1 = new Range(3, 5, 50);

        Weighted weightedGen = new Weighted(new Range[]{t0, t1});
        double isServiced = weightedGen.getNext();

        if(isServiced >= 1.0 && isServiced <= 2.0) {
            simObj.getSim().unregister(this);
            System.out.println("Klient - " + this.clientId + " 3. został obsłużony" );
        }
        else{
            this.simObj.getSim().register(
                    new ServiceClientEvent(
                            this.simObj,
                            (int) (simObj.getSim().getTime()) + 4,
                            this.clientId
                    )
            );
        }
    }
}

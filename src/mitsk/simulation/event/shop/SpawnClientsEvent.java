package mitsk.simulation.event.shop;

import mitsk.generators.RoundRobin;
import mitsk.generators.Uniform;
import mitsk.simulation.event.SimulationEvent;
import mitsk.simulation.event.SimulationObject;

public class SpawnClientsEvent extends SimulationEvent {

    public static int clientsSpawned = 0;
    private static int newClientTimeMin = 4; // newClientTimeMin = 3; Parametr do demonstacji zamykania kasy
    private static int newClientTimeMax = 6; // newClientTimeMax = 50; Parametr do demonstacji zamykania kasy
    // generator wykorzystywany do określenia rozmiaru porcji klientów
    private static RoundRobin roundRobinGen = new RoundRobin(new double[] {8, 7, 5});
    // generator wykorzystany do określenia czasu do wygenerowania nowego klienta
    private static Uniform uniformGen = new Uniform(
            SpawnClientsEvent.newClientTimeMin,
            SpawnClientsEvent.newClientTimeMax
    );

    public SpawnClientsEvent(SimulationObject simObj, int plannedTime) {
        super(simObj, plannedTime);
    }

    @Override
    public void handle() {
        int numberOfClients = (int) SpawnClientsEvent.roundRobinGen.getNext();
        Uniform uniformGenClientService = new Uniform(7.0, 12.0);
        for(int i = 0; i < numberOfClients; i++) {
            System.out.println("Klient - " + SpawnClientsEvent.clientsSpawned + " 1. przybył do sklepu" );
            this.simObj.getSim().register(
                    new ClientEntersShopEvent(
                            this.simObj,
                            (int) (simObj.getSim().getTime() + uniformGenClientService.getNext()),
                            SpawnClientsEvent.clientsSpawned
                    )
            );
            SpawnClientsEvent.clientsSpawned++;
        }

        if(SpawnClientsEvent.clientsSpawned < 20) {
            this.simObj.getSim().register(
                    new SpawnClientsEvent(
                            this.simObj,
                            (int) (simObj.getSim().getTime() + uniformGen.getNext())
                    )
            );
        }
    }
}

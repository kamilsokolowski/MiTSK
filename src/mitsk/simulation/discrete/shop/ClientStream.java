package mitsk.simulation.discrete.shop;

import mitsk.generators.RoundRobin;
import mitsk.generators.Uniform;
import mitsk.simulation.discrete.step.Simulation;
import mitsk.simulation.discrete.step.SimulationObject;

public class ClientStream extends SimulationObject {
    private int simTime; // czas symulacji
    private int timeToGenerateClients; // czas do zarejestrowania nowej porcji klientów
    private int newClientTimeMin = 4; // newClientTimeMin = 3; Parametr do demonstacji zamykania kasy
    private int newClientTimeMax = 6; // newClientTimeMax = 50; Parametr do demonstacji zamykania kasy
    private Uniform uniformGen; // generator wykorzystany do określenia czasu do wygenerowania nowego klienta
    private RoundRobin roundRobinGen; // generator wykorzystywany do określenia rozmiaru porcji klientów

    public ClientStream(Simulation sim) {
        super(sim);
        this.simTime = 0;
        this.uniformGen = new Uniform(this.newClientTimeMin, this.newClientTimeMax);
        this.roundRobinGen = new RoundRobin(new double[] {8, 7, 5});
        this.timeToGenerateClients = (int) (this.getSim().getTime() + uniformGen.getNext());
    }

    @Override
    public void timeAdvanced() {
        this.simTime = this.getSim().getTime(); // aktualizacja czasu symulacji
        // wygenerowanie nowej porcji klientów o określonym czasie
        if(this.simTime == this.timeToGenerateClients) {
            int numberOfClients = (int) this.roundRobinGen.getNext();
            for(int i = 0; i < numberOfClients; ++i) {
                this.getSim().register(new Client(this.getSim())); // Zarejestrowanie nowego Klienta do symulacji
            }
            // określenie nowego czasu do wygenerowania klientów
            this.timeToGenerateClients = (int) (this.getSim().getTime() + uniformGen.getNext());
        }
    }
}

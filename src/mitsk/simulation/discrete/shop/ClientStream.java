package mitsk.simulation.discrete.shop;

import mitsk.generators.RoundRobin;
import mitsk.generators.Uniform;
import mitsk.simulation.discrete.step.Simulation;
import mitsk.simulation.discrete.step.SimulationObject;

public class ClientStream extends SimulationObject {
    private int simTime;
    private int timeToGenerateClients;
    private int newClientTimeMin = 4;
    private int newClientTimeMax = 6;
    private Uniform uniformGen;
    private RoundRobin roundRobinGen;

    public ClientStream(Simulation sim) {
        super(sim);
        this.simTime = 0;
        this.uniformGen = new Uniform(this.newClientTimeMin, this.newClientTimeMax);
        this.roundRobinGen = new RoundRobin(new double[] {8, 7, 5});
        this.timeToGenerateClients = (int) (this.getSim().getTime() + uniformGen.getNext());
    }

    @Override
    public void timeAdvanced() {
        this.simTime = this.getSim().getTime();
        if(this.simTime == this.timeToGenerateClients) {
            int numberOfClients = (int) this.roundRobinGen.getNext();
            for(int i = 0; i < numberOfClients; ++i) {
                this.getSim().register(new Client(this.getSim()));
            }
            this.timeToGenerateClients = (int) (this.getSim().getTime() + uniformGen.getNext());
        }
    }
}

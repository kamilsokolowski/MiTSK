package mitsk.simulation.discrete.shop;

import mitsk.generators.Range;
import mitsk.generators.Uniform;
import mitsk.generators.Weighted;
import mitsk.simulation.discrete.step.Simulation;
import mitsk.simulation.discrete.step.SimulationObject;

public class Client extends SimulationObject {
    public static int clienCount = 0;
    private int simTime;
    private int timeBeforeCheckOut;
    private int timeToLive;
    private int id;
    private boolean inQue;
    private Checkout checkoutImStanding;

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                '}';
    }

    public Client(Simulation sim) {
        super(sim);
        this.simTime = 0;
        Uniform uniformGen = new Uniform(7.0, 12.0);
        this.timeBeforeCheckOut = (int) (this.getSim().getTime() + uniformGen.getNext());
        this.timeToLive = 0;
        this.id = clienCount;
        this.inQue = false;
        clienCount++;
    }

    @Override
    public void timeAdvanced() {
        this.simTime = this.getSim().getTime();
        if(this.inQue == false && (this.timeBeforeCheckOut == this.simTime)) {
            Checkout checkout1 = this.getSim().getCheckouts().get(0);
            Checkout checkout2 = this.getSim().getCheckouts().get(1);
            if(checkout1.getQueLength() <= checkout2.getQueLength()){
                checkoutImStanding = checkout1;
                checkout1.joinQue(this);
            }
            else {
                checkoutImStanding = checkout2;
                checkout2.joinQue(this);
            }
            this.inQue = true;
            this.timeToLive = this.getSim().getTime() + 5;
        }
        if(this.inQue == true && this.timeToLive == this.simTime) {
            Range t0 = new Range(1, 2, 25);
            Range t1 = new Range(3, 5, 75);
            Weighted weightedGen = new Weighted(new Range[]{t0, t1});
            double isLeaving = weightedGen.getNext();
            //System.out.println("Client id: " + this.id + " val: " + isLeaving);
            if(isLeaving >= 1.0 && isLeaving <= 2.0) {
                checkoutImStanding.leaveQue(this);
                this.getSim().unregister(this);
            }
            else{
                this.timeToLive = this.getSim().getTime() + 2;
            }
        }
    }
}

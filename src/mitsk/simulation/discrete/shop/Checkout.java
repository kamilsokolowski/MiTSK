package mitsk.simulation.discrete.shop;

import mitsk.generators.Range;
import mitsk.generators.Weighted;
import mitsk.simulation.discrete.step.Simulation;
import mitsk.simulation.discrete.step.SimulationObject;

import java.util.ArrayList;
import java.util.List;


public class Checkout extends SimulationObject {
    private int idCheckout;
    private int simTime;
    private int timeToHandleClient;
    private int lastClientServedTime;
    private boolean isOpen;
    private boolean isTimeToHandleClientSet;
    private int queLength;
    private int timeToExit;
    private Weighted weightedGen;
    private Checkout neighbourCheckout;
    private List<Client> queue;

    public Checkout(Simulation sim, int id, boolean isOpen) {
        super(sim);
        this.idCheckout = id;
        this.simTime = 0;
        this.timeToHandleClient = 0;
        this.isOpen = isOpen;
        this.isTimeToHandleClientSet = false;
        this.queLength = 0;

        Range t0 = new Range(1, 2, 20);
        Range t1 = new Range(3, 4, 50);
        Range t2 = new Range(5, 6, 10);
        Range t3 = new Range(7, 8, 20);
        this.weightedGen = new Weighted(new Range[] {t0, t1, t2, t3});

        this.queue = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Checkout{" +
                "simTime=" + simTime +
                ", isOpen=" + isOpen +
                ", queLength=" + queLength +
                '}';
    }

    @Override
    public void timeAdvanced() {
        this.simTime = this.getSim().getTime();
        if (this.queLength != 0){
            if(this.queLength > 3 && !neighbourCheckout.isOpen()){
                neighbourCheckout.setOpen(true);
            }
            if(!this.isTimeToHandleClientSet){
                System.out.println("Kasjer " + this.idCheckout + " 1. podjął klienta" );
                double time = weightedGen.getNext();
                if(time >= 1 && time <= 2) {
                    this.timeToHandleClient = this.getSim().getTime() + 1;
                    this.isTimeToHandleClientSet = true;
                }
                else if (time >= 3 && time <= 4) {
                    this.timeToHandleClient = this.getSim().getTime() + 2;
                    this.isTimeToHandleClientSet = true;
                }
                else if (time >= 5 && time <= 6) {
                    this.timeToHandleClient = this.getSim().getTime() + 4;
                    this.isTimeToHandleClientSet = true;
                }
            }
            if(this.timeToHandleClient == this.simTime){
                this.timeToHandleClient = -1;
                Client clnt = this.queue.get(0);
                this.queue.remove(clnt);
                this.getSim().unregister(clnt);
                this.queLength = this.queue.size();
                this.lastClientServedTime = this.getSim().getTime();
                System.out.println("Klient " + clnt.getId() + " 4. został obsłużony" );
                System.out.println("Kasjer " + this.idCheckout + " 2. zakończył obsługę klienta" );
                double time = weightedGen.getNext();
                while(this.timeToHandleClient == -1) {
                    if (time >= 1 && time <= 2) {
                        this.timeToHandleClient = this.getSim().getTime() + 1;
                    } else if (time >= 3 && time <= 4) {
                        this.timeToHandleClient = this.getSim().getTime() + 2;
                    } else if (time >= 5 && time <= 6) {
                        this.timeToHandleClient = this.getSim().getTime() + 4;
                    }
                    time = weightedGen.getNext();
                }
            }
        }
        else {
            this.timeToHandleClient = 0;
        }
    }

    public void joinQue (Client clnt) {
        this.queue.add(clnt);
        this.queLength = this.queue.size();
    }

    public void leaveQue (Client clnt) {
        this.queue.remove(clnt);
        this.queLength = this.queue.size();
    }

    public int getQueLength() {
        return queLength;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public void setNeighbourCheckout(Checkout neighbourCheckout) {
        this.neighbourCheckout = neighbourCheckout;
    }
}

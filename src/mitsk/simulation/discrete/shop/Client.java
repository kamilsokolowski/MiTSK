package mitsk.simulation.discrete.shop;

import mitsk.generators.Range;
import mitsk.generators.Uniform;
import mitsk.generators.Weighted;
import mitsk.simulation.discrete.step.Simulation;
import mitsk.simulation.discrete.step.SimulationObject;

public class Client extends SimulationObject {
    // licznik liczby klientów wykorzystywany do tworzenia unikatowych identyfikatorów klientów
    public static int clienCount = 0;
    private int simTime; // aktualny czas symulacji
    private int timeBeforeCheckOut; //moment w czasie w któym klient dołączy do kolejki
    private int timeToLeave; // moment w czasie w któym klient zaczyna się niecierpliwić
    private int id; // identyfikator klienta
    private boolean inQue; // flaga określająca czy klient stoi w kolejce
    private Checkout checkoutImStanding; // uchwyt do kasy przy której stoi klient

    public Client(Simulation sim) {
        super(sim);
        this.simTime = 0;
        Uniform uniformGen = new Uniform(7.0, 12.0);
        this.timeBeforeCheckOut = (int) (this.getSim().getTime() + uniformGen.getNext());
        this.timeToLeave = 0;
        this.id = clienCount;
        this.inQue = false;
        clienCount++;
        System.out.println("Klient - " + this.id + " 1. przybył do sklepu" );
    }

    @Override
    public void timeAdvanced() {
        this.simTime = this.getSim().getTime();
        // klient dołącza do kolejki jeśli jeszcze w niej nie stoi i w określonym czasie
        if(!this.inQue && (this.timeBeforeCheckOut == this.simTime)) {
            Checkout checkout1 = this.getSim().getCheckouts().get(0);
            Checkout checkout2 = this.getSim().getCheckouts().get(1);
            // wybór checkout1 pod warunkiem że jest jedyną otwartą
            if(checkout1.isOpen() && !checkout2.isOpen()) {
                checkoutImStanding = checkout1;
                checkout1.joinQue(this);
            }
            // wybór kasy jeśli obie są otwarte
            else if(checkout1.isOpen() && checkout2.isOpen()) {
                // wybór kasy o mniejszej kolejce
                if (checkout1.getQueLength() <= checkout2.getQueLength()) {
                    checkoutImStanding = checkout1;
                    checkout1.joinQue(this);
                } else {
                    checkoutImStanding = checkout2;
                    checkout2.joinQue(this);
                }
            }
            // wybór checkout2 pod warunkiem że jest jedyną otwartą
            else if(!checkout1.isOpen() && checkout2.isOpen()) {
                checkoutImStanding = checkout2;
                checkout2.joinQue(this);
            }
            this.inQue = true; // zapalenie flagi po dołączeniu do kolejki
            System.out.println("Klient - " + this.id + " 2. udał się do kasy" );
            this.timeToLeave = this.getSim().getTime() + 5; // ustawienie czasu po którym klient zaczni się niecierpliwić
        }
        // obsługa niecierpliwenia się klienta
        if(this.inQue && this.timeToLeave == this.simTime) {
            Range t0 = new Range(1, 2, 25);
            Range t1 = new Range(3, 5, 75);
            Weighted weightedGen = new Weighted(new Range[]{t0, t1});
            // wylosowanie wartości określającej czy klient się zdenerwował i wyjdzie ze sklepu
            double isLeaving = weightedGen.getNext();
            if(isLeaving >= 1.0 && isLeaving <= 2.0) {
                // opuszczenie sklepu przez klienta
                checkoutImStanding.leaveQue(this);
                this.getSim().unregister(this);
                System.out.println("Klient - " + this.id + " 3. stracił cierpliwość i wyszedł" );
            }
            else{
                this.timeToLeave = this.getSim().getTime() + 2;
            }
        }
    }
    public int getId() {
        return id;
    }
}

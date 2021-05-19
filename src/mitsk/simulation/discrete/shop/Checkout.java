package mitsk.simulation.discrete.shop;

import mitsk.generators.Range;
import mitsk.generators.Weighted;
import mitsk.simulation.discrete.step.Simulation;
import mitsk.simulation.discrete.step.SimulationObject;

import java.util.ArrayList;
import java.util.List;

public class Checkout extends SimulationObject {
    private int idCheckout; // identyfikator klasy
    private int simTime; // aktualny czas symulacji
    private int timeToHandleClient; // moment w czasie w któym nastąpi obśługa klienta
    private int lastClientServedTime; // moment w czasie w któym nastąpiła obśługa ostatniego klienta
    private boolean isOpen; // flaga określająca czy kasa jest otwarta
    private boolean isTimeToHandleClientSet; // flaga określająca czy czas do obsługi klienta jest ustawiony
    private int queLength; // długość kolejki
    private Weighted weightedGen; // generator użawany do określenia czasu obsługi klienta
    private Checkout neighbourCheckout; // uchwyt do sąsiedniej klasy
    private List<Client> queue; // kolejka klientów do kasy

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
    public void timeAdvanced() {
        this.simTime = this.getSim().getTime(); // aktualizacja czasu symulacji
        if (this.queLength != 0) {
            if(neighbourCheckout != null) {
                // otwarcie klasy gdy jest w więcej niż trzech klientów
                if (this.queLength > 3 && !neighbourCheckout.isOpen()) {
                    System.out.println("Otwarto kasę nr: " + this.neighbourCheckout.getIdCheckout());
                    neighbourCheckout.setOpen(true);
                }
            }
            // ustawienei czasu obsługi klienta w momencie gdy kolejka była pusta
            if(!this.isTimeToHandleClientSet){
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
            // obsługa klienta
            if(this.timeToHandleClient == this.simTime){
                Client clnt = this.queue.get(0); // uchywtu do pierwszego klienta w kolejce
                System.out.println("Kasjer - " + this.idCheckout + " 1. podjął klienta" );
                //System.out.println("Kasjer - " + this.idCheckout + " 1. podjął Klienta - " + clnt.getId());
                this.queue.remove(clnt); // usunięcie klienta z kolejki
                this.getSim().unregister(clnt); // wyrejestrowanie klienta z symulacji
                this.queLength = this.queue.size(); // aktualizacja romiaru kolejki
                this.lastClientServedTime = this.simTime; // momenu obsługi ostatniego klienta
                System.out.println("Klient - " + clnt.getId() + " 4. został obsłużony" );
                System.out.println("Kasjer - " + this.idCheckout + " 2. zakończył obsługę klienta" );
                //System.out.println("Kasjer - " + this.idCheckout + " 2. zakończył obsługę Klienta - " + clnt.getId());
                this.timeToHandleClient = -1;
                double time = weightedGen.getNext(); // określenie czasu do obsługi następnego klienta
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
            // zamknięcie kasy gdy jest pusta oraz od trzech jednostek czasu nie przyszedł nowy klient
            if(neighbourCheckout == null) {
                if ((this.simTime - 3) == this.lastClientServedTime && this.isOpen) {
                    System.out.println("Zamknięto kasę nr: " + this.idCheckout);
                    this.isOpen = false;
                    this.isTimeToHandleClientSet = false;
                }
            }
        }
    }
    // metoda pomocnicza dodająca klienta do kolejki przy kasie
    public void joinQue (Client clnt) {
        this.queue.add(clnt);
        this.queLength = this.queue.size();
    }
    // metoda pomocnicza usuwająca klienta do kolejki przy kasie
    public void leaveQue (Client clnt) {
        this.queue.remove(clnt);
        this.queLength = this.queue.size();
    }

    public int getIdCheckout() {
        return idCheckout;
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

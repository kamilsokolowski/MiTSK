package mitsk.simulation.discrete.step;

import mitsk.simulation.discrete.shop.Checkout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Simulation {
    private int maxSimulationTime; // maksymalny czas symulacji
    private int step; // wartość korku symulacji
    private int simTime; // aktualny czas symulacji
    private List<SimulationObject> objects; // lista obiektów symulacyjnych
    private List<SimulationObject> addedObjects; // lista obiektów symulacynych do dodania
    private List<SimulationObject> removedObjects; // lista obiektów symulacynych do usunięcia
    private List<Checkout> checkouts; // lista kas dostępnych w sklepie

    public Simulation(int maxSimulationTime, int step) {
        this.maxSimulationTime = maxSimulationTime;
        this.step = step;
        this.simTime = 0;
        this.objects = new ArrayList<>();
        this.addedObjects = new ArrayList<>();
        this.removedObjects = new ArrayList<>();
        this.checkouts = new ArrayList<>();
    }
    // metoda pomocnicza zwracjąca aktualny czas symulacji
    public int getTime() {
        return this.simTime;
    }
    // metoda pomocnicza rejestrująca klientów
    // użycie dodatkowych list tymczasowych było wymagane z powodu problemu aktulzacji stanu iterowanej listy
    // język java nie pozwala na dodawanie lub usuwanie obiektów z list w momencie jej obsługi przez iterator
    // dlatego wymagane jest użycie list tymczasowych bufforów do wykonania operacji dodania lub usunięcia
    public void register(SimulationObject obj) {
        this.addedObjects.add(obj);
    }
    // metoda pomocnicza wyrejestrowująca klientów
    public void unregister(SimulationObject obj) {
        this.removedObjects.add(obj);
    }
    // funkcja zawierająća główną pętlę symulacji
    public void run() throws InterruptedException {
        while(this.simTime <= this.maxSimulationTime) {
            // pętla wykonująca metodą timeAdvanced() na wszystkich zarejestrowanych obiektach symulacyjnych
            // wykorzstanie iteratora zostało spowodowane mechaniczmem dostępu do metod klas abstrakcyjnych
            // implementowanych w docelowych klasach
            for (Iterator<SimulationObject> it = objects.iterator(); it.hasNext();) {
                SimulationObject next = it.next();
                next.timeAdvanced();
            }
            objects.addAll(addedObjects); // rejestracja nowych obiektów symulacyjnych
            objects.removeAll(removedObjects); // usunięcie obiektó symulacyjnych
            addedObjects.clear(); // wyczyszczenie listy obiektów do dodania
            removedObjects.clear(); // wyczyszczenie listy obiektów do usunięcia
            System.out.println(this.simTime);
            this.simTime += this.step; // aktualizacja czasu symulacji
        }
        this.simTime = -1; // zakończenie symulacji
    }

    public List<Checkout> getCheckouts() {
        return checkouts;
    }

    public void setCheckouts(Checkout checkout) {
        this.checkouts.add(checkout);
    }
}

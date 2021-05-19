package mitsk;

import mitsk.simulation.discrete.shop.Checkout;
import mitsk.simulation.discrete.shop.ClientStream;
import mitsk.simulation.discrete.step.Simulation;

public class Main {

    public static void main(String[] args) {
        Simulation sklep = new Simulation(200, 1);
        Checkout kasa1 = new Checkout(sklep, 1, true);
        Checkout kasa2 = new Checkout(sklep, 2, false);
        kasa1.setNeighbourCheckout(kasa2);
        kasa2.setNeighbourCheckout(null);
        sklep.register(new ClientStream(sklep));
        sklep.register(kasa1);
        sklep.register(kasa2);
        sklep.setCheckouts(kasa1);
        sklep.setCheckouts(kasa2);
        try {
            sklep.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

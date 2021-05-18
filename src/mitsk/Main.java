package mitsk;

import mitsk.simulation.discrete.shop.Checkout;
import mitsk.simulation.discrete.shop.ClientStream;
import mitsk.simulation.discrete.step.Simulation;

public class Main {

    public static void main(String[] args) {
        Simulation sklepi1 = new Simulation(30, 1);
        Checkout c1 = new Checkout(sklepi1, 1, true);
        Checkout c2 = new Checkout(sklepi1, 2, false);
        c1.setNeighbourCheckout(c2);
        c2.setNeighbourCheckout(c1);
        sklepi1.register(new ClientStream(sklepi1));
        sklepi1.register(c1);
        sklepi1.register(c2);
        sklepi1.setCheckouts(c1);
        sklepi1.setCheckouts(c2);
        try {
            sklepi1.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

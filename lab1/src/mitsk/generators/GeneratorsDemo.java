package mitsk.generators;

public class GeneratorsDemo {
    public static void playDemo() {
        System.out.println("-> Demo presentation of generators <-");
        System.out.println("Uniform distribution");

        Uniform u = new Uniform(2, 40);
        for(int i = 0; i <= 10; ++i)
            System.out.println("Uniform: " + u.getNext());

        System.out.println("Round robin generator");

        RoundRobin r = new RoundRobin(new double[] {123, 0.22222, 111.111});

        System.out.println("Round robin: " + r.getNext());
        System.out.println("Round robin: " + r.getNext());
        System.out.println("Round robin: " + r.getNext());
        System.out.println("Round robin: " + r.getNext());
        System.out.println("Round robin: " + r.getNext());
        System.out.println("Round robin: " + r.getNext());
        System.out.println("Round robin: " + r.getNext());
        System.out.println("Round robin: " + r.getNext());
        System.out.println("Round robin: " + r.getNext());
        System.out.println("Round robin: " + r.getNext());
        System.out.println("Round robin: " + r.getNext());
        System.out.println("Round robin: " + r.getNext());

        System.out.println("Weighted distribution");

        Range t0 = new Range(1, 2, 2);
        Range t1 = new Range(3, 5, 4);
        Range t2 = new Range(6, 8, 3);
        Weighted w = new Weighted(new Range[]{t0, t1, t2});

        for(int i=0; i<10; ++i)
            System.out.println("Weighted: " + w.getNext());
    }
}

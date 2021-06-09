package mitsk.relations;

import mitsk.generators.RoundRobin;

public class FunctionDemo {
    public static void playDemo() {
        System.out.println("-> Demo presentation of function class <-");

        Function f = new Function(new double[] { 2.0, -4.0, 3.0});
        RoundRobin r = new RoundRobin(new double[] {123, 0.707, -1000});

        System.out.println("Function0 f(x) = 123 + 0.707x - 1000x^2");
        System.out.println("Value of f(): " + f.calc(r.getNext()));
        System.out.println("Value of function: " + f.calc(r.getNext()));
        System.out.println("Value of function: " + f.calc(r.getNext()));
    }
}

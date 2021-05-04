package mitsk;

import mitsk.generators.GeneratorsDemo;
import mitsk.montecarlo.IntegralCalculatorDemo;
import mitsk.relations.FunctionDemo;

public class Main {

    public static void main(String[] args) {
        GeneratorsDemo.playDemo(); // Demonstration of Uniform, Round Robin and Weight RNG.
        FunctionDemo.playDemo(); // Demonstration of function.
        IntegralCalculatorDemo.playDemo(); // Demonstration of Monte Carlo Integration.
    }
}

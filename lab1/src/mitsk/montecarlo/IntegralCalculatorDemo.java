package mitsk.montecarlo;

import mitsk.generators.Uniform;
import mitsk.relations.Function;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class IntegralCalculatorDemo {
    public static void playDemo() {
        System.out.println("-> Demo presentation of Monte Carlo method of integration <-");
        // Intervals for integral.
        double a = 0.0;
        double b = 4.0;
        Uniform distribution = new Uniform(0.0, 4.0);
        IntegralCalculator I = new IntegralCalculator(distribution);
        List<Integer> numbers = Arrays.asList(15, 100, 500, 1000); // List containing number of trials
        ListIterator<Integer> it = numbers.listIterator(); // Iterator to loop over trials list.

        System.out.println("Integral of function f(x) = 3");
        Function f0 = new Function(new double[] { 3.0 }); // function f(x) = 3
        while (it.hasNext()) {
            Integer trials = it.next();
            System.out.println("Monte carlo integral with: " + trials + " iterations; value = " + I.get(f0, a, b, trials));
        }

        System.out.println("Integral of function f(x) = x");
        Function f1 = new Function(new double[] { 0.0, 1.0 }); // function f(x) = x
        it = numbers.listIterator();
        while (it.hasNext()) {
            Integer trials = it.next();
            System.out.println("Monte carlo integral with: " + trials + " iterations; value = " + I.get(f1, a, b, trials));
        }

        System.out.println("Integral of function f(x) = 6 + 2x");
        Function f2 = new Function(new double[] { 6.0, 2.0}); // function f(x) = 6 + 2x
        it = numbers.listIterator();
        while (it.hasNext()) {
            Integer trials = it.next();
            System.out.println("Monte carlo integral with: " + trials + " iterations; value = " + I.get(f2, a, b, trials));
        }

        System.out.println("Integral of function f(x) = 2 + 4x + -1x^2");
        Function f3 = new Function(new double[] { 2.0, 4.0, -1.0}); // function f(x) = 2 + 4x + -1x^2
        it = numbers.listIterator();
        while (it.hasNext()) {
            Integer trials = it.next();
            System.out.println("Monte carlo integral with: " + trials + " iterations; value = " + I.get(f3, a, b, trials));
        }
    }
}

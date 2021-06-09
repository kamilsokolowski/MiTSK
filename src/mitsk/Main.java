package mitsk;

import mitsk.simulation.continuous.rk.*;
import mitsk.simulation.continuous.rk.relations.*;

import java.util.Enumeration;
import java.util.Vector;

public class Main {

    public static void main(String[] args) {
        double[] arguments = {1.0, 2.0, 3.0, 4.0};
        double[] steps = {0.5, 1.0, 2.0, 4.0};

        Function0 fx0 = new Function0();
        Function1 fx1 = new Function1();
        Function2 fx2 = new Function2();
        Function3 fx3 = new Function3();
        RK2 rk2 = new RK2();
        RK4 rk4 = new RK4();
        RKF5 rkf5 = new RKF5();
        System.out.println("3x + x^2");
        for (double argument : arguments) {
            for (double step : steps) {
                System.out.println("Value of x: " + argument + " step size: " + step);
                System.out.println("RK2: " + rk2.solve(fx0, 1.0, 0.0, argument, step));
                System.out.println("RK4: " + rk4.solve(fx0, 1.0, 0.0, argument, step));
            }
        }
        System.out.println("End of 3x + x^2\n");
        System.out.println("1 - x^2");
        for (double argument : arguments) {
            for (double step : steps) {
                System.out.println("Value of x: " + argument + " step size: " + step);
                System.out.println("RK2: " + rk2.solve(fx1, 1.0, 0.0, argument, step));
                System.out.println("RK4: " + rk4.solve(fx1, 1.0, 0.0, argument, step));
            }
        }
        System.out.println("End of 1 - x^2\n");
        System.out.println("x * y");
        for (double argument : arguments) {
            for (double step : steps) {
                System.out.println("Value of x: " + argument + " step size: " + step);
                System.out.println("RK2: " + rk2.solve(fx2, 1.0, 0.0, argument, step));
                System.out.println("RK4: " + rk4.solve(fx2, 1.0, 0.0, argument, step));
            }
        }
        System.out.println("End x * y\n");
        System.out.println("6 - 2 * x/y");
        for (double argument : arguments) {
            for (double step : steps) {
                System.out.println("Value of x: " + argument + " step size: " + step);
                System.out.println("RK2: " + rk2.solve(fx3, 1.0, 0.0, argument, step));
                System.out.println("RK4: " + rk4.solve(fx3, 1.0, 0.0, argument, step));
            }
        }
        System.out.println("End of 6 - 2 * x/y");
        System.out.println("RKF5");
        for (double argument : arguments) {
            System.out.println("Value of x: " + argument + " step size: 0.5");
            System.out.println("RKF5: " + rkf5.solve(fx0, 1, 0.0, argument, 0.01));
        }
        System.out.println("RKF5");
    }
}

package mitsk.simulation.continuous.rk;

import mitsk.simulation.continuous.rk.relations.FunctionInt;

public class RK4 {
    public double solve(FunctionInt derivative, double initialValue, double firstX, double lastX, double step) {
        if((firstX + step) > lastX) {
            return initialValue;
        }
        double y = initialValue;
        double x = firstX;

        double k1, k2, k3, k4;
        while(x < lastX) {
            k1 = step * derivative.getValue(x, y);
            k2 = step * derivative.getValue(x + (step / 2.0), y + (k1 / 2.0));
            k3 = step * derivative.getValue(x + (step/2.0), y + (k2 / 2.0));
            k4 = step * derivative.getValue(x + step, y + k3);

            y = (y + (k1/6.0 + k2/3.0 + k3/3.0 + k4/6.0));
            x += step;
        }
        return y;
    }
}

package mitsk.simulation.continuous.rk;

import mitsk.simulation.continuous.rk.relations.FunctionInt;

public class RK2 {
    public double solve(FunctionInt derivative, double initialValue, double firstX, double lastX, double step) {
        if((firstX + step) > lastX) {
            return initialValue;
        }
        double y = initialValue;
        double x = firstX;

        double k1, k2;
        while(x < lastX) {
            k1 = step * derivative.getValue(x, y);
            k2 = step * derivative.getValue(x + (step / 2.0), y + (k1 / 2.0));

            y = ( y + k2 );
            x += step;
        }
        return y;
    }
}

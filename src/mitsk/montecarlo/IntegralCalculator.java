package mitsk.montecarlo;

import mitsk.generators.*;
import mitsk.relations.Function;

public class IntegralCalculator {
    private Generator xDistibution; // Distribution of argument.

    public IntegralCalculator(Generator xDistibution) {
        this.xDistibution = xDistibution;
    }

    public double get(Function f, double a, double b, int trials) {
        double valuesOfF = 0.0; // Sum of values f(x) for random x.
        for(int i=0; i < trials; ++i) {
            double x = this.xDistibution.getNext();
            valuesOfF += f.calc(x);
        }
        double integralValue = (valuesOfF / trials ) * (b - a); // Calculating average area.
        return integralValue;
    }
}

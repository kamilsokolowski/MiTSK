package mitsk.simulation.continuous.rk.relations;

public class Function3 implements FunctionInt{
    @Override
    public double getValue(double x, double y) {
        return (6 - ((2 * x) / y));
    }
}

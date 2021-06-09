package mitsk.simulation.continuous.rk.relations;

public class Function0 implements FunctionInt{
    @Override
    public double getValue(double x, double y) {
        return (3 * x  + Math.pow(x, 2));
    }
}

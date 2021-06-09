package mitsk.simulation.continuous.rk.relations;

public class Function1 implements FunctionInt{
    @Override
    public double getValue(double x, double y) {
        return (1  - Math.pow(x, 2));
    }
}
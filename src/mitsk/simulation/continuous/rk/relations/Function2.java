package mitsk.simulation.continuous.rk.relations;

public class Function2 implements FunctionInt{
    @Override
    public double getValue(double x, double y) {
        return (x  * y);
    }
}

package mitsk.relations;

public class Function {
    private double[] coefficients; // a0*x^0 + a1*x^1 + a2*x^2 + a3*x^3 + ... a(n-1)*x^(n-1); where n=size(coefficients)

    public Function(double[] coefficients) {
        this.coefficients = coefficients;
    }

    public double calc(double x) {
        double valueOfFunction = 0.0;
        for(int i=0; i < coefficients.length; ++i) {
            valueOfFunction += coefficients[i] * Math.pow(x, i);
        }
        return valueOfFunction;
    }

}

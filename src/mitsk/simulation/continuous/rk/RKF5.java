package mitsk.simulation.continuous.rk;

import mitsk.simulation.continuous.rk.relations.FunctionInt;

import javax.jws.soap.SOAPBinding;
import java.util.regex.MatchResult;

public class RKF5 {
    FunctionInt derivative;
    double current_step;
    double y;
    double y_wave;
    double y_possible;
    double x = 0.0;
    double R = 0.0;
    double epsilon = 0.00001;
    double delta = 0.0;
    double k1, k2, k3, k4, k5, k6;
    public double solve(FunctionInt derivative, double initialValue, double firstX, double lastX, double step) {
        this.derivative = derivative;
        this.current_step = step;
        this.y = initialValue;
        this.y_wave = initialValue;
        this.y_possible = initialValue;
        this.x = firstX;
        while(this.x <= lastX) {
            System.out.println("====================");
            System.out.println("xn :" + this.x);

            this.k1 = this.current_step * this.derivative.getValue(this.x, this.y);
            this.k1 = Math.round(this.k1 * 10000.0) / 10000.0;

            this.k2 = this.current_step * this.derivative.getValue(this.x + (( 2.0 * this.current_step) / 9.0),this.y + ((2.0 * this.k1) / 9.0));
            this.k2 = Math.round(this.k2 * 10000.0) / 10000.0;

            this.k3 = this.current_step * this.derivative.getValue(this.x + (this.current_step / 3.0),this.y + (this.k1 / 12.0) + (this.k2 / 4.0));
            this.k3 = Math.round(this.k3 * 10000.0) / 10000.0;

            this.k4 = this.current_step * this.derivative.getValue(this.x + (3.0 * (this.current_step) / 4.0),this.y + ((69.0 * this.k1) / 128.0) - ((243.0 * this.k2) / 128.0) + ((135.0 * this.k3) / 64.0));
            this.k4= Math.round(this.k4 * 10000.0) / 10000.0;

            this.k5 = this.current_step * this.derivative.getValue(this.x + this.current_step, this.y - ((17.0 * this.k1) / 12.0) + ((27.0 * this.k2) / 4.0) - ((27.0 * this.k3) / 5.0) + ((16.0 * this.k4) / 15.0));
            this.k5 = Math.round(this.k5 * 10000.0) / 10000.0;

            this.k6 = this.current_step * this.derivative.getValue(this.x + (( 5.0 * this.current_step) / 6.0), this.y + ((65.0 * this.k1) / 432.0) - ((5.0 * this.k2) / 16.0) + ((13.0 * this.k3) / 16.0) + ((4.0 * this.k4) / 27.0) + ((5.0 * this.k5) / 144.0));
            this.k6 = Math.round(this.k6 * 10000.0) / 10000.0;

            this.y_possible = this.y + (((47.0 * this.k1) / 450.0) + ((12.0 * this.k3) / 25.0) + ((32.0 * this.k4) / 225.0) + (this.k5 / 30.0)) + ((6.0 * this.k6) / 25.0);
            this.y_possible = Math.round(this.y_possible * 10000.0) / 10000.0;

            this.R = Math.abs((((-1.0 * this.k1) / 150.0) + ((3.0 * this.k3) / 100.0) + ((-16.0 * this.k4) / 75.0) + ( (-1.0 * this.k5) / 20.0)) + ((6.0 * this.k6) / 25.0));
            this.R = Math.round(this.R * 10000.0) / 10000.0;

            this.delta = 0.9 * this.current_step * ( Math.pow((this.epsilon / this.R ), (1.0 / 5.0)) );
            this.delta = Math.round(this.delta * 10000.0) / 10000.0;

            if(this.R == 0.0) {
                this.delta = 1.0;
            }

            System.out.println("Error : " + this.R);
            System.out.println("Actual step :" + this.current_step);

            if(this.R <= this.epsilon) {
                this.y = this.y_possible;
                this.x += this.current_step;
                this.current_step = this.current_step * this.delta;
            }
            else {
                System.out.println("Step rejected due to too high error");
                this.current_step = this.current_step * this.delta;
            }
            this.current_step = Math.round(this.current_step * 10000.0) / 10000.0;
            this.x = Math.round(this.x * 10000.0) / 10000.0;
            System.out.println("====================\n");
        }
        return y;
    }

}

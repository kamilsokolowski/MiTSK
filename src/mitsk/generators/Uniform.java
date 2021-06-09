package mitsk.generators;

import java.util.Random;

public class Uniform implements Generator {
    private double min;
    private double max;

    public Uniform(double min, double max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public double getNext() {
        if (this.max < this.min) {
            System.err.println("SimGenerator.uniform: give b>a");
            return -1;
        }
        Random rand = new Random();
        double number = rand.nextDouble();
        return number * (this.max - this.min) + this.min;
    }
}

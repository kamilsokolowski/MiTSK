package mitsk.generators;

public class Range {
    private double min;
    private double max;
    private double weight;
    public Range(double min, double max, double weight) {
        this.min = min;
        this.max = max;
        this.weight = weight;
    }
    public double getMin() {
        return min;
    }
    public double getMax() {
        return max;
    }
    public double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Range{" +
                "min=" + min +
                ", max=" + max +
                ", weight=" + weight +
                '}';
    }
}

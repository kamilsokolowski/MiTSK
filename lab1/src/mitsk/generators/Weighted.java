package mitsk.generators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Weighted implements Generator{
    private List<Range> ranges;

    public Weighted(Range[] ranges) {
        this.ranges = new ArrayList<Range>();
        this.ranges.addAll(Arrays.asList(ranges));
    }

    @Override
    public double getNext() {
        Random rand = new Random();
        double number = rand.nextDouble();
        double sumOfWeights = 0.0; // Sum of weights from set of ranges.
        for(Range r: this.ranges)
            sumOfWeights += r.getWeight();
        double a = number * sumOfWeights;
        double s = 0.0;
        Range ren = null;
        for(Range r: this.ranges){
            if(a > s && a < (s + r.getWeight())) {
                ren = r;
                break;
            }
            s += r.getWeight();
        }
        return number * (ren.getMax() - ren.getMin()) + ren.getMin();
    }
}

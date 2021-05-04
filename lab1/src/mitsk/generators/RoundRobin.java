package mitsk.generators;

public class RoundRobin implements Generator {
    private double[] listOfNumbers;
    private int counter = 0;

    public RoundRobin(double[] numbers) {
        this.listOfNumbers = numbers;
    }

    @Override
    public double getNext() {
        double result = listOfNumbers[counter];
        counter++;
        if(this.counter == (listOfNumbers.length))
            counter = 0;
        return result;
    }
}

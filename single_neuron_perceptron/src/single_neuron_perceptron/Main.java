package single_neuron_perceptron;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Perceptron perceptron = new Perceptron();
        perceptron.test(Perceptron.PATTERN.NOT);
        perceptron.test(Perceptron.PATTERN.OR);
        perceptron.test(Perceptron.PATTERN.AND);
    }
}

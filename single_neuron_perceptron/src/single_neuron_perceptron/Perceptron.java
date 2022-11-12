package single_neuron_perceptron;

public class Perceptron {
    double[] enter_neuron; //входы
    double out_neuron; //выход
    double[] weight; //синапсы

    public enum PATTERN {OR, AND, NOT}

    double[][] pattern_OR = {
            {0, 0, 0},
            {0, 1, 1},
            {1, 0, 1},
            {1, 1, 1}
    };

    double[][] pattern_AND = {
            {0, 0, 0},
            {0, 1, 0},
            {1, 0, 0},
            {1, 1, 1}
    };

    double[][] pattern_NOT = {
            {1, 0, 1},
            {1, 1, 0}
    };

    public Perceptron() {
        enter_neuron = new double[2];
        weight = new double[enter_neuron.length];

        for (int i = 0; i < enter_neuron.length; i++) {
            weight[i] = Math.random() * 0.2 + 0.1;
        }
    }

    public void out_calculation() {
        out_neuron = 0;

        for (int i = 0; i < enter_neuron.length; i++) {
            out_neuron += enter_neuron[i] * weight[i];
        }

        if (out_neuron > 0.5) out_neuron = 1; else out_neuron = 0;
    }

    public int training(PATTERN p) {
        double globalError = 0;
        int steps = 0;

        double[][] training_patterns;
        switch (p) {
            case AND:
                training_patterns = pattern_AND;
                break;
            case NOT:
                training_patterns = pattern_NOT;
                break;
            case OR:
            default:
                training_patterns = pattern_OR;
        }

        do {
            steps++;

            globalError = 0;
            for (int i = 0; i < training_patterns.length; i++) {
                enter_neuron = java.util.Arrays.copyOf(training_patterns[i], training_patterns[i].length - 1);

                out_calculation();

                double error = training_patterns[i][training_patterns[0].length - 1] - out_neuron;

                globalError += Math.abs(error);

                for (int j = 0; j < enter_neuron.length; j++) {
                    weight[j] += 0.1 * error * enter_neuron[j];
                }
            }
        } while (globalError != 0);
        return steps;
    }

    public void test(PATTERN p) {

        double[][] training_patterns;
        switch (p) {
            case AND:
                training_patterns = pattern_AND;
                break;
            case NOT:
                training_patterns = pattern_NOT;
                break;
            case OR:
            default:
                training_patterns = pattern_OR;
        }

        int result = training(p);
        System.out.println(p);
        for (int i = 0; i < training_patterns.length; i++) {
            enter_neuron = java.util.Arrays.copyOf(training_patterns[i], training_patterns[i].length - 1);
            out_calculation();
            System.out.println(out_neuron);
        }
        System.out.println("Steps : " + result);
        System.out.println("- - -");
    }
}


public class Perceptron {

  double[] enters;
  double outer;
  double[] weights;
  double[][] patterns = {
          {0, 0},
          {0, 1},
          {1, 0},
          {1, 1}
  };
  double fitness;
  double answers[] = {0, 0, 0, 1};

  public Perceptron(){
    enters = new double[2];
    weights = new double[enters.length];
  }

  void countFitness() {
    fitness = 0;
    for (int p = 0; p < patterns.length; p++) {
      enters = java.util.Arrays.copyOf(patterns[p], patterns[p].length);
      countOuter();
      double error = answers[p] - outer;
      fitness += Math.abs(error);
    }
  }

  public void countOuter() {
    outer = 0;
    for (int i = 0; i < enters.length; i++) {
      outer += enters[i] * weights[i];
    }
    if (outer > 0.5) outer = 1;
    else outer = 0;
  }

  public void test() {
    for (int p = 0; p < patterns.length; p++) {
      enters = java.util.Arrays.copyOf(patterns[p], patterns[p].length);
      countOuter();
      System.out.println(outer);

    }
    System.out.println(weights[0]+" "+weights[1]);
  }
}
package AnnealingSimulation;

import javax.swing.*;

public class AnnealingSimulation {

  double[][] decision;

  public AnnealingSimulation() {
    decision = new double[2][2];
    for (int i = 0; i < decision[0].length; i++) {
      decision[0][i] = Math.random();
    }
  }

  public double mark(double[] decision) {
    double res = 1 / (1 + Math.pow(decision[0], 2) + Math.pow(decision[1], 2));
    return res;
  }

  public void search() {
    double t = 5000;
    System.out.println(decision[0][0] + " " + decision[0][1] + " " + mark(decision[0]));
    do {
      for (int i = 0; i < decision[0].length; i++) {
        decision[1][i] = decision[0][i] + Math.random() * 0.01 - 0.005;
      }
      double e1 = mark(decision[0]);
      double e2 = mark(decision[1]);

      if (e2 < e1) {
        decision[0] = java.util.Arrays.copyOf(decision[1], decision[1].length);
      } else {
        double p = Math.exp(-1 * (e2 - e1)) / t;
        if (Math.random() < p) {
          decision[0] = java.util.Arrays.copyOf(decision[1], decision[1].length);
        }
      }
      t *= 0.99;
    } while (t > 0.01);
    System.out.println(decision[0][0] + " " + decision[0][1] + " " + mark(decision[0]));
  }
}

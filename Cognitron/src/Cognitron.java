
public class Cognitron {

  final static double Q = 0.1;
  int curPattern;
  double enters[];
  double outers[];
  double inhib[];
  double latInhib[];

  double wEO[][];
  double wEI[][];
  double wIO[][];
  double wLO[][];

  double patterns[][] = {
          {1, 1, 1, 1, 1, 1, 1},
          {1, 1, 0, 0, 1, 0, 1}
  };

  Cognitron() {
    enters = new double[patterns[0].length];
    outers = new double[patterns.length];
    inhib = new double[outers.length];
    latInhib = new double[outers.length];

    wEO = new double[enters.length][outers.length];
    wEI = new double[enters.length][inhib.length];
    wIO = new double[inhib.length][outers.length];
    wLO = new double[latInhib.length][outers.length];

    for (int i = 0; i < enters.length; i++) {
      for (int j = 0; j < inhib.length; j++) {
        wEI[i][j] = 1. / enters.length;
      }
    }
    for (int i = 0; i < outers.length; i++) {
      for (int j = 0; j < latInhib.length; j++) {
        wLO[j][i] = 1. / outers.length;
      }
    }
    for (int i = 0; i < outers.length; i++) {
      for (int j = 0; j < enters.length; j++) {
        wEO[j][i] = 1;
      }
    }
    for (int i = 0; i < inhib.length; i++) {
      for (int j = 0; j < outers.length; j++) {
        wIO[i][j] = 1;
      }
    }
    curPattern = 0;
    test();
  }

  public void countOut() {
    double E = 0;
    double I = 0;
    for (int i = 0; i < inhib.length; i++) {
      for (int j = 0; j < enters.length; j++) {
        inhib[i] += enters[j] * wEI[j][i];
      }
    }
    for (int i = 0; i < outers.length; i++) {
      for (int j = 0; j < enters.length; j++) {
        E += enters[j] * wEO[j][i];
      }
      for (int j = 0; j < inhib.length; j++) {
        I += inhib[j] * wIO[j][i];
      }
      double NET = (1 + E) / (1 + I);
      outers[i] = NET > 0 ? NET : 0;
    }
    for (int i = 0; i < latInhib.length; i++) {
      latInhib[i] = 0;
      for (int j = 0; j < outers.length; j++) {
        latInhib[i] += outers[j] * wLO[i][j];
      }
    }
    for (int j = 0; j < outers.length; j++) {
      outers[j] /= latInhib[j];
    }
  }

  public void study() {
    countOut();
    //int maxId = maxOuter();
    curPattern = curPattern == 0 ? 1 : 0;
    int maxId = curPattern;
    for (int i = 0; i < enters.length; i++) {
      wEO[i][maxId] += Q * 1. / enters.length * enters[i];
    }
    double E = 0;
    for (int i = 0; i < enters.length; i++) {
      E += enters[i] * wEO[i][maxId];
    }
    for (int i = 0; i < inhib.length; i++) {
      wIO[i][maxId] += (E * Q) / (2. * inhib[i]);
    }
  }

  public int maxOuter() {
    int maxId = 0;
    for (int i = 0; i < outers.length; i++) {
      if (outers[i] > outers[maxId]) {
        maxId = i;
      }
    }
    return maxId;
  }

  public void test() {
    for (int i = 0; i < 100000; i++) {
      for (int p = 0; p < patterns.length; p++) {
        enters = java.util.Arrays.copyOf(patterns[p], patterns[p].length);
        study();
      }
    }
    for (int p = 0; p < patterns.length; p++) {
      enters = java.util.Arrays.copyOf(patterns[p], patterns[p].length);
      countOut();
      System.out.println(outers[0] + " " + outers[1]);
    }

  }

//    public static void main(String[] args) {
//        new Cognitron();
//    }
}
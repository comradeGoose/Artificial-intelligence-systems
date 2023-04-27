package sample;

import java.util.Random;

public class RandomNumber {

  private static int seed = (int) System.currentTimeMillis();

  private static final ThreadLocal<Random> random = ThreadLocal.withInitial(() -> new Random(seed += 1));

  public static int getInt(int min, int maxExclusive) {
    if (min < 0 || maxExclusive < 0 || maxExclusive <= min) {
      System.out.println("min : " + min + " ; maxExclusive : " + maxExclusive);
//      throw new IllegalArgumentException("min and maxExclusive must be positive and maxExclusive must be greater than min");
      return 0;
    }
    return random.get().nextInt(maxExclusive - min) + min;
  }

  public static float getFloat(float min, float max) {
    return random.get().nextFloat() * (max - min) + min;
  }

  public static double getDouble(double min, double max) {
    return random.get().nextDouble() * (max - min) + min;
  }

  public static double gaussian(float mean, float stdDev) {
    double num1 = 1.0 - getDouble(0, 1);
    double num2 = 1.0 - getDouble(0, 1);
    double normal = Math.sqrt(-2.0 * Math.log(num1)) * Math.sin(2.0 * Math.PI * num2);
    return mean + stdDev * normal;
  }

}

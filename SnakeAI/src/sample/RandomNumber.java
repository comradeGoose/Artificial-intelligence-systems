package sample;

import java.util.Random;

public class RandomNumber {

  private static int seed = (int) System.currentTimeMillis();

  private static final ThreadLocal<Random> random = ThreadLocal.withInitial(() -> new Random(seed += 1));


  /**
   * Генерирует случайное целое число в заданном диапазоне.
   *
   * @param min минимальное значение (включительно)
   * @param maxExclusive максимальное значение (исключительно)
   * @return случайное целое число в заданном диапазоне
   * @throws IllegalArgumentException если min или maxExclusive меньше 0 или maxExclusive меньше или равен min
   */
  public static int getInt(int min, int maxExclusive) {
    if (min < 0 || maxExclusive < 0 || maxExclusive <= min) {
      throw new IllegalArgumentException("min and maxExclusive must be positive and maxExclusive must be greater than min");
    }
    return random.get().nextInt(maxExclusive - min) + min;
  }

  /**
   * Генерирует случайное число с плавающей точкой в заданном диапазоне.
   *
   * @param min минимальное значение
   * @param max максимальное значение
   * @return случайное число с плавающей точкой в заданном диапазоне
   */
  public static float getFloat(float min, float max) {
    return random.get().nextFloat() * (max - min) + min;
  }

  /**
   * Генерирует случайное число с двойной точностью в заданном диапазоне.
   *
   * @param min минимальное значение
   * @param max максимальное значение
   * @return случайное число с двойной точностью в заданном диапазоне
   */
  public static double getDouble(double min, double max) {
    return random.get().nextDouble() * (max - min) + min;
  }

  /**
   * Генерирует число из нормального распределения с заданным средним значением и стандартным отклонением.
   *
   * @param mean среднее значение нормального распределения
   * @param stdDev стандартное отклонение нормального распределения
   * @return число из нормального распределения с заданным средним значением и стандартным отклонением
   */
  public static double gaussian(float mean, float stdDev) {
    double num1 = 1.0 - getDouble(0, 1);
    double num2 = 1.0 - getDouble(0, 1);
    double normal = Math.sqrt(-2.0 * Math.log(num1)) * Math.sin(2.0 * Math.PI * num2);
    return mean + stdDev * normal;
  }

}

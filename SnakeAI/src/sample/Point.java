package sample;
//
import java.util.Arrays;
import java.util.List;
//
public class Point {
  /**
   * Константы, представляющие направления движения на плоскости.
   */
  public static final Point UP = new Point(0, -1);
  public static final Point UP_RIGHT = new Point(1, -1);
  public static final Point RIGHT = new Point(1, 0);
  public static final Point DOWN_RIGHT = new Point(1, 1);
  public static final Point DOWN = new Point(0, 1);
  public static final Point DOWN_LEFT = new Point(-1, 1);
  public static final Point LEFT = new Point(-1, 0);
  public static final Point UP_LEFT = new Point(-1, -1);

  /**
   * Константы, представляющие список направлений движения на плоскости.
   */
  public static final List<Point> FOUR_DIRECTIONS = Arrays.asList(UP, RIGHT, DOWN, LEFT);
  public static final List<String> FOUR_DIRECTIONS_NAME = Arrays.asList("UP", "RIGHT", "DOWN", "LEFT");

  public static final List<Point> EIGHT_DIRECTIONS = Arrays.asList(UP, UP_RIGHT, RIGHT, DOWN_RIGHT, DOWN, DOWN_LEFT, LEFT, UP_LEFT);

  private final int x;
  private final int y;

  /**
   * Конструктор, создающий новую точку с указанными координатами x и y.
   *
   * @param x координата x
   * @param y координата y
   */
  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Метод, возвращающий координату x точки.
   *
   * @return координата x
   */
  public int getX() {
    return x;
  }

  /**
   * Метод, возвращающий координату y точки.
   *
   * @return координата y
   */
  public int getY() {
    return y;
  }

  /**
   * Метод, возвращающий новую точку, полученную при добавлении к текущей точке другой точки.
   *
   * @param other точка, которую нужно добавить
   * @return новая точка, полученная при добавлении
   */
  public Point add(Point other) {
    return new Point(x + other.x, y + other.y);
  }

  /**
   * Метод, возвращающий новую точку, полученную при умножении текущей точки на целое число n.
   *
   * @param n множитель
   * @return новая точка, полученная при умножении
   */
  public Point multiply(int n) {
    return new Point(x * n, y * n);
  }

  /**
   * Метод, определяющий, равна ли текущая точка другой точке.
   *
   * @param other точка, с которой нужно сравнить
   * @return true, если текущая точка равна другой точке, иначе false
   */
  public boolean equals(Object other) {
    if (this == other) return true;
    if (other == null || getClass() != other.getClass()) return false;
    Point pos = (Point) other;
    return x == pos.x && y == pos.y;
  }

  /**
   * Проверяет, находится ли точка в пределах прямоугольника с указанной шириной и высотой.
   *
   * @param pos точка, которую нужно проверить
   * @param width ширина прямоугольника
   * @param height высота прямоугольника
   * @return true, если точка находится внутри прямоугольника, false в противном случае.
   */
  public boolean contains(Point pos, int width, int height) {
    return pos.getX() >= 0 && pos.getY() >= 0 && pos.getX() < width && pos.getY() < height;
  }

}

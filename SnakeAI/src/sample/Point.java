package sample;
//
import java.util.Arrays;
import java.util.List;
//
public class Point {
  public static final Point UP = new Point(0, -1);
  public static final Point UP_RIGHT = new Point(1, -1);
  public static final Point RIGHT = new Point(1, 0);
  public static final Point DOWN_RIGHT = new Point(1, 1);
  public static final Point DOWN = new Point(0, 1);
  public static final Point DOWN_LEFT = new Point(-1, 1);
  public static final Point LEFT = new Point(-1, 0);
  public static final Point UP_LEFT = new Point(-1, -1);

  public static final List<Point> FOUR_DIRECTIONS = Arrays.asList(UP, RIGHT, DOWN, LEFT);
  public static final List<Point> EIGHT_DIRECTIONS = Arrays.asList(UP, UP_RIGHT, RIGHT, DOWN_RIGHT, DOWN, DOWN_LEFT, LEFT, UP_LEFT);

  private final int x;
  private final int y;

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public Point add(Point other) {
    return new Point(x + other.x, y + other.y);
  }

  public Point multiply(int n) {
    return new Point(x * n, y * n);
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) return true;
    if (other == null || getClass() != other.getClass()) return false;
    Point pos = (Point) other;
    return x == pos.x && y == pos.y;
  }

  public boolean contains(Point pos, int width, int height) {
    return pos.getX() >= 0 && pos.getY() >= 0 && pos.getX() < width && pos.getY() < height;
  }

}

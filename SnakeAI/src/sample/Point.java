package sample;

class Point {
  int x;
  int y;

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Point other) {
      return x == other.x && y == other.y;
    }
    return false;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }
}

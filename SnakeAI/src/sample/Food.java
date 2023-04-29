package sample;

public class Food {

  private Point position;

  public Food(int boardHeight, int boardWidth) {
    position = createFood(boardHeight, boardWidth);
  }

  // Создает случайную позицию еды на игровом поле
  public Point createFood(int boardHeight, int boardWidth) {
    int x = (int) (Math.random() * boardWidth);
    int y = (int) (Math.random() * boardHeight);
    return new Point(x, y);
  }

  // Возвращает позицию еды
  public Point getPosition() {
    return position;
  }

  public int getX() {
    return getPosition().getX();
  }

  public int getY() {
    return getPosition().getY();
  }

}

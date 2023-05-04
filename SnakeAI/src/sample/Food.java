package sample;

public class Food {

  private Point position;

  public Food(int boardHeight, int boardWidth) {
    position = createFood(boardHeight, boardWidth);
  }


  // Создает случайную позицию еды на игровом поле
  public Point createFood(int boardHeight, int boardWidth) {
    int x = RandomNumber.getInt(0, boardWidth);
    int y = RandomNumber.getInt(0, boardHeight);
    return new Point(x, y);
//    return new Point(25, 15);
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

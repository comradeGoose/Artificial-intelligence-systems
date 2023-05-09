package sample;

public class Food {

  private Point position;

  public Food(int boardHeight, int boardWidth) {
    createFood(boardHeight, boardWidth);
  }


  // Создает случайную позицию еды на игровом поле
  public void createFood(int boardHeight, int boardWidth) {
    int x = RandomNumber.getInt(0, boardWidth);
    int y = RandomNumber.getInt(0, boardHeight);
    position = new Point(x, y);
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

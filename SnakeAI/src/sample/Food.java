package sample;

public class Food {

  private Point position;

  /**
   * Конструктор класса Food.
   *
   * @param boardHeight высота игрового поля
   * @param boardWidth ширина игрового поля
   */
  public Food(int boardHeight, int boardWidth) {
    createFood(boardHeight, boardWidth);
  }


  /**
   * Устанавливает случайную позицию еды на игровом поле.
   *
   * @param boardHeight высота игрового поля
   * @param boardWidth ширина игрового поля
   */
  public void createFood(int boardHeight, int boardWidth) {
    int x = RandomNumber.getInt(0, boardWidth);
    int y = RandomNumber.getInt(0, boardHeight);
    position = new Point(x, y);
  }

  /**
   * Возвращает позицию еды.
   *
   * @return позиция еды
   */
  public Point getPosition() {
    return position;
  }

  /**
   * Возвращает координату x позиции еды.
   *
   * @return координата x позиции еды
   */
  public int getX() {
    return getPosition().getX();
  }

  /**
   * Возвращает координату y позиции еды.
   *
   * @return координата y позиции еды
   */
  public int getY() {
    return getPosition().getY();
  }

}

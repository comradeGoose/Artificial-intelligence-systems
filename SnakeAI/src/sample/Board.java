package sample;

public class Board {

  private final int height;
  private final int width;
  private int speed = 1;
  private Snake snake;

  /**
   * Конструктор Board создает новое игровое поле с заданными высотой, шириной и скоростью игры.
   *
   * @param height высота игрового поля
   * @param width ширина игрового поля
   * @param speed скорость игры
   */
  public Board(int height, int width, int speed) {
    this.height = height;
    this.width = width;
    this.speed = speed;
    this.snake = new Snake(height, width, speed);
  }

  /**
   * Конструктор Board создает новое игровое поле с заданными высотой, шириной, скоростью игры и мозгом змеи.
   *
   * @param height высота игрового поля
   * @param width ширина игрового поля
   * @param speed скорость игры
   * @param snakeBrain мозг змеи
   */
  public Board(int height, int width, int speed, SnakeBrain snakeBrain) {
    this.height = height;
    this.width = width;
    this.speed = speed;
    this.snake = new Snake(height, width, speed, snakeBrain);
  }

  /**
   * Возвращает змею, находящуюся на игровом поле.
   *
   * @return змея, находящаяся на игровом поле
   */
  public Snake getSnake() {
    return snake;
  }

}
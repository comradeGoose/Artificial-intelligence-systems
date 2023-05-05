package sample;

public class Board {

  private final int height;
  private final int width;
  private int speed = 1;
  private Snake snake;

  public Board(int height, int width, int speed) {
    this.height = height;
    this.width = width;
    this.speed = speed;
    this.snake = new Snake(height, width, speed);
  }

  public Board(int height, int width, int speed, SnakeBrain snakeBrain) {
    this.height = height;
    this.width = width;
    this.speed = speed;
    this.snake = new Snake(height, width, speed, snakeBrain);
  }

  public Snake getSnake() {
    return snake;
  }

}
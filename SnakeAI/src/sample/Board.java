package sample;

public class Board {

  private final int height;
  private final int width;
  private Snake snake;

  public Board(int height, int width) {
    this.height = height;
    this.width = width;
    this.snake = new Snake(height, width);
  }

  public Board(int height, int width, SnakeBrain snakeBrain) {
    this.height = height;
    this.width = width;
    this.snake = new Snake(height, width, snakeBrain);
  }

  public Snake getSnake() {
    return snake;
  }

}
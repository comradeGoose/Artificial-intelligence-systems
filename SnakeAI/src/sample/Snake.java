package sample;

import java.util.ArrayList;
import java.util.List;

public class Snake {
  private int BOARD_HEIGHT;
  private int BOARD_WIDTH;

  private final List<Point> snake = new ArrayList<>();
//  Point snakeHead = snake.get(0);

  SnakeBrain snakeBrain = new SnakeBrain(BOARD_HEIGHT, BOARD_WIDTH);

  public Snake(int board_height, int board_width) {
    this.BOARD_HEIGHT = board_height;
    this.BOARD_WIDTH = board_width;
  }

  public void move() {
    System.out.println("змея ползет");
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void setSnakeSize(int snakeSize) {
    for (int i = 0; i < snakeSize; i++) {
      snake.add(new Point(BOARD_WIDTH / 2, BOARD_HEIGHT / 2));
    }
  }

  public boolean checkCollision(Point foodPosition) {
    return snake.get(0).equals(foodPosition);
  }

  public Point getSnakeHead() {
    return snake.get(0);
  }

  public boolean entry(Point other) {
    return snake.contains(other);
  }

}

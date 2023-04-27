package sample;

import java.util.Random;

public class Food {

  private int BOARD_HEIGHT;
  private int BOARD_WIDTH;

  private Point foodPosition;

  private final RandomNumber random = new RandomNumber();

  public Food(int board_height, int board_width) {
    this.BOARD_HEIGHT = board_height;
    this.BOARD_WIDTH = board_width;
    System.out.println("Food ::: BOARD_HEIGHT : " + BOARD_HEIGHT + " ; BOARD_WIDTH : " +  BOARD_WIDTH);
  }

  public Point createFood(int WIDTH, int HEIGHT) {
    int x = random.getInt(0, BOARD_WIDTH);
    int y = random.getInt(0, BOARD_HEIGHT);
    foodPosition = new Point(x, y);
    System.out.println("еда появилась");
    return foodPosition;
  }

  public Point getFoodPosition() {
    return foodPosition;
  }
}

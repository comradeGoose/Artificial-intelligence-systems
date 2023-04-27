package sample;

public class Board implements Runnable{

  private int BOARD_HEIGHT;
  private int BOARD_WIDTH;

  public Board(int board_height, int board_width) {
    this.BOARD_HEIGHT = board_height;
    this.BOARD_WIDTH = board_width;
  }

  private Snake snake = new Snake(BOARD_HEIGHT, BOARD_WIDTH);
  private Food food = new Food(BOARD_HEIGHT, BOARD_WIDTH);

  public void run() {
    while(true) {
      // здесь можно обновлять состояние игрового поля, например, вызывать методы движения змеи
      // и проверять, если змея съела еду, то генерировать новую еду
      food.createFood(BOARD_WIDTH, BOARD_HEIGHT);
      snake.setSnakeSize(4);
      snake.move();

      if (snake.checkCollision(food.getFoodPosition())) {
        do {
          food.createFood(BOARD_WIDTH, BOARD_HEIGHT);
        } while (snake.entry(food.getFoodPosition()));
      }
      System.out.println("x : " + food.getFoodPosition().getX() + " ; " + " y : " + food.getFoodPosition().getY());
    }
  }
}

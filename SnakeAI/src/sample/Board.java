package sample;

import java.util.Random;

public class Board implements Runnable {


  private final int height;
  private final int width;
  private final Snake snake;

  public Board(int height, int width) {
    this.height = height;
    this.width = width;
    this.snake = new Snake(height, width);
  }

  public Snake getSnake() {
    return snake;
  }

  @Override
  public void run() {
    while (snake.IsAlive) {
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      snake.move();
      snake.eat();

      if (snake.checkCollision(height, width)) {
        snake.IsAlive = false;
      }
      // if snake.getFood().getPosition(), width, height
//      if () {
//        //food.setPosition(getRandomPosition());
//      }
    }
  }

}

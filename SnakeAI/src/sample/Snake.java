package sample;

import com.sun.javafx.tk.PrintPipeline;

import java.io.PipedInputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;

public class Snake {

  private final int _SNAKE_START_LEN_ = 4;
  private final int _SNAKE_STAMINA_ = 400;
  private final int _SNAKE_STAMINA_RECOVERY_ = 100;

  private int speed = 1;


  private int width;
  private int height;
  private SnakeBrain snakeBrain;
  private Point direction; //  = Point.RIGHT
  private Food food;

  public boolean IsAlive = true;
  public long startTime = System.currentTimeMillis();
  public int Lifetime = 0;
  public int FoodCounter = 0;
  public int Stamina = _SNAKE_STAMINA_;
  public int score = 0;

  private LinkedList<Point> body;

  public Point Head() {
    return body.getFirst();
  }

  public SnakeBrain getSnakeBrain() {
    return snakeBrain;
  }

  public double Score() {

    if (Lifetime  == 0 || (Lifetime  == 0 && FoodCounter == 0)) return 1;

    return Math.pow(Lifetime, 2) * Math.pow(2, FoodCounter);

//    return FoodCounter * 100 + Lifetime + Stamina;
  }

  public int getFoodCount() {
    return FoodCounter;
  }

  public int getLifetime() {
    return Lifetime;
  }

  public Food getFood() {
    return this.food;
  }

  public LinkedList<Point> getBody() {
    return body;
  }

  public Snake(int boardHeight, int boardWidth, int speed) {

    this.width = boardWidth;
    this.height = boardHeight;
    this.speed = speed;

    int startX = RandomNumber.getInt(0, boardWidth); //boardWidth / 2;
    int startY = RandomNumber.getInt(0, boardHeight); // boardHeight / 2;

    this.body = new LinkedList<Point>();
    this.snakeBrain = SnakeBrain.random();

    this.food = new Food(boardHeight, boardWidth);

    for (int i = 0; i < _SNAKE_START_LEN_; i++) {
      this.body.add(new Point(startX, startY)); //startX - i
    }
  }

  public Snake(int boardHeight, int boardWidth,int speed, SnakeBrain snakeBrain) {

    this.width = boardWidth;
    this.height = boardHeight;
    this.speed = speed;

    int startX = RandomNumber.getInt(0, boardWidth); //boardWidth / 2;
    int startY = RandomNumber.getInt(0, boardHeight); //boardHeight / 2;

    this.body = new LinkedList<Point>();
    this.snakeBrain = snakeBrain;

    this.food = new Food(boardHeight, boardWidth);

    for (int i = 0; i < _SNAKE_START_LEN_; i++) {
      this.body.add(new Point(startX, startY)); // startX - i
    }
  }

  public boolean move() {
    if (!IsAlive) {
      return false;
    }
    Lifetime = (int) ((System.currentTimeMillis() - startTime) / (1000 / speed));

    LinkedList<Float> observations = gatherObservations();
    LinkedList<Float> actions = snakeBrain.think(observations);

    int maxIndex = 0;
    for (int i = 0; i < snakeBrain.OUTPUT_SIZE; i++) {
      if (actions.get(i) > actions.get(maxIndex)) {
        maxIndex = i;
      }
    }

    setDirection(Point.FOUR_DIRECTIONS.get(maxIndex));

    Point head = body.get(0);
    if (Point.UP.equals(direction)) { //direction
      head = new Point(head.getX(), head.getY() - 1);
    } else if (Point.DOWN.equals(direction)) {
      head = new Point(head.getX(), head.getY() + 1);
    } else if (Point.LEFT.equals(direction)) {
      head = new Point(head.getX() - 1, head.getY());
    } else if (Point.RIGHT.equals(direction)) {
      head = new Point(head.getX() + 1, head.getY());
    }
    body.addFirst(head);
    body.removeLast();
    eat();

    if (checkCollision(height, width)) {
      IsAlive = false;
      return false;
    }
    Stamina--;
    if (Stamina <= 0) {
      IsAlive = false;
      return false;
    }
    return true;
  }

  public void eat() {
    if (Head().getX() == getFood().getX() && Head().getY() == getFood().getY()) {
      FoodCounter++;
      Stamina += _SNAKE_STAMINA_RECOVERY_;
      body.add(new Point(body.getLast().getX(), body.getLast().getY()));
//      body.addLast();
      food.createFood(height, width);
    }
  }

  public void setDirection(Point direction) {
    this.direction = direction;
  }

  public boolean checkCollision(int boardHeight, int boardWidth) {
    Point head = Head();
    if (head.getX() < 0 || head.getX() >= boardWidth || head.getY() < 0 || head.getY() >= boardHeight) {
      return true;
    }
    for (int i = 1; i < body.size(); i++) {
      if (head.getX() == body.get(i).getX() && head.getY() == body.get(i).getY()) {
        return true;
      }
    }
    return false;
  }

  // Собираем информацию о том, насколько близко находятся стены, еда и хвост змейки в каждом из восьми направлений от головы змейки.
    private LinkedList<Float> gatherObservations() {
    LinkedList<Float> observations = new LinkedList<>();
    for (Point dir : Point.EIGHT_DIRECTIONS) {
      observations.add(inverseDistanceToWall(dir));
      observations.add(inverseDistanceToFood(dir));
      observations.add(inverseDistanceToTail(dir));
    }
    return observations;
  }

  // Принимаем направление и определяем, насколько близко находится ближайшая стена в этом направлении.
  // Начинаем с шага 1 и последовательно увеличиваем шаг на 1, проверяя, находится ли точка на расстоянии i в данном
  // направлении от головы змейки в пределах границ игрового поля. Как только точка достигает границы, метод возвращает
  // обратную величину текущего шага (1 / i), что означает, что стена находится на расстоянии i от головы змейки.
  private float inverseDistanceToWall(Point dir) {
    for (int i = 1; true; i++) {
      if (!dir.contains(Head().add(dir.multiply(i)), width, height)) {
        return 0;
      }
      if (dir.contains(Head().add(dir.multiply(i)), width, height)) {
        return 1f / i;
      }
    }
  }

  // также принимает направление и определяет, насколько близко находится ближайшая еда в этом направлении.
  private float inverseDistanceToFood(Point dir) {
    for (int i = 1; true; i++) {
      if (!dir.contains(Head().add(dir.multiply(i)), width, height)) {
        return 0;
      } else if (getFood().equals(body.get(0).add(dir.multiply(i)))) {
        return 1f / i;
      }
    }
  }

  // также принимает направление и определяет, насколько близко находится ближайший хвост змейки в этом направлении.
  private float inverseDistanceToTail(Point dir) {
    for (int i = 1; true; i++) {
      if (!dir.contains(Head().add(dir.multiply(i)), width, height)) {
        return 0;
      } else if (body.contains(body.get(0).add(dir.multiply(i)))) {
        return 1f / i;
      }
    }
  }
}

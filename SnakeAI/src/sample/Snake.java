package sample;

import com.sun.javafx.tk.PrintPipeline;

import java.io.PipedInputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;

public class Snake {

  private final int _SNAKE_START_LEN_ = 4;
  private final int _SNAKE_STAMINA_ = 100;
  private final int _SNAKE_STAMINA_RECOVERY_ = 50;

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

  /**
   * Получает голову змеи.
   *
   * @return координаты головы змеи
   */
  public Point Head() {
    return body.getFirst();
  }

  /**
   * Получает мозг змеи.
   *
   * @return SnakeBrain, управляющий данной змеей
   */
  public SnakeBrain getSnakeBrain() {
    return snakeBrain;
  }

  /**
   * Возвращает текущий счет змеи.
   * Счет вычисляется по формуле: Lifetime^2 * 2^FoodCounter.
   *
   * @return текущий счет змеи
   */
  public double Score() {

    if (Lifetime  == 0 || (Lifetime  == 0 && FoodCounter == 0)) return 1;

    return Math.pow(Lifetime, 2) * Math.pow(2, FoodCounter);
  }

  /**
   * Получает количество съеденной змеей еды.
   *
   * @return количество съеденной змеей еды
   */
  public int getFoodCount() {
    return FoodCounter;
  }

  /**
   * Получает количество времени, которое змея находится в игре.
   *
   * @return количество времени, которое змея находится в игре
   */
  public int getLifetime() {
    return Lifetime;
  }

  /**
   * Получает текущее положение еды на поле.
   *
   * @return текущее положение еды на поле
   */
  public Food getFood() {
    return this.food;
  }

  /**
   * Получает список координат змеи.
   *
   * @return список координат змеи
   */
  public LinkedList<Point> getBody() {
    return body;
  }

  /**
   * Конструктор для создания змеи
   *
   * @param boardHeight высота игрового поля
   * @param boardWidth ширина игрового поля
   * @param speed скорость змеи
   */
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

  /**
   * Конструктор для создания змеи с определенным мозгом
   *
   * @param boardHeight высота игрового поля
   * @param boardWidth ширина игрового поля
   * @param speed скорость змеи
   * @param snakeBrain мозг змеи
   */
  public Snake(int boardHeight, int boardWidth, int speed, SnakeBrain snakeBrain) {

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

  /**
   * Метод для перемещения змеи.
   *
   * @return true если змея еще жива, false в противном случае
   */
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

  /**
   * Метод для съедания еды змейкой. Если координаты головы змейки совпадают с координатами еды, то
   * увеличивается количество еды, восстанавливается выносливость змейки, добавляется одна точка в тело змейки,
   * создается новая еда на игровом поле.
   */
  public void eat() {
    if (Head().getX() == getFood().getX() && Head().getY() == getFood().getY()) {
      FoodCounter++;
      Stamina += _SNAKE_STAMINA_RECOVERY_;
      body.add(new Point(body.getLast().getX(), body.getLast().getY()));
//      body.addLast();
      food.createFood(height, width);
    }
  }

  /**
   * Метод для установки направления движения змейки.
   *
   * @param direction объект Point, содержащий направление движения.
   */
  public void setDirection(Point direction) {
    this.direction = direction;
  }

  /**
   * Метод для проверки столкновения змейки со стенами игрового поля и со своим телом.
   *
   * @param boardHeight высота игрового поля.
   * @param boardWidth ширина игрового поля.
   * @return значение true, если змейка столкнулась со стеной или своим телом, и false в противном случае.
   */
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


  /**
   * Метод для сбора информации о расстоянии от головы змейки до стен, еды и хвоста змейки в каждом из восьми направлений от головы змейки.
   *
   * @return список, содержащий информацию о расстоянии от головы змейки до стен, еды и хвоста змейки в каждом из восьми направлений от головы змейки.
   */
    private LinkedList<Float> gatherObservations() {
    LinkedList<Float> observations = new LinkedList<>();
    for (Point dir : Point.EIGHT_DIRECTIONS) {
      observations.add(inverseDistanceToWall(dir));
      observations.add(inverseDistanceToFood(dir));
      observations.add(inverseDistanceToTail(dir));
    }
    return observations;
  }

  /**
   * Определяет, насколько близко находится стена в направлении dir.
   *
   * @param dir направление, в котором нужно определить ближайшую стену
   * @return обратную величину текущего шага (1 / i), если стена находится на расстоянии i от головы змейки, иначе 0
   */
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

  /**
   * Определяет, насколько близко находится еда в направлении dir.
   *
   * @param dir направление, в котором происходит поиск
   * @return обратная величина шага, на котором была найдена ближайшая еда в заданном направлении. Если еда не найдена, возвращает 0.
   */
  private float inverseDistanceToFood(Point dir) {
    for (int i = 1; true; i++) {
      if (!dir.contains(Head().add(dir.multiply(i)), width, height)) {
        return 0;
      } else if (getFood().equals(body.get(0).add(dir.multiply(i)))) {
        return 1f / i;
      }
    }
  }

  /**
   * Определяет, насколько близко находится ближайший хвост змейки в направлении dir.
   *
   * @param dir направление, в котором производится поиск ближайшего хвоста.
   * @return 0, если в данном направлении нет хвоста змейки, или обратная величина текущего шага (1 / i), на котором хвост был найден, если он есть.
   */
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

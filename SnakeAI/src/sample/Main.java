package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.SQLOutput;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main extends Application {

  private static final int BOARD_WIDTH = 40;
  private static final int BOARD_HEIGHT = 40;
  private static final int TILE_SIZE = 15;
  private int SPEED = 2;

  private int generation = 0;
  private int Worlds = 20;
  private int choseN = 3;
  private int bestN = 3;



  private List<Board> boards;
//  private Board board;
  private Canvas canvas;
  private GraphicsContext gc;

  @Override
  public void start(Stage primaryStage) {
    StackPane root = new StackPane();

    canvas = new Canvas(BOARD_WIDTH * TILE_SIZE, BOARD_HEIGHT * TILE_SIZE);
    gc = canvas.getGraphicsContext2D();

    root.getChildren().add(canvas);

    Scene scene = new Scene(root, BOARD_WIDTH * TILE_SIZE, BOARD_HEIGHT * TILE_SIZE);

    primaryStage.setScene(scene);
    primaryStage.show();


    boards = new LinkedList<Board>();

    for (int i = 0; i < Worlds; i++) {
      Board board = new Board(BOARD_HEIGHT, BOARD_WIDTH, SPEED);
      boards.add(board);
    }



    new AnimationTimer() {

      long lastUpdate = 0;

      @Override
      public void handle(long now) {

        if (now - lastUpdate >= 100_000_000 * (1/SPEED)) {
          lastUpdate = now;

          boolean AllAlive = true;

          if (AllAlive) {
            for (Board board : boards) {
              AllAlive = board.getSnake().move();
            }
          }
          if (!AllAlive) {
            spawnNextGeneration();
          }

          draw(gc);
        }

      }
    }.start();
  }

  public List<Snake> findBestSnakes(List<Board> boards, int count) {
    return boards.stream() // создаем поток из досок
            .map(Board::getSnake) // преобразуем каждую доску в ее змейку
            .sorted(Comparator.comparingDouble(Snake::Score).reversed()) // сортируем змеек по убыванию счета
            .limit(count) // ограничиваем поток двумя змейками с лучшим счетом
            .collect(Collectors.toList()); // собираем результат в список
  }

  private void spawnNextGeneration() {
    System.out.println("-----------------------Поколение " + generation++);
            for (Snake snake : findBestSnakes(boards, bestN)) {
              System.out.println("Lifetime : " + snake.Lifetime
                      + " | " + "Food : " + snake.FoodCounter
                      + " | " + "Stamina : " + snake.Stamina
                      + " | " + "Score : " + snake.Score());
            }

    Selection selection = new Selection(boards.stream().map(Board::getSnake).collect(Collectors.toList()));
    boards = Stream.concat(
            selection.choseN(choseN).stream()
                    .map(brain -> new Board(BOARD_HEIGHT, BOARD_WIDTH, SPEED, brain)),
            IntStream.range(0, Worlds - choseN)
                    .parallel()
                    .mapToObj(i -> new Board(BOARD_HEIGHT, BOARD_WIDTH, SPEED, selection.spawn())))
            .collect(Collectors.toList());
    generation++;
  }

  private synchronized void draw(GraphicsContext gc) {
    gc.setFill(Color.BLACK);
    gc.fillRect(0, 0, BOARD_WIDTH * TILE_SIZE, BOARD_HEIGHT * TILE_SIZE);

    for (Board board : boards) {
      Snake snake = board.getSnake();

      if (snake.IsAlive) {
        Food food = snake.getFood();

        gc.setFill(Color.GREEN);
        for (Point pos : snake.getBody()) {
          if (pos != null) {
            gc.fillRect(pos.getX() * TILE_SIZE, pos.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
          }
        }

        gc.setFill(Color.RED);
        gc.fillRect(food.getX() * TILE_SIZE, food.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
      }

    }
  }

  public static void main(String[] args) {
    launch(args);
  }

}

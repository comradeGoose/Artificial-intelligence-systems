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
  private int SPEED = 6;

  private int generation = 0;
  public int KeepTopN = 10;
  private int Worlds = 100;


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
      Board board = new Board(BOARD_HEIGHT, BOARD_WIDTH);
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
              AllAlive = false;
              if (board.getSnake().move()) {
//                board.getSnake().Lifetime = (System.currentTimeMillis() - board.getSnake().startTime) / 1000;
//                System.out.println((System.currentTimeMillis() - board.getSnake().startTime) / 1000);
                AllAlive = true;
              }
            }
          }
          if (!AllAlive) {
            spawnNextGeneration();
          }
//          if (!AllAlive) {
//            System.out.println("-----------------------Поколение " + generation++);
//            for (Snake snake : findTwoBestSnakes(boards)) {
//              System.out.println(snake.Score() + " : " + snake.Lifetime);
//            }
//            LinkedList<SnakeBrain> snakeBrains = new LinkedList<SnakeBrain>();
//            for (int i = 0; i < Worlds; i++) {
//              snakeBrains.add(Selection.spawn(findTwoBestSnakes(boards)));
//            }
//            boards = new LinkedList<Board>();
//            for (int i = 0; i < Worlds; i++) {
//
//              Board board = new Board(BOARD_HEIGHT, BOARD_WIDTH, snakeBrains.get(i));
//              boards.add(board);
//            }
////            SpawnNextGeneration();
//          }

          draw(gc);
        }

      }
    }.start();
  }

  public List<Snake> findTwoBestSnakes(List<Board> boards) {
    return boards.stream() // создаем поток из досок
            .map(Board::getSnake) // преобразуем каждую доску в ее змейку
            .sorted(Comparator.comparingDouble(Snake::Score).reversed()) // сортируем змеек по убыванию счета
            .limit(2) // ограничиваем поток двумя змейками с лучшим счетом
            .collect(Collectors.toList()); // собираем результат в список
  }


  public static double getBestScore(List<Board> boards) {
      int bestScore = 0;
      for (int i = 0; i < 10; i++) {
        int score = (int) boards.get(i).getSnake().Score();
        if (score > bestScore) {
          bestScore = score;
        }
      }
      return bestScore;
  }

//

//  private void spawnNextGeneration() {
//    System.out.println(" ------------------- ПОКОЛЕНИЕ : " + generation++);
//    for (Board board : boards) {
//      System.out.print(" | " + board.getSnake().Score());
//    }
//    System.out.println(" --------------------------------------------- ");
//
//    List<Snake> snakes = new ArrayList<>();
//    for (Board board : boards) snakes.add(board.getSnake());
//    Selection selection = new Selection(snakes);
//    List<SnakeBrain> snakeBrains = selection.keepTopN(KeepTopN);
//
//
//
//    List<SnakeBrain> newBrains = selection.createPopulation();
//    boards = new LinkedList<Board>();
//    for (SnakeBrain snakeBrain : newBrains) {
//      Board board = new Board(BOARD_HEIGHT, BOARD_WIDTH, snakeBrain);
//      boards.add(board);
//    }
//  }


  private void spawnNextGeneration() {
    System.out.println("-----------------------Поколение " + generation++);
            for (Snake snake : findTwoBestSnakes(boards)) {
              System.out.println(snake.Score() + " : " + snake.Lifetime);
            }

    Selection selection = new Selection(boards.stream().map(Board::getSnake).collect(Collectors.toList()));
    boards = Stream.concat(
            selection.keepTopN(KeepTopN).stream()
                    .map(brain -> new Board(BOARD_HEIGHT, BOARD_WIDTH,brain)),
            IntStream.range(0, Worlds - KeepTopN)
                    .parallel()
                    .mapToObj(i -> new Board(BOARD_HEIGHT, BOARD_WIDTH, selection.spawn())))
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

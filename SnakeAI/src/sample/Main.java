package sample;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

  private static final int BOARD_WIDTH = 40;
  private static final int BOARD_HEIGHT = 30;
  private static final int TILE_SIZE = 20;

  private List<Board> boards;

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

    boards = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      Board board = new Board(BOARD_HEIGHT, BOARD_WIDTH);
      boards.add(board);
      new Thread(board).start();
    }

    new Thread(() -> {
      while (true) {
        Platform.runLater(() -> draw(gc));
        try {
          Thread.sleep(50);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }).start();
  }

  private void draw(GraphicsContext gc) {

    gc.setFill(Color.WHITE);
    gc.fillRect(0, 0, BOARD_WIDTH * TILE_SIZE, BOARD_HEIGHT * TILE_SIZE);

    for (Board board : boards) {
      Snake snake = board.getSnake();
      Food food = snake.getFood();

      gc.setFill(Color.GREEN);
      for (Point pos : snake.getBody()) {
        gc.fillRect(pos.getX() * TILE_SIZE, pos.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
      }

      gc.setFill(Color.RED);
      gc.fillRect(food.getX() * TILE_SIZE, food.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }
  }

  public static void main(String[] args) {
    launch(args);
  }

}

package snake;

import com.sun.javafx.geom.Matrix3f;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.MatrixType;
import javafx.stage.Stage;

import java.awt.*;
import java.sql.SQLOutput;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MainSnake extends Application {

  private Group root;
  private Canvas canvas;
  private GraphicsContext gc;
  private Scene scene;

  private static final int BOARD_WIDTH = 40;
  private static final int BOARD_HEIGHT = 40;
  private static final int TILE_SIZE = 15;

  private int SPEED = 1;

  @Override
  public void start(Stage primaryStage) {

//    Point x = new Point(0,0);
//    Point y = new Point(39,39);
//    System.out.println(x.distance(y));

//    Point head = new Point(14,35);
//    Point food = new Point(7,14);
//    int any = 0;
//    Point top = new Point(head.x, 0);
//    double distanceTop = head.distance(head.x, 0);
//    Point left = new Point(0, head.y);
//    double distanceLeft = head.distance(0, head.y);
//    Point bot = new Point(head.x, BOARD_HEIGHT - 1);
//    double distanceBot = head.distance(head.x, BOARD_HEIGHT - 1);
//    Point right = new Point(BOARD_WIDTH - 1, head.y);
//    double distanceRight = head.distance(BOARD_WIDTH - 1, head.y);
//    double distanceFood = head.distance(food);
//    boolean eat = head.equals(food);
//
//    System.out.println(head.toString());

    root = new Group();

    canvas = new Canvas(BOARD_WIDTH * TILE_SIZE, BOARD_HEIGHT * TILE_SIZE);

    gc = canvas.getGraphicsContext2D();

    root.getChildren().add(canvas);

    scene = new Scene(root);

    scene.setOnKeyPressed(event -> {
      if ((event.getCode() == KeyCode.PLUS || event.getCode() == KeyCode.EQUALS) && SPEED < 10) SPEED++;
      if (event.getCode() == KeyCode.MINUS && SPEED > 1) SPEED--;
    }
    );


    new AnimationTimer() {

      long lastUpdate = 0;

      @Override
      public void handle(long now) {

        if (now - lastUpdate >= 100_000_000 * (1/SPEED)) {
          lastUpdate = now;
          System.out.println(SPEED);
          draw(gc);
        }
      }
    }.start();

    primaryStage.setScene(scene);
    primaryStage.show();
  }

  private synchronized void draw(GraphicsContext gc) {
    gc.setFill(Color.BLACK);
    gc.fillRect(0, 0, BOARD_WIDTH * TILE_SIZE, BOARD_HEIGHT * TILE_SIZE);
  }

  public static void main(String[] args) {
    launch(args);
  }
}


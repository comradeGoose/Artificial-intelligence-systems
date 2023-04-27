package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnakeBrain {
  private static final int TILE_SIZE = 20;
  private static final int WIDTH = 40;
  private static final int HEIGHT = 30;

  private int direction = 0;
  private Point food = new Point(0, 0);
  private List<Point> snake = new ArrayList<>();
  private Random random = new Random();

  private int[] hiddenLayer = new int[3];
  private int[][] inputHiddenWeights = new int[5][3];
  private int[] hiddenOutputWeights = new int[3];

  public SnakeBrain() {
    // Инициализация весов
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 3; j++) {
        inputHiddenWeights[i][j] = random.nextInt(3) - 1;
      }
    }
    for (int i = 0; i < 3; i++) {
      hiddenOutputWeights[i] = random.nextInt(3) - 1;
    }

    // Задание начальной длины змейки
    for (int i = 0; i < 4; i++) {
      snake.add(new Point(WIDTH / 2, HEIGHT / 2));
    }
  }

  // Получить рекомендуемое направление движения змейки
  public int getRecommendedDirection() {
    // Входной вектор
    int[] input = new int[5];
    input[0] = direction;
    input[1] = food.getX() - snake.get(0).getX();
    input[2] = food.getY() - snake.get(0).getY();
    input[3] = snake.get(0).getX() - snake.get(1).getX();
    input[4] = snake.get(0).getY() - snake.get(1).getY();

    // Прямое распространение
    for (int i = 0; i < 3; i++) {
      hiddenLayer[i] = 0;
      for (int j = 0; j < 5; j++) {
        hiddenLayer[i] += input[j] * inputHiddenWeights[j][i];
      }
      hiddenLayer[i] = Math.max(hiddenLayer[i], 0);
    }

    int output = 0;
    for (int i = 0; i < 3; i++) {
      output += hiddenLayer[i] * hiddenOutputWeights[i];
    }

    return output > 0 ? direction : (direction + 2) % 4;
  }
}

package sample;

import java.util.*;
import java.util.stream.Collectors;

public class Selection {

  private final List<Pair<SnakeBrain, Double>> scores;
  private final double totalScore;
  private final List<Snake> snakes;


  public Selection(List<Snake> snakes) {
    scores = snakes.stream()
            .map(snake -> new Pair<>(snake.getSnakeBrain(), snake.Score()))
            .sorted(Collections.reverseOrder(Comparator.comparing(Pair::getRight)))
            .collect(Collectors.toList());

    this.snakes = snakes;

    totalScore = scores.stream().mapToDouble(Pair::getRight).sum();
  }

  public List<SnakeBrain> choseN(int count) {
    return scores.stream()
            .map(Pair::getLeft)
            .limit(count)
            .collect(Collectors.toList());
  }

  public SnakeBrain spawn() {
    SnakeBrain mom = chooseMom(snakes);
    SnakeBrain dad = chooseMom(snakes);
    return SnakeBrain.cross(mom, dad);
  }

  private SnakeBrain chooseMom(List<Snake> snakes) {
    Optional<Snake> maxFoodCountSnake = snakes.stream()
            .filter(snake -> snake.getFoodCount() >= 2)
            .max(Comparator.comparing(Snake::getFoodCount));
    if (maxFoodCountSnake.isPresent()) {
      return maxFoodCountSnake.get().getSnakeBrain();
    } else {
      return chooseParent();
    }
  }

  private SnakeBrain chooseDad(List<Snake> snakes) {
    return snakes.stream()
            .max(Comparator.comparing(Snake::getLifetime))
            .map(Snake::getSnakeBrain)
            .orElseThrow(() -> new IllegalStateException("No matching snake found"));
  }

  private SnakeBrain chooseParent() {
    double seed = RandomNumber.getDouble(0, totalScore);
    for (Pair<SnakeBrain, Double> pair : scores) {
      seed -= pair.getRight();
      if (seed < 0) {
        return pair.getLeft();
      }
    }
    throw new IllegalStateException("No matching snake found");
  }

}






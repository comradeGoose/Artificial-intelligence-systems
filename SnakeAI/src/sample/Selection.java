package sample;

import java.util.*;
import java.util.stream.Collectors;

public class Selection {
//
//  private final double totalScore;
//  private final List<Snake> snakes;
//
//  public Selection(List<Board> boards) {
//    this.totalScore = boards.stream()
//            .mapToDouble(board -> board.getSnake().Score())
//            .sum();
//    this.snakes = sortSnakes(boards);
//  }
//
//  public List<SnakeBrain> keepTopN(int count) {
//    return snakes.stream()
//            .map(snake -> snake.getSnakeBrain())
//            .limit(count)
//            .collect(Collectors.toList());
//  }
//
//  public List<Snake> sortSnakes(List<Board> boards) {
//    return boards.stream()
//            .map(Board::getSnake)
//            .sorted(Comparator.comparingDouble(Snake::Score).reversed()) //
//            .collect(Collectors.toList());
//  }
//
//
//  private SnakeBrain chooseParent() {
//    double seed = RandomNumber.getDouble(0, totalScore);
//    for (Snake snake : snakes) {
//      seed -= snake.Score();
//      if (seed < 0) {
//        return snake.getSnakeBrain();
//      }
//    }
//    throw new IllegalStateException("No matching snake found");
//  }
//
//
//  public SnakeBrain spawn() {
//    SnakeBrain mom = chooseParent();
//    SnakeBrain dad = chooseParent();
//    return SnakeBrain.cross(mom, dad);
//  }
//
//  public List<SnakeBrain> createPopulation() {
//    List<SnakeBrain> newPopulation = new ArrayList<>();
//    for (int i = 0; i < snakes.size(); i++) {
//      SnakeBrain offspring = spawn();
//      newPopulation.add(offspring);
//    }
//    return newPopulation;
//  }

  private final List<Pair<SnakeBrain, Double>> scores;
  private final double totalScore;

  public Selection(List<Snake> snakes) {
    scores = snakes.stream()
            .map(snake -> new Pair<>(snake.getSnakeBrain(), snake.Score()))
            .sorted(Collections.reverseOrder(Comparator.comparing(Pair::getRight)))
            .collect(Collectors.toList());
    totalScore = scores.stream().mapToDouble(Pair::getRight).sum();
  }

  public List<SnakeBrain> keepTopN(int count) {
    return scores.stream()
            .map(Pair::getLeft)
            .limit(count)
            .collect(Collectors.toList());
  }

  public SnakeBrain spawn() {
    SnakeBrain mom = chooseParent();
    SnakeBrain dad = chooseParent();
    return SnakeBrain.cross(mom, dad);
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






package sample;

import java.util.*;
import java.util.stream.Collectors;

public class Selection {

  private final List<Pair<SnakeBrain, Double>> scores;
  private final double totalScore;
  private final List<Snake> snakes;

  /**
   * Конструктор класса Selection, который инициализирует поля scores, totalScore и snakes.
   *
   * @param snakes список змеек, из которых будет производиться выбор при селекции.
   */
  public Selection(List<Snake> snakes) {
    scores = snakes.stream()
            .map(snake -> new Pair<>(snake.getSnakeBrain(), snake.Score()))
            .sorted(Collections.reverseOrder(Comparator.comparing(Pair::getRight)))
            .collect(Collectors.toList());

    // змейки для функци chooseMom
    this.snakes = snakes;

    // суммируем счет всех змеек
    totalScore = scores.stream().mapToDouble(Pair::getRight).sum();
  }

  /**
   * Выбирает N лучших SnakeBrain из списка змеек.
   *
   * @param count Число выбранных змеек
   * @return Список выбранных SnakeBrain
   */
  public List<SnakeBrain> choseN(int count) {
    return scores.stream()
            .map(Pair::getLeft)
            .limit(count)
            .collect(Collectors.toList());
  }

  /**
   * Создает новую змейку на основе генетического материала родительских змеек.
   *
   * @return Новый объект SnakeBrain, представляющий генетический материал потомка змейки
   */
  public SnakeBrain spawn() {
//    SnakeBrain mom = chooseMom(snakes);
//    SnakeBrain dad = chooseMom(snakes);

    // создаем мозг двух предков змеек
    SnakeBrain mom = chooseParent();
    SnakeBrain dad = chooseParent();

    // возвращаем мозг потомка змейки
    return SnakeBrain.cross(mom, dad);
  }

  /**
   * Метод выбирает мозг змейки, которая съела больше двух.
   *
   * @param snakes Список змеек для выбора родительской змейки
   * @return SnakeBrain объект, представляющий генетический материал выбранной змейки
   */
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
  /**
   * Метод выбирает мозг змейки с наибольшим временем жизни из списка змеек.
   *
   * @param snakes список змеек, из которых нужно выбрать отца
   * @return мозг змейки-отца с наибольшим временем жизни
   * @throws IllegalStateException если список змеек пустой
   */
  private SnakeBrain chooseDad(List<Snake> snakes) {
    return snakes.stream()
            .max(Comparator.comparing(Snake::getLifetime))
            .map(Snake::getSnakeBrain)
            .orElseThrow(() -> new IllegalStateException("No matching snake found"));
  }

  /**
   * Метод выбирает случайного родителя на основе весов, определяемых счетом змеек.
   *
   * @return мозг выбранной змейки-родителя
   * @throws IllegalStateException если список змеек пустой
   */
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






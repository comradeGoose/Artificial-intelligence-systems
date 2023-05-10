package sample;

import java.util.LinkedList;
import java.util.Random;


/**
 * Класс, представляющий мозг змейки.
 * Мозг представлен двумя матрицами весов: inputToHidden и hiddenToOutput,
 * которые преобразуют входные значения в выходные.
 * Входной слой содержит INPUT_SIZE нейронов, скрытый слой содержит HIDDEN_SIZE нейронов,
 * а выходной слой содержит OUTPUT_SIZE нейронов.
 * Все значения нейронов и входных значений в диапазоне от -1 до 1.
 * С вероятностью MUTATION_CHANCE при создании новых экземпляров матриц возможны мутации.
 * Мутация происходит с вероятностью MUTATION_CHANCE в случайном направлении.
 */
public class SnakeBrain {

  // Количество нейронов в каждом слое.
  public static final int INPUT_SIZE = 24;
  public static final int HIDDEN_SIZE = 16;
  public static final int OUTPUT_SIZE = 4;

  // Вероятность мутации.
  private static final float MUTATION_CHANCE = 0.5f;

  // Матрица весов между входным и скрытым слоями.
  private final Matrix inputToHidden;

  // Матрица весов между скрытым и выходным слоями.
  private final Matrix hiddenToOutput;

  /**
   * Конструктор класса, принимающий матрицы весов в качестве аргументов.
   *
   * @param inputToHidden матрица весов от входного слоя к скрытому слою
   * @param hiddenToOutput матрица весов от скрытого слоя к выходному слою
   */
  public SnakeBrain(Matrix inputToHidden, Matrix hiddenToOutput) {
    this.inputToHidden = inputToHidden;
    this.hiddenToOutput = hiddenToOutput;
  }

  /**
   * Создает новый мозг змеи со случайными значениями весов.
   *
   * @return случайный мозг змеи
   */
  public static SnakeBrain random() {
    return new SnakeBrain(
            Matrix.Random(HIDDEN_SIZE, INPUT_SIZE + 1),
            Matrix.Random(OUTPUT_SIZE, HIDDEN_SIZE + 1)
    );
  }

  /**
   * Добавляет 1 в конец списка значений.
   *
   * @param values список значений
   * @return новый список со значением 1 в конце
   */
  public static LinkedList<Float> attachOne(LinkedList<Float> values) {
    LinkedList<Float> result = values;
    result.add(1f);
    return result;
  }

  /**
   * Вычисляет выходной список на основе входного списка.
   *
   * @param inputs входной список значений
   * @return список выходных значений, полученный путем обработки входных значений
   */
  public LinkedList<Float> think(LinkedList<Float> inputs) {

    // Принимаем список входных значений , представленный как связный список чисел с плавающей точкой.
    // На вход мозга змейки также добавляется 1 в качестве смещения.

    LinkedList<Float> inputsWith1 = attachOne(inputs);

    // Вычисляем сумму для каждого нейрона скрытого слоя, умножая входы и матрицу весов между входным и скрытым слоем.
    // Умножаем, а затем преобразуеем в список.
    LinkedList<Float> sums = inputToHidden.multiply(inputToHidden.VectorToColumnMatrix(inputsWith1)).columnMatrixToVector();

    // каждое значение суммы проходит через функцию активации sigmoid
    LinkedList<Float> hiddens = new LinkedList<>();
    for (float sum : sums) {
      hiddens.add(sigmoid(sum));
    }

    // Создаем новый список, hiddensWith1, путем добавления 1 в качестве смещения в конец списка hiddens.
    LinkedList<Float> hiddensWith1 = attachOne(hiddens);

    // Повторяем процесс вычисления суммы, но уже используя список hiddensWith1 и матрицу весов hiddenToOutput
    LinkedList<Float> outputs = hiddenToOutput.multiply(inputToHidden.VectorToColumnMatrix(hiddensWith1)) .columnMatrixToVector();

    return outputs;
  }

  /**
   * Функция активации сигмоида для использования в нейронной сети.
   *
   * @param x значение аргумента функции сигмоида
   * @return результат применения функции сигмоида к значению аргумента
   */
  private float sigmoid(float x) {
    return 1 / (1 + (float) Math.exp(-x));
  }

  /**
   * Функция скрещивания наиболее успешных предков для создания нового потомка-мозга змейки.
   *
   * @param mom родительский мозг-змейка (мать)
   * @param dad родительский мозг-змейка (отец)
   * @return потомок-мозг змейки, созданный путем скрещивания матриц весов родительских мозгов с вероятностью мутации
   */
  public static SnakeBrain cross(SnakeBrain mom, SnakeBrain dad) {
    return new SnakeBrain(
            Matrix.cross(mom.inputToHidden, dad.inputToHidden, MUTATION_CHANCE),
            Matrix.cross(mom.hiddenToOutput, dad.hiddenToOutput, MUTATION_CHANCE)
    );
  }
}

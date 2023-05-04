package sample;

import java.util.LinkedList;
import java.util.Random;

public class SnakeBrain {

  public static final int INPUT_SIZE = 24;
  public static final int HIDDEN_SIZE = 12;
  public static final int OUTPUT_SIZE = 4;
  public static final float MUTATION_CHANCE = 0.02f;

  private Matrix inputToHidden;
  private Matrix hiddenToOutput;

  public SnakeBrain(Matrix inputToHidden, Matrix hiddenToOutput) {
    this.inputToHidden = inputToHidden;
    this.hiddenToOutput = hiddenToOutput;
  }

  public static SnakeBrain random() {
    return new SnakeBrain(
            Matrix.Random(HIDDEN_SIZE, INPUT_SIZE + 1),
            Matrix.Random(OUTPUT_SIZE, HIDDEN_SIZE + 1)
    );
  }

  public static LinkedList<Float> attachOne(LinkedList<Float> values) {
    LinkedList<Float> result = values;
    result.add(1f);
    return result;
  }

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

  private float sigmoid(float x) {
    return 1 / (1 + (float) Math.exp(-x));
//    return Math.max(0, x);
  }

  public static SnakeBrain cross(SnakeBrain mom, SnakeBrain dad) {
    return new SnakeBrain(
            Matrix.cross(mom.inputToHidden, dad.inputToHidden, MUTATION_CHANCE),
            Matrix.cross(mom.hiddenToOutput, dad.hiddenToOutput, MUTATION_CHANCE)
    );
  }
}

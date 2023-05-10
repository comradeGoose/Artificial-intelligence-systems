package sample;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Matrix {
  public final int Rows;
  public final int Columns;
  public final float[][] Cells;


  /**
   * Создает новый объект матрицы с заданным числом строк и столбцов.
   *
   * @param rows число строк в матрице.
   * @param columns число столбцов в матрице.
   */
  public Matrix(int rows, int columns) {
    Rows = rows;
    Columns = columns;
    Cells = new float[Rows][Columns];
  }

  /**
   * Создает новый объект матрицы из заданного двумерного массива.
   *
   * @param cells двумерный массив значений для создания матрицы.
   */
  public Matrix(float[][] cells) {
    Rows = cells.length;
    Columns = cells[0].length;
    Cells = cells;
  }

  /**
   * Создает новую матрицу со случайными значениями в диапазоне [-1, 1].
   *
   * @param rows число строк в матрице.
   * @param columns число столбцов в матрице.
   * @return новая матрица со случайными значениями в диапазоне [-1, 1].
   */
  public static Matrix Random(int rows, int columns) {
    Matrix matrix = new Matrix(rows, columns);
    Random rng = new Random();
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        matrix.Cells[i][j] = rng.nextFloat() * 2 - 1; // Random float between -1 and 1
      }
    }
    return matrix;
  }

  /**
   * Возвращает значение ячейки в заданной строке и столбце.
   *
   * @param i индекс строки.
   * @param j индекс столбца.
   * @return значение ячейки в заданной строке и столбце.
   */
  public float get(int i, int j) {
    return Cells[i][j];
  }

  /**
   * Задает значение ячейки в заданной строке и столбце.
   *
   * @param i индекс строки.
   * @param j индекс столбца.
   * @param value новое значение ячейки.
   */
  public void set(int i, int j, float value) {
    Cells[i][j] = value;
  }

  /**
   * Умножает матрицу на заданную матрицу b.
   *
   * @param b матрица, на которую нужно умножить эту матрицу.
   * @return результат умножения матрицы на матрицу b.
   * @throws IllegalArgumentException если число столбцов этой матрицы не равно числу строк матрицы b.
   */
  public Matrix multiply(Matrix b) {
    if (Columns != b.Rows) {
      throw new IllegalArgumentException("Matrix sizes are incompatible for multiplication.");
    }
    int m = Rows;
    int n = Columns;
    int p = b.Columns;
    float[][] cells = new float[m][p];
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < p; j++) {
        float sum = 0;
        for (int k = 0; k < n; k++) {
          sum += Cells[i][k] * b.Cells[k][j];
        }
        cells[i][j] = sum;
      }
    }
    return new Matrix(cells);
  }

  /**
   * Выполняет скрещивание двух матриц с возможной мутацией.
   *
   * @param a Первая матрица для скрещивания.
   * @param b Вторая матрица для скрещивания.
   * @param mutationChance Шанс мутации для каждого элемента результирующей матрицы.
   * @return Новая матрица, полученная в результате скрещивания a и b с возможной мутацией mutationChance.
   * @throws IllegalArgumentException Если размеры матриц a и b не совпадают.
   */
  public static Matrix cross(Matrix a, Matrix b, float mutationChance) {
    if (a.Rows != b.Rows || a.Columns != b.Columns) {
      throw new IllegalArgumentException("Matrix sizes do not match.");
    }

    float[][] cells = new float[a.Rows][a.Columns];
    Random rng = new Random();

    for (int i = 0; i < a.Rows; i++) {
      for (int j = 0; j < a.Columns; j++) {
        if (rng.nextFloat() < 0.5f) {
          cells[i][j] = a.Cells[i][j];
        } else {
          cells[i][j] = b.Cells[i][j];
        }
        if (rng.nextFloat() < mutationChance) {
          cells[i][j] = Math.max(-1, Math.min(1, cells[i][j] + (float) rng.nextGaussian()));
//          cells[i][j] = RandomNumber.getFloat(-1, 1);
        }
      }
    }
    return new Matrix(cells);
  }

  /**
   * Преобразует матрицу вектора в список значений.
   *
   * @return Новый связный список значений, созданный из матрицы.
   */
  public LinkedList<Float> columnMatrixToVector() {
    LinkedList<Float> vector = new LinkedList<Float>();
//    List<float[]> name = Arrays.stream(Cells).toList();
    for (float[] row : Cells) {
      for (float value : row) {
        vector.add(value);
      }
    }
    return vector;
  }


  /**
   * Преобразует список значений вектора в матрицу.
   *
   * @param vector Список значений вектора.
   * @return Новая матрица, созданная из значений вектора.
   */
  public static Matrix VectorToColumnMatrix(LinkedList<Float> vector) {
    float[][] column = new float[vector.size()][1];
    int i = 0;
    for (float value : vector) {
      column[i++][0] = value;
    }
    return new Matrix(column);
  }
}

package sample;

import java.util.LinkedList;
import java.util.Random;

public class Matrix {
  public final int Rows;
  public final int Columns;
  public final float[][] Cells;

  public Matrix(int rows, int columns) {
    Rows = rows;
    Columns = columns;
    Cells = new float[Rows][Columns];
  }

  public Matrix(float[][] cells) {
    Rows = cells.length;
    Columns = cells[0].length;
    Cells = cells;
  }

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

  public float get(int i, int j) {
    return Cells[i][j];
  }

  public void set(int i, int j, float value) {
    Cells[i][j] = value;
  }

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
        }
      }
    }
    return new Matrix(cells);
  }

  public LinkedList<Float> columnMatrixToVector() {
    LinkedList<Float> vector = new LinkedList<Float>();
    for (float[] row : Cells) {
      for (float value : row) {
        vector.add(value);
      }
    }
    return vector;
  }

  public static Matrix VectorToColumnMatrix(LinkedList<Float> vector) {
    float[][] column = new float[vector.size()][1];
    int i = 0;
    for (float value : vector) {
      column[i++][0] = value;
    }
    return new Matrix(column);
  }

  public static LinkedList<Float> attachOne(LinkedList<Float> values) {
    LinkedList<Float> result = new LinkedList<>(values);
    result.add(1f);
    return result;
  }

}

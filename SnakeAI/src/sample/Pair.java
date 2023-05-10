package sample;

public class Pair<L, R> {
  private final L left;
  private final R right;


  /**
   * Конструктор Pair принимает два параметра типа L и R, которые являются левым и правым элементами пары соответственно.
   */
  public Pair(L left, R right) {
    this.left = left;
    this.right = right;
  }

  /**
   * Метод getLeft возвращает левый элемент пары, имеющий тип L.
   *
   * @return левый элемент пары, имеющий тип L.
   */
  public L getLeft() {
    return left;
  }

  /**
   * Метод getRight возвращает правый элемент пары, имеющий тип R.
   *
   * @return правый элемент пары, имеющий тип R.
   */
  public R getRight() {
    return right;
  }
}
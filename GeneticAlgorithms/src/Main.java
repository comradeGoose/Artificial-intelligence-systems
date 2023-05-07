public class Main {

    public static void main(String[] args) {
      System.out.println("Обучить перцептрон с помощью генетического алгоритма");
      MultilayerPerceptron multilayerPerceptron = new MultilayerPerceptron();
      System.out.println();
      System.out.println("Максимизировать функцию с помощью генетического алгоритма");
      MaximizeFunction maximizeFunction = new MaximizeFunction();
    }
}

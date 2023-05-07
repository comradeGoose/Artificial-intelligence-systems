import java.util.ArrayList;
import java.util.Comparator;


public class MaximizeFunction {

  java.util.List<Double> proba;
  java.util.List<Chromosome> chromosomes;
  public MaximizeFunction() {

    init();
    for(int i=0; i<1000; i++){
      mark();
      markSel();
      selection1();
      selectionRulet();
      recombination();
    }
    mark();
    markSel();
    selection();
    Chromosome best = chromosomes.get(0);
    System.out.println(best.x+" "+best.y+" "+best.fitness);
  }

  void init(){
    chromosomes = new java.util.ArrayList<>();
    proba = new java.util.ArrayList<>();
    java.util.Random r = new java.util.Random();
    for(int i=0; i<100; i++){
      Chromosome chromosome = new Chromosome();
      chromosome.x = r.nextDouble()-500;
      chromosome.y = r.nextDouble()-500;
      chromosomes.add(chromosome);
    }
  }

  void mark(){
    for(Chromosome chromosome:chromosomes){
      chromosome.countFitness();
    }
  }

  void markSel(){
    for(Chromosome chromosome:chromosomes){
      chromosome.countSelProb(chromosomes);
    }
  }

  void selection(){
    chromosomes.sort(new Comparator(){
      @Override
      public int compare(Object obj1, Object obj2){
        int res = 0;
        double fitness1 = ((Chromosome)obj1).fitness;
        double fitness2 = ((Chromosome)obj2).fitness;
        if (fitness2>fitness1) res=1; else
        if(fitness2<fitness1) res = -1; else res=0;
        return res;
      }
    });
    chromosomes = chromosomes.subList(0, chromosomes.size()/2);
  }

  void selection1() {
    chromosomes.sort(new Comparator() {
      @Override
      public int compare(Object obj1, Object obj2) {
        int res = 0;
        double prob1 = ((Chromosome) obj1).selectionProb;
        double prob2 = ((Chromosome) obj2).selectionProb;
        if (prob2 < prob1) res = 1;
        else if (prob2 > prob1) res = -1;
        else res = 0;
        return res;
      }
    });
  }

  void selectionRulet() {
    double prob = 0;

    java.util.ArrayList<Chromosome> chromosomes2 = new java.util.ArrayList<>();
    java.util.Random r = new java.util.Random();
    for (int i = 0; i < chromosomes.size(); i++) {
      prob += chromosomes.get(i).selectionProb;
      proba.add(prob);
    }


    for(int i=0; i<50; i++){
      int index = -1;
      double subject = r.nextDouble();

      for (int j = 0; j < proba.size(); j++) {
        index = proba.get(j) >= subject ? j : -1;
        if (index >= 0) {
          chromosomes2.add(chromosomes.get(index));
          break;
        }
      }
    }
    chromosomes=chromosomes2;
    proba.clear();
  }


  void recombination(){
    java.util.Random r = new java.util.Random();
    java.util.ArrayList<Chromosome> chromosomes2 = new java.util.ArrayList<>();
    for(int i=0; i<50; i++){
      int index = r.nextInt(chromosomes.size());
      Chromosome c1 = chromosomes.get(index);

      index = r.nextInt(chromosomes.size());
      Chromosome c2 = chromosomes.get(index);


      Chromosome c1new = new Chromosome();
      c1new.x = c1.x + Math.random()*0.3-0.15;
      c1new.y = c2.y + Math.random()*0.3-0.15;

      Chromosome c2new = new Chromosome();
      c2new.x = c2.x;
      c2new.y = c1.y;

      chromosomes2.add(c1new);
      chromosomes2.add(c2new);
    }
    chromosomes = chromosomes2;
  }
}

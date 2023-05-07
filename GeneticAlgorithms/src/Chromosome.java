
public class Chromosome {
  double x;
  double y;
  double fitness;
  double selectionProb;

  void countFitness(){
    fitness = (double) 1 / (1 + Math.pow(x,2) + Math.pow(y,2));
  }

  void countSelProb(java.util.List<Chromosome> chromosomes){
    double Sum = 0;
    for (Chromosome chrom: chromosomes) {
      Sum+=chrom.fitness;
    }
    selectionProb = fitness/Sum;
  }
}

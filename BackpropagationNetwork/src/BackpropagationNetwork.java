
public class BackpropagationNetwork {
  double[] enters;
  double[] outers;
  double[][] weights;
  double[] kohonen;
  double[] countKohActive;

  double[][] patterns = {
          {1, 0},
          {0, 1}
  };

  public BackpropagationNetwork(){
    enters = new double[2];
    kohonen = new double[2];
    weights = new double[enters.length][kohonen.length];
    countKohActive = new double[kohonen.length];
    for (int i = 0; i < enters.length; i++){
      for(int j = 0; j < kohonen.length; j++)
      {
        weights[i][j] =Math.random()-0.5;
      }
    }
    for(int i = 0; i < 1000; i++){
      int maxId = studyKohonen();
      countKohActive[maxId]++;
    }
    for(int i = 0; i < countKohActive.length; i++ ){
      System.out.println(countKohActive[i]);
    }

    for (int p = 0; p < patterns.length; p++ ){
      enters = java.util.Arrays.copyOf(patterns[p], patterns[p].length);
      countOut();
      System.out.println(kohonen[0] + " " + kohonen[1]);
    }
  }

  public int countOut(){
    for(int i = 0; i < kohonen.length; i++ ){
      kohonen[i] = 0;
      for(int j = 0; j < enters.length; j++){
        kohonen[i] += enters[j] * weights[j][i];
      }
    }
    int di = -1;
    if(countKohActive[0]-countKohActive[1]>20){
      di=0;
    }else if(countKohActive[0]-countKohActive[1]<-15){
      di=1;
    }

    double maxKoh = kohonen[0];
    int maxKohId = 0;
    for(int i = 1; i < kohonen.length; i++){
      if(kohonen[i] > maxKoh){
        maxKoh = kohonen[i];
        maxKohId = i;
      }
    }

    for(int i = 0; i < kohonen.length; i++){
      kohonen[i]= 0;
    }
    kohonen[maxKohId] = 1;

    if(maxKohId == di &&(di == 0))
      return 1;
    else if (maxKohId == di &&(di == 1))
      return 0;
    else return maxKohId;
  }

  public int studyKohonen(){
    double a = 0.7;
    int maxId = 0;
    for (int p = 0; p < patterns.length; p++ ){
      enters = java.util.Arrays.copyOf(patterns[p], patterns[p].length);
      maxId = countOut();
      for (int i = 0; i < enters.length; i++)
      {
        for(int j =0; j < kohonen.length; j++){
          weights[i][maxId] += a*(enters[i]-weights[i][maxId]);
        }
      }
    }
    return maxId;
  }
}
package multilayer_perceptron;

public class XOR {

    double enters[];
    double hiddens[];
    double out;
    double[][] weightsEH;
    double[] weightsHO;
    double[][] patterns ={
            {0, 0},
            {0, 1},
            {1, 0},
            {1, 1}

    };
    float answers[] = {0, 1, 1, 0};

    XOR(){
        enters = new double[patterns[0].length];
        hiddens = new double[enters.length];
        weightsEH = new double[enters.length][hiddens.length];
        weightsHO = new double[hiddens.length];
    }

    public void init(){
        for (int i = 0; i < weightsEH.length; i++) {
            for (int j = 0; j < weightsEH[i].length; j++) {
                weightsEH[i][j] = 0.2 * Math.random() + 0.1;
            }
        }
        for (int i = 0; i < weightsHO.length; i++) {
            weightsHO[i] = 0.2 * Math.random() + 0.1;
        }
    }

    public void countOut(){
        for (int i = 0; i < hiddens.length; i++) {
            hiddens[i] = 0;
            for (int j = 0; j < enters.length; j++) {
                hiddens[i]+=enters[j]*weightsEH[j][i];
            }
            if(hiddens[i]>0.5) hiddens[i]=1;
            else hiddens[i]=0;
        }
        out = 0;
        for (int i = 0; i < hiddens.length; i++) {
            out+=hiddens[i]*weightsHO[i];
        }
        if(out>0.5)out=1;
        else out=0;
    }

    public void study(){
        double[] err = new double[hiddens.length];
        double gErr = 0;
        int step = 0;
        do {
            gErr =0;
            for (int i = 0; i < patterns.length; i++) {
                for (int j = 0; j < enters.length; j++) {
                    enters[j] = patterns[i][j];
                }
                countOut();

                double lErr = answers[i] - out;
                gErr += Math.abs(lErr);

                for (int j = 0; j < hiddens.length; j++) {
                    err[j] = lErr*weightsHO[j];
                }

                for (int j = 0; j < enters.length; j++) {
                    for (int k = 0; k < hiddens.length; k++) {
                        weightsEH[j][k]+=0.1*err[k]*enters[j];
                    }
                }
                for (int j = 0; j < hiddens.length; j++) {
                    weightsHO[j]+=0.1*lErr*hiddens[j];
                }
            }
            step++;
        } while (gErr!=0);

        System.out.println("Steps : " + step);
    }

    public void test() {
        init();
        study();
        for (int p = 0; p < patterns.length; p++) {
            enters = java.util.Arrays.copyOf(patterns[p], patterns[p].length);
            countOut();
            System.out.println(out);
        }
    }
}

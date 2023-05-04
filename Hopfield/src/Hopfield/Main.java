package Hopfield;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
      Hopfield hopfield = new Hopfield(new File("src/resources/images/A.png"));
      hopfield.remember("src/resources/images/broken_A.png");

//      hopfield.setImage(new File("src/resources/images/C.png"));
//      hopfield.getImage();
//      hopfield.study();
//      hopfield.remember("src/resources/images/A_C.png");
    }
}

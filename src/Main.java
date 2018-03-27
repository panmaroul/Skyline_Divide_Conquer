import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.awt.geom.Point2D;

public class Main {
    public static void main(String [] args) {

        // The name of the file to open.
        String fileName = "input10.txt";


        // This will reference one line at a time
        String line = null;

        try {
            Scanner s = new Scanner(new File("input10.txt"));

            int [][] listI = new int[s.nextInt()][2];
            int i=0;
            while(s.hasNextInt()) {
                listI[i][0] = s.nextInt();
                listI[i][1] = s.nextInt();
                i++;
            }
            System.out.println(Arrays.deepToString(listI));


        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }




    }
}

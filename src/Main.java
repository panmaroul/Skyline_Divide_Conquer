import java.io.*;
import java.util.*;

public class Main {

    private static SortedMap<Integer,Integer> skylineMap = new TreeMap<>(); //declaring a sorted map for easier checking

    /**
     *The main method. It creates a timer to count the time needed for the skyline to be created.
     *  It reads a file, creates a 2d initial array that saves all the coordinates given, sorting them in ascending order
     *  in the x dimension. Then it calls the divideAndConquer method with the initialArray as parameter. Finally it prints
     *  the Map we created in the beginning, in the way it should be printed.
     * @param args receives from command line a string used for file reading.
     */
    public static void main(String[] args) {
        final long startTime = System.nanoTime(); // starts counting time
        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            String sCurrentLine;
            sCurrentLine = br.readLine();
            int[][] initialArray = new int[Integer.parseInt(sCurrentLine)][2];
            int i=0;
            String[] xyPoints; //array for saving the numbers from each line
            while((sCurrentLine=br.readLine())!=null){
                xyPoints = sCurrentLine.split("\t"); //splitting numbers from each line

                //Parsing the numbers as Integers to the initiallArray
                initialArray[i][0] = Integer.parseInt(xyPoints[0]);
                initialArray[i++][1] = Integer.parseInt(xyPoints[1]);
            }
            Arrays.sort(initialArray, Comparator.comparingInt(point -> point[0])); //sorting in the x dimension the initialArray using lambda expression
            divideAndConquer(initialArray); //Calling divideAndConquer method with initialArray as parameter
        }catch (IOException e){
            e.printStackTrace();
        }
        skylineMap.forEach((key, value) -> System.out.println(key + " " + value)); //prints skylineMap the proper way
        final long totalTime = System.nanoTime() - startTime;
        double seconds = (double)totalTime / 1000000000.0;
        System.out.println(seconds); //prints the time counted in seconds
    }

    /**
     * This method is used for dividing, each time a recursion happens, the array in two halves (left, right)
     * If the length of either one of these arrays is equal or less than 2 then the method findSkyline is called
     * with the array as parameter.
     * @param pointsArray an Array of points that is divided every time a recursion is made
     */
    public static void divideAndConquer(int[][] pointsArray){
        int totalSize = pointsArray.length;
        int halfSize = totalSize/2;

        int[][] leftArray = Arrays.copyOfRange(pointsArray,0,halfSize); //Creates in each recursion a leftArray with half size
        int[][] rightArray = Arrays.copyOfRange(pointsArray,halfSize,totalSize); //Creates in each recursion a rightArray with the other half size

        // Checking each time if an array's length is <=2 and then it calls the findSkyline method with either leftArray or rightArray as parameter
        Arrays.asList(leftArray,rightArray).forEach(array -> {
            if (array.length <= 2) findSkyline(array);
            else divideAndConquer(array);
        });
    }

    /**
     *This method checks firstly if the array is null or its length is zero and returns nothing secondly if the array is of length 1
     * then calls the putToSkyline method with parameters the coordinates of the point and thirdly if the arrays is of length 2 calls the
     * checkDomination method with pointsArray as parameter
     * @param pointsArray this array contains either two points or one or zero.
     */
    public static void findSkyline(int[][] pointsArray){
        if (pointsArray == null || pointsArray.length ==0) return;
        int x1 = pointsArray[0][0], y1 = pointsArray[0][1];
        if (pointsArray.length == 1){
            putToSkyline(x1,y1);
            return;
        }
        checkDomination(pointsArray);
    }

    /**
     * This method checks if any of the two points given is dominant to the other. If the first point is dominant to the second it
     * is inserted to the putToSkyline method. Same as the second. And in the end if neither of these two points is dominant to the other
     * they are both parsed to the putToSkyline method.
     * @param twoPoints an array of two points
     */
    public static void checkDomination(int[][] twoPoints){
        int x1 = twoPoints[0][0], y1 = twoPoints[0][1], x2 = twoPoints[1][0], y2 = twoPoints[1][1];
        if((x1 <= x2 && y1 <= y2)) putToSkyline(x1,y1);
        else if((x2 <= x1 && y2 <= y1)) putToSkyline(x2,y2);
        else{
            putToSkyline(x1,y1);
            putToSkyline(x2,y2);
        }
    }

    /**
     * Checks if the point given is dominant from at least one point from the skyline. If it is dominated then
     * it will not be inserted into the skyline.
     * @param x x coordinate
     * @param y y coordinate
     */
    public static void putToSkyline(int x,int y){
        boolean flag = false;
        for (Map.Entry<Integer,Integer> entry : skylineMap.entrySet()){
            if (entry.getValue() <= y){
                flag = true;
                break;
            }
        }
        if (!flag) skylineMap.put(x,y);
    }
}

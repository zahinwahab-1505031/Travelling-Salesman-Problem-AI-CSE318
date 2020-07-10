/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelling_salesman_problem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

/**
 *
 * @author Zahin
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static int START;
    public static int edgeType;
    public static int numberOfNodes = -1;

    static double[] readInput(String name) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(name));
        String str = null, str_type = "NULL_TYPE", str_dim = "DIM";
        for (int i = 0;; i++) {

            if (scanner.hasNext()) {
                str = scanner.nextLine();
                if (str.contains("EDGE_WEIGHT_TYPE")) {
                    str_type = str;
                }
                if (str.contains("DIMENSION")) {
                    str_dim = str;
                }
                if (str.contains("NODE_COORD_SECTION")) {
                    break;
                }
            }
        }
        String[] arr = str_type.split(":");
        String type = arr[1];
        String[] arr2 = str_dim.split(":");
        String dimension = arr2[1].trim();
        Main.numberOfNodes = Integer.parseInt(dimension);

        if (type.contains("EUC_2D")) {
            Main.edgeType = 0;
        } else {
            Main.edgeType = 1;
        }
        System.out.println("In MAIN, Type: " + type + " , dim = " + Main.numberOfNodes + "type_int = " + Main.edgeType);
        //NODE_COORD_SECTION
        double[] arr_double = new double[500];

        if (scanner.hasNext()) {
//            System.out.println("Next:" + scanner.nextLine());
        }
        List<Double> list = new ArrayList<>();

        int idx = 0;
        double temp;
        while (scanner.hasNextDouble()) {
            //System.out.println("Next double is " + scanner.nextDouble());
            temp = scanner.nextDouble();

            arr_double[idx] = (double) temp;
            ///    System.out.println("N -> <" + arr_double[idx] + ">"); 
            idx++;

        }

        return arr_double;

    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
    
  //  run2("berlin52.tsp");
  run2("pr76.tsp");
    //  run2("st70.tsp");
     
 //  run2("burma14.tsp");
    }

    static void run2(String fileName) throws FileNotFoundException, IOException {
        Scanner scanner = new Scanner(new File("input1.txt"));

        FileWriter fileWriter = new FileWriter("output.txt");
        //  fileWriter.write(fileContent);
        // fileWriter.close();
        double[] arr = readInput(fileName); //new double[500];


        int n = Main.numberOfNodes;//(int) arr[0];
        double[] costArr = new double[1000];
        double bestCase = 10000000;
        double worstCase = -1;
        int indexForBestCase = -1;
        int indexForWorstCase = -1;
          List<Vector<Point>> list = new ArrayList<Vector<Point>>();
   
    
      
          double sum = 0;
         //iter;
        for (int iter = 0; iter < n; iter++) {
            // int   iter = 0;
            
  START = 11;
            Graph graph = new Graph(n);
            int k = 1;
            for (int i = 0; i < n; i++) {
                graph.points[i] = new Point(arr[k], arr[k + 1]);
                graph.visitedMap.put(graph.points[i], false);
                graph.indexMap.put(graph.points[i], i);
                k += 3;
            }

           graph.SavingsHeuristic_Randomized();
           
            costArr[iter] = graph.getCost();
            sum+= costArr[iter];
            if (costArr[iter] < bestCase) {
                bestCase = costArr[iter];
                indexForBestCase = iter;
            }
            if (costArr[iter] > worstCase) {
                worstCase = costArr[iter];
                indexForWorstCase = iter;
            }
            System.out.println("Cost: " + graph.getCost());
            String fileContent = iter + " " + costArr[iter] + "\n";
            
            fileWriter.write(fileContent);
            list.add(graph.OptimalTour);

        }
//                System.out.println("BEST CASE:   COST:" + bestCase + " " + indexForBestCase);
//        System.out.println("WORST CASE:   COST:" + worstCase + " " + indexForWorstCase);
//          System.out.println("AVG CASE:   COST:" + sum/n);
        Collections.sort(list, new TourComparator());
        list = list.subList(0, 3);
       //iter;

            Graph graph = new Graph(n);
            int k = 1;
            for (int i = 0; i < n; i++) {
                graph.points[i] = new Point(arr[k], arr[k + 1]);
                graph.visitedMap.put(graph.points[i], false);
                graph.indexMap.put(graph.points[i], i);
                k += 3;
            }
            START = 11;

           graph.SavingsHeuristic_Randomized();
           list.add(graph.OptimalTour);
         //  System.out.println(list.size());
        fileWriter.close();

       costArr = new double[1000];
         bestCase = 10000000;
         worstCase = -1;
         indexForBestCase = -1;
         indexForWorstCase = -1;
         sum = 0;
         for (int iter = 0; iter < 4; iter++) {
           

            graph = new Graph(n);
             k = 1;
            for (int i = 0; i < n; i++) {
                graph.points[i] = new Point(arr[k], arr[k + 1]);
                graph.visitedMap.put(graph.points[i], false);
                graph.indexMap.put(graph.points[i], i);
                k += 3;
            }

        graph.UpdateTour(list.get(iter));
      graph.TwoOptHeuristic_BestImprovement();
       // graph.TwoOptHeuristic_FirstImprovement();
            //  System.out.println("Solution: ");
            System.out.println(iter);
            graph.printOptimalTour();
            costArr[iter] = graph.getCost();
            sum+= costArr[iter];
            if (costArr[iter] < bestCase) {
                bestCase = costArr[iter];
                indexForBestCase = iter;
            }
            if (costArr[iter] > worstCase) {
                worstCase = costArr[iter];
                indexForWorstCase = iter;
            }
            System.out.println("Cost: " + graph.getCost());
            
           

        }
              System.out.println("BEST CASE:   COST:" + bestCase + " " + indexForBestCase);
        System.out.println("WORST CASE:   COST:" + worstCase + " " + indexForWorstCase);
           System.out.println("AVG CASE:   COST:" + sum/4);
   
    }

}

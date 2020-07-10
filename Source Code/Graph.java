/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelling_salesman_problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author Zahin
 */
public class Graph {

    int NUM_OF_POINTS;
    Point[] points;
    Vector<Point> OptimalTour;
    HashMap<Point, Boolean> visitedMap;
    HashMap<Point, Integer> indexMap;

    public Graph(int n) {
        NUM_OF_POINTS = n;
        points = new Point[n];
        OptimalTour = new Vector<Point>();
        visitedMap = new HashMap<Point, Boolean>();
        indexMap = new HashMap<Point, Integer>();
    }

    public double getCost(Vector<Point> tour) {

        double cost = 0;
        for (int i = 0; i < tour.size(); i++) {
            cost += tour.get(i).getDistance(tour.get((i + 1) % tour.size()));

        }
        cost += tour.get(0).getDistance(tour.get(tour.size() - 1));

        return cost;

    }

    public static double returnCost(Vector<Point> tour) {

        double cost = 0;
        for (int i = 0; i < tour.size(); i++) {
            cost += tour.get(i).getDistance(tour.get((i + 1) % tour.size()));

        }
        cost += tour.get(0).getDistance(tour.get(tour.size() - 1));

        return cost;

    }

    public double getCost() {

        double cost = 0;
        for (int i = 0; i < OptimalTour.size(); i++) {
            cost += OptimalTour.get(i).getDistance(OptimalTour.get((i + 1) % OptimalTour.size()));

        }
        cost += OptimalTour.get(0).getDistance(OptimalTour.get(OptimalTour.size() - 1));

        return cost;

    }

    public Point NearestUnvisited(Point point) {
        double min = Integer.MAX_VALUE;
        Point nearest = null;
        for (int i = 0; i < NUM_OF_POINTS; i++) {
            // if(points[i]!=point) {
            if (visitedMap.get(points[i]) == false) {
                if (point.getDistance(points[i]) < min) {
                    nearest = points[i];
                    min = point.getDistance(points[i]);
                }
            }
            // }
        }
        //    System.out.println(point + "--->" + min + "<----" + nearest);
        return nearest;

    }

    public Point NearestUnvisited_Randomized(Point point) {
        // double min = Integer.MAX_VALUE;
        //   Point nearest = null;
        List<Index_Dist> list = new ArrayList<>();
        for (int i = 0; i < NUM_OF_POINTS; i++) {
            // if(points[i]!=point) {
            if (visitedMap.get(points[i]) == false) {
                Index_Dist id = new Index_Dist(i, point.getDistance(points[i]));
                list.add(id);
            }
            // }
        }
        //    System.out.println(point + "--->" + min + "<----" + nearest);

        Collections.sort(list, new Index_DistComparator());
        //    System.out.println("start");
        Random rand = new Random();

        int n; //= rand.nextInt(4) + 0;
        Index_Dist nearest = null; //= bestFive.get(n);
        if (list.size() > 5) {
            List<Index_Dist> bestFive = list.subList(0, 5);
            n = rand.nextInt(4) + 0;
            nearest = bestFive.get(n);
            return points[nearest.x];
        } else if (list.size() <= 5 && list.size() > 1) {
            // List<Index_Dist> bestFive = list.subList(0, 4);
            //     System.out.println("here"+list.size());
            n = rand.nextInt(list.size() - 1) + 0;
            //  System.out.println("to here");
            nearest = list.get(n);
            return points[nearest.x];
        } else {

            return points[list.get(0).x];
        }

        //     System.out.println("end");
    }

    void NearestNeighbourHeuristic(int isRandom) {
        Random rand = new Random();
        int start = Main.START;//rand.nextInt(NUM_OF_POINTS-1);

        Point pt = points[start];
        OptimalTour.add(pt);
        visitedMap.put(pt, true);
        while (OptimalTour.size() < NUM_OF_POINTS) {
          if(isRandom == 0)    pt = NearestUnvisited(pt);
         if (isRandom == 1)  pt = NearestUnvisited_Randomized(pt);
            visitedMap.put(pt, true);
            OptimalTour.add(pt);
        }

    }

    Point NearestUnvisitedForSubTour() {
        double min = Integer.MAX_VALUE;
        Point nearest = null;
        for (int i = 0; i < OptimalTour.size(); i++) {
            Point near = NearestUnvisited(OptimalTour.get(i));
            if (near.getDistance(OptimalTour.get(i)) < min) {
                nearest = near;
                min = near.getDistance(OptimalTour.get(i));
            }
        }
        return nearest;

    }

    Point MinimumEdge(Point k) {
        double min = Integer.MAX_VALUE;
        Point start = null;
        for (int it = 0; it < OptimalTour.size(); it++) {
            Point i = OptimalTour.get(it);
            Point j = OptimalTour.get((it + 1) % OptimalTour.size());
            double dist = i.getDistance(k) + j.getDistance(k) - i.getDistance(j);
            if (dist < min) {
                min = dist;
                start = i;

            }
        }
        return start;
    }

    void printOptimalTour() {
        System.out.println("=========PRINTING TOUR===========");
        for (int i = 0; i < OptimalTour.size(); i++) {

            // System.out.print(OptimalTour.get(i)+"---");
            System.out.print(indexMap.get(OptimalTour.get(i)) + " ");
        }
        System.out.println("\n====================");
    }

    void NearestInsertionHeuristic() {
        OptimalTour = new Vector<Point>();
        Random rand = new Random();
        int start = rand.nextInt(NUM_OF_POINTS - 1);
        Point pt = points[start];
        System.out.println(pt);
        OptimalTour.add(pt);
        visitedMap.put(pt, true);
        Point pt2 = NearestUnvisited(pt);
        OptimalTour.add(pt2);
        visitedMap.put(pt2, true);
        printOptimalTour();
        while (OptimalTour.size() < NUM_OF_POINTS) {
            Point k = NearestUnvisitedForSubTour();
            System.out.print("<" + k);
            Point i = MinimumEdge(k);
            System.out.println("," + i + ">");
            int index = 0;
            for (int it = 0; it < OptimalTour.size(); it++) {
                if (OptimalTour.get(it).equals(i)) {
                    index = it;
                    break;
                }
            }
            System.out.println("Index:  " + index);
            Vector<Point> copy = new Vector<Point>();

            for (int it = index + 1; it < OptimalTour.size(); it++) {
                copy.add(OptimalTour.get(it));

            }
            OptimalTour.add(index + 1, k);
            System.out.println("after adding at required index");
            printOptimalTour();
            visitedMap.put(k, Boolean.TRUE);
            printOptimalTour();

        }
    }

    void CheapestInsertionHeuristic() {
        OptimalTour = new Vector<Point>();
        Random rand = new Random();
        int s = rand.nextInt(NUM_OF_POINTS - 1);
        Point pt = points[s];
        System.out.println(pt);
        OptimalTour.add(pt);
        visitedMap.put(pt, true);
        Point pt2 = NearestUnvisited(pt);
        OptimalTour.add(pt2);
        visitedMap.put(pt2, true);
        while (OptimalTour.size() < NUM_OF_POINTS) {
            double min = Integer.MAX_VALUE;
            Point start = null;
            Point k = null;
            for (int it = 0; it < OptimalTour.size(); it++) {
                Point i = OptimalTour.get(it);
                Point j = OptimalTour.get((it + 1) % OptimalTour.size());
                for (int it1 = 0; it1 < NUM_OF_POINTS; it1++) {
                    if (visitedMap.get(points[it1]) == false) {
                        double dist = i.getDistance(points[it1]) + j.getDistance(points[it1]) - i.getDistance(j);
                        if (dist < min) {
                            min = dist;
                            start = i;
                            k = points[it1];

                        }
                    }
                }
            }
            //Point k = NearestUnvisitedForSubTour();
            //System.out.print("<"+k);
            Point i = start;
            //System.out.println(","+i+">");
            int index = 0;
            for (int it = 0; it < OptimalTour.size(); it++) {
                if (OptimalTour.get(it).equals(i)) {
                    index = it;
                    break;
                }
            }

            Vector<Point> copy = new Vector<Point>();

            for (int it = index + 1; it < OptimalTour.size(); it++) {
                copy.add(OptimalTour.get(it));

            }
            OptimalTour.add(index + 1, k);

            visitedMap.put(k, Boolean.TRUE);

        }
    }

    public Vector<Point> TwoOptSwap(int i, int k) {
        Vector<Point> tour = new Vector<Point>();
        for (int it = 0; it <= i - 1; it++) {
            tour.add(OptimalTour.get(it));
        }
        for (int it = k; it >= i; it--) {

            tour.add(OptimalTour.get(it));
        }
        for (int it = k + 1; it < OptimalTour.size(); it++) {
            tour.add(OptimalTour.get(it));
        }

        return tour;
    }

    public void UpdateTour(Vector<Point> tour) {
        OptimalTour = new Vector<Point>();
        for (int i = 0; i < tour.size(); i++) {
            OptimalTour.add(tour.get(i));
        }
    }

    public void UpdateTour(Vector<Point> tour, Vector<Point> temp) {
        tour = new Vector<Point>();
        for (int i = 0; i < temp.size(); i++) {
            tour.add(temp.get(i));
        }
    }

    public void TwoOptHeuristic_FirstImprovement() {
        // SavingsHeuristic();
        //   NearestNeighbourHeuristic();
        //SavingsHeuristic_Randomized();
        //   System.out.println("Cost using nearest neighbour heuristic:" + getCost());

        int it = 0;
        while (true) {
            //   it++;
            boolean improvement = false;
            double cost = getCost();
            for (int i = 1; i < OptimalTour.size() - 1; i++) {
                for (int k = i + 1; k < OptimalTour.size(); k++) {
                    Vector<Point> newTour = TwoOptSwap(i, k);
                    double newCost = getCost(newTour);
                    if (newCost < cost) {
                        cost = newCost;
                        UpdateTour(newTour);
                        improvement = true;
                        break;
                    }
                    //   if(improvement==true) break;
                }
                if (improvement == true) {
                    break;
                }
            }
            if (improvement == false) {
                break;
            }

        }
    }

    public void TwoOptHeuristic_BestImprovement() {
        //    SavingsHeuristic_Randomized();
     //   NearestNeighbourHeuristic();
        //   System.out.println("Cost using nearest neighbour heuristic:" + getCost());
        //     int improvement = 0;
        int it = 0;
        while (true) {
            it++;
            double cost = getCost();
            boolean improvement = false;
            for (int i = 1; i < OptimalTour.size() - 1; i++) {
                for (int k = i + 1; k < OptimalTour.size(); k++) {
                    Vector<Point> newTour = TwoOptSwap(i, k);
                    double newCost = getCost(newTour);
                    if (newCost < cost) {
                        cost = newCost;
                        UpdateTour(newTour);
                        improvement = true;

                    }
                }
            }
            if (improvement == false) {
                break;
            }
        }
    }

    public Vector<Point> ThreeOptSwap1(int i, int j, int k) {
        Vector<Point> tour = new Vector<>();
        for (int iter = 0; iter <= i; iter++) {
            tour.add(OptimalTour.get(iter));
        }
        for (int iter = j + 1; iter <= k; iter++) {
            tour.add(OptimalTour.get(iter));
        }
        for (int iter = i + 1; iter <= j; iter++) {
            tour.add(OptimalTour.get(iter));
        }
        for (int iter = k + 1; iter < OptimalTour.size(); iter++) {
            tour.add(OptimalTour.get(iter));
        }

        return tour;
    }

    public Vector<Point> ThreeOptSwap2(int i, int j, int k) {
        Vector<Point> tour = new Vector<>();

        /*   Vector<Point> subtour = new Vector<>();
         for(int it = (j);it>=(i+1);it--) {
             subtour.add(OptimalTour.get(it));
             
         }
         for (int iter = (i+1); iter < (j+1); iter++) {
            OptimalTour.set(iter, subtour.get(iter - (i+1)));
        }
         */
        Vector<Point> temp = TwoOptSwap(i + 1, j);
        UpdateTour(temp);

        //Add elements upto i
        for (int iter = 0; iter <= i; iter++) {
            tour.add(OptimalTour.get(iter));
        }

        //Normal adding between i and j
        for (int iter = i + 1; iter <= j; iter++) {
            tour.add(OptimalTour.get(iter));
        }
        for (int iter = j + 1; iter <= k; iter++) {
            tour.add(OptimalTour.get(iter));
        }
        //Normal adding done

        //Final adding elements
        for (int iter = k + 1; iter < OptimalTour.size(); iter++) {
            tour.add(OptimalTour.get(iter));
        }

        return tour;
    }

    public Vector<Point> ThreeOptSwap3(int i, int j, int k) {
        Vector<Point> tour = new Vector<>();

        //Reverse the tour from (i to j)
        /*  Vector<Point> subtour = new Vector<>();
         for(int it = (j);it>=(i+1);it--) {
             subtour.add(OptimalTour.get(it));
             
         }
         for (int iter = (i+1); iter < (j+1); iter++) {
            OptimalTour.set(iter, subtour.get(iter - (i+1)));
        }*/
        Vector<Point> temp = TwoOptSwap(i + 1, j);
        UpdateTour(temp);

        //Add elements upto i
        for (int iter = 0; iter <= i; iter++) {
            tour.add(OptimalTour.get(iter));
        }

        //Reverse adding between i and j
        for (int iter = j + 1; iter <= k; iter++) {
            tour.add(OptimalTour.get(iter));
        }
        for (int iter = i + 1; iter <= j; iter++) {
            tour.add(OptimalTour.get(iter));
        }
        //Reverse adding done

        //Final adding elements
        for (int iter = k + 1; iter < OptimalTour.size(); iter++) {
            tour.add(OptimalTour.get(iter));
        }

        return tour;
    }

    public Vector<Point> ThreeOptSwap4(int i, int j, int k) {
        Vector<Point> tour = new Vector<>();

        //Reverse tour from (j, k);
        /*  Vector<Point> subtour = new Vector<>();
         for(int it = (k);it>=(j+1);it--) {
             subtour.add(OptimalTour.get(it));
             
         }
         for (int iter = (j+1); iter < (k+1); iter++) {
            OptimalTour.set(iter, subtour.get(iter - (j+1)));
        }*/
        Vector<Point> temp = TwoOptSwap(j + 1, k);
        UpdateTour(temp);
        for (int iter = 0; iter <= i; iter++) {
            tour.add(OptimalTour.get(iter));
        }
        //Normal adding
        for (int iter = i + 1; iter <= j; iter++) {
            tour.add(OptimalTour.get(iter));
        }
        for (int iter = j + 1; iter <= k; iter++) {
            tour.add(OptimalTour.get(iter));
        }
        for (int iter = k + 1; iter < OptimalTour.size(); iter++) {
            tour.add(OptimalTour.get(iter));
        }

        return tour;
    }

    public Vector<Point> ThreeOptSwap5(int i, int j, int k) {
        Vector<Point> tour = new Vector<>();

        //Reverse tour from (j, k);
        /*  Vector<Point> subtour = new Vector<>();
         for(int it = (k);it>=(j+1);it--) {
             subtour.add(OptimalTour.get(it));
             
         }
         for (int iter = (j+1); iter < (k+1); iter++) {
            OptimalTour.set(iter, subtour.get(iter - (j+1)));
        }*/
        Vector<Point> temp = TwoOptSwap(j + 1, k);
        UpdateTour(temp);
        for (int iter = 0; iter <= i; iter++) {
            tour.add(OptimalTour.get(iter));
        }
        //Reverse adding
        for (int iter = j + 1; iter <= k; iter++) {
            tour.add(OptimalTour.get(iter));
        }
        for (int iter = i + 1; iter <= j; iter++) {
            tour.add(OptimalTour.get(iter));
        }

        for (int iter = k + 1; iter < OptimalTour.size(); iter++) {
            tour.add(OptimalTour.get(iter));
        }

        return tour;

    }

    public Vector<Point> ThreeOptSwap7(int i, int j, int k) {
        Vector<Point> latestTourFormed = new Vector<>();

        /*  Vector<Point> subtour = new Vector<>();
         for(int it = (j);it>=(i+1);it--) {
             subtour.add(OptimalTour.get(it));
             
         }
         for (int iter = (i+1); iter < (j+1); iter++) {
            OptimalTour.set(iter, subtour.get(iter - (i+1)));
        }

       subtour = new Vector<>();
         for(int it = (k);it>=(j+1);it--) {
             subtour.add(OptimalTour.get(it));
             
         }
         for (int iter = (j+1); iter < (k+1); iter++) {
            OptimalTour.set(iter, subtour.get(iter - (j+1)));
        }*/
        Vector<Point> temp = TwoOptSwap(i + 1, j);
        UpdateTour(temp);
        temp = TwoOptSwap(j + 1, k);
        UpdateTour(temp);

        for (int iter = 0; iter <= i; iter++) {
            latestTourFormed.add(OptimalTour.get(iter));
        }

        //Reverse adding between i and j
        for (int iter = j + 1; iter <= k; iter++) {
            latestTourFormed.add(OptimalTour.get(iter));
        }
        for (int iter = i + 1; iter <= j; iter++) {
            latestTourFormed.add(OptimalTour.get(iter));
        }
        //Reverse adding done

        //Final adding elements
        for (int iter = k + 1; iter < OptimalTour.size(); iter++) {
            latestTourFormed.add(OptimalTour.get(iter));
        }

        return latestTourFormed;
    }

    public Vector<Point> ThreeOptSwap6(int i, int j, int k) {
        Vector<Point> latestTourFormed = new Vector<>();

        /*  Vector<Point> subtour = new Vector<>();
         for(int it = (j);it>=(i+1);it--) {
             subtour.add(OptimalTour.get(it));
             
         }
         for (int iter = (i+1); iter < (j+1); iter++) {
            OptimalTour.set(iter, subtour.get(iter - (i+1)));
        }

       subtour = new Vector<>();
         for(int it = (k);it>=(j+1);it--) {
             subtour.add(OptimalTour.get(it));
             
         }
         for (int iter = (j+1); iter < (k+1); iter++) {
            OptimalTour.set(iter, subtour.get(iter - (j+1)));
        }*/
        Vector<Point> temp = TwoOptSwap(i + 1, j);
        UpdateTour(temp);
        temp = TwoOptSwap(j + 1, k);
        UpdateTour(temp);
        //Add elements upto i
        for (int iter = 0; iter <= i; iter++) {
            latestTourFormed.add(OptimalTour.get(iter));
        }

        //Normal adding between i and j
        for (int iter = i + 1; iter <= j; iter++) {
            latestTourFormed.add(OptimalTour.get(iter));
        }
        for (int iter = j + 1; iter <= k; iter++) {
            latestTourFormed.add(OptimalTour.get(iter));
        }
        //Normal adding done

        //Final adding elements
        for (int iter = k + 1; iter < OptimalTour.size(); iter++) {
            latestTourFormed.add(OptimalTour.get(iter));
        }

        return latestTourFormed;
    }

    public Vector<Point> ThreeOptSwap(int i, int j, int k, int p) {
        if (p == 1) {
            return ThreeOptSwap1(i, j, k);
        } else if (p == 2) {
            return ThreeOptSwap2(i, j, k);
        } else if (p == 3) {
            return ThreeOptSwap3(i, j, k);
        } else if (p == 4) {
            return ThreeOptSwap4(i, j, k);
        } else if (p == 5) {
            return ThreeOptSwap5(i, j, k);
        } else if (p == 6) {
            return ThreeOptSwap6(i, j, k);
        } else {
            return ThreeOptSwap7(i, j, k);
        }

    }

    public void ThreeOptHeuristic2() {

//        NearestNeighbourHeuristic();
        while (true) {
            double present = getCost();
            boolean Changed = false;

            for (int i = 0; i < OptimalTour.size(); i++) {
                for (int j = i + 2; j < OptimalTour.size(); j++) {
                    for (int k = j + 2; k < OptimalTour.size(); k++) {
                        for (int p = 1; p < 8; p++) {
                            Vector<Point> temp = ThreeOptSwap(i, j, k, p);
                            double NewCost = getCost(temp);
                            if (NewCost < present) {
                                Changed = true;
                                UpdateTour(temp);
                                break;
                            }
                        }
                        if (Changed) {
                            break;
                        }
                    }
                    if (Changed) {
                        break;
                    }
                }
            }
            if (!Changed) {
                break;
            }
        }

    }

    public void SavingsHeuristic() {
        int hubIdx = Main.START;

        List<SavingsElement> savingsElements = new ArrayList<>();
        for (int i = 0; i < NUM_OF_POINTS - 1; i++) {
            for (int j = i + 1; j < NUM_OF_POINTS; j++) {
                if (i != hubIdx && j != hubIdx) {
                    double savingValue = points[i].getDistance(points[hubIdx]) + points[j].getDistance(points[hubIdx]) - points[i].getDistance(points[j]);
                    SavingsElement savingElement = new SavingsElement(i, j, savingValue);
                    savingsElements.add(savingElement);
                }

            }
        }
        Collections.sort(savingsElements, new ElementComparator()); // sort in ascending order
        Collections.reverse(savingsElements); // but we need descending order

        visitedMap.put(points[hubIdx], true);
        int k = 0;
        int count = NUM_OF_POINTS - 1;
        int[] parent = new int[NUM_OF_POINTS];
        int[] degrees = new int[NUM_OF_POINTS];
        int[] connection = new int[NUM_OF_POINTS];
        for (int i = 0; i < NUM_OF_POINTS; i++) {
            parent[i] = i;
            degrees[i] = 0;
        }

        List<Edge> tour = new ArrayList<>();

        int edgeCount = 0;
        k = 0;
        while (count > 2) {
            //  System.out.println(count);
            SavingsElement element = savingsElements.get(k);
            k++;
            //    System.out.println(element);
            if (visitedMap.get(points[element.i]) == false && visitedMap.get(points[element.j]) == false && (parent[element.i] != parent[element.j])) {
                //  if(connection[element.i]>0 || connection[element.j]>0){ 
                degrees[element.i]++;
                degrees[element.j]++;

                tour.add(new Edge(element.i, element.j));
//        tour[edgeCount][0] = element.i;
//        tour[edgeCount][1] = element.j;
                edgeCount++;
                //   System.out.println(element);
                if (parent[element.i] < parent[element.j]) {
                    int prev = parent[element.j];
                    parent[element.j] = parent[element.i];

                    for (int it = 0; it < NUM_OF_POINTS; it++) {
                        if (it != element.i) {
                            if (parent[it] == prev) {
                                parent[it] = parent[element.i];
                            }
                        }
                    }

                } else {
                    int prev = parent[element.i];
                    parent[element.i] = parent[element.j];

                    for (int it = 0; it < NUM_OF_POINTS; it++) {
                        if (it != element.j) {
                            if (parent[it] == prev) {
                                parent[it] = parent[element.j];
                            }
                        }
                    }
                    //   parent[parent[element.i]] = parent[element.j];

                }
                if (degrees[element.i] == 2) {
                    visitedMap.put(points[element.i], Boolean.TRUE);
                    count--;
                }
                if (degrees[element.j] == 2) {
                    visitedMap.put(points[element.j], Boolean.TRUE);
                    count--;
                }
            }
        }
        for (int i = 0; i < NUM_OF_POINTS; i++) {
            if (visitedMap.get(points[i]) == false) {
                if (hubIdx < i) {
                    tour.add(new Edge(hubIdx, i));
                } else {
                    tour.add(new Edge(i, hubIdx));
                }

//            tour[edgeCount][0] = hubIdx;
//            tour[edgeCount][1] = i;
                edgeCount++;
                //  System.out.print(i+"  ");
            }
        }
        Collections.sort(tour, new EdgeComparator());
        //   System.out.println("printing edges");
        for (int i = 0; i < tour.size(); i++) {

            //   System.out.println(tour.get(i));
        }
        int lastNode = tour.get(0).y;
        int firstNode = tour.get(0).x;
        int start = tour.get(0).x;
        boolean[] vis = new boolean[NUM_OF_POINTS];
        for (int i = 0; i < NUM_OF_POINTS; i++) {
            vis[i] = false;
        }
        OptimalTour.add(points[firstNode]);
        vis[firstNode] = true;
        for (int i = 0; i < NUM_OF_POINTS; i++) {
            for (int j = 0; j < tour.size(); j++) {
                if (i != j) {
                    if (start == tour.get(j).x && tour.get(j).y != lastNode && (vis[tour.get(j).x] == false || vis[tour.get(j).y] == false)) {

                        OptimalTour.add(points[tour.get(j).y]);
                        start = tour.get(j).y;
                        vis[tour.get(j).y] = true;
                        //  printOptimalTour();
                        break;
                    }
                    if (start == tour.get(j).y && tour.get(j).x != lastNode && (vis[tour.get(j).x] == false || vis[tour.get(j).y] == false)) {
                        OptimalTour.add(points[tour.get(j).x]);
                        start = tour.get(j).x;
                        vis[tour.get(j).x] = true;
                        //     printOptimalTour();

                        break;
                    }

                }
            }
        }
        OptimalTour.add(points[lastNode]);

    }

    public void SavingsHeuristic_Randomized() {
        int hubIdx = Main.START;

        ArrayList<SavingsElement> savingsElements = new ArrayList<>();
        for (int i = 0; i < NUM_OF_POINTS - 1; i++) {
            for (int j = i + 1; j < NUM_OF_POINTS; j++) {
                if (i != hubIdx && j != hubIdx) {
                    double savingValue = points[i].getDistance(points[hubIdx]) + points[j].getDistance(points[hubIdx]) - points[i].getDistance(points[j]);
                    SavingsElement savingElement = new SavingsElement(i, j, savingValue);
                    savingsElements.add(savingElement);
                }

            }
        }
        Collections.sort(savingsElements, new ElementComparator()); // sort in ascending order
        Collections.reverse(savingsElements); // but we need descending order
//System.out.println(savingsElements.size());
        visitedMap.put(points[hubIdx], true);
        int k = 0;
        int count = NUM_OF_POINTS - 1;
        int[] parent = new int[NUM_OF_POINTS];
        int[] degrees = new int[NUM_OF_POINTS];
        for (int i = 0; i < NUM_OF_POINTS; i++) {
            parent[i] = i;
            degrees[i] = 0;
        }

        List<Edge> tour = new ArrayList<>();

        int edgeCount = 0;
        k = 0;
        Random rand = new Random();
        while (count > 2) {
            //  System.out.println(count);
            SavingsElement element;
            if (savingsElements.size() > 5) {

                int n = rand.nextInt(4) + 0;
                element = savingsElements.get(n);
                savingsElements.remove(n);
                //     System.out.println(n + "//element);

            } else if (savingsElements.size() <= 5 && savingsElements.size() > 1) {

                int n = rand.nextInt(savingsElements.size() - 1) + 0;
                element = savingsElements.get(n);
                savingsElements.remove(n);
                //  savingsElements.remove(element);
            } else {

                element = savingsElements.get(0);
                savingsElements.remove(0);
            }

            k++;
            //    System.out.println(element);
            if (visitedMap.get(points[element.i]) == false && visitedMap.get(points[element.j]) == false && (parent[element.i] != parent[element.j])) {

                //  if(connection[element.i]>0 || connection[element.j]>0){ 
                degrees[element.i]++;
                degrees[element.j]++;

                tour.add(new Edge(element.i, element.j));
//        tour[edgeCount][0] = element.i;
//        tour[edgeCount][1] = element.j;
                edgeCount++;
                //   System.out.println(element);
                if (parent[element.i] < parent[element.j]) {
                    int prev = parent[element.j];
                    parent[element.j] = parent[element.i];

                    for (int it = 0; it < NUM_OF_POINTS; it++) {
                        if (it != element.i) {
                            if (parent[it] == prev) {
                                parent[it] = parent[element.i];
                            }
                        }
                    }

                } else {
                    int prev = parent[element.i];
                    parent[element.i] = parent[element.j];

                    for (int it = 0; it < NUM_OF_POINTS; it++) {
                        if (it != element.j) {
                            if (parent[it] == prev) {
                                parent[it] = parent[element.j];
                            }
                        }
                    }
                    //   parent[parent[element.i]] = parent[element.j];

                }
                if (degrees[element.i] == 2) {
                    visitedMap.put(points[element.i], Boolean.TRUE);
                    count--;
                }
                if (degrees[element.j] == 2) {
                    visitedMap.put(points[element.j], Boolean.TRUE);
                    count--;
                }
            }
        }
        for (int i = 0; i < NUM_OF_POINTS; i++) {
            if (visitedMap.get(points[i]) == false) {
                if (hubIdx < i) {
                    tour.add(new Edge(hubIdx, i));
                } else {
                    tour.add(new Edge(i, hubIdx));
                }

//            tour[edgeCount][0] = hubIdx;
//            tour[edgeCount][1] = i;
                edgeCount++;
                //  System.out.print(i+"  ");
            }
        }
        Collections.sort(tour, new EdgeComparator());
        //   System.out.println("printing edges");
        for (int i = 0; i < tour.size(); i++) {

            //   System.out.println(tour.get(i));
        }
        int lastNode = tour.get(0).y;
        int firstNode = tour.get(0).x;
        int start = tour.get(0).x;
        boolean[] vis = new boolean[NUM_OF_POINTS];
        for (int i = 0; i < NUM_OF_POINTS; i++) {
            vis[i] = false;
        }
        OptimalTour.add(points[firstNode]);
        vis[firstNode] = true;
        for (int i = 0; i < NUM_OF_POINTS; i++) {
            for (int j = 0; j < tour.size(); j++) {
                if (i != j) {
                    if (start == tour.get(j).x && tour.get(j).y != lastNode && (vis[tour.get(j).x] == false || vis[tour.get(j).y] == false)) {

                        OptimalTour.add(points[tour.get(j).y]);
                        start = tour.get(j).y;
                        vis[tour.get(j).y] = true;
                        //  printOptimalTour();
                        break;
                    }
                    if (start == tour.get(j).y && tour.get(j).x != lastNode && (vis[tour.get(j).x] == false || vis[tour.get(j).y] == false)) {
                        OptimalTour.add(points[tour.get(j).x]);
                        start = tour.get(j).x;
                        vis[tour.get(j).x] = true;
                        //     printOptimalTour();

                        break;
                    }

                }
            }
        }
        OptimalTour.add(points[lastNode]);

    }

}

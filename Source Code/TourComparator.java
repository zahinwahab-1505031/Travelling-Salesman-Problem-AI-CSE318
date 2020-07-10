/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelling_salesman_problem;

import java.util.Comparator;
import java.util.Vector;

/**
 *
 * @author gahab
 */

class TourComparator implements Comparator<Vector <Point>>{ 
              
            // Overriding compare()method of Comparator  
                        // for descending order of cgpa 
            @Override
            public int compare(Vector<Point> n1, Vector<Point> n2) { 
                if (Graph.returnCost(n1)>Graph.returnCost(n1)) return 1;
                else   if (Graph.returnCost(n1)>Graph.returnCost(n1)) return -1;
                else return 0;
            }
        }
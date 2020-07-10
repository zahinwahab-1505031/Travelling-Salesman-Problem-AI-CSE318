/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelling_salesman_problem;

import java.util.Comparator;

/**
 *
 * @author Zahin
 */

class PointComparator implements Comparator<Point>{ 
              
            // Overriding compare()method of Comparator  
                        // for descending order of cgpa 
            @Override
            public int compare(Point n1, Point n2) { 
                if (n1.x>n2.x) return 1;
                else if (n1.x < n2.x)return -1;
                else return 0;
            }
        }
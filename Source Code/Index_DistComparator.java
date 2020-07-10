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

class Index_DistComparator implements Comparator<Index_Dist>{ 
              
            // Overriding compare()method of Comparator  
                        // for descending order of cgpa 
            @Override
            public int compare(Index_Dist n1, Index_Dist n2) { 
                if (n1.y>n2.y) return 1;
                else if (n1.y < n2.y)return -1;
                else return 0;
            }
        }
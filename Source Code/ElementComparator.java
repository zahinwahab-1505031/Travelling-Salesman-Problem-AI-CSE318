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

class ElementComparator implements Comparator<SavingsElement>{ 
              
            // Overriding compare()method of Comparator  
                        // for descending order of cgpa 
            @Override
            public int compare(SavingsElement n1, SavingsElement n2) { 
                if (n1.val>n2.val) return 1;
                else if (n1.val < n2.val)return -1;
                else return 0;
            }
        }
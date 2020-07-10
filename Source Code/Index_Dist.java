/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelling_salesman_problem;

/**
 *
 * @author Zahin
 */
public class Index_Dist {
    int x;
    double y;

    public Index_Dist(int x, double y) {
        this.x = x;
        this.y = y;
    }
     @Override
    public int hashCode() {
        double hash = 3;
        hash = 53 * hash + x;
        hash = 53 * hash + y;
        return (int)hash;
    }
     public boolean equals(Object o){
        if(o==null) return false;
        else if(!(o instanceof Index_Dist)) return false;
        Index_Dist st = (Index_Dist) o;
        if(this.x == st.x && this.y==st.y) return true;
        return false;
      
    }
     public Index_Dist myclone(){
         return new Index_Dist(this.x,this.y);
     }
     public String toString(){
         return "("+x+","+y+")";
     }
  
}


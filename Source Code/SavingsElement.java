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
public class SavingsElement {
    int i;
    int j;
    double val;
    public SavingsElement(int i, int j,double val) {
        this.i = i;
        this.j = j;
        this.val = val;
    }
     @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + i;
        hash = 53 * hash + j;
        hash = 53 * hash + (int)val;
        return hash;
    }
     public boolean equals(Object o){
        if(o==null) return false;
        else if(!(o instanceof Point)) return false;
        SavingsElement st = (SavingsElement) o;
        if(this.i == st.i && this.j==st.j && this.val==st.val) return true;
        return false;
      
    }
     public Point myclone(){
         return new Point(this.i,this.j);
     }
     public String toString(){
         return "("+i+","+j+","+val+")";
     }
     
}

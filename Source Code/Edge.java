/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package travelling_salesman_problem;

/**
 *
 * @author gahab
 */
public class Edge {
      int x;
    int y;

    public Edge(int x, int y) {
        this.x = x;
        this.y = y;
    }
     @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + x;
        hash = 53 * hash + y;
        return (int)hash;
    }
     public boolean equals(Object o){
        if(o==null) return false;
        else if(!(o instanceof Edge)) return false;
        Edge st = (Edge) o;
        if(this.x == st.x && this.y==st.y) return true;
        return false;
      
    }
     public Edge myclone(){
         return new Edge(this.x,this.y);
     }
     public String toString(){
         return "("+x+","+y+")";
     }
}

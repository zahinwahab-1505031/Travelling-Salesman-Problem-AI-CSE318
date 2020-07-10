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
public class Point {

    double x;
    double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        double hash = 3;
        hash = 53 * hash + x;
        hash = 53 * hash + y;
        return (int) hash;
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (!(o instanceof Point)) {
            return false;
        }
        Point st = (Point) o;
        if (this.x == st.x && this.y == st.y) {
            return true;
        }
        return false;

    }

    public Point myclone() {
        return new Point(this.x, this.y);
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public double getDistanceBetween(double lat1, double lon1, double lat2, double lon2) {
        double EARTH_RADIUS = 6371;
        double dLat = toRadians(lat2 - lat1);
        double dLon = toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(toRadians(lat1)) * Math.cos(toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = EARTH_RADIUS * c;

        return d;
    }

    public double toRadians(double degrees) {
        return degrees * (Math.PI / 180);
    }

    public double getDistance(Point p) {
        //System.out.println("INSIDE getDist, Main.et = " + Main.edgeType);
        if (Main.edgeType == 0) {

            return Math.sqrt(((this.x - p.x) * (this.x - p.x)) + ((this.y - p.y) * (this.y - p.y)));
        } else {

          //  System.out.println("hereeee");
            return getDistanceBetween(this.x, this.y, p.x, p.y);

        }
    }
}

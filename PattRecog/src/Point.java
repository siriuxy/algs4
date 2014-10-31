/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER= new CompareBySlope();       // YOUR DEFINITION HERE
    private class CompareBySlope implements  Comparator<Point>{
        public int compare(Point p, Point q) {
            if (Point.this.slopeTo(p)<Point.this.slopeTo(q)) return -1;//refer from inner class: add outer class's name
            if (Point.this.slopeTo(p)>Point.this.slopeTo(q)) return 1;
            if (Point.this.slopeTo(p)==Point.this.slopeTo(q)) return 0;
            throw new NoSuchFieldError("unexpected comparison1");//need a better exception.
        }
    }
    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        if (this.y==that.y) return +0.0;
        if (this.x==that.x) return Double.POSITIVE_INFINITY;
        return (this.x-that.x)*1.0/(this.y-that.y); //implicit cast to double
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if (this.y<that.y) return -1;
        if (this.y>that.y) return 1;
        if (this.y==that.y){
            if (this.x<that.x) return -1;
            if (this.x>that.x) return 1;
            if (this.x==that.x) return 0;
        }
        throw new NoSuchFieldError("unexpected comparison");
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}
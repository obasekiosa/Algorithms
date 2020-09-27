/* *****************************************************************************
 *  Name:              Osakpolor Obaseki
 *  Last modified:     3/8/2020
 *
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  NOTE: Only valid for points with coordinate ranges
 *  of typical UNSIGNED INTS 0 - 32767.
 **************************************************************************** */

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // public static int ccw(Point a, Point b, Point c) {
    //     // area > 0 ccw, area < 0 cw, area = 0 collinear;
    //     double area = (b.x - a.x) * (c.y - a.y) -
    //             (b.y - a.y) * (c.x - a.x);
    //
    //     if (area < 0) return -1;
    //     else if (area > 0) return +1;
    //     else return 0;
    // }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        double dy = this.y - that.y;
        double dx = this.x - that.x;

        // special convinient case for slope of a point
        if (dy == 0 && dx == 0) return Double.NEGATIVE_INFINITY;
            // horizontal line
        else if (dy == 0) return 0.0;
            // special convinient case for slope of a vertical line
        else if (dx == 0) return Double.POSITIVE_INFINITY;
            // typical slope
        else return dy / dx;
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     * point (x0 = x1 and y0 = y1);
     * a negative integer if this point is less than the argument
     * point; and a positive integer if this point is greater than the
     * argument point
     */
    public int compareTo(Point that) {
        int dy = this.y - that.y;
        int dx = this.x - that.x;
        if (dy != 0) return dy;
        else return dx;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        Comparator<Point> pointComparator = new Comparator<Point>() {
            public int compare(Point p1, Point p2) {
                double s1 = slopeTo(p1);
                double s2 = slopeTo(p2);

                double result = s1 - s2;

                if (result < 0) return -1;
                else if (result > 0) return 1;
                else return 0;
            }
        };

        return pointComparator;
    }

    // public Comparator<Point> BY_POLAR_ORDER = new Comparator<Point>() {
    //     public int compare(Point p1, Point p2) {
    //         int dy1 = y - p1.y;
    //         int dx1 = x - p1.x;
    //         int dy2 = y - p2.y;
    //         int dx2 = x - p2.x;
    //
    //         double s1 = Math.atan2(dy1, dx1);
    //         double s2 = Math.atan2(dy2, dx2);
    //
    //         double result = s1 - s2;
    //
    //         if (result < 0) return -1;
    //         else if (result > 0) return 1;
    //         else return 0;
    //     }
    // };


    /**
     * Returns a string representation of this point.
     *
     * @return a string representation of this point
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {

    }
}

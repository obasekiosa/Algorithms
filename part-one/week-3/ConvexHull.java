/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class ConvexHull {

    Stack<Point> hullPoints;
    int numOfPoints;

    public ConvexHull(Point[] points) {

        if (points == null) throw new NullPointerException();
        hullPoints = new Stack<>();

        if (points.length < 3) {
            for (Point p : points) {
                hullPoints.push(p);
            }
        }
        else {
            Arrays.sort(points);
            Point minY = points[0];

            Arrays.sort(points, minY.BY_POLAR_ORDER);

            hullPoints.push(minY);
            hullPoints.push(points[0]);

            for (int i = 1; i < points.length - 1; i++) {
                Point top = hullPoints.pop();
                while (Point.ccw(hullPoints.peek(), top, points[i]) <= 0) {
                    top = hullPoints.pop();
                }
                hullPoints.push(top);
                hullPoints.push(points[i]);
            }
        }

    }

    public LineSegment[] segments() {
        if (hullPoints.isEmpty()) return null;

        LineSegment[] lines = new LineSegment[hullPoints.size()];

        Point p0 = hullPoints.pop();
        Point start = p0;
        int i = 0;
        for (Point p : hullPoints) {
            lines[i++] = new LineSegment(p0, p);
            p0 = p;
        }
        lines[i++] = new LineSegment(p0, start);

        return lines;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
            StdOut.println(x + " " + y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenRadius(0.01);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        ConvexHull hull = new ConvexHull(points);
        for (LineSegment segment : hull.segments()) {
            StdOut.println(segment);
            segment.draw();
            StdDraw.pause(2000);
            StdDraw.show();
        }
        StdDraw.show();
        StdOut.println("Done");

    }
}

/* *****************************************************************************
 *  Name:              Osakpolor Obaseki
 *  Last modified:     3/8/2020
 *
 *  Compilation:  javac FastCollinearPoints.java
 *  Execution:    java FastCollinearPoints inputFile.txt
 *  Dependencies: Point.java, LineSegment.java
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {

    private int numberOfLineSegments;
    private LineSegment[] segmentsFound;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {

        if (points == null) throw new IllegalArgumentException();

        for (int i = 0; i < points.length; i++)
            if (points[i] == null) throw new IllegalArgumentException();


        Arrays.sort(points);
        for (int i = 0; i < points.length - 1; i++)
            if (points[i].compareTo(points[i + 1]) == 0) throw new IllegalArgumentException();


        double[] slopes = new double[points.length];

        // Todo: optimize : if there are N points there are at most N/4 4-point min line segments
        LineSegment[] temp = new LineSegment[points.length];
        numberOfLineSegments = 0;

        for (int i = 0; i < points.length; i++) {

            for (int j = 0; j < points.length; j++) {
                slopes[j] = points[i].slopeTo(points[j]);
            }

            Arrays.sort(slopes);
            Arrays.sort(points, points[i].slopeOrder());

            Point start = points[0], end = points[0], current;
            int count = 0;
            // first point will always be the origin point (sepecial case of NEGATIVE INFINITY)
            for (int j = 1; j < points.length - 1; j++) {
                current = points[j];
                count++;
                if (start.compareTo(current) > 0) {
                    start = current;
                }
                else if (end.compareTo(current) < 0) {
                    end = current;
                }

                if (slopes[j] != slopes[j + 1]) {
                    if (count >= 3) {
                        temp[numberOfLineSegments++] = new LineSegment(start, end);

                    }
                    count = 0;
                    end = points[0];
                    start = points[0];
                }

            }

            if (start.compareTo(points[0]) != 0
                    && end.compareTo(points[0]) != 0) {

                if (start.compareTo(points[points.length - 1]) > 0) {
                    start = points[points.length - 1];
                }
                else if (end.compareTo(points[points.length - 1]) < 0) {
                    end = points[points.length - 1];
                }

                temp[numberOfLineSegments++] = new LineSegment(start, end);
            }
        }

        segmentsFound = new LineSegment[numberOfLineSegments];
        for (int i = 0; i < numberOfLineSegments; i++) {
            segmentsFound[i] = temp[i];
        }

    }

    // the number of line segments
    public int numberOfSegments() {
        return this.numberOfLineSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        if (this.segmentsFound.length == 0) return this.segmentsFound;
        LineSegment[] temp = new LineSegment[this.segmentsFound.length];
        int k = 0;

        for (int i = 0; i < this.segmentsFound.length; i++) {
            if (isUnique(temp, k, this.segmentsFound[i])) {
                temp[k++] = this.segmentsFound[i];
            }
        }
        LineSegment[] result = new LineSegment[k];
        for (int i = 0; i < k; i++) {
            result[i] = temp[i];
        }

        return result;
    }

    private boolean isUnique(LineSegment[] s, int len, LineSegment l) {
        for (int i = 0; i < len; i++) {
            if (l.toString().equals(s[i].toString())) {
                return false;
            }
        }
        return true;
    }


    /**
     * Unit tests the FastCollinearPoints method.
     */
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
        // StdOut.println("Done");

    }
}

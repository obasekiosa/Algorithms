/* *****************************************************************************
 *  Name:              Osakpolor Obaseki
 *  Last modified:     3/8/2020
 *
 *  Compilation:  javac BruteCollinearPoints.java
 *  Execution:    java BruteCollinearPoints inputFile.txt
 *  Dependencies: Point.java, LineSegment.java
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {

    private int numberOfLineSegments;
    private LineSegment[] segmentsFound;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        for (int i = 0; i < points.length; i++)
            if (points[i] == null) throw new IllegalArgumentException();

        int length = points.length;

        segmentsFound = new LineSegment[length];
        numberOfLineSegments = 0;
        Point[] newPoints = new Point[points.length];
        for (int i = 0; i < points.length; i++)
            newPoints[i] = points[i];

        Arrays.sort(newPoints);

        for (int i = 0; i < points.length - 1; i++)
            if (points[i].compareTo(points[i + 1]) == 0) throw new IllegalArgumentException();

        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                for (int k = j + 1; k < length; k++) {
                    for (int l = k + 1; l < length; l++) {
                        if (points[i].slopeOrder().compare(points[j], points[k]) == 0 &&
                                points[j].slopeOrder().compare(points[k], points[l]) == 0) {
                            segmentsFound[numberOfLineSegments++] = new LineSegment(points[i],
                                                                                    points[l]);

                        }
                    }
                }
            }
        }

        LineSegment[] temp = new LineSegment[numberOfLineSegments];
        for (int i = 0; i < numberOfLineSegments; i++) {
            temp[i] = segmentsFound[i];
        }
        segmentsFound = temp;

    }

    // the number of line segments
    public int numberOfSegments() {
        return numberOfLineSegments;
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

    private boolean isUnique(LineSegment[] s, int len, LineSegment lineSegment) {
        for (int i = 0; i < len; i++) {
            if (lineSegment.toString().equals(s[i].toString())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Unit tests the BruteForceCollinearPoints method.
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

    }
}

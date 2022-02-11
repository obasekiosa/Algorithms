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

    private final int numberOfLineSegments;
    private final LineSegment[] segmentsFound;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();

        for (int i = 0; i < points.length; i++)
            if (points[i] == null) throw new IllegalArgumentException();

        Point[] tmpPoints = new Point[points.length];
        for (int i = 0; i < points.length; i++)
            tmpPoints[i] = points[i];

        Arrays.sort(tmpPoints);

        LineSegment[] tmpLineSegment = new LineSegment[points.length];
        int segmentCount = 0;

        for (int i = 0; i < tmpPoints.length - 1; i++)
            if (tmpPoints[i].compareTo(tmpPoints[i + 1]) == 0) throw new IllegalArgumentException();

        for (int i = 0; i < tmpPoints.length; i++) {
            for (int j = i + 1; j < tmpPoints.length; j++) {
                for (int k = j + 1; k < tmpPoints.length; k++) {
                    if (tmpPoints[i].slopeOrder().compare(tmpPoints[j], tmpPoints[k]) == 0) {
                        for (int m = k + 1; m < tmpPoints.length; m++) {
                            if (tmpPoints[i].slopeOrder().compare(tmpPoints[j], tmpPoints[m])
                                    == 0) {
                                tmpLineSegment[segmentCount++] = new LineSegment(tmpPoints[i],
                                                                                 tmpPoints[m]);
                            }
                        }
                    }
                }
            }
        }


        this.segmentsFound = new LineSegment[segmentCount];
        this.numberOfLineSegments = segmentCount;
        for (int i = 0; i < segmentCount; i++) {
            this.segmentsFound[i] = tmpLineSegment[i];
        }

    }

    // the number of line segments
    public int numberOfSegments() {
        return this.numberOfLineSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] result = new LineSegment[this.numberOfLineSegments];
        for (int i = 0; i < this.numberOfLineSegments; i++) {
            result[i] = this.segmentsFound[i];
        }
        return result;
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

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

    private final int numberOfLineSegments;
    private final LineSegment[] segmentsFound;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();

        Point[] tmpPoints = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
            tmpPoints[i] = points[i];
        }

        Arrays.sort(tmpPoints);
        for (int i = 0; i < tmpPoints.length - 1; i++)
            if (tmpPoints[i].compareTo(tmpPoints[i + 1]) == 0) throw new IllegalArgumentException();

        LineSegment[] tempLineSegments = new LineSegment[points.length];
        int segmentCount = 0;


        for (int i = 0; i < points.length; i++) {

            Arrays.sort(tmpPoints, points[i].slopeOrder());
            // first point will always be part of any line found
            Point lineHighestPoint = tmpPoints[0];
            Point lineLowestPoint = tmpPoints[0];
            int pointCount = 1;
            boolean done = false;

            for (int j = 1; j < tmpPoints.length - 1; j++) {
                Point currentPoint = tmpPoints[j];
                if (tmpPoints[0].slopeOrder().compare(tmpPoints[j], tmpPoints[j + 1]) == 0) {
                    // check current point is less than lowest point
                    if (currentPoint.compareTo(lineLowestPoint) < 0) {
                        lineLowestPoint = currentPoint;
                    }
                    else if (currentPoint.compareTo(lineHighestPoint) > 0) {
                        // check current point is greater than highest point
                        lineHighestPoint = currentPoint;
                    }
                    pointCount++;

                    if (j + 1 == points.length - 1) {

                        currentPoint = tmpPoints[j + 1];
                        if (currentPoint.compareTo(lineLowestPoint) < 0) {
                            lineLowestPoint = currentPoint;
                        }
                        else if (currentPoint.compareTo(lineHighestPoint) > 0) {
                            // check current point is greater than highest point
                            lineHighestPoint = currentPoint;
                        }
                        pointCount++;
                        done = true;
                    }
                }
                else {
                    // check current point is less than lowest point
                    if (currentPoint.compareTo(lineLowestPoint) < 0) {
                        lineLowestPoint = currentPoint;
                    }
                    else if (currentPoint.compareTo(lineHighestPoint) > 0) {
                        // check current point is greater than highest point
                        lineHighestPoint = currentPoint;
                    }
                    pointCount++;
                    done = true;
                }

                if (done) {
                    if (pointCount >= 4) {
                        if (tmpPoints[0].compareTo(lineLowestPoint) == 0) {

                            if (segmentCount == tempLineSegments.length - 1) {
                                LineSegment[] tmp = new LineSegment[tempLineSegments.length * 2];
                                for (int index = 0; index < tempLineSegments.length; index++) {
                                    tmp[index] = tempLineSegments[index];
                                }
                                tempLineSegments = tmp;
                            }
                            tempLineSegments[segmentCount++] = new LineSegment(
                                    lineLowestPoint, lineHighestPoint);
                        }
                    }
                    done = false;
                    pointCount = 1;
                    // first point will always be part of any line found
                    lineHighestPoint = tmpPoints[0];
                    lineLowestPoint = tmpPoints[0];
                }
            }
        }

        this.numberOfLineSegments = segmentCount;
        this.segmentsFound = new LineSegment[this.numberOfLineSegments];
        for (int i = 0; i < this.numberOfLineSegments; i++) {
            this.segmentsFound[i] = tempLineSegments[i];
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

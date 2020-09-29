/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import java.util.Arrays;

public class Hold {
    public static void main(String[] args) {

    }

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();

        Point[] tmpPoints = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
            tmpPoints[i] = points[i];
        }


        Arrays.sort(tmpPoints);
        for (int i = 0; i < points.length - 1; i++)
            if (tmpPoints[i].compareTo(tmpPoints[i + 1]) == 0) throw new IllegalArgumentException();


        double[] slopes = new double[points.length];

        LineSegment[] temp = new LineSegment[points.length];
        int segmentCount = 0;

        for (int i = 0; i < points.length; i++) {

            for (int j = 0; j < points.length; j++) {
                slopes[j] = points[i].slopeTo(tmpPoints[j]);
            }

            Arrays.sort(slopes);
            Arrays.sort(tmpPoints, points[i].slopeOrder());

            Point start = points[0], end = points[0], current;
            int count = 0;
            // first point will always be the origin point (sepecial case of NEGATIVE INFINITY)
            for (int j = 1; j < points.length - 1; j++) {
                current = tmpPoints[j];
                count++;
                if (start.compareTo(current) > 0) {
                    start = current;
                }
                else if (end.compareTo(current) < 0) {
                    end = current;
                }

                if (slopes[j] != slopes[j + 1]) {
                    if (count >= 3) {
                        if (start.compareTo(tmpPoints[0]) == 0) {
                            if (segmentCount == temp.length - 1) {
                                LineSegment[] tmp = new LineSegment[temp.length * 2];
                                for (int index = 0; index < temp.length; index++) {
                                    tmp[index] = temp[index];
                                }
                                temp = tmp;
                            }
                            temp[segmentCount++] = new LineSegment(start, end);
                        }

                    }
                    count = 0;
                    end = tmpPoints[0];
                    start = tmpPoints[0];
                }

            }

            if (start.compareTo(tmpPoints[0]) != 0
                    && end.compareTo(tmpPoints[0]) != 0) {

                if (start.compareTo(tmpPoints[tmpPoints.length - 1]) > 0) {
                    start = tmpPoints[tmpPoints.length - 1];
                }
                else if (end.compareTo(tmpPoints[tmpPoints.length - 1]) < 0) {
                    end = tmpPoints[tmpPoints.length - 1];
                }

                if (count >= 3) {
                    if (start.compareTo(tmpPoints[0]) == 0) {
                        if (segmentCount == temp.length - 1) {
                            LineSegment[] tmp = new LineSegment[temp.length * 2];
                            for (int index = 0; index < temp.length; index++) {
                                tmp[index] = temp[index];
                            }
                            temp = tmp;
                        }
                        temp[segmentCount++] = new LineSegment(start, end);
                    }

                }
            }
        }

        segmentsFound = new LineSegment[segmentCount];
        this.numberOfLineSegments = segmentCount;
        for (int i = 0; i < numberOfLineSegments; i++) {
            segmentsFound[i] = temp[i];
        }

    }


    {


    }
}

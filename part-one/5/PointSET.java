/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.TreeSet;

// import edu.princeton.cs.algs4.StdDraw;

public class PointSET {

    private final TreeSet<Point2D> set;

    // construct an empty set of points
    public PointSET() {
        set = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return set.isEmpty();
    }

    // number of points in the set
    public int size() {
        return set.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        set.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        return set.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        // StdDraw.enableDoubleBuffering();
        for (Point2D p : set) {
            p.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();

        Bag<Point2D> bag = new Bag<>();

        for (Point2D point : set) {
            if (rect.contains(point)) {
                bag.add(point);
            }
        }

        return bag;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        // Point2D floor = set.floor(p);
        // Point2D ceil = set.ceiling(p);
        // if (floor == null) floor = p;
        // if (ceil == null) ceil = p;
        //
        // Point2D best = null;
        // double bestDistance = Double.POSITIVE_INFINITY;
        //
        // for (Point2D point : set.subSet(floor, ceil)) {
        //     double distance = p.distanceSquaredTo(point);
        //     if (best == null) {
        //         best = point;
        //         bestDistance = distance;
        //     }
        //     else if (distance < bestDistance) {
        //         best = point;
        //         bestDistance = distance;
        //     }
        // }
        //
        // return best;


        Point2D best = null;
        for (Point2D point : set) {
            if (best == null) best = point;
            else if (p.distanceToOrder().compare(point, best) < 0) best = point;
        }

        return best;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}

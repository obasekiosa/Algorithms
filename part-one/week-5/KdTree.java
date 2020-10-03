/* *****************************************************************************
 *  Name:              Osakpolor Obaseki
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

    private Node root;


    // BST keys are in the content of value property
    private class Node {
        private Point2D value;
        private Node left, right;
        private int size;

        public Node(Point2D value) {
            this.value = value;
            this.size = 1;
        }
    }

    // construct an empty set of points
    public KdTree() {
    }

    // is the set empty?
    public boolean isEmpty() {
        return this.root == null;
    }

    // number of points in the set
    public int size() {

        return size(root);
    }

    private int size(Node node) {
        if (node == null) return 0;

        return node.size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        this.root = put(this.root, p, 0);
    }

    private Node put(Node node, Point2D p, int level) {
        if (node == null) return new Node(p);

        int cmp;
        level %= 2;
        if (level == 0) cmp = Point2D.X_ORDER.compare(p, node.value);
        else cmp = Point2D.Y_ORDER.compare(p, node.value);

        if (cmp < 0) node.left = put(node.left, p, level + 1);
        else if (cmp > 0) node.right = put(node.right, p, level + 1);
        else {
            if (level == 0) {
                if (Point2D.Y_ORDER.compare(p, node.value) != 0)
                    node.right = put(node.right, p, level + 1); //left b4
                else node.value = p;

            }
            else {
                if (Point2D.X_ORDER.compare(p, node.value) != 0)
                    node.right = put(node.right, p, level + 1);
                else node.value = p;
            }
        }

        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }

    private Point2D get(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        return get(this.root, p, 0);
    }

    private Point2D get(Node node, Point2D p, int level) {
        if (node == null) return null;

        int cmp;
        level %= 2;
        if (level == 0) cmp = Point2D.X_ORDER.compare(p, node.value);
        else cmp = Point2D.Y_ORDER.compare(p, node.value);

        if (cmp < 0) return get(node.left, p, level + 1);
        else if (cmp > 0) return get(node.right, p, level + 1);
        else {
            if (level == 0) {
                if (Point2D.Y_ORDER.compare(p, node.value) != 0)
                    return get(node.right, p, level + 1); // left b4
                else return node.value;

            }
            else {
                if (Point2D.X_ORDER.compare(p, node.value) != 0)
                    return get(node.right, p, level + 1);
                else return node.value;
            }
        }

    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return get(p) != null;
    }

    // draw all points to standard draw
    public void draw() {
        draw(this.root, 0.0, 0.0, 1.0, 1.0, 0);
    }

    private void draw(Node node, Double xMin, Double yMin, Double xMax, Double yMax, int level) {
        if (node == null) return;


        double r = StdDraw.getPenRadius();

        level %= 2;
        if (level == 0) {
            double midX = node.value.x();
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(midX, yMin, midX, yMax);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.02);
            node.value.draw();
            StdDraw.setPenRadius(r);
            draw(node.left, xMin, yMin, midX, yMax, level + 1);
            draw(node.right, midX, yMin, xMax, yMax, level + 1);
        }
        else {

            double midY = node.value.y();
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(xMin, midY, xMax, midY);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.02);
            node.value.draw();
            StdDraw.setPenRadius(r);
            draw(node.left, xMin, yMin, xMax, midY, level + 1);
            draw(node.right, xMin, midY, xMax, yMax, level + 1);

        }

    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        Queue<Point2D> q = new Queue<>();
        RectHV rootRect = new RectHV(0.0, 0.0, 1.0, 1.0);
        search(this.root, rect, rootRect, q, 0);
        return q;

    }

    private void search(Node node, RectHV queryRect, RectHV nodeRect, Queue<Point2D> q, int level) {
        if (node == null) return;

        if (!nodeRect.intersects(queryRect)) return;

        if (queryRect.contains(node.value)) q.enqueue(node.value);

        level %= 2;
        if (level == 0) {
            double midX = node.value.x();
            RectHV leftRect = new RectHV(nodeRect.xmin(), nodeRect.ymin(), midX, nodeRect.ymax());
            RectHV rightRect = new RectHV(midX, nodeRect.ymin(), nodeRect.xmax(), nodeRect.ymax());
            search(node.left, queryRect, leftRect, q, level + 1);
            search(node.right, queryRect, rightRect, q, level + 1);
        }
        else {
            double midY = node.value.y();
            RectHV bottomRect = new RectHV(nodeRect.xmin(), nodeRect.ymin(), nodeRect.xmax(), midY);
            RectHV topRect = new RectHV(nodeRect.xmin(), midY, nodeRect.xmax(), nodeRect.ymax());
            search(node.left, queryRect, bottomRect, q, level + 1);
            search(node.right, queryRect, topRect, q, level + 1);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;

        RectHV rootRect = new RectHV(0.0, 0.0, 1.0, 1.0);
        return nearest(this.root, p, rootRect, 0, root.value);
    }

    private Point2D nearest(Node node, Point2D qPoint, RectHV nodeRect, int level, Point2D best) {

        if (node == null) return best;

        if (nodeRect.distanceSquaredTo(qPoint) > best.distanceSquaredTo(qPoint)) return best;

        if (node.value.distanceSquaredTo(qPoint) < best.distanceSquaredTo(qPoint))
            best = node.value;

        level %= 2;
        if (level == 0) {
            double midX = node.value.x();
            RectHV leftRect = new RectHV(nodeRect.xmin(), nodeRect.ymin(), midX, nodeRect.ymax());
            RectHV rightRect = new RectHV(midX, nodeRect.ymin(), nodeRect.xmax(), nodeRect.ymax());

            if (rightRect.contains(qPoint)) {
                best = nearest(node.right, qPoint, rightRect, level + 1, best);
                best = nearest(node.left, qPoint, leftRect, level + 1, best);

            }
            else {
                best = nearest(node.left, qPoint, leftRect, level + 1, best);
                best = nearest(node.right, qPoint, rightRect, level + 1, best);

            }
        }
        else {
            double midY = node.value.y();
            RectHV bottomRect = new RectHV(nodeRect.xmin(), nodeRect.ymin(), nodeRect.xmax(), midY);
            RectHV topRect = new RectHV(nodeRect.xmin(), midY, nodeRect.xmax(), nodeRect.ymax());
            if (topRect.contains(qPoint)) {
                best = nearest(node.right, qPoint, topRect, level + 1, best);
                best = nearest(node.left, qPoint, bottomRect, level + 1, best);
            }
            else {
                best = nearest(node.left, qPoint, bottomRect, level + 1, best);
                best = nearest(node.right, qPoint, topRect, level + 1, best);

            }
        }

        return best;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}

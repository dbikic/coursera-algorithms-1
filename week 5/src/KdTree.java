import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;

public class KdTree {

  private enum Orientation {
    HORIZONTAL, VERTICAL;
  }

  private static class Node {

    private Point2D point;
    private Node left, right;
    private final Orientation orientation;
    private int count;

    public Node(Point2D point, Node parent) {
      this.point = point;
      this.count = 1;
      if (parent == null) {
        this.orientation = Orientation.VERTICAL;
      } else {
        this.orientation = parent.getOppositeDirection();
      }
    }

    private Orientation getOppositeDirection() {
      if (orientation == Orientation.HORIZONTAL) {
        return Orientation.VERTICAL;
      } else {
        return Orientation.HORIZONTAL;
      }
    }

    public int compareWithPoint(Point2D that) {
      if (orientation == Orientation.VERTICAL) {
        if (point.x() == that.x()) {
          if (point.y() == that.y()) {
            return 0;
          } else {
            return -1;
          }
        } else {
          return Double.compare(point.x(), that.x());
        }
      } else {
        if (point.y() == that.y()) {
          if (point.x() == that.x()) {
            return 0;
          } else {
            return -1;
          }
        } else {
          return Double.compare(point.y(), that.y());
        }
      }
    }
  }

  private Node root;

  // construct an empty set of points
  public KdTree() {
    root = null;
  }

  // is the set empty?
  public boolean isEmpty() {
    return root == null;
  }

  // number of points in the set
  public int size() {
    return size(root);
  }

  private int size(Node x) {
    if (x == null) {
      return 0;
    }
    return x.count;
  }

  // add the point to the set (if it is not already in the set)
  public void insert(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }
    root = put(root, null, p);
    int i = 0;
  }

  private Node put(Node x, Node parentNode, Point2D p) {
    if (x == null) {
      return new Node(p, parentNode);
    }
    int cmp = x.compareWithPoint(p);
    if (cmp < 0) {
      x.left = put(x.left, x, p);
    } else if (cmp > 0) {
      x.right = put(x.right, x, p);
    } else {
      x.point = p;
    }
    x.count = 1 + size(x.left) + size(x.right);
    return x;
  }

  // does the set contain point p?
  public boolean contains(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }
    Node x = root;
    while (x != null) {
      int cmp = x.compareWithPoint(p);
      if (cmp < 0) {
        x = x.left;
      } else if (cmp > 0) {
        x = x.right;
      } else {
        return true;
      }
    }
    return false;
  }

  // draw all points to standard draw
  public void draw() {
    for (Node node : keys()) {
      StdDraw.setPenRadius(0.01);
      StdDraw.setPenColor(StdDraw.BLACK);
      StdDraw.point(node.point.x(), node.point.y());
    }
  }

  private Iterable<Node> keys() {
    Queue<Node> q = new Queue<>();
    inorder(root, q);
    return q;
  }

  private void inorder(Node x, Queue<Node> q) {
    if (x == null) {
      return;
    }
    inorder(x.left, q);
    q.enqueue(x);
    inorder(x.right, q);
  }

  // all points that are inside the rectangle (or on the boundary)
  public Iterable<Point2D> range(RectHV rect) {
    if (rect == null) {
      throw new IllegalArgumentException();
    }
    ArrayList<Point2D> range = new ArrayList<>();
    if (root != null) {
      rangeSearch(root, rect, range);
    }
    return range;
  }

  private void rangeSearch(Node node, RectHV rect, ArrayList<Point2D> range) {
    if (node == null) {
      return;
    }
    if (rect.contains(node.point)) {
      range.add(node.point);
    }
    rangeSearch(node.left, rect, range);
    rangeSearch(node.right, rect, range);
  }

  // a nearest neighbor in the set to point p; null if the set is empty
  public Point2D nearest(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }
    if (root == null) {
      return null;
    }
    nearestPoint = null;
    nearestPointDistance = Double.MAX_VALUE;
    nearestSearch(root, p);
    return nearestPoint;
  }

  private Point2D nearestPoint;
  private double nearestPointDistance;

  private void nearestSearch(Node x, Point2D p) {
    if (x == null) {
      return;
    }
    double distance = x.point.distanceSquaredTo(p);
    if (distance < nearestPointDistance) {
      nearestPoint = x.point;
      nearestPointDistance = distance;
    }
    double leftChildDistance = Double.POSITIVE_INFINITY;
    double rightChildDistance = Double.POSITIVE_INFINITY;
    if (x.left != null) {
      leftChildDistance = x.left.point.distanceSquaredTo(p);
    }
    if (x.right != null) {
      rightChildDistance = x.right.point.distanceSquaredTo(p);
    }
    if (leftChildDistance > rightChildDistance) {
      nearestSearch(x.right, p);
      nearestSearch(x.left, p);
    } else {
      nearestSearch(x.left, p);
      nearestSearch(x.right, p);
    }
  }
}
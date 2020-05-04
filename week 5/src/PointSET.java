import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
import java.util.TreeSet;

public class PointSET {

  private final TreeSet<Point2D> treeSet;

  // construct an empty set of points
  public PointSET() {
    treeSet = new TreeSet<>();
  }

  // is the set empty?
  public boolean isEmpty() {
    return treeSet.isEmpty();
  }

  // number of points in the set
  public int size() {
    return treeSet.size();
  }

  // add the point to the set (if it is not already in the set)
  public void insert(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }
    treeSet.add(p);
  }

  // does the set contain point p?
  public boolean contains(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }
    return treeSet.contains(p);
  }

  // draw all points to standard draw
  public void draw() {
    for (Point2D point2D : treeSet) {
      StdDraw.point(point2D.x(), point2D.y());
    }
  }

  // all points that are inside the rectangle (or on the boundary)
  public Iterable<Point2D> range(RectHV rect) {
    if (rect == null) {
      throw new IllegalArgumentException();
    }
    ArrayList<Point2D> range = new ArrayList<>();
    for (Point2D point2D : treeSet) {
      if (rect.contains(point2D)) {
        range.add(point2D);
      }
    }
    return range;
  }

  // a nearest neighbor in the set to point p; null if the set is empty
  public Point2D nearest(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }
    if (treeSet.isEmpty()) {
      return null;
    }
    Point2D champion = treeSet.first();
    for (Point2D point2D : treeSet) {
      if (point2D.distanceSquaredTo(p) < champion.distanceSquaredTo(p)) {
        champion = point2D;
      }
    }
    return champion;
  }
}

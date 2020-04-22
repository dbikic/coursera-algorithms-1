import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {

  private final ArrayList<LineSegment> segments = new ArrayList<>();

  // finds all line segments containing 4 points
  public BruteCollinearPoints(Point[] points) {
    validateInput(points);
    for (int i = 0; i < points.length; i++) {
      for (int j = i + 1; j < points.length; j++) {
        for (int k = j + 1; k < points.length; k++) {
          for (int m = k + 1; m < points.length; m++) {
            if (pointsCollinear(points[i], points[j], points[k], points[m])) {
              addNewSegment(points[i], points[j], points[k], points[m]);
            }
          }
        }
      }
    }
  }

  private void addNewSegment(Point p1, Point p2, Point p3, Point p4) {
    LineSegment lineSegment = getLineSegmentOfLongestLine(p1, p2, p3, p4);
    segments.add(lineSegment);
  }

  private boolean pointsCollinear(Point p1, Point p2, Point p3, Point p4) {
    double slope1 = p1.slopeTo(p2);
    double slope2 = p1.slopeTo(p3);
    double slope3 = p1.slopeTo(p4);
    return slope1 == slope2 && slope2 == slope3;
  }

  private LineSegment getLineSegmentOfLongestLine(Point p1, Point p2, Point p3, Point p4) {
    List<Point> pointsList = Arrays.asList(p1, p2, p3, p4);
    Object[] pointsArray = pointsList.toArray();
    Arrays.sort(pointsArray);
    return new LineSegment((Point) pointsArray[0], (Point) pointsArray[3]);
  }

  // the number of line segments
  public int numberOfSegments() {
    return segments.size();
  }

  // the line segments
  public LineSegment[] segments() {
    LineSegment[] result = new LineSegment[segments.size()];
    return segments.toArray(result);
  }

  private void validateInput(Point[] points) {
    if (points == null) {
      throw new IllegalArgumentException();
    }
    for (Point point : points) {
      if (point == null) {
        throw new IllegalArgumentException();
      }
    }
    for (int i = 0; i < points.length; i++) {
      for (int j = i + 1; j < points.length; j++) {
        if (points[i].compareTo(points[j]) == 0) {
          throw new IllegalArgumentException();
        }
      }
    }
  }
}
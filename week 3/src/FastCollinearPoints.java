import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

  private final ArrayList<LineSegment> segments = new ArrayList<>();

  // finds all line segments containing 4 points
  public FastCollinearPoints(Point[] points) {
    validateInput(points);
    int n = points.length;
    for (int i = 0; i < n; i++) {
      ArrayList<SlopeBetweenPoints> slopes = new ArrayList<>();
      for (int j = i + 1; j < n; j++) {
        slopes.add(new SlopeBetweenPoints(points[i], points[j]));
      }
      addCollinearSegments(slopes.toArray());
    }
  }

  private void addCollinearSegments(Object[] slopes) {
    if (slopes.length == 0) {
      return;
    }
    Arrays.sort(slopes);
    double comboSlope = ((SlopeBetweenPoints) slopes[0]).slope;
    int comboCount = 1;
    int minComboForASegment = 3;
    for (int i = 1; i < slopes.length; i++) {
      double currentSlope = ((SlopeBetweenPoints) slopes[i]).slope;
      if (currentSlope == comboSlope) {
        comboCount++;
      } else {
        if (comboCount >= minComboForASegment) {
          convertComboToLineSegment(Arrays.copyOfRange(slopes, i - comboCount, i));
        }
        comboSlope = currentSlope;
        comboCount = 1;
      }
    }
    if (comboCount >= minComboForASegment) {
      int n = slopes.length;
      int from = n - comboCount;
      if (from < 0) {
        from = 0;
      }
      convertComboToLineSegment(Arrays.copyOfRange(slopes, from, n));
    }
  }

  private void convertComboToLineSegment(Object[] slopes) {
    ArrayList<Point> points = new ArrayList<>();
    for (Object slope : slopes) {
      points.add(((SlopeBetweenPoints) slope).p);
      points.add(((SlopeBetweenPoints) slope).q);
    }
    Object[] pointsArray = points.toArray();
    Arrays.sort(pointsArray);
    Point minPoint = (Point) pointsArray[0];
    Point maxPoint = (Point) pointsArray[pointsArray.length - 1];
    segments.add(new LineSegment(minPoint, maxPoint));
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

  private static class SlopeBetweenPoints implements Comparable<SlopeBetweenPoints> {

    private final Point p;
    private final Point q;
    private final double slope;

    public SlopeBetweenPoints(Point p, Point q) {
      this.p = p;
      this.q = q;
      this.slope = p.slopeTo(q);
    }

    @Override
    public int compareTo(SlopeBetweenPoints that) {
      return Double.compare(this.slope, that.slope);
    }
  }
}
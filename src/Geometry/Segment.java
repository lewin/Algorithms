package Geometry;

import static Geometry.Utils.*;

/**
 * Representation of a Segment
 * 
 * @author Lewin
 *
 */
public class Segment {
  public Point a, b;
  public double length;

  public Segment(Point a, Point b) {
    this.a = a;
    this.b = b;
    length = distance(a, b);
  }

  /**
   * Returns shortest point distance
   * 
   * @param c point we are considering
   * @return shortest length from c to this segment
   */
  public double ptDist(Point c) {
    if (dot(a, b, c) > 0)
      return distance(b, c);
    if (dot(b, a, c) > 0)
      return distance(a, c);
    if (length == 0)
      return distance(a, c);
    return Math.abs(cross(a, b, c) / length);
  }

  /**
   * Returns a point that segment intersects with s
   * 
   * @param s the segment that we are testing for intersection
   * @return A wrapper containting a boolean and a point if boolean is true and point is null this
   *         and s have infinitely many intersections if boolean is true and point is not null this
   *         and s intersect exactly once at point if boolean is false (implies point is null) this
   *         and s do not intersect
   */
  public Wrapper segments_intersect(Segment s) {
    Line l1 = new Line(a, b), l2 = new Line(s.a, s.b);
    Point p = new Point(0, 0);

    if (l1.same_line(l2))
      return new Wrapper((point_in_box(a, s.a, s.b) || point_in_box(b, s.a, s.b)
          || point_in_box(s.a, a, b) || point_in_box(s.b, a, b)), null);

    if (l1.parallel(l2))
      return new Wrapper(false, null);

    p = l1.intersection_point(l2);

    return new Wrapper((point_in_box(p, a, b) && point_in_box(p, s.a, s.b)), p);
  }

  static class Wrapper {
    public boolean state;
    public Point p;

    public Wrapper(boolean _state, Point _p) {
      state = _state;
      p = _p;
    }
  }
}

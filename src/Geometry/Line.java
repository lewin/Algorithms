package Geometry;

import static Geometry.Utils.*;

/**
 * Representation of a Line
 * 
 * @author Lewin
 *
 */
public class Line {
  public double a, b, c;

  // ax + by = c

  /**
   * Standard representation
   */
  public Line(double a, double b, double c) {
    this.a = a;
    this.b = b;
    this.c = c;
  }

  /**
   * Unique line between two points
   */
  public Line(Point p1, Point p2) { // two points
    if (p1.x == p2.x) {
      a = 1;
      b = 0;
      c = -p1.x;
    } else {
      b = 1;
      a = -(p1.y - p2.y) / (p1.x - p2.x);
      c = -(a * p1.x) - (b * p1.y);
    }
  }

  /**
   * Point-slope formula
   */
  public Line(Point p, double m) {
    a = -m;
    b = 1;
    c = -((a * p.x) + (b * p.y));
  }

  // returns true if line l is parallel to this
  public boolean parallel(Line l) {
    return (Math.abs(a - l.a) <= EPS && Math.abs(b - l.b) <= EPS);
  }

  // tests if two lines describe the same object
  public boolean same_line(Line l) {
    return (parallel(l) && Math.abs(c - l.c) <= EPS);
  }

  // finds intersection point between lines
  // if no intersection found, returns null;
  // if same line, returns INF, INF point
  public Point intersection_point(Line l) {
    Point p = new Point(INF, INF);
    if (same_line(l))
      return p;
    if (parallel(l))
      return null;

    p.x = (l.b * c - b * l.c) / (l.a * b - a * l.b);
    if (Math.abs(b) > EPS)
      p.y = -(a * p.x + c) / b;
    else
      p.y = -(l.a * p.x + l.c) / l.b;
    return p;
  }

  // Finds the point closest to p lying on this line
  public Point closest_Point(Point p) {
    Point p_c = new Point(INF, INF);
    if (Math.abs(b) <= EPS) {
      p_c.x = -c;
      p_c.y = p.y;
      return p_c;
    }
    if (Math.abs(a) <= EPS) {
      p_c.x = p.x;
      p_c.y = -c;
      return p_c;
    }
    Line perp = new Line(p, 1 / a);
    return intersection_point(perp);
  }
}

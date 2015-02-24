package Geometry;

/**
 * Useful functions
 * 
 * @author Lewin
 * 
 */
public class Utils {
  public static final double EPS = 1e-9;
  public static final int INF = Integer.MAX_VALUE >> 2;

  // returns dot product of |a-b|*|b-c|
  public static double dot(Point a, Point b, Point c) {
    Point ab = new Point(b.x - a.x, b.y - a.y);
    Point bc = new Point(c.x - b.x, c.y - b.y);
    return ab.x * bc.x + ab.y * bc.y;
  }

  // return cross product of |b-a|x|c-a|
  public static double cross(Point a, Point b, Point c) {
    Point ab = new Point(b.x - a.x, b.y - a.y);
    Point ac = new Point(c.x - a.x, c.y - a.y);
    return ab.x * ac.y - ab.y * ac.x;
  }

  // returns if a,b,c are colinear
  public static boolean colinear(Point a, Point b, Point c) {
    return (a.x - b.x) * (a.y - c.y) == (a.x - c.x) * (a.y - b.y);
  }

  // returns distance between points a, and b
  public static double distance(Point a, Point b) {
    return Math.hypot(a.x - b.x, a.y - b.y);
  }

  // returns true iff p lies in box with opposite corners b1, b2
  public static boolean point_in_box(Point p, Point b1, Point b2) {
    return ((p.x >= Math.min(b1.x, b2.x)) && (p.x <= Math.max(b1.x, b2.x))
        && (p.y >= Math.min(b1.y, b2.y)) && (p.y <= Math.max(b1.y, b2.y)));
  }
}

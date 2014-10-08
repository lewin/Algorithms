package Geometry;

import static Geometry.Utils.EPS;
import static Geometry.Utils.cross;

import java.util.Arrays;
import java.util.Comparator;


/**
 * Convex hull made easy
 * 
 * Adapted from USACO training pages
 * 
 * @author Lewin
 */
public class ConvexHull {
  /**
   * Given an array of N unsorted points, returns an array of points that make up the convex hull of
   * the points in clockwise order
   * 
   * The points are the same objects (NOT copies)
   * 
   * Uses the gift-wrapping algorithm. Runs in O(N log N) time
   * 
   * @param p array of points
   * @return array of points in convex hull in order
   */
  private static Point[] convexHull(Point[] p) {
    int numPoints = p.length;
    Point m = new Point(0, 0);
    for (int i = 0; i < numPoints; i++) {
      m.x += p[i].x / numPoints;
      m.y += p[i].y / numPoints;
    }

    for (int i = 0; i < numPoints; i++)
      p[i].angle = Math.atan2(p[i].y - m.y, p[i].x - m.x);

    Arrays.sort(p, new Comparator<Point>() {
      public int compare(Point a, Point b) {
        return (int) Math.signum(a.angle - b.angle);
      }
    });

    Point[] hull = new Point[numPoints];
    hull[0] = p[0];
    hull[1] = p[1];

    int hullPos = 2;
    for (int i = 2; i < numPoints - 1; i++) {
      while (hullPos > 1 && cross(hull[hullPos - 2], hull[hullPos - 1], p[i]) < EPS)
        hullPos--;
      hull[hullPos++] = p[i];
    }

    // add last point
    Point p2 = p[numPoints - 1];
    while (hullPos > 1 && cross(hull[hullPos - 2], hull[hullPos - 1], p2) < EPS)
      hullPos--;

    int hullStart = 0;
    boolean flag = false;
    do {
      flag = false;
      if (hullPos - hullStart >= 2 && cross(hull[hullPos - 1], p2, hull[hullStart]) < EPS) {
        p2 = hull[--hullPos];
        flag = true;
      }
      if (hullPos - hullStart >= 2 && cross(p2, hull[hullStart], hull[hullStart + 1]) < EPS) {
        hullStart++;
        flag = true;
      }
    } while (flag);
    hull[hullPos++] = p2;

    return Arrays.copyOfRange(hull, hullStart, hullPos);
  }
}

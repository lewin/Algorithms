package Geometry;

import java.util.Arrays;

public class ConvexHull {
  public static Point[] convexHull(Point[] p) {
    int n = p.length;
    if (n <= 1) return p;
    Arrays.sort(p, (a, b) -> Integer.compare(a.x, b.x) != 0 ? Integer.compare(a.x, b.x) : Integer.compare(a.y, b.y));
    Point[] q = new Point[n * 2];
    int cnt = 0;
    for (int i = 0; i < n; q[cnt++] = p[i++])
      for (; cnt > 1 && cross(q[cnt - 2], q[cnt - 1], p[i]) >= 0; --cnt);
    for (int i = n - 2, t = cnt; i >= 0; q[cnt++] = p[i--])
      for (; cnt > t && cross(q[cnt - 2], q[cnt - 1], p[i]) >= 0; --cnt);
    return Arrays.copyOf(q, cnt - 1 - (q[0].x == q[1].x && q[0].y == q[1].y ? 1 : 0));
  }

  public static class Point {
    final int x, y;

    public Point(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }

  static long cross(Point a, Point b, Point c) {
    return (long) (b.x - a.x) * (c.y - a.y) - (long) (b.y - a.y) * (c.x - a.x);
  }
}

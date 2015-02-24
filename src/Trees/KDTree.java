package Trees;

public class KDTree {
  // don't know if this works
  static class kd_tree {
    public kd_tree Lchild = null, Rchild = null;
    public Point[] points;
    public int d;

    public kd_tree(Point[] points, int depth) {
      this.points = points;
      int k = points[0].coord.length;
      int N = points.length;
      d = depth % k;

      if (N == 1)
        return;

      Point[] temp;
      int lo = 0, hi = N - 1, comp;
      while (true) {
        temp = new Point[N];
        comp = points[lo].coord[d];
        int f1 = 0, f2 = N - 1;
        for (int i = lo; i <= hi; i++) {
          if (points[i].coord[d] < comp)
            temp[f1++] = points[i];
          else
            temp[f2--] = points[i];
        }
        points = temp;
        if (f1 == N / 2)
          break;
        if (f1 < N / 2)
          lo = f1;
        else
          hi = f2;
      }

      Point[] first = new Point[N / 2], second = new Point[N - N / 2];
      System.arraycopy(points, 0, first, 0, N / 2);
      System.arraycopy(points, N / 2, second, 0, N - N / 2);
      Lchild = new kd_tree(first, depth + 1);
      Rchild = new kd_tree(second, depth + 1);
    }

  }

  static class Point {
    public int[] coord;

    public Point(int[] coord) {
      this.coord = coord;
    }
  }
}

package LinearProgramming;

public class LPSolver {
  public static double EPS = 1e-9;
  public int m, n;
  public int[] B, N;
  public double[][] D;
  public double[] x;

  public LPSolver(double[][] A, double[] b, double[] c) {
    n = c.length;
    m = b.length;
    N = new int[n + 1];
    B = new int[m];
    D = new double[m + 2][n + 2];
    for (int i = 0; i < m; i++)
      for (int j = 0; j < n; j++)
        D[i][j] = A[i][j];
    for (int i = 0; i < m; i++) {
      B[i] = n + i;
      D[i][n] = -1;
      D[i][n + 1] = b[i];
    }
    for (int j = 0; j < n; j++) {
      N[j] = j;
      D[m][j] = -c[j];
    }
    N[n] = -1;
    D[m + 1][n] = 1;
  }

  public void pivot(int r, int s) {
    for (int i = 0; i < m + 2; i++)
      if (i != r)
        for (int j = 0; j < n + 2; j++)
          if (j != s)
            D[i][j] -= D[r][j] * D[i][s] / D[r][s];
    for (int j = 0; j < n + 2; j++)
      if (j != s)
        D[r][j] /= D[r][s];
    for (int i = 0; i < m + 2; i++)
      if (i != r)
        D[i][s] /= -D[r][s];
    D[r][s] = 1.0 / D[r][s];
    int t = B[r];
    B[r] = N[s];
    N[s] = t;
  }

  public boolean simplex(int phase) {
    int x = phase == 1 ? m + 1 : m;
    while (true) {
      int s = -1;
      for (int j = 0; j <= n; j++) {
        if (phase == 2 && N[j] == -1)
          continue;
        if (s == -1 || D[x][j] < D[x][s] || (Math.abs(D[x][j] - D[x][s]) < EPS && N[j] < N[s]))
          s = j;
      }
      if (D[x][s] >= -EPS)
        return true;
      int r = -1;
      for (int i = 0; i < m; i++) {
        if (D[i][s] <= 0)
          continue;
        if (r == -1 || D[i][n + 1] * D[r][s] < D[r][n + 1] * D[i][s]
            || (Math.abs(D[i][n + 1] * D[r][s] - D[r][n + 1] * D[i][s]) < EPS && B[i] < B[r]))
          r = i;
      }
      if (r == -1)
        return false;
      pivot(r, s);
    }
  }

  public double solve() {
    int r = 0;
    for (int i = 1; i < m; i++)
      if (D[i][n + 1] < D[r][n + 1])
        r = i;
    if (D[r][n + 1] <= -EPS) {
      pivot(r, n);
      if (!simplex(1) || D[m + 1][n + 1] < -EPS)
        return Double.NEGATIVE_INFINITY;
      for (int i = 0; i < m; i++)
        if (B[i] == -1) {
          int s = -1;
          for (int j = 0; j <= n; j++)
            if (s == -1 || D[i][j] < D[i][s] || (Math.abs(D[i][j] - D[i][s]) < EPS && N[j] < N[s]))
              s = j;
          pivot(i, s);
        }
    }
    if (!simplex(2))
      return Double.POSITIVE_INFINITY;
    x = new double[n];
    for (int i = 0; i < m; i++)
      if (B[i] < n)
        x[B[i]] = D[i][n + 1];
    return D[m][n + 1];
  }
}

package Matrix;

public class MatrixInverse {

  // square matrix
  // returns null if no inverse
  private static double[][] inv(double[][] arr) {
    int N = arr.length;
    double[][] M = new double[N][2 * N];
    for (int i = 0; i < arr.length; i++) {
      System.arraycopy(arr[i], 0, M[i], 0, N);
      M[i][i + N] = 1;
    }

    for (int r = 0; r < N; r++) {
      int k = r;
      while (M[k][r] == 0) {
        k++;
        if (k == N)
          return null; // no inverse
      }
      double[] temp = M[r];
      M[r] = M[k];
      M[k] = temp;

      double lv = M[r][r];
      for (int j = 0; j < 2 * N; j++)
        M[r][j] /= lv;

      for (int i = 0; i < N; i++) {
        if (i != r) {
          lv = M[i][r];
          for (int j = 0; j < 2 * N; j++)
            M[i][j] -= lv * M[r][j];
        }
      }
    }

    double[][] ret = new double[N][N];
    for (int i = 0; i < N; i++)
      System.arraycopy(M[i], N, ret[i], 0, N);
    return ret;
  }
}

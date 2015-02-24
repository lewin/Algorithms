package Matrix;

/**
 * Row reduce function
 * 
 * @author Lewin
 *
 */
public class RowReduce {
  private static void rref(double[][] M) {
    int row = M.length;
    if (row == 0)
      return;

    int col = M[0].length;

    int lead = 0;
    for (int r = 0; r < row; r++) {
      if (lead >= col)
        return;

      int k = r;
      while (M[k][lead] == 0) {
        k++;
        if (k == row) {
          k = r;
          lead++;
          if (lead == col)
            return;
        }
      }
      double[] temp = M[r];
      M[r] = M[k];
      M[k] = temp;

      double lv = M[r][lead];
      for (int j = 0; j < col; j++)
        M[r][j] /= lv;

      for (int i = 0; i < row; i++) {
        if (i != r) {
          lv = M[i][lead];
          for (int j = 0; j < col; j++)
            M[i][j] -= lv * M[r][j];
        }
      }
      lead++;
    }
  }
}

package Matrix;

/**
 * Operations on matrices
 * 
 * @author Lewin
 *
 */
public class MatrixOperations {
  private static int mod;

  private static int[][] mat_exp(int[][] A, int e) {
    if (e == 1)
      return A;
    else if (e % 2 == 0) {
      int[][] A1 = mat_exp(A, e / 2);
      return matrix_mult(A1, A1);
    } else
      return matrix_mult(A, mat_exp(A, e - 1));
  }

  private static int[][] matrix_mult(int[][] A, int[][] B) {
    int[][] C = new int[A.length][A.length];
    for (int i = 0; i < A.length; i++)
      for (int j = 0; j < A.length; j++)
        for (int k = 0; k < A.length; k++)
          C[i][k] = (C[i][k] + A[i][j] * B[j][k]) % mod;
    return C;
  }
}

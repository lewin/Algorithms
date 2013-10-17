package Matrix;

public class Determinant {
    
    // square matrix
    private static double det (double [][] arr) {
        int N = arr.length;
        double [][] M = new double [N][N];
        for (int i = 0; i < arr.length; i++)
            System.arraycopy (arr[i], 0, M[i], 0, N);
        
        double mult = 1;
        for (int r = 0; r < N; r++) {
            int k = r;
            while (M[k][r] == 0) {
                k++;
                if (k == N)
                    return 0;
            }
            double [] temp = M[r];
            M[r] = M[k];
            M[k] = temp;
            if (r != k) {
                mult *= -1;
            }
            
            double lv = M[r][r];
            for (int j = 0; j < N; j++)
                M[r][j] /= lv;
            mult *= lv;
            
            for (int i = r; i < N; i++) {
                if (i != r) {
                    lv = M[i][r];
                    for (int j = r; j < N; j++)
                        M[i][j] -= lv * M[r][j];
                }
            }
        }
        return mult;
    }
}

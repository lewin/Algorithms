import java.util.Arrays;


public class LinearProgramming {
    public static final double EPS = 1e-9;
    /**
     * Maximizes CX subject to AX <= B with X >= 0
     * X will be set as a result, but method returns value
     */
    public static double [] X;
    public static double simplex (double [][] A, double [] B, double [] C) {
        // peturbations to avoid cycling
        for (int i = 0; i < B.length; i++)
            B [i] += Math.random() * 1e-300;
        
        int vars = C.length;
        int cons = B.length;
        int rows = cons, cols = vars + cons + 1;
        double [][] a = new double [cons + 1][vars + cons + 2];
        for (int i = 0; i < cons; i++) {
            for (int j = 0; j < vars; j++)
                a [i][j] = A[i][j];
            for (int j = 0; j < cons; j++)
                a [i][j + vars] = i == j ? 1 : 0;
            a[i][vars + cons] = 0;
            a[i][vars + cons + 1] = B[i];
        }
        
        for (int j = 0; j < vars; j++) {
            a[cons][j] = C[j] == 0 ? 0 : -C[j];
            a[cons][j + vars] = 0;
        }
        
        a[cons][vars + cons] = 1;
        a[cons][vars + cons + 1] = 0;
        
        while (true) {
            int pc = 0;
            for (int i = 1; i < cols; i++)
                if (a[rows][i] < a[rows][pc])
                    pc = i;
            
            if (a[rows][pc] >= 0) break;
            
            int pr = -1;
            for (int i = 0; i < rows; i++)
                if (a[i][pc] >= EPS)
                    if (pr == -1 || a[i][cols] / a[i][pc] <
                        a[pr][cols] / a[pr][pc])
                        pr = i;
            if (pr == -1) {
                System.out.println ("Unbounded");
                return Double.MAX_VALUE;
            }
            
            for (int i = 0; i <= rows; i++) {
                if (i == pr) continue;
                double ratio = a[i][pc] / a[pr][pc];
                for (int j = 0; j <= cols; j++)
                    a[i][j] -= ratio * a[pr][j];
                a[i][pc] = 0;
            }
            
            // normalize
            for (int i = 0; i <= rows; i++) {
                double max = 0;
                for (int j = 0; j <= cols; j++)
                    max = Math.max (max, Math.abs (a[i][j]));
                for (int j = 0; j <= cols; j++)
                    a[i][j] /= max;
            }
        }
        
        // get X vector
        X = new double [vars];
        for (int i = 0; i < vars; i++) {
            boolean found = false;
            for (int j = 0; j < cons; j++) {
                if (a[j][i] != 0) {
                    if (!found) {
                        X[i] = a[j][cols] / a[j][i];
                        found = true;
                    } else {
                        X[i] = 0;
                        break;
                    }
                }
            }
        }
        return a[rows][cols] / a[rows][cols - 1];
    }
}

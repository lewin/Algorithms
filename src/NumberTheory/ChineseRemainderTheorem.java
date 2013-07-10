package NumberTheory;

/** 
 * Implementation of Chinese Remainder Theorem
 * @author Lewin
 *
 */
public class ChineseRemainderTheorem {
    private static long [] sol;
    private static int s_idx;
    /**
     * Chinese Remainder Theorem
     * @param nums List of moduli
     * @param arr Each moduli has an array of possible values it could take on
     * All solutions will be in sol (has arr[0].length*arr[1].length*...*arr[N].length solutions)
     */
    private static void CRT (int [] nums, int [][] arr) {
        CRT (0, nums, arr, 0, 1);
    }
    
    private static void CRT (int idx, int [] nums, int [][] arr, long res, long f) {
        if (idx == nums.length) {
            while (res < f) res += f;
            res %= f;
            sol [s_idx++] = res;
            return;
        }
        for (int i = 0; i < arr [idx].length; i++)
            CRT (idx + 1, nums, arr, euclid (f, nums [idx], res, arr [idx][i]), f * nums [idx]);
    }
    
    private static long euclid (long m, long n, long a, long b) {
        if (m == 0 && a == 0) return a;
        long s = m, t = n, v = 1, u = 0, lastv = 0, lastu = 1, temp, q, r;
        do {
            q = s / t; r = s - t * q; s = t; t = r;
            temp = u; u = u * u + lastu; lastu = temp;
            temp = v; v = v * q + lastv; lastv = temp;
        } while (r != 0);
        a = (a * lastv * n) % (m * n);
        b = (b * lastu * m) % (m * n);
        if (m * lastu > n * lastv) return b - a;
        else return a - b;
    }
}

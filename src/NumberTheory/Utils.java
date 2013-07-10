package NumberTheory;

import static NumberTheory.PrimeSieve.*;
/**
 * Collection of common operations
 * @author Lewin
 *
 */

public class Utils {
    // Euler's Totient Function
    private static long phi (long n) {// make sure to generate primes
        long temp = n;
        for (int i = 0; prime [i] * prime [i] <= temp && i < idx; i++)
            if (temp % prime [i] == 0) {
                n -= n / prime [i];
                while (temp % prime [i] == 0)
                    temp /= prime [i];
            }
        return temp == 1 ? n : n / temp * (temp - 1);
    }
    
    // N choose K
    private static long comb (int n, int k) {
        int a = Math.min (k, n - k);
        long res = 1;
        for (int i = 1; i <= a; i++) {
            res *= n--; res /= i;
        }
        return res;
    }
    
    // Greatest Common Divisor
    private static long gcd (long x, long y) {
        for (; x != 0; x ^= y, y ^= x, x ^= y, x %= y);
        return y;
    }
}

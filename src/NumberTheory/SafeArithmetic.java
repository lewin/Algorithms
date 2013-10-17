package NumberTheory;

/**
 * Safely multiplies and adds number under a modulus to prevent overflow
 * Operations may still overflow if modulus is greater than Long.MAX_VALUE / 2
 * @author Lewin
 *
 */
public class SafeArithmetic {
    public static long mod_mult (long x, long y, long p) {
        if (x == 0 || y == 0) return 0;
        long a = x, b = y, c = 0;
        while (a > 0) {
            if ((a & 1) == 1) c = (c + b) % p; 
            a >>= 1; 
            b = (b * 2) % p;
        }
        return c;
    }
    
    public static long mod_exp (long b, long e, long mod) {
        if (b == 0 || b == 1) return b;
        long res = 1;
        while (e > 0) {
            if ((e & 1) == 1)
                res = mod_mult (res, b, mod);
            e >>= 1;
            b = mod_mult (b, b, mod);
        }
        return res;
    }
}

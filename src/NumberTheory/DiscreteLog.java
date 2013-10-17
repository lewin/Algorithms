package NumberTheory;

import java.util.HashMap;
import static NumberTheory.Utils.inv;
import static NumberTheory.SafeArithmetic.mod_exp;


public class DiscreteLog {
    // gcd (g, mod) = 1
    public static long discreteLog (long g, long gn, long mod) {
        long m = (long)Math.ceil (mod);
        HashMap<Long, Long> mp = new HashMap <Long, Long>();
        long cur = 1;
        for (int i = 0; i < m; i++) {
            mp.put(cur, (long)i);
            cur = (cur * g) % mod;
        }
        long st2 = mod_exp (inv (g, mod), m, mod);
        long y = gn;
        for (int i = 0; i < m; i++) {
            if (mp.containsKey(y))
                return i * m + mp.get(y);
            y = (y * st2) % mod;
        }
        return -1;
    }
}

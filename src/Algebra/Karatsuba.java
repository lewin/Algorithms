package Algebra;

import java.util.Arrays;

public class Karatsuba {
  public static long mod;

  public static long[] multiply(long[] a, long[] b) {
    if (b.length <= 7) {
      long[] r = new long[a.length + b.length - 1];
      for (int i = 0; i < r.length; i++)
        for (int j = Math.max(i - a.length + 1, 0); i - j >= 0 && j < b.length; j++)
          r[i] = (r[i] + a[i - j] * b[j]) % mod;
      return r;
    }
    if (a.length <= 7) {
      long[] r = new long[a.length + b.length - 1];
      for (int i = 0; i < r.length; i++)
        for (int j = Math.max(i - b.length + 1, 0); i - j >= 0 && j < a.length; j++)
          r[i] = (r[i] + b[i - j] * a[j]) % mod;
      return r;
    }
    int K = a.length / 2;
    long[] a1 = Arrays.copyOfRange(a, 0, K);
    long[] a2 = Arrays.copyOfRange(a, K, a.length);
    long[] b1 = Arrays.copyOfRange(b, 0, K);
    long[] b2 = Arrays.copyOfRange(b, K, b.length);
    long[] c1 = multiply(a1, b1);
    long[] c2 = multiply(a2, b2);
    long[] c3 = sub(sub(multiply(add(a1, a2), add(b1, b2)), c1), c2);
    long[] ret = new long[a.length + b.length - 1];
    System.arraycopy(c1, 0, ret, 0, c1.length);
    for (int i = 2 * K; i < 2 * K + c2.length; i++) {
      ret[i] = (ret[i] + c2[i - 2 * K]) % mod;
    }
    for (int i = K; i < K + c3.length; i++) {
      ret[i] = (ret[i] + c3[i - K]) % mod;
    }
    return ret;
  }

  public static long[] sub(long[] a, long[] b) {
    long[] r = new long[Math.max(a.length, b.length)];
    System.arraycopy(a, 0, r, 0, a.length);
    for (int i = 0; i < b.length; i++)
      r[i] = (r[i] + mod - b[i]) % mod;
    return r;
  }

  public static long[] add(long[] a, long[] b) {
    long[] r = new long[Math.max(a.length, b.length)];
    System.arraycopy(b, 0, r, 0, b.length);
    for (int i = 0; i < a.length; i++)
      r[i] = (r[i] + a[i]) % mod;
    return r;
  }
}

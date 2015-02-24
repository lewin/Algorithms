package NumberTheory;

import static NumberTheory.PrimeSieve.*;

import java.util.ArrayList;

/**
 * Collection of common operations
 * 
 * @author Lewin
 *
 */

public class Utils {
  // Euler's Totient Function
  public static long phi(long n) {// make sure to generate primes
    long temp = n;
    for (int i = 0; prime[i] * prime[i] <= temp && i < idx; i++)
      if (temp % prime[i] == 0) {
        n -= n / prime[i];
        while (temp % prime[i] == 0)
          temp /= prime[i];
      }
    return temp == 1 ? n : n / temp * (temp - 1);
  }

  // N choose K
  public static long comb(int n, int k) {
    int a = Math.min(k, n - k);
    long res = 1;
    for (int i = 1; i <= a; i++) {
      res *= n--;
      res /= i;
    }
    return res;
  }

  // Greatest Common Divisor
  public static long gcd(long x, long y) {
    for (; x != 0; x ^= y, y ^= x, x ^= y, x %= y);
    return y;
  }

  // Inverse of N mod M;
  public static long inv(long N, long M) {
    long x = 0, lastx = 1, y = 1, lasty = 0, q, t, a = N, b = M;
    while (b != 0) {
      q = a / b;
      t = a % b;
      a = b;
      b = t;
      t = x;
      x = lastx - q * x;
      lastx = t;
      t = y;
      y = lasty - q * y;
      lasty = t;
    }
    return (lastx + M) % M;
  }

  // Chinese Remainder Theorem
  // all mods are pairwise coprime
  public static long CRT(long[] vals, long[] mods) {
    long prodall = 1;
    for (long j : mods)
      prodall *= j;

    long ret = 0;
    for (int i = 0; i < vals.length; i++) {
      long ni = mods[i], ai = vals[i];
      ret = (ret + ai * prodall / ni % prodall * inv(prodall / ni % ni, ni)) % prodall;
    }
    return ret;
  }
}

package NumberTheory;

/**
 * Prime Siever
 * 
 * @author Lewin
 *
 */
public class PrimeSieve {
  public static boolean[] isPrime;
  public static int[] prime;
  public static int idx, len;

  private static void generatePrimes() {
    isPrime = new boolean[len + 1];
    prime = new int[len / 2];
    isPrime[2] = true;
    prime[idx++] = 2;
    int i;
    for (i = 3; i <= len; i += 2)
      isPrime[i] = true;
    for (i = 3; i * i <= len; i += 2) {
      if (isPrime[i]) {
        prime[idx++] = i;
        for (int j = i * i; j <= len; j += 2 * i)
          isPrime[j] = false;
      }
    }
    for (; i <= len; i += 2)
      if (isPrime[i])
        prime[idx++] = i;
  }
}

package NumberTheory;

/**
 * Euler's Totient Function Siever
 * 
 * @author Lewin http://en.wikipedia.org/wiki/Euler's_totient_function
 */
public class TotientSieve {
  private static boolean[] isPrime;
  private static int[] prime;
  private static int idx, len;
  private static int[] phi;

  private static void phi_prime_sieve() {
    isPrime = new boolean[len + 1];
    prime = new int[len / 2];
    phi = new int[len + 1];
    phi[0] = 0;
    phi[1] = 1;
    for (int i = 2; i <= len; i++) {
      isPrime[i] = true;
      phi[i] = i;
    }
    for (int i = 2; i <= len; i++) {
      if (isPrime[i]) {
        prime[idx++] = i;
        phi[i] = i - 1;
        for (int j = i + i; j <= len; j += i) {
          isPrime[j] = false;
          phi[j] /= i;
          phi[j] *= i - 1;
        }
      }
    }
  }
}

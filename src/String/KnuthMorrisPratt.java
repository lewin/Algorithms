package String;

/**
 * Implementation of Knuth-Morris-Pratt
 * 
 * See http://en.wikipedia.org/wiki/Knuth%E2%80%93Morris%E2%80%93Pratt_algorithm
 * 
 * @author Lewin
 *
 */
public class KnuthMorrisPratt {
  private static int KMP(String text, String word) {
    char[] a = text.toCharArray(), b = word.toCharArray();
    int[] T = new int[a.length + b.length];

    int pos = 2, cnd = 0;
    T[0] = -1;
    T[1] = 0;
    while (pos < b.length) {
      if (b[pos - 1] == b[cnd]) {
        cnd++;
        T[pos] = cnd;
        pos++;
      } else if (cnd > 0)
        cnd = T[cnd];
      else {
        T[pos] = 0;
        pos++;
      }
    }

    int m = 0, i = 0;
    while (m + i < a.length) {
      if (b[i] == a[m + i]) {
        if (i == b.length - 1)
          return m;
        i++;
      } else {
        m += i - T[i];
        if (T[i] > -1)
          i = T[i];
        else
          i = 0;
      }
    }
    return a.length;
  }
}

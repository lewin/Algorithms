package String;

public class ZAlgorithm {
  public int[] zAlgorithm(char[] let) {
    int N = let.length;
    int[] z = new int[N];
    int L = 0, R = 0;
    for (int i = 1; i < N; i++) {
      if (i > R) {
        L = R = i;
        while (R < N && let[R - L] == let[R])
          R++;
        z[i] = R - L;
        R--;
      } else {
        int k = i - L;
        if (z[k] < R - i + 1)
          z[i] = z[k];
        else {
          L = i;
          while (R < N && let[R - L] == let[R])
            R++;
          z[i] = R - L;
          R--;
        }
      }
    }
    z[0] = N;
    return z;
  }
}

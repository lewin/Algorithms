package String;
/**
 * @author: Lewin
 * A linear time BWT encoder/decoder
 * also computes suffix arrays in linear time
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class BWT {
    // number of letters in our alphabet
    private static final int K = 53;
    
    private static int toInt (char c) {
        if (c == '$') return 0;
        if (c >= 'A' && c <= 'Z') return c - 'A' + 1;
        else return c - 'a' + ('Z' - 'A' + 1) + 1;
    }
    
    private static char toChar(int i) {
        if (i == 0) return '$';
        if (i > 0 && i <= 26) return (char)(i + 'A' - 1);
        else return (char)(i - 27 + 'a');
    }
    
    private static int[] radix_sort (int [] s, int [] pos) {
        int N = pos.length;
        int [] count = new int [s.length];
        for (int i = 0; i < N; i++)
            count[s[pos[i]]]++;
        
        for (int j = 1; j < count.length; j++)
            count [j] += count [j - 1];
        for (int j = count.length - 1; j > 0; j--)
            count [j] = count [j - 1];
        count [0] = 0;
        
        // get new positions
        int [] nord = new int [N];
        for (int i = 0; i < N; i++)
            nord[count[s[pos[i]]]++] = pos[i];
        
        return nord;
    }
    
    private static int[] suffix_array (int [] s) {
        int N = s.length;
        
        int n1 = (N + 1) / 3, n2 = N / 3, n0 = (N + 2) / 3;
        
        if (N % 3 == 1) n1++;
        // pad S
        int [] ns = new int [N + 3];
        System.arraycopy (s, 0, ns, 0, s.length);
        
        int [] pos = new int [n1 + n2];
        int idx = 0;
        for (int i = 1; i < N; i += 3)
            pos [idx++] = i;
        if (N % 3 == 1) pos [idx++] = N;
        for (int i = 2; i < N; i += 3)
            pos [idx++] = i;
        
        int [] bmap = new int [N + 1];
        for (int i = 0; i < pos.length; i++)
            bmap[pos[i]] = i;
        
        int [] fmap = new int [n1 + n2];
        System.arraycopy (pos, 0, fmap, 0, n1 + n2);
        
        for (int i = 0; i < pos.length; pos[i++] += 2);
        pos = radix_sort (ns, pos);
        for (int i = 0; i < pos.length; pos[i++]--);
        pos = radix_sort (ns, pos);
        for (int i = 0; i < pos.length; pos[i++]--);
        pos = radix_sort (ns, pos);
        
        // now relabel strings
        int p0 = -1, p1 = -1, p2 = -1, cidx = 0;
        int [] rs = new int [n1 + n2];
        for (int i = 0; i < pos.length; i++) {
            if (ns[pos[i]] != p0 || ns[pos[i] + 1] != p1 || ns[pos[i] + 2] != p2) {
                p0 = ns[pos[i]];
                p1 = ns[pos[i] + 1];
                p2 = ns[pos[i] + 2];
                rs[bmap[pos[i]]] = cidx++;
            } else {
                rs[bmap[pos[i]]] = cidx - 1;
            }
        }
        
        int [] rSA = new int [n1 + n2];
        if (cidx == n1 + n2) {
            System.arraycopy (pos, 0, rSA, 0, n1 + n2);
        } else {
            int [] r = suffix_array(rs);
            for (int i = 0; i < rSA.length; i++)
                rSA[i] = fmap[r[i]];
        }
        
        int [] rank = new int [N + 3];
        for (int i = 0; i < rSA.length; i++)
            rank[rSA[i]] = i + 1;
        
        int [] pos0 = new int [n0];
        int qidx = 0;
        for (int i = 0; i < rSA.length; i++)
            if (rSA[i] % 3 == 1)
                pos0 [qidx++] = rSA[i] - 1;
        pos0 = radix_sort (s, pos0);
        
        int [] ret = new int [N];
        int fidx = 0;
        for (int i = 0, j = 0; fidx < N; ) {
            if (j == n1 + n2)
                ret[fidx++] = pos0[i++];
            else if (rSA[j] == N)
                j++;
            else if (i == n0)
                ret[fidx++] = rSA[j++];
            else {
                if (rSA[j] % 3 == 1) {
                    if (ns[pos0[i]] < ns[rSA[j]] 
                      || (ns[pos0[i]] == ns[rSA[j]] && rank[pos0[i]+1] < rank[rSA[j] + 1]))
                        ret[fidx++] = pos0[i++];
                    else
                        ret[fidx++] = rSA[j++];
                } else {
                    if (ns[pos0[i]] < ns[rSA[j]] 
                      || (ns[pos0[i]] == ns[rSA[j]] && ns[pos0[i] + 1] < ns[rSA[j] + 1])
                      || (ns[pos0[i]] == ns[rSA[j]] && ns[pos0[i] + 1] == ns[rSA[j] + 1] && rank[pos0[i]+2] < rank[rSA[j] + 2]))
                        ret[fidx++] = pos0[i++];
                    else
                        ret[fidx++] = rSA[j++];
                }
            }
        }
        
        return ret;
    }
    
    private static int[][] createMaps(String s) {
        int N = s.length();
        char[] c = s.toCharArray();
        
        int [] fmap = new int [K], bmap = new int [K];
        for (int i = 0; i < N; i++)
            fmap[toInt(c[i])]++;

        for (int i = 0, idx = 0; i < K; i++)
            if (fmap[i] > 0) {
                fmap[i] = idx;
                bmap[idx++] = i;
            }
        
        int [] arr = new int [N];
        for (int i = 0; i < N; i++)
            arr[i] = fmap[toInt(s.charAt(i))];
        
        return new int[][] {arr, bmap};
    }
    
    private static int [] inverse_BWT (int [] s) {
        int N = s.length;
        int [] pos = new int [N];
        for (int i = 0; i < N; i++)
            pos [i] = i;
        
        pos = radix_sort(s, pos);
        
        int [] res = new int [N];
        for (int i = 0, cur = pos[pos[0]]; i < N; 
             res[i] = s[cur], cur = pos[cur], i++);
        
        return res;
    }
    
    public static String inverse_BWT (String s) {
        int N = s.length();
        int [][] c = createMaps(s);
        int [] b = inverse_BWT (c[0]);
        
        char [] ret = new char [N];
        for (int i = 0; i < N; i++)
            ret[i] = toChar(c[1][b[i]]);
        return new String (ret);
    }
    
    public static String BWT (String s) {
        int N = s.length();
        int [] pos = suffix_array(createMaps(s)[0]);
        char [] ret = new char [N];
        for (int i = 0; i < N; i++)
            ret[i] = s.charAt(pos[i] == 0 ? N - 1 : pos[i] - 1);
        return new String (ret);
    }
}

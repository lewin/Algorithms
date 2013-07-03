package String;

import java.util.Arrays;
/**
 * O(n log n) suffix array
 * O(n) longest common prefix
 * 
 * @author Lewin
 *
 */
public class SuffixArray {
    
    private static int [] suffix_array (char [] s) {
        int n = s.length;
        int [] id = new int [n];
        for (int i = 0; i < n; i++)
            id[i] = s[i] - 'a';
        
        for (int k = 1; k <= n; k <<= 1) {
            Triplet [] elem = new Triplet [n];
            for (int i = 0; i < n; i++)
                elem[i] = new Triplet (id[i], (i + k < n) ? id[i + k] : -1, i);
            Arrays.sort (elem);
            int cur = -1;
            for (int i = 0; i < n; i++) {
                if (i == 0
                        || !(elem[i].a == elem[i - 1].a && elem[i].b == elem[i - 1].b))
                    cur++;
                id[elem[i].c] = cur;
            }
        }
        
        return id;
    }
    
    private static int [] lcp (char [] A, int [] order, int [] rank) {
        int n = order.length;
        int [] height = new int [n];
        
        int h = 0;
        for (int i = 0; i < n; i++) {
            if (order [i] > 0) {
                int j = rank [order [i] - 1];
                while (i + h < n && j + h < n 
                        && A [i + h] == A [j + h]) h++;
                height [order [i]] = h;
                if (h > 0) h--;
            }
        }
        
        return height;
    }
    
    static class Triplet implements Comparable <Triplet> {
        public int  a, b, c;
        
        public Triplet (int _a, int _b, int _c) {
            a = _a;
            b = _b;
            c = _c;
        }
        
        public int compareTo (Triplet other) {
            return (a == other.a) ? b - other.b : a - other.a;
        }
    }
}

package Trees;

import java.util.Arrays;
import java.util.HashMap;

public class HeavyLightTree {
private static int N;
    private static LazyIntervalTree root;
    
    static class Pair {
        public int a, b;
        public Pair (int a, int b) {
            this.a = a; this.b = b;
        }
        
        @Override
        public boolean equals (Object other) {
            if (!(other instanceof Pair)) return false;
            return ((Pair)other).a == this.a && ((Pair)other).b == this.b;
        }
    }

    private static HashMap<Pair, Integer> mp;
    
    // call before everything
    private static void init (int K) {
        N = K;
        eadj = new int [2 * N];
        elast = new int [N];
        eprev = new int [2 * N];
        ecost = new int [2 * N];
        eidx = 0;
        Arrays.fill (elast, -1);
        mp = new HashMap<Pair, Integer> ();
    }
    
    // call after adding all the edges
    private static void init2 () {
        makeCs();
        root = new LazyIntervalTree (0, N - 1);
    }
    
    private static int [] eadj, elast, eprev, ecost;
    private static int eidx;
    
    private static void addEdge (int a, int b, int c) {
        eadj [eidx] = b; ecost [eidx] = c; eprev [eidx] = elast [a]; elast [a] = eidx++;
        eadj [eidx] = a; ecost [eidx] = c; eprev [eidx] = elast [b]; elast [b] = eidx++;
        mp.put (new Pair (a, b), c);
        mp.put (new Pair (b, a), c);
    }
    
    // trace heavy edge back up, -1 if no heavy edge
    // cs order heavy paths next to each other
    // pos position of vertex in cs
    private static int [] numChild, depth, trace, cs, pos, endSeg;
    private static int [][] par;
    
    private static void dfs (int u) {
        numChild [u] = 1;
        int t = -1;
        for (int e = elast [u]; e != -1; e = eprev [e]) {
            int v = eadj [e];
            if (numChild [v] == 0) {
                par [v][0] = u;
                depth [v] = depth [u] + 1;
                dfs (v);
                numChild [u] += numChild [v];
                if (t == -1 || numChild [t] < numChild [v])
                    t = v;
            }
        }
        trace [t] = u;
    }
    
    private static void makeCs() {
        int idx = 0;
        dfs(0);
        for (int i = 1; i < 20; i++) {
            for (int u = 0; u < N; u++)
                par [i][u] = par [i - 1][par [i - 1][u]];
        }
        
        for (int i = 0; i < N; i++) {
            if (numChild [i] == 1) { // leaf
                int u = i;
                do {
                    cs [idx++] = u;
                    u = trace [u];
                } while (u != -1);
                u = i;
                do {
                    endSeg [u] = cs [idx - 1];
                    u = trace [u];
                } while (u != -1);
            }
        }
        
        for (int i = 0; i < idx; i++)
            pos [cs [i]] = i;
    }
    
    private static int lca (int a, int b) {
        if (depth[a] < depth[b]) {
            a ^= b;
            b ^= a;
            a ^= b;
        }
        
        int diff = depth[a] - depth[b];
        for (int i = 0; 1 << i <= diff; i++)
            if (((diff) & 1 << i) != 0)
                a = par[i][a];
        
        if (a == b)
            return a;
        
        int log = 0;
        while (1 << (log + 1) <= depth[a])
            log++;
        
        // another binary search
        for (int i = log; i >= 0; i--)
            if (par[i][a] != par[i][b]) {
                a = par[i][a];
                b = par[i][b];
            }
        
        return par[0][a];
    }
}

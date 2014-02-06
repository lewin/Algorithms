package Graph.Algorithms;

import java.util.Arrays;

public class MaximumMatching {
    private static int N;
    private static boolean [][] grid;
    
    private static void addEdge (int a, int b) {
        grid[a][b] = grid[b][a] = true;
    }
    
    private static int []   match, queue, pre, base;
    private static boolean []   hash, blossom;
    
    private static int maximum_matching () {
        pre = new int [N];
        base = new int [N];
        queue = new int [N];
        hash = new boolean [N];
        blossom = new boolean [N];
        match = new int [N];
        Arrays.fill (match, -1);
        int ans = 0;
        for (int i = 0; i < N; i++)
            if (match[i] == -1)
                ans += bfs (i);
        return ans;
    }
    
    private static int bfs (int p) {
        Arrays.fill (pre, -1);
        Arrays.fill (hash, false);
        for (int i = 0; i < N; i++)
            base[i] = i;
        int front = 0, back = 0;
        queue[back++] = p;
        while (front < back) {
            int u = queue[front++];
            for (int v = 0; v < N; v++) {
                if (grid[u][v] && base[u] != base[v] && v != match[u]) {
                    if (v == p || (match[v] != -1 && pre[match[v]] != -1)) {
                        int b = contract (u, v);
                        for (int i = 0; i < N; i++) {
                            if (blossom[base[i]]) {
                                base[i] = b;
                                if (!hash[i]) {
                                    hash[i] = true;
                                    queue[back++] = i;
                                }
                            }
                        }
                    } else if (pre[v] == -1) {
                        pre[v] = u;
                        if (match[v] == -1) {
                            augment (v);
                            return 1;
                        } else {
                            queue[back++] = match[v];
                            hash[match[v]] = true;
                        }
                    }
                }
            }
        }
        return 0;
    }
    
    private static void augment (int u) {
        while (u != -1) {
            int v = pre[u], k = match[v];
            match[u] = v;
            match[v] = u;
            u = k;
        }
    }
    
    private static int contract (int u, int v) {
        Arrays.fill (blossom, false);
        int b = find_base (base[u], base[v]);
        change_blossom (b, u);
        change_blossom (b, v);
        if (base[u] != b)
            pre[u] = v;
        if (base[v] != b)
            pre[v] = u;
        return b;
    }
    
    private static void change_blossom (int b, int u) {
        while (base[u] != b) {
            int v = match[u];
            blossom[base[v]] = blossom[base[u]] = true;
            u = pre[v];
            if (base[u] != b)
                pre[u] = v;
        }
    }
    
    private static int find_base (int u, int v) {
        boolean [] in_path = new boolean [N];
        while (true) {
            in_path[u] = true;
            if (match[u] == -1)
                break;
            u = base[pre[match[u]]];
        }
        while (!in_path[v])
            v = base[pre[match[v]]];
        return v;
    }
}

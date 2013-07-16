package Graph.Algorithms;
import static Graph.Representation.LinkedList.N;
import static Graph.Representation.LinkedList.eadj;
import static Graph.Representation.LinkedList.ecost;
import static Graph.Representation.LinkedList.elast;
import static Graph.Representation.LinkedList.eprev;
import java.util.Arrays;

public class LowestCommonAncestor {
    private static int      root;
    private static int []   depth, queue;
    private static int [][] anc;
    
    private static void init () {
        Arrays.fill (depth, 1 << 29); // INF
        
        int front = 0, back = 0;
        depth[root] = 0;
        queue[back++] = root;
        anc[0][root] = -1;
        
        while (front != back) {
            int node = queue[front++];
            for (int e = elast[node]; e != -1; e = eprev[e])
                if (depth[node] + ecost[e] < depth[eadj[e]]) {
                    depth[eadj[e]] = depth[node] + ecost[e];
                    anc[0][eadj[e]] = node;
                    queue[back++] = eadj[e];
                }// depth [i] is distance from root to i
                    // anc [0][i] is the first ancestor of i
        }
        
        for (int k = 1; 1 << k < N; k++)
            for (int i = 0; i < N; i++)
                anc[k][i] = anc[k - 1][i] == -1 ? -1 : anc[k - 1][anc[k - 1][i]];
    }
    
    private static int lca (int a, int b) {
        if (depth[a] < depth[b]) {
            a ^= b;
            b ^= a;
            a ^= b;
        }
        
        // make depth [a] = depth [b] with binary search
        int diff = depth[a] - depth[b];
        for (int i = 0; 1 << i <= diff; i++)
            if (((diff) & 1 << i) != 0)
                a = anc[i][a];
        
        if (a == b)
            return a;
        
        int log = 0;
        while (1 << (log + 1) <= depth[a])
            log++;
        
        // another binary search
        for (int i = log; i >= 0; i--)
            if (anc[i][a] != anc[i][b]) {
                a = anc[i][a];
                b = anc[i][b];
            }
        
        return anc[0][a];
    }
    
    private static int dist (int a, int b) {
        return depth[a] + depth[b] - 2 * depth[lca (a, b)];
    }
}

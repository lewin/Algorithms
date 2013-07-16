package Graph.Algorithms.NetworkFlow;

import java.util.Arrays;
import static Graph.Representation.AdjacencyMatrix.*;
public class MinCostMaxFlow {
    private static int [][] cap, cost;
    private static int []   pot;
    
    // add an edge from x to y with capacity w and cost c
    private static void addEdge (int x, int y, int w, int c) {
        cap[x][y] = w;
        cost[x][y] = c;
        cost[y][x] = -c;
    }
    
    // if we want max cost, take replace c with Q - c, (Q > all c)
    // then take Q * flow [0] - flow [1]
    private static int [] flow (int source, int sink) {
        int ans_flow = 0, ans_cost = 0;
        pot = new int [N]; // potential of the node
        
        while (true) {
            boolean [] used = new boolean [N];
            int [] dist = new int [N], prev = new int [N];
            Arrays.fill (dist, INF);
            dist[source] = 0;
            
            while (true) {
                int x = -1;
                for (int i = 0; i < N; i++)
                    if (dist[i] != INF && !used[i] && (x == -1 || dist[i] < dist[x]))
                        x = i;
                
                if (x == -1)
                    break;
                
                used[x] = true;
                for (int i = 0; i < N; i++)
                    if (cap[x][i] > 0
                            && dist[x] + cost[x][i] + pot[x] - pot[i] < dist[i]) {
                        dist[i] = dist[x] + cost[x][i] + pot[x] - pot[i];
                        prev[i] = x;
                    }
            }
            
            if (!used[sink])
                break;
            
            int ansf = INF, ansc = 0;
            for (int x = sink; x != source; x = prev[x])
                ansf = Math.min (ansf, cap[prev[x]][x]);
            
            ans_flow += ansf;
            for (int x = sink; x != source; x = prev[x]) {
                ansc += cost[prev[x]][x] * ansf;
                cap[prev[x]][x] -= ansf;
                cap[x][prev[x]] += ansf;
            }
            
            for (int i = 0; i < N; i++)
                pot[i] += dist[i];
            
            ans_flow += ansf;
            ans_cost += ansc;
        }
        
        return new int [] { ans_flow, ans_cost };
        // returns both flow and cost
    }
}

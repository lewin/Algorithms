package Graph.Algorithms.NetworkFlow;
import static Graph.Representation.AdjacencyMatrix.*;
public class FordFulkerson {
    private static int networkFlow (int source, int sink) {
        if (source == sink)
            return INF;
        
        int totalFlow = 0;
        int [][] cap = new int [N][N]; // don't want to alter original array
        for (int i = 0; i < N; i++)
            // if you do want to alter it, change input from grid to cap
            System.arraycopy (grid[i], 0, cap[i], 0, N); // get rid of these three
                                                                        // lines
        
        while (true) { // while there exists an augmenting path
            int [] prev = new int [N], flow = new int [N];
            boolean [] visited = new boolean [N];
            int [] queue = new int [N];
            int front = 0, back = 0;
            
            queue[back++] = source;
            visited[source] = true;
            flow[source] = INF;
            boolean success = false;
            
            // find the path
            outer : while (front != back) {
                int node = queue[front++];
                
                for (int i = 0; i < N; i++)
                    if (!visited[i] && cap[node][i] > 0) {
                        prev[i] = node;
                        flow[i] = Math.min (flow[node], cap[node][i]);
                        visited[i] = true;
                        
                        if (i == sink) {
                            success = true;
                            break outer;
                        }
                        
                        queue[back++] = i;
                    }
            }
            if (!success)
                break;
            
            // push flow through
            int pathCap = flow[sink];
            totalFlow += pathCap;
            
            for (int x = sink; x != source; x = prev[x]) {
                cap[prev[x]][x] -= pathCap;
                cap[x][prev[x]] += pathCap;
            }
        }
        
        return totalFlow;
    }
}

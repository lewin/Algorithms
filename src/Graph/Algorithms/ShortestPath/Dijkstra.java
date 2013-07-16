package Graph.Algorithms.ShortestPath;

import java.util.Arrays;
import java.util.PriorityQueue;
import Graph.Triplet;
import static Graph.Representation.LinkedList.*;

public class Dijkstra {
    private static int [] dijkstra_prev (int [] dist, int start) {
        Arrays.fill (dist, INF);
        int [] prev = new int [N];
        boolean [] visited = new boolean [N];
        PriorityQueue <Triplet> pq = new PriorityQueue <Triplet> ();
        pq.add (new Triplet (start, -1, 0));
        
        while (pq.size () > 0) {
            int node = pq.peek ().a, pre = pq.peek ().b, weight = pq.peek ().c;
            pq.poll ();
            if (visited[node])
                continue;
            visited[node] = true;
            dist[node] = weight;
            prev[node] = pre;
            
            for (int e = elast[node]; e != -1; e = eprev[e])
                if (!visited[eadj[e]])
                    pq.add (new Triplet (eadj[e], node, ecost[e] + weight));
        }
        
        return prev; // parent pointers (in case you want to recreate the path)
    }
}

package Graph.Algorithms;

import java.util.PriorityQueue;
import static Graph.Representation.LinkedList.*;
import Graph.Pair;

public class SpanningTree {

  // for max spanning tree, add -weight and return -treeCost
  // for product trees, take the log of each weight (log (ab) = log (a) + log (b))
  private static int minSpanningTree() { // Prim's algorithm
    boolean[] visited = new boolean[N];
    PriorityQueue<Pair> pq = new PriorityQueue<Pair>();
    int treeCost = 0, treeSize = 0;
    pq.add(new Pair(0, 0));

    while (treeSize < N) {
      int node = pq.peek().a, weight = pq.peek().b;
      pq.poll();
      if (visited[node])
        continue;
      treeSize++;
      treeCost += weight;
      visited[node] = true;

      for (int e = elast[node]; e != -1; e = eprev[e])
        if (!visited[eadj[e]])
          pq.add(new Pair(eadj[e], ecost[e]));
    }

    return treeCost;
  }
}

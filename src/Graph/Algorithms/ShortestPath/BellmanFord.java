package Graph.Algorithms.ShortestPath;

import static Graph.Representation.EdgeList.*;
import Graph.Triplet;

public class BellmanFord {
  private static Triplet[] edges;

  private static int[] bellman_ford(int[] dist, int start) {
    // IMPORTANT: use this if and only if there are negative path values
    // this is the only way to process them
    int[] prev = new int[N];
    for (int i = 0; i < N; i++) {
      dist[i] = INF;
      prev[i] = -1;
    }

    dist[start] = 0;
    // shortest non-cyclic path is at most length N
    for (int i = 0; i < N; i++) {
      boolean changed = false;

      for (int j = 0; j < M; j++)
        if (dist[edges[j].a] + edges[j].c < dist[edges[j].b]) {
          dist[edges[j].b] = dist[edges[j].a] + edges[j].c;
          prev[edges[j].b] = edges[j].a;
          changed = true;
        }

      if (!changed)
        break;
    }

    // if we can make a path shorter, there exists a negative cycle
    for (int i = 0; i < M; i++)
      if (dist[edges[i].b] > dist[edges[i].a] + edges[i].c)
        return null; // negative cycle

    return prev; // no cycles, parent pointers to recreate path
    // replace this with true/false if you don't care about parent pointers
  }
}

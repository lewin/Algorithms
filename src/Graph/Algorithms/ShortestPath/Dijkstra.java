package Graph.Algorithms.ShortestPath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

public class Dijkstra {
  public static int INF = 1 << 29;
  
  static class Edge implements Comparable<Edge>{
    public int to, weight;
    public Edge(int to, int weight) {
      this.to = to;
      this.weight = weight;
    }
    
    public int compareTo(Edge other) {
      return weight - other.weight;
    }
  }
  private static void dijkstra(ArrayList<Edge>[] graph, int[] dist, int start) {
    int N = dist.length;
    Arrays.fill(dist, INF);
    PriorityQueue<Edge> pq = new PriorityQueue<Edge>();
    pq.add(new Edge(start, 0));

    while (pq.size() > 0) {
      int node = pq.peek().to, weight = pq.peek().weight; pq.poll();
      if (dist[node] != weight) continue;
      
      for (Edge e : graph[node]) {
        if (dist[e.to] < dist[node] + e.weight)
          pq.add(new Edge(e.to, dist[e.to] = dist[node] + e.weight));
      }
    }
  }
}

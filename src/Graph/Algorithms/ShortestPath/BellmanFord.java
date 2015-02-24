package Graph.Algorithms.ShortestPath;

public class BellmanFord {
  public static int INF = 1 << 29;
  static class Edge {
    public int from, to, weight;
    public Edge(int from, int to, int weight) {
      this.from = from;
      this.to = to;
      this.weight = weight;
    }
  }

  private static int[] bellman_ford(Edge[] edges, int[] dist, int start) {
    int N = dist.length;
    int M = edges.length;
    
    int[] prev = new int[N];
    for (int i = 0; i < N; i++) {
      dist[i] = INF;
      prev[i] = -1;
    }

    dist[start] = 0;
    for (int i = 0; i < N; i++) {
      boolean changed = false;

      for (int j = 0; j < M; j++)
        if (dist[edges[j].from] + edges[j].weight < dist[edges[j].to]) {
          dist[edges[j].to] = dist[edges[j].from] + edges[j].weight;
          prev[edges[j].to] = edges[j].from;
          changed = true;
        }

      if (!changed)
        break;
    }

    for (int i = 0; i < M; i++)
      if (dist[edges[i].to] > dist[edges[i].from] + edges[i].weight)
        return null;

    return prev;
  }
}

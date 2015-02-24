package Graph.Algorithms.ShortestPath;

public class FloydWarshall {
  private static void floyd_warshall(int[][] dist) {
    int N = dist.length;
    for (int k = 0; k < N; k++)
      for (int i = 0; i < N; i++)
        for (int j = 0; j < N; j++)
          dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
    // dist [i][j] is the shortest distance from node i to node j
    // if dist [i][j] == INF, then they are not connected
  }
}

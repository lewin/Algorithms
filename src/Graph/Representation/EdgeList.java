package Graph.Representation;

public class EdgeList implements Graph {
  static class Edge {
    public int from, to, weight;

    public Edge(int from, int to, int weight) {
      this.from = from;
      this.to = to;
      this.weight = weight;
    }
  }

  public static int N, M, idx;
  public static Edge[] edges;

  @Override
  public void init(int N, int M) {
    this.N = N;
    this.M = M;
    edges = new Edge[M];
    idx = 0;
  }

  @Override
  public void addEdge(int a, int b, int weight) {
    edges[idx++] = new Edge(a, b, weight);
  }

}

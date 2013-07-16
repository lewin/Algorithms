package Graph.Representation;

public interface Graph {
    // N number of nodes
    // nodes labeled 0...N-1
    // M number of edges
    public static final int INF = 1 << 29;
    public void init (int N, int M);
    public void addEdge (int a, int b, int weight);
}

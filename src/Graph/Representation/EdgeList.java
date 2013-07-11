package Graph.Representation;

public class EdgeList implements Graph {
    static class Edge {
        public int from, to, weight;
        public Edge (int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }
    
    public int M;
    public Edge [] edges;
    private int idx;

    @Override
    public void init(int N, int M) {
        this.M = M;
        edges = new Edge [M];
        idx = 0;
    }
    
    @Override
    public void addEdge(int a, int b, int weight) {
        edges [idx++] = new Edge (a, b, weight);
    }
    
}

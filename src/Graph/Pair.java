package Graph;

public class Pair implements Comparable <Pair> {
    public int a, b;
    public Pair (int a, int b) {
        this.a = a;
        this.b = b;
    }
    
    public int compareTo(Pair other) {
        return b == other.b ? a - other.a : b - other.b;
    }
    
}

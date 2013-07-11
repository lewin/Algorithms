package Graph;

public class Triplet implements Comparable <Triplet> {
    public int a, b, c;
    
    public Triplet (int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    
    public int compareTo (Triplet other) {
        return c == other.c ? b == other.b ? a - other.a : b - other.b : c - other.c;
    }
}

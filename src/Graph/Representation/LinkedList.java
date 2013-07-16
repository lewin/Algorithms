package Graph.Representation;

/**
 * 
 * @author Lewin
 *
 */
public class LinkedList implements Graph {
    public static int []   eadj, elast, eprev, ecost;
    public static int      eidx;
    public static int N, M;
    
    @Override
    public void init (int N, int M) {
        this.N = N;
        this.M = M;
        
        eadj = new int [2 * M];
        eprev = new int [2 * M];
        ecost = new int [2 * M];
        elast = new int [N];
        eidx = 0;
    }
    
    @Override
    public void addEdge (int a, int b, int weight) {
        eadj [eidx] = b; ecost [eidx] = weight; eprev [eidx] = elast [a]; elast [a] = eidx++;
        // if don't want bidirectional, comment out below line
        eadj [eidx] = a; ecost [eidx] = weight; eprev [eidx] = elast [b]; elast [b] = eidx++;
    }
}

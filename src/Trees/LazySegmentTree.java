package Trees;

public class LazySegmentTree {
    public static int []        tree, lazy;
    public static final int INF = 1 << 29;
    public static int           PN, N;
    
    // PN = 1<<(f(N))
    // f(N): for(i = 0; N>>i > 0; i++)
    
    private static void modify (int pos, int val) {
        tree[pos] += val;
        lazy[pos] += val;
    }
    
    private static void push (int pos) {
        modify (2 * pos, lazy[pos]);
        modify (2 * pos + 1, lazy[pos]);
        lazy[pos] = 0;
    }
    
    private static void join (int pos) {
        tree[pos] = Math.min (tree[2 * pos], tree[2 * pos + 1]);
    }
    
    private static int query (int pos, int start, int end, int a, int b) {
        if (a <= start && end <= b)
            return tree[pos];
        if (pos >= PN || start >= b || a >= end)
            return INF;
        push (pos);
        int mid = (start + end) >> 1;
        return Math.min (query (2 * pos, start, mid, a, b),
                query (2 * pos + 1, mid + 1, end, a, b));
    }
    
    private static void update (int pos, int start, int end, int a, int b,
            int val) {
        if (a <= start && end <= b) {
            modify (pos, val);
            return;
        }
        if (pos >= PN || start >= b || a >= end)
            return;
        push (pos);
        int mid = (start + end) >> 1;
        update (2 * pos, start, mid, a, b, val);
        update (2 * pos + 1, mid + 1, end, a, b, val);
        join (pos);
    }
    
    private static void build (int pos, int start, int end) {
        if (pos >= PN || start >= N)
            return;
        int mid = (start + end) >> 1;
        build (2 * pos, start, mid);
        build (2 * pos + 1, mid + 1, end);
        tree[pos] = tree[2 * pos];
        lazy[pos] = 0;
        if (mid < N)
            tree[pos] = Math.min (tree[pos], tree[2 * pos + 1]);
    }
}

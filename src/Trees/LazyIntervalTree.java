package Trees;

public class LazyIntervalTree {
    public int  min, max, lazy, start, end;
    public LazyIntervalTree child1  = null, child2 = null;
    
    public LazyIntervalTree (int start, int end) {
        this.start = start;
        this.end = end;
        if (start != end) {
            int mid = (start + end) >> 1;
            child1 = new LazyIntervalTree (start, mid);
            child2 = new LazyIntervalTree (mid + 1, end);
        }
    }
    
    public void modify (int val) {
        min += val;
        max += val;
        lazy += val;
    }
    
    public void push () {
        if (child1 == null)
            return;
        child1.modify (lazy);
        child2.modify (lazy);
        lazy = 0;
    }
    
    public void join () {
        if (child1 == null)
            return;
        min = Math.min (child1.min, child2.min);
        max = Math.max (child1.max, child2.max);
    }
    
    public int minQuery (int a, int b) {
        if (a <= start && end <= b)
            return min;
        if (start > b || a > end)
            return Integer.MAX_VALUE;
        if (child1 == null)
            return min;
        
        push ();
        return Math.min (child1.minQuery (a, b), child2.minQuery (a, b));
    }
    
    public int maxQuery (int a, int b) {
        if (a <= start && end <= b)
            return max;
        if (start > b || a > end)
            return -Integer.MAX_VALUE;
        if (child1 == null)
            return max;
        
        push ();
        return Math.max (child1.maxQuery (a, b), child2.maxQuery (a, b));
    }
    
    public void updateRange (int a, int b, int val) {
        if (a <= start && end <= b) {
            modify (val);
            return;
        }
        if (start > b || a > end)
            return;
        if (child1 == null)
            return;
        
        push ();
        child1.updateRange (a, b, val);
        child2.updateRange (a, b, val);
        join ();
    }
}

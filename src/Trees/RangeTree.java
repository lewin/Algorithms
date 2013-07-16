package Trees;

public class RangeTree {
    public int  start, end, num;
    public RangeTree    child1  = null, child2 = null;
    
    public RangeTree (int start, int end) {
        this.start = start;
        this.end = end;
        if (start != end) {
            child1 = new RangeTree (start, (start + end) / 2);
            child2 = new RangeTree ((start + end) / 2 + 1, end);
        }
    }
    
    public void insert (int ind) {
        num++;
        if (child1 == null)
            return;
        if (child1.end >= ind)
            child1.insert (ind);
        else
            child2.insert (ind);
    }
    
    public void delete (int ind) {
        num--;
        if (child1 == null)
            return;
        if (child1.end >= ind)
            child1.delete (ind);
        else
            child2.delete (ind);
    }
    
    public int query (int st, int en) {
        if (start == st && end == en)
            return num;
        if (st > child1.end)
            return child2.query (st, en);
        if (en < child2.start)
            return child1.query (st, en);
        return child1.query (st, child1.end) + child2.query (child2.start, en);
    }
    
}

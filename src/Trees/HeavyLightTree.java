package Trees;

import java.util.Arrays;

public class HeavyLightTree {
  public static int MAXN = 10000;
  static {
    size = new int[MAXN];
    cp = new int[MAXN];
    depth = new int[MAXN];
    anc = new int[15][MAXN];
    bestchild = new int[MAXN];
    whchain = new int[MAXN];
    chainhead = new int[MAXN];
    index = new int[MAXN];
    chains = new int[MAXN];
    emp = new Edge[MAXN];
    g = new Graph (MAXN, MAXN - 1);
  }

  static class Graph {
    public int[] eadj, eprev, elast, ecost;
    public int eidx;

    public Graph(int N, int M) {
      eadj = new int[2 * M];
      eprev = new int[2 * M];
      ecost = new int[2 * M];
      elast = new int[N];
      reset();
    }
    
    public void reset() {
      Arrays.fill (elast, -1);
      eidx = 0;
    }

    public void addEdge(int a, int b, int c) {
      eadj[eidx] = b;
      ecost[eidx] = c;
      eprev[eidx] = elast[a];
      elast[a] = eidx++;
      eadj[eidx] = a;
      ecost[eidx] = c;
      eprev[eidx] = elast[b];
      elast[b] = eidx++;
    }
  }

  static class Edge {
    public int a, b;

    public Edge(int a, int b) {
      this.a = a;
      this.b = b;
    }

    @Override
    public int hashCode() {
      return a * 10000 + b;
    }

    @Override
    public boolean equals(Object other) {
      if (!(other instanceof Edge))
        return false;
      return ((Edge) other).a == a && ((Edge) other).b == b;
    }
  }

  public static int[] arr;

  static class SegmentTree {
    public int start, end, max;
    public SegmentTree left, right;

    public SegmentTree(int start, int end) {
      this.start = start;
      this.end = end;
      if (start == end) {
        max = arr[start];
      } else {
        int mid = (start + end) >> 1;
        left = new SegmentTree(start, mid);
        right = new SegmentTree(mid + 1, end);
        max = Math.max(left.max, right.max);
      }
    }

    public void update(int pos, int val) {
      if (start == pos && end == pos) {
        max = val;
        return;
      }
      int mid = (start + end) >> 1;
      if (mid >= pos)
        left.update(pos, val);
      else
        right.update(pos, val);
      max = Math.max(left.max, right.max);
    }

    public int query(int s, int e) {
      if (start == s && end == e) {
        return max;
      }
      int mid = (start + end) >> 1;
      if (mid >= e)
        return left.query(s, e);
      else if (mid < s)
        return right.query(s, e);
      else
        return Math.max(left.query(s, mid), right.query(mid + 1, e));
    }
  }

  public static int N;
  public static Graph g;
  public static Edge[] emp;

  public static int[] size, cp, depth;
  public static int[][] anc;
  public static int[] bestchild;

  public static int dfs0(int node, int par) {
    anc[0][node] = par;
    depth[node] = par == -1 ? 0 : depth[par] + 1;
    size[node] = 1;
    bestchild[node] = -1;
    int max = 0;
    for (int e = g.elast[node]; e != -1; e = g.eprev[e]) {
      int next = g.eadj[e];
      if (next == par)
        continue;
      cp[next] = g.ecost[e];
      int sizen = dfs0(next, node);
      if (sizen > max) {
        max = sizen;
        bestchild[node] = next;
      }
      size[node] += sizen;
    }
    return size[node];
  }

  public static int[] whchain, chainhead, index, chains;
  public static int cidx, widx;

  public static void dfs1(int node, int par) {
    whchain[node] = widx;
    index[node] = cidx;
    chains[cidx++] = node;
    
    if (bestchild[node] == -1)
      return;
    dfs1(bestchild[node], node);
    for (int e = g.elast[node]; e != -1; e = g.eprev[e]) {
      int next = g.eadj[e];
      if (next == par || next == bestchild[node])
        continue;
      widx++;
      chainhead[widx] = next;
      dfs1(next, node);
    }
  }

  public static SegmentTree root;
  public static void init() {
    dfs0(0, -1);
    for (int i = 1; i < 15; i++) {
      for (int j = 0; j < N; j++) {
        anc[i][j] = anc[i - 1][j] == -1 ? -1 : anc[i - 1][anc[i - 1][j]];
      }
    }
    chainhead[0] = 0;
    widx = 0;
    cidx = 0;
    dfs1(0, -1);
    arr = new int[cidx];
    for (int i = 0; i < cidx; i++)
      arr[i] = cp[chains[i]];
    root = new SegmentTree(0, cidx - 1);
  }

  public static int lca(int a, int b) {
    if (depth[a] < depth[b]) {
      a ^= b;
      b ^= a;
      a ^= b;
    }

    int diff = depth[a] - depth[b];
    for (int i = 0; i < anc.length && diff > 0; i++, diff >>= 1)
      if ((diff & 1) != 0)
        a = anc[i][a];

    if (a == b)
      return a;

    for (int i = anc.length - 1; i >= 0; i--)
      if (anc[i][a] != anc[i][b]) {
        a = anc[i][a];
        b = anc[i][b];
      }

    return anc[0][a];
  }

  public static int query_up(int a, int b) {
    // a is an ancestor of b
    int achain = whchain[a], bchain = whchain[b];
    int ans = 0;
    while (achain != bchain) { // try to get them on the same chain
      if (b != chainhead[bchain])
        ans = Math.max(ans, root.query(index[chainhead[bchain]], index[b]));
      b = anc[0][chainhead[bchain]];
      bchain = whchain[b];
    }
    if (a != b)
      ans = Math.max(ans, root.query(index[a] + 1, index[b]));
    return ans;
  }

  public static int query(int a, int b) {
    int lca = lca(a, b);
    return Math.max(query_up(lca, a), query_up(lca, b));
  }

  public static void update(int x, int c) {
    Edge e = emp[x];
    int a = e.a, b = e.b;
    if (depth[a] < depth[b]) {
      int t = a; a = b; b = t;
    }
    root.update(index[b], c);
  }
}

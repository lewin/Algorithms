package Trees;

public class LinkCutTree {
  private static final int INF = 1000000000;
  private static final TEdge NULL = new TEdge(null, INF);

  static class TEdge {
    public Node n;
    public int weight;

    public TEdge(Node n, int weight) {
      this.n = n;
      this.weight = n == null ? INF : weight;
    }

    public String toString() {
      return (n == null ? null : n.id) + " " + weight;
    }
  }

  static class Node {
    int id, min;
    TEdge left;
    TEdge right;
    TEdge parent;
    boolean flip;

    public Node(int id) {
      this.id = id;
      this.min = INF;
      left = NULL;
      right = NULL;
      parent = NULL;
    }

    public String toString() {
      return id + " " + "|L: " + left + " |R: " + right + " |P: " + parent;
    }
  }

  static void push(Node x) {
    if (!x.flip)
      return;
    x.flip = false;
    TEdge t = x.left;
    x.left = x.right;
    x.right = t;
    if (x.left.n != null)
      x.left.n.flip = !x.left.n.flip;
    if (x.right.n != null)
      x.right.n.flip = !x.right.n.flip;
  }

  // Whether x is a root of a splay tree
  static boolean isRoot(Node x) {
    return x.parent.n == null || (x.parent.n.left.n != x && x.parent.n.right.n != x);
  }

  static void connect(Node ch, Node p, int weight, boolean leftChild) {
    if (leftChild)
      p.left = new TEdge(ch, weight);
    else
      p.right = new TEdge(ch, weight);

    p.min =
        Math.min(p.right.n == null ? INF : Math.min(p.right.n.min, p.right.weight),
            p.left.n == null ? INF : Math.min(p.left.n.min, p.left.weight));

    if (ch != null) {
      ch.parent = new TEdge(p, weight);
    }
  }

  // rotate edge (x, x.parent)
  static void rotate(Node x) {
    Node p = x.parent.n;
    Node g = p.parent.n;
    boolean isRootP = isRoot(p);
    boolean leftChildX = (x == p.left.n);

    TEdge next = leftChildX ? x.right : x.left;
    int w1 = p.parent.weight;
    int w2 = x.parent.weight;
    int w3 = next.n == null ? w2 : next.weight;
    connect(next.n, p, w2, leftChildX);
    connect(p, x, w3, !leftChildX);

    if (!isRootP)
      connect(x, g, w1, p == g.left.n);
    else
      x.parent = new TEdge(g, w1);
  }

  static void splay(Node x) {
    while (!isRoot(x)) {
      Node p = x.parent.n;
      Node g = p.parent.n;
      if (!isRoot(p))
        push(g);
      push(p);
      push(x);
      if (!isRoot(p))
        rotate((x == p.left.n) == (p == g.left.n) ? p : x);
      rotate(x);
    }
    push(x);
  }

  // Makes node x the root of the virtual tree, and also x is the leftmost
  // node in its splay tree
  static Node expose(Node x) {
    Node last = null;
    int lw = INF;
    for (Node y = x; y != null; y = y.parent.n) {
      splay(y);
      y.left = new TEdge(last, lw);
      y.min =
          Math.min(last == null ? INF : Math.min(last.min, lw),
              y.right.n == null ? INF : Math.min(y.right.n.min, y.right.weight));
      last = y;
      lw = y.parent.weight;
    }
    splay(x);
    return last;
  }

  public static Node findRoot(Node x) {
    expose(x);
    while (x.right.n != null) {
      x = x.right.n;
      push(x);
    }
    return x;
  }

  // prerequisite: x and y are in distinct trees
  public static void link(Node x, Node y, int idx) {
    if (findRoot(x) == findRoot(y))
      throw new RuntimeException("error: x and y are connected");
    expose(x);
    x.flip = !x.flip; // evert
    x.parent = new TEdge(y, idx);
    expose(x);
  }

  public static boolean connected(Node x, Node y) {
    if (x == y)
      return true;
    return findRoot(x) == findRoot(y);
  }

  public static void cut(Node x, Node y) {
    expose(x);
    x.flip = !x.flip; // evert
    expose(y);
    if (y.right.n != x || x.left.n != null || x.right.n != null)
      throw new RuntimeException("error: no edge (x,y)");
    y.right.n.parent = NULL;
    y.right = NULL;
    y.min = y.left.n == null ? INF : Math.min(y.left.weight, y.left.n.min);
  }

  static int find(Node x, Node y) {
    expose(x);
    x.flip = !x.flip;
    expose(y);
    int res = INF;
    Node last = null;
    for (Node z = x; z != y; z = z.parent.n) {
      if (z.left.n != null && z != last)
        res = Math.min(res, Math.min(z.left.weight, z.left.n.min));
      res = Math.min(res, z.parent.weight);
      last = z.left.n;
    }
    return res;
  }
}

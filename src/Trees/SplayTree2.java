package Trees;

public class SplayTree2 {
  static class Node {
    int size;
    Node left;
    Node right;
    Node parent;
    boolean flip;
    char id;

    public Node(char id) {
      this.id = id;
      this.size = 1;
      left = null;
      right = null;
      parent = null;
    }

    public String toString() {
      return id + " " + size;
    }
  }

  static void push(Node x) {
    if (!x.flip)
      return;
    x.flip = false;
    Node t = x.left;
    x.left = x.right;
    x.right = t;
    if (x.left != null)
      x.left.flip = !x.left.flip;
    if (x.right != null)
      x.right.flip = !x.right.flip;
    join(x);
  }

  // Whether x is a root of a splay tree
  static boolean isRoot(Node x) {
    return x.parent == null;
  }

  static void connect(Node ch, Node p, boolean leftChild) {
    if (leftChild)
      p.left = ch;
    else
      p.right = ch;
    join(p);
    if (ch != null) {
      ch.parent = p;
    }
  }

  // rotate edge (x, x.parent)
  static void rotate(Node x) {
    Node p = x.parent;
    Node g = p.parent;
    boolean isRootP = isRoot(p);
    boolean leftChildX = (x == p.left);

    Node next = leftChildX ? x.right : x.left;
    connect(next, p, leftChildX);
    connect(p, x, !leftChildX);

    if (!isRootP)
      connect(x, g, p == g.left);
    else
      x.parent = g;
  }

  static void splay(Node x) {
    while (!isRoot(x)) {
      Node p = x.parent;
      Node g = p.parent;
      if (!isRoot(p))
        push(g);
      push(p);
      push(x);
      if (!isRoot(p))
        rotate((x == p.left) == (p == g.left) ? p : x);
      rotate(x);
    }
    push(x);
    root = x;
  }

  static Node cutLeft(Node x) {
    Node ret = x.left;
    if (ret != null) {
      x.left.parent = null;
      x.left = null;
      join(x);
    }
    return ret;
  }

  static Node cutRight(Node x) {
    Node ret = x.right;
    if (ret != null) {
      x.right.parent = null;
      x.right = null;
      join(x);
    }
    return ret;
  }

  static void join(Node x) {
    x.size = (x.left == null ? 0 : x.left.size) + (x.right == null ? 0 : x.right.size) + 1;
  }

  private static Node getElementAtPosition(Node start, int a) {
    Node cur = start;
    while (a > 0) {
      push(cur);
      int sz = (cur.left == null ? 0 : cur.left.size);
      if (a <= sz) {
        cur = cur.left;
        continue;
      }
      a -= sz + 1;
      if (a == 0)
        break;
      cur = cur.right;
    }
    splay(cur);
    return cur;
  }

  private static char getElement(int a) {
    return getElementAtPosition(root, a).id;
  }

  private static void flip(int a, int b) {
    if (a == b)
      return;
    Node right = getElementAtPosition(root, b);
    Node ra = cutRight(right);
    Node left = getElementAtPosition(root, a);
    Node la = cutLeft(left);
    splay(left);
    left.flip = !left.flip;
    push(left);
    connect(ra, left, false);
    splay(right);
    connect(la, right, true);
  }

  private static String s;
  private static Node initRec(int start, int end) {
    if (start == end) {
      return new Node(s.charAt(start - 1));
    }
    int mid = (start + end) >> 1;
    Node x = new Node(s.charAt(mid - 1));
    if (start <= mid - 1)
      connect(initRec(start, mid - 1), x, true);
    if (mid + 1 <= end)
      connect(initRec(mid + 1, end), x, false);
    return x;
  }

  private static Node root;
}

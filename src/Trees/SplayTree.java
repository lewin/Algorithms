package Trees;

// from Sedgewick
public class SplayTree<Key extends Comparable<Key>, Value> {

  private Node root;

  private class Node {
    private Key key;
    private Value value;
    private Node left, right;

    public Node(Key key, Value value) {
      this.key = key;
      this.value = value;
    }
  }

  public boolean contains(Key key) {
    return (get(key) != null);
  }

  // return value associated with the given key
  // if no such value, return null
  public Value get(Key key) {
    root = splay(root, key);
    int cmp = key.compareTo(root.key);
    if (cmp == 0)
      return root.value;
    else
      return null;
  }

  public void put(Key key, Value value) {
    // splay key to root
    if (root == null) {
      root = new Node(key, value);
      return;
    }

    root = splay(root, key);

    int cmp = key.compareTo(root.key);

    // Insert new node at root
    if (cmp < 0) {
      Node n = new Node(key, value);
      n.left = root.left;
      n.right = root;
      root.left = null;
      root = n;
    }

    // Insert new node at root
    else if (cmp > 0) {
      Node n = new Node(key, value);
      n.right = root.right;
      n.left = root;
      root.right = null;
      root = n;
    }

    // It was a duplicate key. Simply replace the value
    else if (cmp == 0) {
      root.value = value;
    }

  }

  public void remove(Key key) {
    if (root == null)
      return; // empty tree

    root = splay(root, key);

    int cmp = key.compareTo(root.key);

    if (cmp == 0) {
      if (root.left == null) {
        root = root.right;
      } else {
        Node x = root.right;
        root = root.left;
        splay(root, key);
        root.right = x;
      }
    }

    // else: it wasn't in the tree to remove
  }

  private Node splay(Node h, Key key) {
    if (h == null)
      return null;

    int cmp1 = key.compareTo(h.key);

    if (cmp1 < 0) {
      if (h.left == null) {
        return h;
      }
      int cmp2 = key.compareTo(h.left.key);
      if (cmp2 < 0) {
        h.left.left = splay(h.left.left, key);
        h = rotateRight(h);
      } else if (cmp2 > 0) {
        h.left.right = splay(h.left.right, key);
        if (h.left.right != null)
          h.left = rotateLeft(h.left);
      }

      if (h.left == null)
        return h;
      else
        return rotateRight(h);
    }

    else if (cmp1 > 0) {
      if (h.right == null) {
        return h;
      }

      int cmp2 = key.compareTo(h.right.key);
      if (cmp2 < 0) {
        h.right.left = splay(h.right.left, key);
        if (h.right.left != null)
          h.right = rotateRight(h.right);
      } else if (cmp2 > 0) {
        h.right.right = splay(h.right.right, key);
        h = rotateLeft(h);
      }

      if (h.right == null)
        return h;
      else
        return rotateLeft(h);
    }

    else
      return h;
  }

  // height of tree (1-node tree has height 0)
  public int height() {
    return height(root);
  }

  private int height(Node x) {
    if (x == null)
      return -1;
    return 1 + Math.max(height(x.left), height(x.right));
  }


  public int size() {
    return size(root);
  }

  private int size(Node x) {
    if (x == null)
      return 0;
    else
      return (1 + size(x.left) + size(x.right));
  }

  // right rotate
  private Node rotateRight(Node h) {
    Node x = h.left;
    h.left = x.right;
    x.right = h;
    return x;
  }

  // left rotate
  private Node rotateLeft(Node h) {
    Node x = h.right;
    h.right = x.left;
    x.left = h;
    return x;
  }
}

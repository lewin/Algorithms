/* Tree234.java */

package Trees;
public class Tree234 {
    static class Tree234Node {
        int keys, key1, key2, key3;
        Tree234Node parent, child1, child2, child3, child4;

        Tree234Node() {
            keys = 0;
            parent = child1 = child2 = child3 = child4 = null;
        }
        
        Tree234Node(Tree234Node p, int key) {
            this();
            keys = 1;
            key1 = key;
            parent = p;
        }

        // returns final position of the key
        protected int addKey(int key) {
            assert keys < 3;
            
            switch (keys++) {
                case 2 : key3 = key; if (key3 > key2) return 3; key3 = key2;
                case 1 : key2 = key; if (key2 > key1) return 2; key2 = key1;
                case 0 : key1 = key; return 1;
            }

            throw new RuntimeException();
        }

        // returns the new parent.
        protected Tree234Node ejectMiddle() {
            assert keys == 3 && (parent == null || parent.keys < 3);
            
            if (parent == null) parent = new Tree234Node();
            Tree234Node c1 = new Tree234Node (parent, key1);
            c1.updateChild (1, child1); c1.updateChild (2, child2);
            Tree234Node c2 = new Tree234Node (parent, key3);
            c2.updateChild (1, child3); c2.updateChild (2, child4);

            int pos = parent.addKey (key2);
            if (pos <= 2) parent.updateChild (4, parent.child3);
            if (pos <= 1) parent.updateChild (3, parent.child2);
            parent.updateChild (pos, c1); parent.updateChild (pos + 1, c2);

            return parent;
        }

        // update this child(num) with child
        private void updateChild(int num, Tree234Node child) {
            if (child != null) child.parent = this ;
            switch (num) {
                case 1 : child1 = child; break; case 2 : child2 = child; break;
                case 3 : child3 = child; break; case 4 : child4 = child; break;
            }
        }
        
        // returns true if this node has the key
        public boolean containsKey (int key) {
            return key == key1 || (keys > 1 && key == key2) || (keys > 2 && key == key3);
        }
        
        // returns next child according to key
        protected Tree234Node next (int key) {
            return key < key1 ? child1 : keys == 1 || key < key2 ? child2 
                    : keys == 2 || key < key3 ? child3 : child4;
        }
    }
    // END Tree234Node
    
	Tree234Node root;
	int size = 0;

	public Tree234() {
		root = null;
		size = 0;
	}

	public boolean find(int key) {
		for (Tree234Node cur = root; cur != null; cur = cur.next (key))
			if (cur.containsKey (key))
				return true;
		return false;
	}

	public void insert(int key) {
		if (find (key)) return;
		if (root == null) root = new Tree234Node();
		
		Tree234Node cur;
		for (cur = root; cur.keys == 3 || cur.child1 != null; 
			cur = (cur.keys == 3 ? cur.ejectMiddle() : cur).next(key));
		cur.addKey (key);
		
		size++; if (root.parent != null) root = root.parent;
	}
}
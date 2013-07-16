package Graph.Algorithms;
import static Graph.Representation.LinkedList.*;

public class TopologicalSort {
    private static int []   indeg;
    
    // note new addEdge method
    private static void d_addEdge (int a, int b, int c) { // directed + indegrees
        indeg[b]++; eadj[eidx] = b; eprev[eidx] = elast[a]; ecost[eidx] = c; elast[a] = eidx++;
    }
    
    private static boolean toposort (int [] topo) {
        int [] queue = new int [N];
        int front = 0, back = 0, idx = 0;
        
        // add all nodes with no incoming edges
        for (int i = 0; i < indeg.length; i++)
            if (indeg[i] == 0)
                queue[back++] = i;
        
        while (front != back) {
            int node = queue[front++];
            topo[idx++] = node;
            
            // expand current node, removing all of its edges
            for (int e = elast[node]; e != -1; e = eprev[e])
                if (--indeg[eadj[e]] == 0)
                    queue[back++] = eadj[e];
        }
        
        return idx != topo.length; // true if successful, false if not a DAG
        // topo will contain topologically sorted list of nodes
    }
}

package Graph.Algorithms;

import java.util.Arrays;
import static Graph.Representation.LinkedList.*;

public class StronglyConnectedComponents {
  private static int[] part, index, lowlink, stack, size;
  private static int pidx, sidx, ssize;
  private static boolean[] instack;

  private static void tarjan() {
    stack = new int[N];
    index = new int[N];
    part = new int[N];
    lowlink = new int[N];
    instack = new boolean[N];
    size = new int[N];
    Arrays.fill(index, -1);

    pidx = 0;
    sidx = 0;
    ssize = 0;
    for (int i = 0; i < N; i++)
      if (index[i] == -1)
        strongconnect(i);
  }

  private static void strongconnect(int node) {
    index[node] = pidx;
    lowlink[node] = pidx;
    pidx++;
    stack[sidx++] = node;
    instack[node] = true;

    for (int e = elast[node]; e != -1; e = eprev[e]) {
      if (index[eadj[e]] == -1) {
        strongconnect(eadj[e]);
        if (lowlink[eadj[e]] < lowlink[node])
          lowlink[node] = lowlink[eadj[e]];
      } else if (instack[eadj[e]]) {
        if (index[eadj[e]] < lowlink[node])
          lowlink[node] = index[eadj[e]];
      }
    }

    if (lowlink[node] == index[node]) {
      int cur, csize = 0;
      do {
        cur = stack[--sidx];
        csize++;
        part[cur] = ssize;
        instack[cur] = false;
      } while (cur != node);
      size[ssize++] = csize;
    }
  }
}

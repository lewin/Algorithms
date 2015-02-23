package Graph.Algorithms.NetworkFlow;

import java.util.Arrays;
public class Dinic {
  public static int N;
  public static int INF = 1 << 29;
  public static int[] eadj, eprev, elast;
  public static int eidx;
  private static int []   flow, capa, now;
  
  public static void init(int _N, int M) {
    N = _N;
    eadj = new int[M];
    eprev = new int[M];
    elast = new int[N];
    flow = new int[M];
    capa = new int[M];
    now = new int[N];
    level = new int[N];
    eidx = 0;
    Arrays.fill(elast, -1);
  }

  private static void add_edge (int a, int b, int c) {
    eadj[eidx] = b; flow[eidx] = 0; capa[eidx] = c; eprev[eidx] = elast[a]; elast[a] = eidx++;
    eadj[eidx] = a; flow[eidx] = c; capa[eidx] = c; eprev[eidx] = elast[b]; elast[b] = eidx++;
  }
    
  private static int dinic (int source, int sink) {
    int res, flow = 0;
    while (bfs (source, sink)) { // see if there is an augmenting path
      System.arraycopy (elast, 0, now, 0, N);
      while ((res = dfs (source, INF, sink)) > 0) // push all possible flow through
        flow += res;
    }
    return flow;
  }
    
  private static int []   level;
    
  private static boolean bfs (int source, int sink) {
    Arrays.fill (level, -1);
    int front = 0, back = 0;
    int [] queue = new int [N];
    level[source] = 0;
    queue[back++] = source;
    while (front < back && level[sink] == -1) {
      int node = queue[front++];
      for (int e = elast[node]; e != -1; e = eprev[e]) {
        int to = eadj[e];
        if (level[to] == -1 && flow[e] < capa[e]) {
          level[to] = level[node] + 1;
          queue[back++] = to;
        }
      }
    }

    return level[sink] != -1;
  }

  private static int dfs (int cur, int curflow, int goal) {
    if (cur == goal) return curflow;

    for (int e = now[cur]; e != -1; now[cur] = e = eprev[e]) {
      if (level[eadj[e]] > level[cur] && flow[e] < capa[e]) {
        int res = dfs (eadj[e], Math.min (curflow, capa[e] - flow[e]), goal);
        if (res > 0) {
          flow[e] += res;
          flow[e ^ 1] -= res;
          return res;
        }
      }
    }
    return 0;
  }
}

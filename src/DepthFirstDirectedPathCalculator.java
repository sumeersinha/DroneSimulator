

/**
 * @author sumeersinha
 *
 */
public class DepthFirstDirectedPathCalculator {
	private boolean[] marked;  // marked[v] = true if v is reachable from s
    private int[] edgeTo;      // edgeTo[v] = last edge on path from s to v
    private final int s;
	
    public DepthFirstDirectedPathCalculator(EdgeWeightedDigraph G, int s) {
		// TODO Auto-generated constructor stub
    	marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        dfs(G, s);
	}
    
    private void dfs(EdgeWeightedDigraph G, int v) { 
        marked[v] = true;
        for (DirectedEdge e : G.adj(v)) {
        	int w=e.to();
            if (!marked[w]) {
                edgeTo[w] = v;
                dfs(G, w);
            }
        }
    }
    
    public boolean hasPathTo(int v) {
        return marked[v];
    }
    
    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<Integer>();
        for (int x = v; x != s; x = edgeTo[x])
            path.push(x);
        path.push(s);
        return path;
    }
	
}

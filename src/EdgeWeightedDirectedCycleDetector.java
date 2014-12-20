

/**
 * @author sumeersinha
 *
 */
public class EdgeWeightedDirectedCycleDetector {
	private boolean marked[];
	private DirectedEdge[] edgeTo;
	private boolean[] onStack;
	private Stack<DirectedEdge> cycle;
	
	
	public EdgeWeightedDirectedCycleDetector(EdgeWeightedDigraph G) {
		// TODO Auto-generated constructor stub
		marked=new boolean[G.V()];
		onStack=new boolean[G.V()];
		edgeTo=new DirectedEdge[G.V()];
		for (int v = 0; v < G.V(); v++)
            if (!marked[v]) dfs(G, v);
		
		assert check(G);
	}


	private void dfs(EdgeWeightedDigraph G, int v) {
		// TODO Auto-generated method stub
		onStack[v]=true;
		marked[v]=true;
		for(DirectedEdge e:G.adj(v)){
			int w=e.to();
			
			if(cycle!=null) return;
			
			//if we find new vertex
			else if(!marked[w]){
				edgeTo[w]=e;
				dfs(G,w);
			}
			else if(onStack[w]){
				cycle=new Stack<DirectedEdge>();
				while(e.from()!=w){
					cycle.push(e);
					e=edgeTo[e.from()];
				}
				cycle.push(e);
			}
		}
		onStack[v]=false;
		
	}
	
	public boolean hasCycle() {
        return cycle != null;
    }

	
	public Iterable<DirectedEdge> cycle() {
        return cycle;
    }
	
	private boolean check(EdgeWeightedDigraph G) {

        // edge-weighted digraph is cyclic
        if (hasCycle()) {
            // verify cycle
            DirectedEdge first = null, last = null;
            for (DirectedEdge e : cycle()) {
                if (first == null) first = e;
                if (last != null) {
                    if (last.to() != e.from()) {
                        System.err.printf("cycle edges %s and %s not incident\n", last, e);
                        return false;
                    }
                }
                last = e;
            }

            if (last.to() != first.from()) {
                System.err.printf("cycle edges %s and %s not incident\n", last, first);
                return false;
            }
        }


        return true;
    }
	
	
	
}

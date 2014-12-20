

/**
 * @author sumeersinha
 *
 */
public class TopologicalSorterGen {
	
	private Iterable<Integer> order; 
	

	public TopologicalSorterGen(EdgeWeightedDigraph G) {
		// TODO Auto-generated constructor stub
		EdgeWeightedDirectedCycleDetector finder = new EdgeWeightedDirectedCycleDetector(G);
        if (!finder.hasCycle()) {
            DepthFirstOrder dfs = new DepthFirstOrder(G);
            order = dfs.reversePost();
        }
	}
	
	public Iterable<Integer> order() {
        return order;
    }

	public boolean hasOrder() {
        return order != null;
    }
	
}

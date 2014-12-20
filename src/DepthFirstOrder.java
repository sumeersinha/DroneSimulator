

/**
 * @author sumeersinha
 *
 */
public class DepthFirstOrder {
	
	private boolean marked[];
	private int pre[];
	private int post[];
	private Queue<Integer> preOrder;
	private Queue<Integer> postOrder;
	private int preCounter;
	private int postCounter;
	
	public DepthFirstOrder(EdgeWeightedDigraph G) {
		// TODO Auto-generated constructor stub
		pre=new int[G.V()];
		post=new int[G.V()];
		postOrder=new Queue<Integer>();
		preOrder=new Queue<Integer>();
		marked=new boolean[G.V()];
		for(int v=0;v<G.V();v++){
			if(!marked[v]) dfs(G,v);
		}
		
	}

	private void dfs(EdgeWeightedDigraph G, int v) {
		// TODO Auto-generated method stub
		marked[v]=true;
		pre[v]=preCounter++;
		preOrder.enqueue(v);
		for(DirectedEdge e:G.adj(v)){
			int w=e.to();
			if(!marked[w]) dfs(G,w);
		}
		postOrder.enqueue(v);
		post[v]=postCounter++;
	}
	
	public int pre(int v) {
        return pre[v];
    }
	
	public int post(int v) {
        return post[v];
    }
	
	public Iterable<Integer> post() {
        return postOrder;
    }
	
	public Iterable<Integer> pre() {
        return preOrder;
    }
	
	public Iterable<Integer> reversePost() {
        Stack<Integer> reverse = new Stack<Integer>();
        for (int v : postOrder)
            reverse.push(v);
        return reverse;
    }
	
	/*
	private boolean check(Digraph G) {

        // check that post(v) is consistent with post()
        int r = 0;
        for (int v : post()) {
            if (post(v) != r) {
                StdOut.println("post(v) and post() inconsistent");
                return false;
            }
            r++;
        }

        // check that pre(v) is consistent with pre()
        r = 0;
        for (int v : pre()) {
            if (pre(v) != r) {
                StdOut.println("pre(v) and pre() inconsistent");
                return false;
            }
            r++;
        }


        return true;
    }*/

}

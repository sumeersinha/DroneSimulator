

/**
 * @author sumeersinha
 *
 */
public class EdgeWeightedDigraph {
	
	private final int V;
	private int E;
	private Bag<DirectedEdge>[] adj;
	private int inDegree[];

	public EdgeWeightedDigraph(int V) {
		// TODO Auto-generated constructor stub
		if(V<0) throw new IllegalArgumentException("Number of vertices in Digraph must be non-negative");
		this.V=V;
		this.E=0;
		adj=(Bag<DirectedEdge>[])new Bag[V];
		inDegree=new int[V];
		for(int v=0;v<V;v++){
			adj[v]=new Bag<DirectedEdge>();
			inDegree[v]=0;
		}
	}
	
	public int V(){
		return V;
	}
	
	public int E(){
		return E;
	}
	
	public void addEdge(DirectedEdge e){
		
		int v = e.from();
		int w = e.to();
		
		inDegree[w]++;
        
		adj[v].add(e);
        E++;
	}
	
	public Iterable<DirectedEdge> adj(int v){
		if(v<0 || v>=V) throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (V-1));
		return adj[v];
	}
	
	public Iterable<DirectedEdge> edges(){
		Bag<DirectedEdge> list= new Bag<DirectedEdge>();
		for(int v=0;v<V;v++){
			for(DirectedEdge e:adj[v]){
				list.add(e);
			}
		}
		return list;
	}
	
	 public int outdegree(int v) {
	        if (v < 0 || v >= V) throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (V-1));
	        return adj[v].size();
	    }
	 
	 public int indegree(int v) {
	        if (v < 0 || v >= V) throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (V-1));
	        return inDegree[v];
	    }
	 
	
	public String toString(){
		StringBuilder s = new StringBuilder();
		String newLine=System.getProperty("line.separator");
		s.append(V + " vertices, "+E+" edges"+newLine);
		for(int v=0;v<V;v++){
			s.append(String.format("%d", v));
			for(DirectedEdge e:adj[v])
				s.append(e+" ");
			s.append(" "+inDegree[v]+newLine);
		}
		return s.toString();
	}
	
}

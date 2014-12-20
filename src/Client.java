

/**
 * @author sumeersinha
 * 
 */
public class Client {
	
	
	public static void main(String[] args) throws InterruptedException {
		int V=5;
		int E=3;
		int W=20;
		double P=0.7;
		
		boolean foundSequence=false;
		
		while(!foundSequence)
		{
			foundSequence=execute(V, E, W, P);
			System.out.println("********************");
			Thread.sleep(1000);
		}
		
	}

	public static boolean execute(int V, int E, int W, double P) {
		
		EdgeWeightedDigraph myGraph = EdgeWeightedRandomDigraphGen
				.simpleProbGen(V, E, W, P);
		System.out.println(myGraph);
		
		TopologicalSorterGen gen = new TopologicalSorterGen(myGraph);
		
		RandomNodeSeqGen nodeSeqGen=null;

		
		if (gen.hasOrder()) {
			System.out.println("\nTopological sort:");
			
			
			int arrayIndex=0;
			int topoSortMasterSequence[]=new int[((Stack)gen.order()).size()];
			for (int node : gen.order()) {
				System.out.print(node);
				topoSortMasterSequence[arrayIndex]=node;
				arrayIndex++;
			}
			
			DepthFirstDirectedPathCalculator calculator=null;
			
			for(int i=0;i<topoSortMasterSequence.length-1;i++){
				int v = topoSortMasterSequence[i];
				calculator=new DepthFirstDirectedPathCalculator(myGraph,v);
				for(int j=i+1;j<topoSortMasterSequence.length;j++){
					int w=topoSortMasterSequence[j];
					if(calculator.hasPathTo(w)){
						System.out.println("");
						for(int node:calculator.pathTo(w))
							StdOut.print("-" + node);
					}
				}
			}
			
			
			
			nodeSeqGen=new RandomNodeSeqGen(myGraph);
			
			
			int seq[] = nodeSeqGen.generateSequence();

			System.out.println("\nGenerated sequence:");
			for(int i=0; i<seq.length;i++){
				System.out.print(seq[i]);
			}

			
			boolean topoSortFlag=true;
			
			// check if generated sequence follows topological sort or not
			if (myGraph.indegree(seq[0]) == 0) {
				for (int q = 0; q < seq.length - 1; q++) {
					int node1 = seq[q];
					int node2 = seq[q + 1];
					if (!isDirectlyConnected(myGraph, node1, node2)) {
						System.out
								.println("\n"+node1
										+ " and "
										+ node2
										+ " is not directly connected! Hence, the generated sequence is not topologically ordered!");
						topoSortFlag=false;
						break;
					}
				}
			} else {
				System.out
						.println("\nNode:"+seq[0]+ " has greater than 0 in degree, Generated sequence is not topologicaly ordered");
				topoSortFlag=false;
			}

			if(topoSortFlag){
				System.out.println("\nGenerated sequence follows topological order");
				System.out.println("\nCalculating shortest path using Dijkstra's from Node:"+seq[0]);
				printShortestPath(myGraph, seq[0]);
				return true;
			}
				
		} else {
			System.out.println("\nRandomly generated graph is cyclic!!");
		}
		return false;
		
	}

	static boolean isDirectlyConnected(EdgeWeightedDigraph myGraph, int node1,
			int node2) {
		for (DirectedEdge e : myGraph.adj(node1)) {
			if (e.to() == node2) {
				System.out.println("\nDirect connection found between "+node1+" and "+node2+ " ...continuing with next node");
				return true;
			}
		}
		return false;
	}
	
	static void printShortestPath(EdgeWeightedDigraph G,int s){
		DijsktraShortestPath sp=new DijsktraShortestPath(G,s);
		for (int t = 0; t < G.V(); t++) {
            if (sp.hasPathTo(t)) {
                StdOut.printf("%d to %d (%.2f)  ", s, t, sp.distTo(t));
                if (sp.hasPathTo(t)) {
                    for (DirectedEdge e : sp.pathTo(t)) {
                        StdOut.print(e + "   ");
                    }
                }
                StdOut.println();
            }
            else {
                StdOut.printf("%d to %d         no path\n", s, t);
            }
        }
	}
	
	
	
}

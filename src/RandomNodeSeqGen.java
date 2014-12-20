

/**
 * @author sumeersinha
 *
 */
public class RandomNodeSeqGen {
	
	private boolean isMarked[];
	private int V;
	private EdgeWeightedDigraph myGraph;
	
	public RandomNodeSeqGen(EdgeWeightedDigraph myGraph) {
		// TODO Auto-generated constructor stub
		this.myGraph=myGraph;
		this.V=myGraph.V();
		isMarked=new boolean[myGraph.V()];
		for(int i=0;i<myGraph.V();i++){
			isMarked[i]=false;
		}
	}
	
	public int[] generateSequence(){
		int seq[]=new int[RandomGenerator.genRandom(3, V-1)];

		//find vertex with 0 in-degree
		int firstNode=zeroInDegNodeFinder(myGraph, V);

		if(firstNode==-1) System.out.println("No node exists with zero in-degree edges");
		
		seq[0]=firstNode;
		isMarked[firstNode]=true;
		
		int node=0;
		// generate sequence of nodes
		for(int i=1;i<seq.length;i++){
			while(true){
				node=RandomGenerator.genRandom(0, V);
				if(!isMarked[node]){
					seq[i]=node;
					isMarked[node]=true;
					break;
				}
			} 
		}
		
		return seq;
	}
	
	static int zeroInDegNodeFinder(EdgeWeightedDigraph myGraph, int V){
		for(int i=0;i<V;i++){
			if(myGraph.indegree(i)==0)
				return i;
		}
		return -1;
	}
	
	

}

/**
 * 
 */

/**
 * @author sumeersinha
 * 
 */
public class GraphEngine {

	private static String filename = "routes.txt";
	private static String delimiter = " ";
	
	
	private SymbolEdgeWeightedDigraph sg;
	private EdgeWeightedDigraph G;
	private Queue<String> targetQueue;
	
	public GraphEngine() {
		// TODO Auto-generated constructor stub
		sg = new SymbolEdgeWeightedDigraph(filename, delimiter);
		G=sg.G();
	}
	

	public Target getNewTarget(String src, String destination) throws NoPathException {
		// TODO Auto-generated method stub
		sg = new SymbolEdgeWeightedDigraph(filename, delimiter);
		G=sg.G();
		int v=sg.index(src);
		int w=sg.index(destination);
		String nextDest=null;
		DijsktraShortestPath shortestPathCalc = new DijsktraShortestPath(G, v);
		if(shortestPathCalc.hasPathTo(w)){
			for (DirectedEdge e : shortestPathCalc.pathTo(w)) {
				nextDest=sg.name(e.to());
				break;
			}
		}
		else{
			throw new NoPathException();
		}
		
		
		if(nextDest!=null){
			DestinationCoordinates coordinates=LocationLookUpUtil.map.get(nextDest);
			Target t=new Target(coordinates.getX(),coordinates.getY(),nextDest);
			return t;
		}
		//Check
		else{
			return null;
		}
	}


	public Target getTarget(String src) {
		// TODO Auto-generated method stub
		DestinationCoordinates coordinates=LocationLookUpUtil.map.get(src);
		Target t=new Target(coordinates.getX(),coordinates.getY(),src);
		return t;
	}

}

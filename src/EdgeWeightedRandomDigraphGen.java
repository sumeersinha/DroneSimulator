

/**
 * @author sumeersinha
 *
 */
public class EdgeWeightedRandomDigraphGen {
	public static EdgeWeightedDigraph simpleProbGen(int V, int degreeOnV,double weightRange, double p) {
		if(V<3){
			System.out.println("Taking default V as 5");
			V=5;
		}
		
        if (p < 0.0 || p > 1.0)
            throw new IllegalArgumentException("Probability must be between 0 and 1");
        EdgeWeightedDigraph G = new EdgeWeightedDigraph(V);
        
        int randomDegOnV=0;
        double randWeight=0;
        
        DirectedEdge e=null;
        
        for (int v = 0; v < V; v++){
        	randomDegOnV=RandomGenerator.genRandom(0, degreeOnV);
        	System.out.println("Random Degree on "+v+" is "+randomDegOnV);
        	for (int w = 0; w < randomDegOnV; w++)
                if (v != w)
                    if (StdRandom.bernoulli(p)){
                    	randWeight=RandomGenerator.genRandomDouble(1.0,weightRange);
                    	e=new DirectedEdge(v,w,randWeight);
                    	G.addEdge(e);
                    }

        }
        return G;
    }
}

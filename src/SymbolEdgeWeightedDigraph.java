
import java.util.ArrayList;

/**
 * @author sumeersinha
 * 
 */
public class SymbolEdgeWeightedDigraph {
	private ST<String, Integer> st;
	private String[] keys;
	private EdgeWeightedDigraph G;

	public SymbolEdgeWeightedDigraph(String filename, String delimiter) {
		st = new ST<String, Integer>();

		// First pass builds the index by reading strings to associate
		// distinct strings with an index
		In in = new In(filename);
		while (in.hasNextLine()) {
			String[] a = in.readLine().split(delimiter);
			for (int i = 0; i < a.length; i++) {
				if (!st.contains(a[i]))
					st.put(a[i], st.size());
			}
		}

		// inverted index to get string keys in an array
		keys = new String[st.size()];
		for (String name : st.keys()) {
			keys[st.get(name)] = name;
		}

		// second pass builds the edge-weighted-digraph by connecting first
		// vertex on each
		// line to all others
		G = new EdgeWeightedDigraph(st.size());
		in = new In(filename);

		DirectedEdge edge = null;

		while (in.hasNextLine()) {
			String[] a = in.readLine().split(delimiter);
			int v = st.get(a[0]);
			for (int i = 1; i < a.length; i++) {
				int w = st.get(a[i]);
				edge = new DirectedEdge(v, w, getWeight(v, w));
				G.addEdge(edge);
			}
		}
	}

	private double getWeight(int v, int w) {
		// TODO Set weight with respect to destination weather
		System.out.println("Fetching weight");
		String source = keys[v];
		String destination = keys[w];
		System.out.println("Source:" + source + "\tDestination:" + destination);
		double weight= Math.round(100 * Math.random()) / 100.0;
		return weight;
	}

	public boolean contains(String s) {
		return st.contains(s);
	}

	public int index(String s) {
		return st.get(s);
	}

	public String name(int v) {
		return keys[v];
	}

	public EdgeWeightedDigraph G() {
		return G;
	}

	public static void main(String[] args) throws InterruptedException {
		String filename = args[0];
		String delimiter = args[1];
		SymbolEdgeWeightedDigraph sg;
		EdgeWeightedDigraph G;
		DijsktraShortestPath shortestPathCalc;
		while (true) {
			sg = new SymbolEdgeWeightedDigraph(filename, delimiter);
			G = sg.G();

			// Find shortest path to destination
			shortestPathCalc = new DijsktraShortestPath(G, sg.index("JFK"));
			if (shortestPathCalc.hasPathTo(sg.index("DEN"))) {
				for (DirectedEdge e : shortestPathCalc.pathTo(sg.index("DEN"))) {
					StdOut.print(sg.name(e.from()) + "->" + sg.name(e.to())
							+ " " + e.weight() + "    ");
				}
			}

			while (!StdIn.isEmpty()) {
				String t = StdIn.readLine();
				for (DirectedEdge e : G.adj(sg.index(t))) {
					int v = e.to();
					StdOut.println("   " + sg.name(v));
				}
				StdIn.readLine();
				break;
			}

		}

	}

}

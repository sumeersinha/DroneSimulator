

/**
 * @author sumeersinha
 *
 */
public class DirectedEdge implements Comparable<DirectedEdge> {
	private final int v;
	private final int w;
	private final double weight;
	
	public DirectedEdge(int v, int w, double weight) {
		// TODO Auto-generated constructor stub
		if(v<0) throw new IndexOutOfBoundsException("Vertex name must be non-negative integer");
		if(w<0) throw new IndexOutOfBoundsException("Vertex name must be non-negative integer");
		if(Double.isNaN(weight)) throw new IllegalArgumentException("Weight is NaN");
		this.v=v;
		this.w=w;
		this.weight=weight;
	}
	
	public double weight(){
		return weight;
	}
	
	public int from(){
		return v;
	}
	
	public int to(){
		return w;
	}
	
	public int compareTo(DirectedEdge that) {
        if      (this.weight() < that.weight()) return -1;
        else if (this.weight() > that.weight()) return +1;
        else                                    return  0;
    }
	
	public String toString() {
        return String.format(" %d->%d %5.2f", v, w, weight);
    }
	
}

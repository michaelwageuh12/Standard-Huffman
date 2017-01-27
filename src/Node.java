public class Node implements Comparable<Node> {
	String s ;
	double p ;
	Node left ;
	Node right ;
	String code ;
	
	public Node(){
		s = "" ;
		p = 0.0 ;
		left = right = null ;
	}
	@Override
	public int compareTo(Node o) {
		return Double.compare(o.p, this.p);
	}
}
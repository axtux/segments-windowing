package data;
/**
 * Node to be used into a {@link Pst} containing a Segment, a median, a reference to left and right node.
 */
public class PstNode {
	private Segment segment;
	private int median;
	private PstNode left;
	private PstNode right;
	
	/**
	 * Set left node.
	 * @param left Left node. Can be null.
	 */
	public void setLeft(PstNode left) {
		this.left = left;
	}
	/**
	 * Set right node.
	 * @param right Right node. Can be null.
	 */
	public void setRight(PstNode right) {
		this.right = right;
	}
	/**
	 * Set median.
	 * @param median New median.
	 */
	public void setMedian(int median) {
		this.median = median;
	}
	/**
	 * Get median.
	 * @return Median number used by some trees.
	 */
	public int getMedian() {
		return median;
	}
	/**
	 * Get left node.
	 * @return Left node.
	 */
	public PstNode getLeft() {
		return left;
	}
	/**
	 * Get right node.
	 * @return Right node.
	 */
	public PstNode getRight() {
		return right;
	}
	/**
	 * Get data.
	 * @return Get data object.
	 */
	public Segment getSegment() {
		return segment;
	}
	/**
	 * Create node containing segment and median set to 0.
	 * @param segment Segment contained by this node.
	 */
	public PstNode(Segment segment){
		this(segment, 0);
	}
	/**
	 * Create node containing segment and median.
	 * @param segment Segment contained by this node.
	 * @param median Median of this node.
	 */
	public PstNode(Segment segment, int median){
		this.segment = segment;
		this.median = median;
	}
	/**
	 * Print a node and all children.
	 * @return String representation of this node and all children.
	 */
	public String toString() {
		return toString("")+"\n";
	}
	/**
	 * Print a node and all children with prefix before each line.
	 * @param prefix Prefix to add at the beginning of each line.
	 * @return String representation of this node and all children.
	 */
	private String toString(String prefix) {
		String s = prefix + segment;
		
		if(left != null) {
			s += "\nl-son:"+left.toString(prefix+"|-----");
		}
		if(right != null) {
			s += "\nr-son:"+right.toString(prefix+"|-----");
		}
		
		return s;
	}
	public boolean isLeaf(){
		return (left==null && right==null);
	}

	/**
	 * This method is used to get the height of the Node in the Pst
	 * @return A int.
	 */
	public int getHeight(){
		if (isLeaf())
			return 1;
		else if (getLeft()==null)
			return 1+getRight().getHeight();
		else if (getRight()==null)
			return 1+getLeft().getHeight();
		else
			return 1+Math.max( getLeft().getHeight(), getRight().getHeight() );
	}
}
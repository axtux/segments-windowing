package data;
/**
 * Node to be used into a {@link Pst} containing a Segment, a median, a reference to left and right node.
 */
public class PstNode {
	private Segment segment;
	private int median;
	private PstNode left;
	private PstNode right;
	private boolean flag;
	

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
	 * Set flag.
	 * @param flag Flag value.
	 */
	public void setFlag(boolean flag){
		this.flag = flag;
	}
	/**
	 * Get flag.
	 * @return Flag value.
	 */
	public boolean getFlag() {
		return flag;
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
	 * String representation of this node and sub nodes.
	 */
	public String toString() {
		return toString("")+"\n";
	}
	/**
	 * This method print a BasicPst wich the root is given in parameter using the printSeg() method in Segment.
	 * @param temp the root of the tree to be print
	 * @param acc the Symbol of a node ( examples : @,|,(), ...)
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
}
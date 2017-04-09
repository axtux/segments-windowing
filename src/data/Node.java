package data;
/**
 * Node containing a data, a reference to left and right node and a median.
 * @param <E> Class to be used as node data.
 */
public class Node<E> {
	private E data;
	private float median;
	private Node<E> left;
	private Node<E> right;
	/**
	 * Set left node.
	 * @param left Left node. Can be null.
	 */
	public void setLeft(Node<E> left) {
		this.left = left;
	}
	/**
	 * Set right node.
	 * @param right Right node. Can be null.
	 */
	public void setRight(Node<E> right) {
		this.right = right;
	}
	/**
	 * Get median.
	 * @return Median number used by some trees.
	 */
	public float getMedian() {
		return median;
	}
	/**
	 * Get left node.
	 * @return Left node.
	 */
	public Node<E> getLeft() {
		return left;
	}
	/**
	 * Get right node.
	 * @return Right node.
	 */
	public Node<E> getRight() {
		return right;
	}
	/**
	 * Get data.
	 * @return Get data object.
	 */
	public E getData() {
		return data;
	}
	/**
	 * Create node with custom data and median set to 0.
	 * @param data Data owned by this node.
	 */
	public Node(E data){
		this(data, 0);
	}
	/**
	 * Create node with custom data and median.
	 * @param data Data owned by this node.
	 * @param median Median of this node.
	 */
	public Node(E data, float median){
		this.data=data;
		this.median=median;
	}
}
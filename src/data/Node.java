package data;
/**
 * Created by marco on 1/04/17.
 */
public class Node {
	private Segment data;
	private final float median;
	private Node left;
	private Node right;
	
	public void setLeft(Node left) {
		this.left = left;
	}
	
	public void setRight(Node right) {
		this.right = right;
	}
	
	public float getMedian() {
		return median;
	}
	
	public Node getLeft() {
		return left;
	}
	
	public Node getRight() {
		return right;
	}
	
	public Segment getData() {
		return data;
	}
	
	public Node(Segment data){
		this.data=data;
		median = 0;//put a special number here
	}
	
	public Node(Segment data, float median){
		this.data=data;
		this.median=median;
	}
}
package data;
/**
 * Created by marco on 1/04/17.
 */
public class Node<E> {
	private E data;
	private float median;
	private Node<E> left;
	private Node<E> right;
	private boolean flag=false;
	
	public void setLeft(Node<E> left) {
		this.left = left;
	}
	
	public void setRight(Node<E> right) {
		this.right = right;
	}
	
	public float getMedian() {
		return median;
	}
	
	public Node<E> getLeft() {
		return left;
	}
	
	public Node<E> getRight() {
		return right;
	}
	
	public E getData() {
		return data;
	}

	public void putflag(){
		flag=true;
	}

	public Node(E data){
		this.data=data;
		median = 0;//put a special number here
	}
	
	public Node(E data, float median){
		this.data=data;
		this.median=median;
	}

	public boolean notmarqued() {
		return !flag;
	}
}
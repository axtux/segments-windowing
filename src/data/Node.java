package data;
/**
 * Created by marco on 1/04/17.
 */
public class Node {

	private Seg data;
	public final float median;
	public Node nextr;
	public Node nextl;

	public void setNextl(Node nextl) {
        this.nextl = nextl;
    }

	public void setNextr(Node nextr) {
        this.nextr = nextr;
    }

	public float getMedian() {
        return median;
    }

	public Seg getData() {
		return data;
	}

	public Node(Seg data){
		this.data=data;
		median = 0;
	}

	public Node(Seg data, float median){
		this.data=data;
		this.median=median;
	}
}
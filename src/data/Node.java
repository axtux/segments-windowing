package data;
/**
 * Created by marco on 1/04/17.
 */
public class Node {

	private Segment data;
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

	public Segment getData() {
		return data;
	}

	public Node getNextl() {
		return nextl;
	}

	public Node getNextr() {

		return nextr;
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
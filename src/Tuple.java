/**
 * Created by marco on 28/03/17.
 */

public class Tuple implements Comparable<Tuple> {
	private final int x1;
	private final int x2;
	private final int y1;
	private final int y2;
	
	public Tuple(int x1, int x2, int y1, int y2) {
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	}
	
	public int getX1() {
		return x1;
	}
	public int getX2() {
		return x2;
	}
	public int getY1(){
		return y1;
	}
	public int getY2(){
		return y2;
	}
	/***
	 * compare y1 then y2
	 */
	public int compareTo(Tuple o) {
		if(o == null) {
			throw new NullPointerException();
		}
		
		int c = Integer.compare(y1, o.getY1());
		if(c != 0) {
			return c;
		}
		return Integer.compare(y2, o.getY2());
	}
}

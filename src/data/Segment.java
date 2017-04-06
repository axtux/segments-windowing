package data;
/**
 *  Created by marco on 28/03/17.
 *  This class represnets a segment
 */

public class Segment implements Comparable<Segment> {

	private final int x1;
	private final int x2;
	private final int y1;
	private final int y2;

	/***
	 * This class represents a segment
	 * @param x1 the first coordinate in x
	 * @param x2 the last coordinate in x
	 * @param y1 the first coordinate in y
	 * @param y2 the last coordinate in y
	 */
	public Segment(int x1, int x2, int y1, int y2) {
		// check order to get y1 < y2
		if (y1 < y2) {
			this.x1=x1;
			this.y1=y1;
			this.x2=x2;
			this.y2=y2;
		} else { // y1 >= y2, put y coordinates in right order
			this.x1=x2;
			this.y1=y2;
			this.x2=x1;
			this.y2=y1;
		}
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
	public int compareTo(Segment o) {
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

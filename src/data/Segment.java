package data;
/**
 *  Created by marco on 28/03/17.
 *  This class represnets a segment
 */

public class Seg implements Comparable<Seg> {

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
	public Seg(int x1, int x2, int y1, int y2) {
		//we here take the min or max to instaure an order in the segments ([a,b] where a < b)
		this.x1 = Math.min(x1,x2);
		this.x2 = Math.max(x1,x2);
		this.y1 = Math.min(y1,y2);
		this.y2 = Math.max(y1,y2);
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
	public int compareTo(Seg o) {
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

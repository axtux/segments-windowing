package data;
/**
 *  Created by marco on 28/03/17.
 *  This class represents a segment with y1 <= y2
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
		// check order to get y1 <= y2
		if (y1 <= y2) {
			this.x1=x1;
			this.y1=y1;
			this.x2=x2;
			this.y2=y2;
		} else { // y1 > y2, put y coordinates in right order
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
			throw new NullPointerException("the object to compare for compareTo is null");
		}
		
		int c = Integer.compare(y1, o.getY1());
		if(c != 0) {
			return c;
		}
		return Integer.compare(y2, o.getY2());
	}
	@Override
	public boolean equals(Object o){

		if (o==this)
			return true;
		if (o==null)
			throw new NullPointerException("the object to compare for equals is null");
		Segment other = (Segment) o;
		//we here compare the 4 coordinate
		if (this.getX1()==other.getX1() && this.getX2()==other.getX2() && this.getY1()==other.getY1() && this.getY2()==other.getY2())
			return true;

		return false;
	}

	/**
	 * This method print the Segment in this form : "Seg : x1,y1,x2,y2"
	 */
	public void printSeg() {
		System.out.print(" Seg : " + getX1() + " " + getY1() + " " + getX2() + " " + getY2());
	}

	public String toString() {
		return "Segment(x1="+Integer.toString(x1)+", x2="+Integer.toString(x2)+", y1="+Integer.toString(y1)+", y2="+Integer.toString(y2)+")";
	}
}

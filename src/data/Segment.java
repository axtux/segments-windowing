package data;
/**
 * Segment represented by 2 points (x1, y1) and (x2, y2).
 */
public class Segment implements Comparable<Segment> {
	private final int x1;
	private final int x2;
	private final int y1;
	private final int y2;
	/**
	 * Create a segment from its points coordinates and make sure {@code y1 <= y2}.
	 * @param x1 X first coordinate
	 * @param x2 X last coordinate
	 * @param y1 Y first coordinate
	 * @param y2 Y last coordinate
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
	/**
	 * Compare y1 then y2 if y1 is equal.
	 */
	public int compareTo(Segment o) {
		if(o == null) throw new NullPointerException("the object to compare for compareTo is null");
		
		int c = Integer.compare(y1, o.getY1());
		return c == 0 ? Integer.compare(y2, o.getY2()) : c;
	}
	/**
	 * Compares all coordinates
	 */
	public boolean equals(Object o){
		if (o==null) throw new NullPointerException("the object to compare for equals is null");
		if (o==this) return true;
		if(!(o instanceof Segment)) return false;
		
		Segment s = (Segment) o;
		// compare coordinates
		return getX1() == s.getX1() && getX2() == s.getX2() && getY1() == s.getY1() && getY2() == s.getY2();
	}
	/**
	 * String representation of this segment.
	 */
	public String toString() {
		return "Segment(x1="+x1+", x2="+x2+", y1="+y1+", y2="+y2+")";
	}
}

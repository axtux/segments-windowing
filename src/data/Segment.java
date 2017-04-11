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
	 * Create a segment from its points coordinates and make sure {@code y1 <= y2} and {@code x1 <= x2 if y1 == y2}.
	 * @param x1 X first coordinate
	 * @param x2 X last coordinate
	 * @param y1 Y first coordinate
	 * @param y2 Y last coordinate
	 */
	public Segment(int x1, int x2, int y1, int y2) {
		// y1 is always <= y2, if y1 == y2, then x1 <= x2
		if (y1 < y2 || (y1 == y2 && x1 <= x2)) {
			this.x1=x1;
			this.y1=y1;
			this.x2=x2;
			this.y2=y2;
		} else {
			// y1 > y2 or y1 == y2 and x2 > x1, reverse points
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
	
	public int getMinX() {
		return Math.min(x1, x2);
	}
	public int getMaxX() {
		return Math.max(x1, x2);
	}
	public int getMinY() {
		// already ordered in constructor
		return y1;
	}
	public int getMaxY() {
		// already ordered in constructor
		return y2;
	}
	/**
	 * Oppose i maintaining extreme value.
	 * @param i Integer to oppose.
	 * @return Opposed i.
	 */
	private int oppose(int i) {
		switch(i) {
		case Integer.MAX_VALUE: return Integer.MIN_VALUE;
		case Integer.MIN_VALUE: return Integer.MAX_VALUE;
		default: return -i;
		}
	}
	/**
	 * Create new segment with opposed coordinates. All coordinates are opposed. Segment(1, 1, 1, 1) becomes (-1, -1, -1, -1).
	 * @return New segment with opposed coordinates build from constructor to get coordinates ordered.
	 */
	public Segment oppose() {
		return new Segment(oppose(x1), oppose(x2), oppose(y1), oppose(y2));
	}
	/**
	 * Create new segment with exchanged coordinates. X coordinates become Y coordinates and inversely. Segment(1, 1, 2, 2) becomes (2, 2, 1, 1).
	 * @return New segment with exchanged coordinates build from constructor to get coordinates ordered.
	 */
	public Segment exchange() {
		return new Segment(y1, y2, x1, x2);
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

package data;
/**
 * Point containing X and Y integer coordinates
 */
public class Point implements Comparable<Point> {
	private final int x;
	private final int y;
	/**
	 * Create point.
	 * @param x X Coordinate.
	 * @param y Y Coordinate.
	 */
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	/**
	 * Get X.
	 * @return X Coordinate.
	 */
	public int getX() {
		return x;
	}
	/**
	 * Get Y.
	 * @return Y Coordinate.
	 */
	public int getY(){
		return y;
	}
	/**
	 * Compare X coordinate then Y coordinate if X coordinate is equal.
	 */
	public int compareTo(Point o) {
		if(o == null) throw new NullPointerException();
		
		int c = Integer.compare(x, o.getX());
		return c == 0 ? Integer.compare(y, o.getY()) : c;
	}
}
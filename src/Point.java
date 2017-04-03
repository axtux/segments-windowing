/**
 * Point with X and Y integer coordinates
 */
public class Point implements Comparable<Point> {
	private final int x;
	private final int y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	public int getY(){
		return y;
	}
	/***
	 * compare x then y
	 */
	public int compareTo(Point o) {
		if(o == null) {
			throw new NullPointerException();
		}
		
		int c = Integer.compare(x, o.getX());
		if(c != 0) {
			return c;
		}
		return Integer.compare(y, o.getY());
	}
}
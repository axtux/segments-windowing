import java.util.ArrayList;

/**
 * Main class
 */
public class Test {
	/**
	 * 
	 * @param args No arguments supported.
	 */
	public static void main(String args[]) {
		ArrayList<Tuple> segments = new ArrayList<Tuple>();
		segments.add(new Tuple(0, 100, 0, 100));
		new SegmentsWindow(new Tuple(-500, 500, -500, 500), segments);
	}
}

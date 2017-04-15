package data;

/**
 * Priority Search Tree making easy to get segments within a window.
 * The most efficient window is [-∞, X']x[Y, Y']. Other windows can be gotten efficiently using {@link Pst}.
 */
public class BasicPst {
	private PstNode root;
	/**
	 * Create Priority Search Tree from unsorted Segment list.
	 * @param segments Segment list from which to create PST.
	 */
	public BasicPst(Array<Segment> segments) {
		// sort Segments by Y coordinate using heap sort
		segments.sort(Segment::compareTo);
		this.root = makePstNode(segments);
	}
	/**
	 * Create PstNode and sub nodes from Segment list. Used by constructor to create priority search tree root.
	 * @param segments Segment list, sorted by Y coordinate.
	 */
	private PstNode makePstNode(Array<Segment> segments) {
		if(segments == null || segments.size() == 0) return null;
		
		// attribute Segment containing minimum X to this node
		PstNode node = new PstNode(segments.remove(getMinX(segments)));
		
		if(segments.size() > 0) {
			int median = segments.size()/2;
			node.setMedian(segments.get(median).getY1());
			node.setLeft(makePstNode(segments.subArray(0, median)));
			node.setRight(makePstNode(segments.subArray(median, segments.size())));
		}
		
		return node;
	}
	/**
	 * Get root node.
	 * @return Root node.
	 */
	public PstNode getRoot(){
		return root;
	}

	/**
	 * This method is used to get the height of the Pst
	 * @return A int.
	 */
	public int getHeight(){
		return root.getHeight();
	}

	/**
	 * Get index of the Segment owning the minimum X value.
	 * @param list A list of segments.
	 * @return Index of the Segment owning the minimum X value or -1 if list is null or if its size is 0.
	 */
	private static int getMinX(Array<Segment> list) {

		if(list == null || list.size() == 0) return -1;

		int min = 0;
		for (int i = 1; i < list.size(); ++i){
			if(list.get(i).getMinX() < list.get(min).getMinX()) {
				min=i;
			}
		}
		return min;
	}
	/**
	 * Print root node.
	 */
	public void printPst() {
		if(root == null) {
			System.out.println("null");
		} else {
			System.out.println(root.toString());
		}
	}
	/**
	 * Apply windowing algorithm using root node, reporting viewed segments through window.
	 * @param window Only segments viewed through this window will be reported.
	 * @return Reported segments (can be viewed through window).
	 */
	public Array<Segment> windowing(Segment window){
		return windowing(window, true, true);
	}
	/**
	 * Apply windowing algorithm using root node, reporting viewed segments through window.
	 * @param window Only segments viewed through this window will be reported.
	 * @param down If true, report segments starting below the window.
	 * @param center If true, report segments starting in the window.
	 * @return Reported segments (can be viewed through window).
	 */
	public Array<Segment> windowing(Segment window, boolean down, boolean center) {
		window = window.getWindow();
		
		Array<Segment> reported = new Array<Segment>();
		subWindowing(root, window, reported, down, center);
		
		return reported;
	}
	/**
	 * Apply windowing algorithm using node as Pst root node, reporting viewed nodes through window into reported.
	 * @param node Root node to use to make windowing.
	 * @param window Only nodes with a segment viewed through this window will be reported.
	 * @param reported Array into which reported node will be added.
	 */
	private void subWindowing(PstNode node, Segment window, Array<Segment> reported, boolean down, boolean center) {
		if(node == null) return;
		
		Segment s = node.getSegment();
		
		// if we don't need center, we can cut earlier
		if(!center && s.getMinX() > window.getX1()) return;
		
		// segment and all child nodes are below window
		if(s.getMinX() > window.getX2()) return;
		
		// check if this node has to be reported, only one report can match a segment
		if(center && reportCenter(s, window)) reported.add(s);
		if(down && reportDown(s, window)) reported.add(s);
		if(reportLeft(s, window)) reported.add(s);
		
		// we can avoid this if we don't look for segments under window
		if(down || node.getMedian() >= window.getY1()) {
			subWindowing(node.getLeft(), window, reported, down, center);
		}
		
		// only go to right when needed
		if(node.getMedian() <= window.getY2()) {
			subWindowing(node.getRight(), window, reported, down, center);
		}
	}
	/**
	 * Check that segment first point is into window. First point has lowest Y coordinate.
	 * @param s Segment to check.
	 * @param window Window into which point may be.
	 * @return True if segment first point is into window. False otherwise.
	 */
	private boolean reportCenter(Segment s, Segment window) {
		// minimum Y in window center
		if(window.getY1() <= s.getY1() && window.getY2() >= s.getY1()) {
			// corresponding X in window center
			if(window.getX1() <= s.getX1() && window.getX2() >= s.getX1()) {
				return true;
			}
		}
		
		return false;
	}
	/**
	 * Check that segment is crossing window down border (Starting before and stopping on border or ending after).
	 * @param s Segment to check.
	 * @param window Window that segment may cross.
	 * @return True if segment is crossing window down border. False otherwise.
	 */
	private boolean reportDown(Segment s, Segment window) {
		// X1 in window
		if(window.getX1() <= s.getX1() && s.getX1() <= window.getX2()) {
			// crossing window Y1 and starting before
			if(s.getY1() < window.getY1() && s.getY2() >= window.getY1()) {
				return true;
			}
		}
		
		return false;
	}
	/**
	 * Check that segment is crossing window left border (Starting before and stopping on border or ending after).
	 * @param s Segment to check.
	 * @param window Window that segment may cross.
	 * @return True if segment is crossing window left border. False otherwise.
	 */
	private boolean reportLeft(Segment s, Segment window) {
		// Y1 in window
		if(window.getY1() <= s.getY1() && s.getY1() <= window.getY2()) {
			// crossing window X1 and starting before
			if(s.getMinX() < window.getX1() && s.getMaxX() >= window.getX1()) {
				return true;
			}
		}
		
		return false;
	}

}
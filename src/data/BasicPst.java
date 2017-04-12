package data;

import java.util.ArrayList;

/**
 * Priority Search Tree making easy to get segments within a window.
 * Implemented windows :
 * - [X, X']x[Y, Y'] ;
 * - [-∞, X']x[Y, Y'].
 * Other windows can be gotten efficiently using {@link Pst}.
 */
public class BasicPst {
	private PstNode root;
	/**
	 * Create Priority Search Tree from unsorted Segment list.
	 * @param list Segment list from which to create PST.
	 */
	public BasicPst(ArrayList<Segment> list) {
		Array<Segment> segments = new Array<Segment>(list);
		// sort Segments by Y coordinate
		segments.sort(Segment::compareTo);
		this.root = makePstNode(segments);
	}
	/**
	 * Create PstNode and sub nodes from Segment list. Used by constructor to create priority search tree root.
	 * @param list Segment list, sorted by Y coordinate.
	 */
	private PstNode makePstNode(Array<Segment> list) {
		if(list == null || list.size() == 0) return null;
		
		// attribute Segment containing minimum X to this node
		PstNode temp = new PstNode(list.remove(getMinX(list)));
		
		if(list.size() > 0) {
			int median = list.size()/2;
			temp.setMedian(list.get(median).getY1());
			temp.setLeft(makePstNode(list.subArray(0, median)));
			temp.setRight(makePstNode(list.subArray(median, list.size())));
		}
		
		return temp;
	}
	/**
	 * Get root node.
	 * @return Root node.
	 */
	public PstNode getRoot(){
		return root;
	}
	/**
	 * Get index of the Segment owning the minimum X value.
	 * @param list A list of segments.
	 * @return Index of the Segment owning the minimum X value or -1 if list is null or if its size is 0.
	 */
	private static int getMinX(ArrayList<Segment> list) {

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
	 * @return Reported segments (can be viewed through window).
	 */
	public Array<Segment> windowing(Segment window, boolean down, boolean center) {
		window = window.getWindow();
		
		Array<PstNode> reported = new Array<PstNode>();
		subWindowing(root, window, reported, down, center);
		
		Array<Segment> response = new Array<Segment>(reported.size());
		// get segments and reset flag
		for(PstNode n : reported) {
			response.add(n.getSegment());
			n.setFlag(false);
		}
		
		return response;
	}
	/**
	 * Apply windowing algorithm using node as Pst root node, reporting viewed nodes through window into reported.
	 * @param node Root node to use to make windowing.
	 * @param window Only nodes with a segment viewed through this window will be reported.
	 * @param reported Array into which reported node will be added.
	 */
	private void subWindowing(PstNode node, Segment window, Array<PstNode> reported, boolean down, boolean center) {
		if(node == null) return;
		
		Segment s = node.getSegment();
		
		// if we don't need center, we can cut earlier
		if(center && s.getMinX() > window.getX1()) return;
		
		// segment and all child nodes are below window
		if(s.getMinX() > window.getX2()) return;
		
		// check if this node has to be reported
		report(node, window, reported, down, center);
		
		// we can avoid this if we don't look for segments under window
		if(!down && node.getMedian() >= window.getY1()) {
			subWindowing(node.getLeft(), window, reported, down, center);
		}
		
		// only go to right when needed
		if(node.getMedian() <= window.getY2()) {
			subWindowing(node.getRight(), window, reported, down, center);
		}
	}
	/**
	 * Add node to reported if node segment can be viewed through window.
	 * @param node Node to check.
	 * @param window Node is only reported if its segment can be viewed through that window.
	 * @param reported Array to add reported node.
	 */
	public void report(PstNode node, Segment window, Array<PstNode> reported, boolean down, boolean center) {
		if(node.getFlag()) {
			// already reported
			return;
		}
		
		boolean report  = false;
		Segment s = node.getSegment();
		
		if(center) {
			report = report || reportCenter(s, window);
		}
		if(down) {
			report = report || reportDown(s, window);
		}
		report = report || reportLeft(s, window);
		
		if(report) {
			node.setFlag(true);
			reported.add(node);
		}
	}
	/**
	 * Check that segment has at least one point into window.
	 * @param s Segment to check.
	 * @param window Window into which segment may be.
	 * @return True if segment has at least one point into window. False otherwise.
	 */
	private boolean reportCenter(Segment s, Segment window) {
		// minimum Y in window center
		if(window.getY1() <= s.getY1() && window.getY2() >= s.getY1()) {
			// corresponding X in window center
			if(window.getX1() <= s.getX1() && window.getX2() >= s.getX1()) {
				return true;
			}
		}
		// maximum Y in window center
		if(window.getY1() <= s.getY2() && window.getY2() >= s.getY2()) {
			// corresponding X in window center
			if(window.getX1() <= s.getX2() && window.getX2() >= s.getX2()) {
				return true;
			}
		}
		
		return false;
	}
	/**
	 * Check that segment is crossing window down border.
	 * @param s Segment to check.
	 * @param window Window that segment may cross.
	 * @return True if segment is crossing window down border. False otherwise.
	 */
	private boolean reportDown(Segment s, Segment window) {
		// only report vertical segment
		if (s.getX1() != s.getX2()) {
			return false;
		}
		
		// X1 in window (and X2 because X1==X2)
		if(window.getX1() <= s.getX1() && s.getX1() <= window.getX2()) {
			// crossing window Y1
			if(s.getY1() <= window.getY1() && s.getY2() >= window.getY1()) {
				return true;
			}
		}
		
		return false;
	}
	/**
	 * Check that segment is crossing window left border.
	 * @param s Segment to check.
	 * @param window Window that segment may cross.
	 * @return True if segment is crossing window left border. False otherwise.
	 */
	private boolean reportLeft(Segment s, Segment window) {
		// only report horizontal segment
		if (s.getY1() != s.getY2()) {
			return false;
		}
		
		// Y1 in window (and Y2 because Y1==Y2)
		if(window.getY1() <= s.getY1() && s.getY1() <= window.getY2()) {
			// crossing window X1
			if(s.getMaxX() >= window.getX1() && s.getMinX() <= window.getX1()) {
				return true;
			}
		}
		
		return false;
	}

}
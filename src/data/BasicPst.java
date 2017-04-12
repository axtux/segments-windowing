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
	 * This method print a BasicPst wich the root is given in parameter using the printSeg() method in Segment.
	 * @param temp the root of the tree to be print
	 * @param acc the Symbol of a node ( examples : @,|,(), ...)
	 */
	public void printPst(PstNode temp, String acc) {

		System.out.print(acc + temp.getSegment());
		if (temp.getLeft() != null) {
			System.out.println();
			System.out.print("l-son:");
			printPst(temp.getLeft(), acc+"|-----");
		}
		if (temp.getRight() != null) {
			System.out.println();
			System.out.print("r-son:");
			printPst(temp.getRight(), acc+"|-----");
		}
	}

	/**
	 * this method apply the windowing on the BasicPst
	 * @param window The window to apply ( has to e ordered )
	 * @return An ArrayList with all the segment in the window, or otherwise an empty Arrraylist
	 */
	public Array<Segment> windowing(Segment window){
		window = window.getWindow();
		
		Array<PstNode> reported = new Array<PstNode>();
		subWindowing(root, window, reported);
		
		Array<Segment> response = new Array<Segment>(reported.size());
		// get segments and reset flag
		for(PstNode n : reported) {
			response.add(n.getSegment());
			n.setFlag(false);
		}
		
		return response;
	}

	/**
	 * This method apply the subWindowing method on the BasicPst and return an ArrayList with the Segment in it where the segment have one end-point in it,
	 * or an empty ArrayList if there is no Segment in the window.
	 * The window have to be in that form : [x:x']X[y:y'] where {@code x<=x'} and {@code y<=y'}(prerequisite)
	 * @param window a Segment object representing the window to apply
	 * @param node Temporary node.
	 * @param reporting the reporting type to do
	 * @return an ArrayList of the Segment, or an empty ArrayList
	 */
	public void subWindowing(PstNode node, Segment window, Array<PstNode> reported) {
		if(node == null) return;
		
		Segment s = node.getSegment();
		// segment and all child nodes are below window
		if(s.getMinX() > window.getX2()) return;
		
		// check if this node has to be reported
		report(node, window, reported);
		
		int median = node.getMedian();
		
		// we can't avoid this as we need segments starting down the window
		subWindowing(node.getLeft(), window, reported);
		
		// only go to right when needed
		if(median <= window.getY2()) {
			subWindowing(node.getRight(), window, reported);
		}
	}
	
	/**
	 * return the segment (Node data) if it's in the window that we choose
	 * and it hasn't been visited, or null otherwise
	 * @param n Node to report
	 * @param window a Segment wich represents the window
	 * @param type a ReportType enumeration to know wich type of report to do
	 * @return Reported segment if reported or null.
	 */
	public void report(PstNode node, Segment window, Array<PstNode> reported) {
		if(node.getFlag()) {
			// already reported
			return;
		}
		
		boolean report  = false;
		Segment s = node.getSegment();
		
		report = report || reportCenter(s, window);
		report = report || reportDown(s, window);
		report = report || reportLeft(s, window);
		
		if(report) {
			node.setFlag(true);
			reported.add(node);
		}
	}
	
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
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
	public static int getMinX(ArrayList<Segment> list) {
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
	public ArrayList<Segment> windowing(Segment window){
		window = window.getWindow();
		
		ArrayList<Segment> answer = subWindowing(window,root, ReportType.NormalWindow);

		if (window.getX1() == Integer.MIN_VALUE) {
			Segment window2=new Segment(window.getX1(),window.getX2(),Integer.MIN_VALUE,window.getY1());
			//case : two -infinity in x1 and y1
			ArrayList<Segment> answer2=subWindowing(window2,root, ReportType.DownWindow);
			answer.addAll(answer2);
		}
		else {
			Segment window2=new Segment(Integer.MIN_VALUE,window.getX1(),window.getY1(),window.getY2());
			Segment window3=new Segment(window.getX1(),window.getX1(),Integer.MIN_VALUE,window.getY1());
			answer.addAll(subWindowing(window2,root, ReportType.LeftWindow));
			answer.addAll(subWindowing(window3,root, ReportType.DownWindow));
		}
		return answer;
	}

	/**
	 * This method apply the subWindowing method on the BasicPst and return an ArrayList with the Segment in it where the segment have one end-point in it,
	 * or an empty ArrayList if there is no Segment in the window.
	 * The window have to be in that form : [x:x']X[y:y'] where {@code x<=x'} and {@code y<=y'}(prerequisite)
	 * @param window a Segment object representing the window to apply
	 * @param temp Temporary node.
	 * @param reporting the reporting type to do
	 * @return an ArrayList of the Segment, or an empty ArrayList
	 */
	public ArrayList<Segment> subWindowing(Segment window, PstNode temp, ReportType reporting){

		ArrayList<Segment> rep=new ArrayList<Segment>();

		if (temp!=null) {

			if (window.getX1() == Integer.MIN_VALUE) {//the window is without min in x : [-infinity;x2]X[y1,y2]

				if (Math.min(temp.getSegment().getX1(), temp.getSegment().getX2()) <= window.getX2()) {
					//we can continue because all the element in the subtree aren't greater than the window in x
					Segment s =report(temp,window,reporting);
					if (s!=null)
						rep.add(s);
					if (window.getY1() < temp.getMedian() && window.getY2() < temp.getMedian())
						rep.addAll(subWindowing(window, temp.getLeft(), reporting));
					if (window.getY1() > temp.getMedian() && window.getY2() > temp.getMedian())
						rep.addAll(subWindowing(window, temp.getRight(), reporting));
					if (window.getY1() <= temp.getMedian() && window.getY2() >= temp.getMedian()) {
						rep.addAll(subWindowing(window, temp.getLeft(), reporting));
						rep.addAll(subWindowing(window, temp.getRight(), reporting));
					}
				}
			}

			if (window.getY1() == Integer.MIN_VALUE) {
				//the window is without min in y : [x1;x2]X[-infinity,y2] ,or special case : [-infinity;x2]X[-infinity, y2]

				if (Math.min(temp.getSegment().getX1(), temp.getSegment().getX2()) <= window.getX2()) {
					//we can continue because all the element in the subtree aren't greater than the window in x
					Segment s =report(temp,window,reporting);
					if (s!=null)
						rep.add(s);
					if (window.getY2() < temp.getMedian())
						rep.addAll(subWindowing(window, temp.getLeft(), reporting));
					if (window.getY2() >= temp.getMedian()) {
						rep.addAll(subWindowing(window, temp.getLeft(), reporting));
						rep.addAll(subWindowing(window, temp.getRight(), reporting));
					}
				}
			}

			else {//case of a limited window

				if (Math.min(temp.getSegment().getX1(), temp.getSegment().getX2()) <= window.getX2()) {
					Segment s =report(temp,window,reporting);
					if (s!=null)
						rep.add(s);
					//it will do nothing if the node is not in the x window
					if (window.getY1() < temp.getMedian() && window.getY2() < temp.getMedian())
						rep.addAll(subWindowing(window, temp.getLeft(), reporting));
					if (window.getY1() > temp.getMedian() && window.getY2() > temp.getMedian())
						rep.addAll(subWindowing(window, temp.getRight(), reporting));
					if (window.getY1() <= temp.getMedian() && window.getY2() >= temp.getMedian()) {
						rep.addAll(subWindowing(window, temp.getLeft(), reporting));
						rep.addAll(subWindowing(window, temp.getRight(), reporting));
					}
				}
			}
		}
		return rep;
	}

	/**
	 * return the segment (Node data) if it's in the window that we choose
	 * and it hasn't been visited, or null otherwise
	 * @param n Node to report
	 * @param window a Segment wich represents the window
	 * @param type a ReportType enumeration to know wich type of report to do
	 * @return Reported segment if reported or null.
	 */
	public Segment report(PstNode n, Segment window, ReportType type){

		if (ReportType.NormalWindow.equals(type)) {
			if (!n.getFlag()
							&& ((window.getY1() <= n.getSegment().getY1() && window.getY2()>=n.getSegment().getY1()
							&& window.getX1() <= Math.min(n.getSegment().getX1(), n.getSegment().getX2())
							&& window.getX2() >= Math.min(n.getSegment().getX1(), n.getSegment().getX2()))//first end point
							||
							(window.getY1() <= n.getSegment().getY2() && window.getY2()>=n.getSegment().getY2()
							&& window.getX1() <= Math.max(n.getSegment().getX1(), n.getSegment().getX2())
							&& window.getX2() >= Math.max(n.getSegment().getX1(), n.getSegment().getX2())))//second end point
							){

				n.setFlag(true);
				return n.getSegment();
			}
		}

		if (ReportType.DownWindow.equals(type)) {
			if (!n.getFlag()
							&& n.getSegment().getX1()==n.getSegment().getX2()//vertical segment
							&& window.getX1()<= n.getSegment().getX1() && n.getSegment().getX1() <=window.getX2()//in the window
							&& n.getSegment().getY2()>=window.getY2()//goes throught the true window
							){

				n.setFlag(true);
				return n.getSegment();

			}
		}
		if (ReportType.LeftWindow.equals(type)) {
			if (!n.getFlag()
							&& n.getSegment().getY1()==n.getSegment().getY2()//horyzontal segment
							&& window.getY1()<= n.getSegment().getY1() && n.getSegment().getY1() <=window.getY2()//in the window
							&& Math.max(n.getSegment().getX1(),n.getSegment().getX2())>=window.getX2()//goes throught the true window
							){

				n.setFlag(true);
				return n.getSegment();

			}
		}
		return null;
	}

}
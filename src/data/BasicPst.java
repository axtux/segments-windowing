package data;

import java.util.ArrayList;

/**
 * This class represents a priority search tree.
 */
public class BasicPst {
	private PstNode root;

	public BasicPst(ArrayList<Segment> list) {
		this.root=construct(new Array<Segment>(list));
	}

	/***
	 * This method is used by the constructor to create step by step the priority search tree.
	 * @param list a list of element sorted in y
	 */
	private PstNode construct(Array<Segment> list) {
		//for the method list.sublist() , the first index is inclusive and the second exclusive
		PstNode temp = null;
		if (list.size()>=3) {
			temp=new PstNode(list.remove(getMinX(list)), (list.get((list.size()-1)/2).getY1()) );
			temp.setLeft(construct(list.subArray(0,(list.size())/2)));
			temp.setRight(construct(list.subArray((list.size())/2,list.size())));
		} else if (list.size()==1) {
			//base case where the sub tree containt one element
			temp=new PstNode(list.remove(getMinX(list)));//median is null ( it's a leaf)
		} else if (list.size()==2){ //base case where subtree containt two element
			temp=new PstNode(list.remove(getMinX(list)), (list.get(0).getY1()));//the median is the y1 of the unique son
			temp.setLeft(new PstNode(list.remove(0)));
		}
		//case size == 0 , do nothing
		return temp;
	}

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

		ArrayList<Segment> answer = subWindowing(window,root, ReportType.NormalWindow);

		if (window.getX1() == Integer.MIN_VALUE) {
			Segment window2=new Segment(window.getX1(),window.getX2(),Integer.MIN_VALUE,window.getY1());
			//case : two -infinity in x1 and y1
			ArrayList<Segment> answer2=subWindowing(window2,root, ReportType.DownWindow);
			answer.addAll(answer2);
		}
		/*if (window.getX2() == Integer.MAX_VALUE) {
			Segment window2=new Segment(window.getX1(),window.getX2(),window.getY2(),Integer.MAX_VALUE);
			//case to add too with infinity in x2,y2
			ArrayList<Segment> answer2=subWindowing(window2,root);
			answer.addAll(answer2);
		}
		if (window.getY1() == Integer.MIN_VALUE) {
			Segment window2=new Segment(Integer.MIN_VALUE,window.getX1(),window.getY1(),window.getY2());
			// """ """"  x1,y1
			ArrayList<Segment> answer2=subWindowing(window2,root);
			answer.addAll(answer2);
		}
		if (window.getY2() == Integer.MAX_VALUE) {
			Segment window2=new Segment(window.getX2(),Integer.MAX_VALUE,window.getY1(),window.getY2());
			//""""  x2,y2
			ArrayList<Segment> answer2=subWindowing(window2,root);
			answer.addAll(answer2);
		}*/
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
	 * The window have to be in that form : [x:x']X[y:y'] where x<=x' and y<=y'(prerequisite)
	 * @param window a Segment object representing the window to apply
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
			/*if (window.getX2() == Integer.MAX_VALUE && window.getY2() != Integer.MAX_VALUE) {//the window is without min in x : [x1;+infinity]X[y1,y2]
				Segment s =report(root,window);
				if (s!=null)
					rep.add(s);
				//it will do nothing if the node is not in the x window
				if (window.getY1() < root.getMedian() && window.getY2() < root.getMedian())
					rep.addAll(subWindowing(window, root.getLeft()));
				if (window.getY1() > root.getMedian() && window.getY2() > root.getMedian())
					rep.addAll(subWindowing(window, root.getRight()));
				if (window.getY1() <= root.getMedian() && window.getY2() >= root.getMedian()) {
					rep.addAll(subWindowing(window, root.getLeft()));
					rep.addAll(subWindowing(window, root.getRight()));
				}
			}*/
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
			/*
			if (window.getY2() == Integer.MAX_VALUE && window.getX2()!=Integer.MAX_VALUE) {//the window is without max in y : [x1;x2]X[y1,+infinity]
				if (Math.min(root.getData().getX1(), root.getData().getX2()) <= window.getX2()) {
					//we can continue because all the element in the subtree aren't greater than the window in x
					Segment s =report(root,window);
					if (s!=null)
						rep.add(s);
					if (window.getY1() <= root.getMedian()) {
						rep.addAll(subWindowing(window, root.getLeft()));
						rep.addAll(subWindowing(window, root.getRight()));
					}
					if (window.getY1() > root.getMedian())
						rep.addAll(subWindowing(window, root.getRight()));
				}
			}
			if (window.getY2() == Integer.MAX_VALUE && window.getX2()==Integer.MAX_VALUE) {// special case : [x1;+infinity]X[y1,+infinity]

				Segment s =report(root,window);
				if (s!=null)
					rep.add(s);
				if (window.getY1() <= root.getMedian()) {
					rep.addAll(subWindowing(window, root.getLeft()));
					rep.addAll(subWindowing(window, root.getRight()));
				}
				if (window.getY1() > root.getMedian())
					rep.addAll(subWindowing(window, root.getRight()));
			}*/

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
	 * @param n a node<Segment> to report
	 * @param window a Segment wich represents the window
	 * @param type a ReportType enumeration to know wich type of report to do
	 */
	public Segment report(PstNode n, Segment window, ReportType type){

		if (ReportType.NormalWindow.equals(type)) {
			if (!n.getFlag()
							&& ((window.getY1() <= root.getSegment().getY1() && window.getX1() <= Math.min(n.getSegment().getX1(), n.getSegment().getX2()))
							|| (window.getY2() >= root.getSegment().getY2() && window.getX2() >= Math.max(n.getSegment().getX1(), n.getSegment().getX2())))
							){

				n.setFlag(true);
				return n.getSegment();
			}
		}

		if (ReportType.DownWindow.equals(type)) {
			if (!n.getFlag()
							&& n.getSegment().getX1()==n.getSegment().getX2()//vertical segment
							&& window.getX1()<= n.getSegment().getX1() && n.getSegment().getX1() <=window.getX2()//in the window
							&& n.getSegment().getY2()>=window.getY1()//goes throught the true window
							){

				n.setFlag(true);
				return n.getSegment();

			}
		}
		if (ReportType.LeftWindow.equals(type)) {
			if (!n.getFlag()
							&& n.getSegment().getY1()==n.getSegment().getY2()//horyzontal segment
							&& window.getY1()<= n.getSegment().getY1() && n.getSegment().getY1() <=window.getY2()//in the window
							&& n.getSegment().getX2()>=window.getX1()//goes throught the true window
							){

				n.setFlag(true);
				return n.getSegment();

			}
		}
		return null;
	}

}
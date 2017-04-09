package data;

import java.util.ArrayList;

/**
 * Created by marco on 1/04/17.
 * this class represents the priority search tree
 */
public class Pst {

	private Node<Segment> root;

	public Pst(ArrayList<Segment> list) {
		this.root=construct(list);
	}

	/***
	 * This method is used by the constructor to create step by step the priority search tree.
	 * @param list a list of element sorted in y
	 */
	private Node<Segment> construct(ArrayList<Segment> list) {
		//for the method list.sublist() , the first index is inclusive and the second exclusive
		Node<Segment> temp = null;
		while (list.size()>=3) {
			temp=new Node<Segment>(list.remove(firstx(list)), (list.get((list.size()-1)/2).getY1()) );
			temp.setLeft(construct(new ArrayList<Segment>(list.subList(0,(list.size())/2))));
			temp.setRight(construct(new ArrayList<Segment>(list.subList((list.size())/2,list.size()))));
			return temp;
		}
		if (list.size()==1) //base case where the sub tree containt one element
			temp=new Node<Segment>(list.remove(firstx(list)));//median is null ( it's a leaf)
		else if (list.size()==2){ //base case where subtree containt two element
			temp=new Node<Segment>(list.remove(firstx(list)), (list.get(0).getY1()));//the median is the y1 of the unique son
			temp.setLeft(new Node<Segment>(list.remove(0)));
		}
		//case size == 0 , do nothing
		return temp;
	}

	public Node<Segment> getRoot(){
		return root;
	}


	/***
	 * this method is used to have the minimum in x in a list of Segment sorted in y,
	 * note that there is no duplicate value in x (prerequisite).
	 * @param list
	 * @return the INDICE of the minimum Segment in x
	 */
	public int firstx(ArrayList<Segment> list) {
		int min = 0;//indice of the minimum,init with 0 (the first indice)
		Segment val, mini;
		for (int i=1;i<list.size();i++){
			val=list.get(i);
			mini=list.get(min);
			//There is no order in the segment between x1 and x2
			if (Math.min(val.getX1(),val.getX2()) < Math.min(mini.getX1(),mini.getX2()) )
				min=i;
		}
		return min;
	}

	/**
	 * This method print a Pst wich the root is given in parameter using the printSeg() method in Segment.
	 * @param temp the root of the tree to be print
	 * @param acc the Symbol of a node ( examples : @,|,(), ...)
	 */
	public void printPst(Node<Segment> temp, String acc) {

		System.out.print(acc);
		temp.getData().printSeg();
		if (temp.getLeft() != null) {
			System.out.println();
			System.out.print("l-son:");
			printPst(temp.getLeft(), acc+"|-----");
		}
		if (temp.getRight() != null) {
			System.out.println();
			System.out.print("r-son:");;
			printPst(temp.getRight(), acc+"|-----");
		}
	}

	/**
	 * This method apply the windowing method on the Pst and return an ArrayList with the Segment in it where the segment have one end-point in it,
	 * or an empty ArrayList if there is no Segment in the window.
	 * The window have to be in that form : [x:x']X[y:y'] where x<=x' and y<=y'(prerequisite)
	 * @param window a Segment object representing the window to apply
	 * @return an ArrayList of the Segment, or an empty ArrayList
	 */
	public ArrayList<Segment> windowing(Segment window, Node<Segment> root){//window is like that : [x:x']X[y:y']
		ArrayList<Segment> rep=new ArrayList<>();
		if (root!=null) {
			if (window.getX1() == Integer.MAX_VALUE) {//the window is without min in x : [-infinity;x2]X[y1,y2]
				if (Math.min(root.getData().getX1(), root.getData().getX2()) <= window.getX2()) {
					//we can continue because all the element in the subtree aren't greater than the window in x
					report(root, window, rep);
					if (window.getY1() < root.getMedian())
						windowing(window, root.getLeft());
					if (window.getY1() > root.getMedian())
						windowing(window, root.getRight());
					if (window.getY1() == root.getMedian()) {//case where there are y1 elements equals to the median
						windowing(window, root.getLeft());
						windowing(window, root.getRight());
					}
				}
			}
			if (window.getX2() == Integer.MAX_VALUE) {//the window is without min in x : [x1;+infinity]X[y1,y2]
				report(root, window, rep);
				if (window.getY1() < root.getMedian())
					windowing(window, root.getLeft());
				if (window.getY1() > root.getMedian())
					windowing(window, root.getRight());
				if (window.getY1() == root.getMedian()) {//case where there are y1 elements equals to the median
					windowing(window, root.getLeft());
					windowing(window, root.getRight());
				}
			}
			if (window.getY1() == Integer.MAX_VALUE) {//the window is without min in y : [x1;x2]X[-infinity,y2]
				if (Math.min(root.getData().getX1(), root.getData().getX2()) <= window.getX2()) {
					//we can continue because all the element in the subtree aren't greater than the window in x
					report(root, window, rep);
					if (window.getY2() <= root.getMedian())
						windowing(window, root.getLeft());
					if (window.getY2() > root.getMedian()) {
						windowing(window, root.getLeft());
						windowing(window, root.getRight());
					}
				}
			}
			if (window.getY2() == Integer.MAX_VALUE) {//the window is without max in y : [x1;x2]X[y1,+infinity]
				if (Math.min(root.getData().getX1(), root.getData().getX2()) <= window.getX2()) {
					//we can continue because all the element in the subtree aren't greater than the window in x
					report(root, window, rep);
					if (window.getY1() < root.getMedian())
						windowing(window, root.getLeft());
						windowing(window, root.getRight());
					if (window.getY1() >= root.getMedian())
						windowing(window, root.getRight());
				}
			}
		}
		return rep;
	}

	/**
	 * Add the segment (Node data) to rep if one of its end points is in the window, otherwise do nothing
	 * @param n a node<Segment> to report
	 * @param window a Segment wich represents the window
	 * @param rep The ArrayList<Segment> in wich whe add the segment
	 */
	public void report(Node<Segment> n,Segment window,ArrayList<Segment> rep){
		if ( (window.getY1()<=root.getData().getY1() && window.getX1()<=Math.min(n.getData().getX1(),n.getData().getX2()))
						// search if there is one end point in the window
						|| (window.getY2()>=root.getData().getY2() && window.getX2()>=Math.max(n.getData().getX1(),n.getData().getX2()))
				)
			rep.add(root.getData());
	}


}
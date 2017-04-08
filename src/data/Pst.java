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
	 * This method apply the windowing method on the Pst and return an ArrayList with the Segment in it,
	 * or an empty ArrayList if there is no Segment in the window.
	 * The window have to be in that form : [x:x']X[y:y'] where x<=x' and y<=y'(prerequisite)
	 * @param window a Segment object representing the window to apply
	 * @return an ArrayList with the Segment in it, or an empty ArrayList
	 */
	public ArrayList<Segment> windowing(Segment window){//window is like that : [x:x']X[y:y']
		ArrayList<Segment> rep=new ArrayList<>();
		Node<Segment> temp=root;
		if (window.getX1()==Integer.MAX_VALUE){//the window is without min in x : [-infinity;x2]X[y1,y2]
			while (temp!=null){//this will take all the segment
				if (Math.max(temp.getData().getX1(), temp.getData().getX2())<=window.getX2()){//it's in the x window
					if (window.getY1()<=temp.getData().getY1() && window.getY2()>=temp.getData().getY2())//it's in the y window
						rep.add(temp.getData());
					if (window.getY1()<=temp.getMedian())
						temp=temp.getLeft();
					if (window.getY1()>=temp.getMedian())
						temp=temp.getRight();
				}
			}
		}
		if (window.getX2()==Integer.MAX_VALUE){//the window is without min in x : [x1;+infinity]X[y1,y2]

		}
		if (window.getY1()==Integer.MAX_VALUE){//the window is without min in y : [x1;x2]X[-infinity,y2]

		}
		if (window.getY2()==Integer.MAX_VALUE){//the window is without max in y : [x1;x2]X[y1,+infinity]

		}
		return rep;
	}


}
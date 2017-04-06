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
			temp.setLeft(construct(new ArrayList<Segment>(list.subList(0,(list.size()-1)/2))));
			temp.setRight(construct(new ArrayList<Segment>(list.subList((list.size()-1)/2,list.size()))));
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
}
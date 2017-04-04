package data;

import java.util.ArrayList;

/**
 * Created by marco on 1/04/17.
 * this class represents the priority search tree
 */
public class Pst {

	private Node root;

	public Pst(ArrayList<Seg> list) {
		construct(list,root);
	}

	/***
	 * This method is used by the constructor to create step by step the priority search tree.
	 * @param list a list of element sorted in y
	 * @param temp a Node variable that will stock the root of the tree
	 */
	private void construct(ArrayList<Seg> list, Node temp) {
		//for the method list.sublist() , the first index is inclusive and the second exclusive
		while (list.size()!=1) {
			temp=new Node(list.remove(firstx(list)), (list.get((list.size()-1)/2).getY1()) );
			construct((ArrayList<Seg>) list.subList(0,list.size()/2),temp.nextl);
			construct((ArrayList<Seg>) list.subList(list.size()/2,list.size()-1),temp.nextr);
		}
		temp=new Node(list.remove(firstx(list)), (list.get(0).getY1()) );
	}


	/***
	 * this method is used to have the minimum in x in a list of Seg sorted in y,
	 * note that there is no duplicate value in x (prerequisite).
	 * @param list
	 * @return the INDICE of the minimum Seg in x
	 */
	public int firstx(ArrayList<Seg> list) {
		int min = 0;
		Seg val, mini;
		for (int i=1;i<list.size();i++){
			val=list.get(i);
			mini=list.get(min);
			if (val.getX1() < mini.getX1())
				min=i;
			else {
				if (val.getX1() == mini.getX1() && val.getX2() < mini.getX2())
					min=i;
			}
		}
		return min;
	}
}
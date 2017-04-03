package data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marco on 1/04/17.
 * this class represents the priority search tree
 */
public class Pst {

	private Node root;

	public Pst(ArrayList<Tuple> list) {
		construct(list,root);
			//root.setNextl(list.subList(0,(list.size()/2)-1));
	}

	private void construct(ArrayList<Tuple> list,Node temp) {
		while (list.size()!=1) {
			temp=new Node(list.remove(firstx(list)), (list.get(list.size() - 1).getY1() - list.get(0).getY1())/2);
			construct((ArrayList<Tuple>) list.subList(0,list.size()/2),temp.nextl);//voir si compris ou pas le dernier elem
			construct((ArrayList<Tuple>) list.subList(list.size()/2,list.size()-1),temp.nextr);
		}
		temp=new Node(list.remove(firstx(list)), (list.get(list.size() - 1).getY1() - list.get(0).getY1())/2);
		construct((ArrayList<Tuple>) list.subList(0,list.size()/2),temp.nextl);
	}


	/***
	 * this method is used to have the minimum in x in a list of Tuple sorted in y, there is no duplicate value in x
	 * @param list
	 * @return the INDICE of the minimum Tuple in x
	 */
	public int firstx(ArrayList<Tuple> list) {
		int min = 0;
		Tuple val, mini;
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
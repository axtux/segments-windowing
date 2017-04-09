package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import data.Node;
import data.Pst;
import data.Segment;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by marco on 5/04/17.
 */
public class PstTests {

	ArrayList<Segment> list = new ArrayList<Segment>();

	/**
	 * This initialise a list of Segment and add it to list(variable)
	 */
	@Before
	public void  initList(){
		int i=0;
		while (i<30) {
			int x1= i+1;
			int x2= i+2;
			int y1= i+1;
			int y2= i+2;
			list.add(new Segment(x1,x2,y1,y2));
			i++;
		}
	}

	/**
	 * This function is used to have each Segment object in all the nodes of the Priority Search Tree
	 * @param root the root of the Priority Search Tree
	 * @return a list containing all {@link Segment}s in the tree
	 */
	public ArrayList<Segment> listofSonsData(Node<Segment> root){
		ArrayList<Segment> rep=new ArrayList<Segment>();
		if (root!= null){
			rep.add(root.getData());
			if (root.getLeft()!=null)
				rep.addAll(listofSonsData(root.getLeft()));
			if(root.getRight()!=null)
				rep.addAll(listofSonsData(root.getRight()));
		}
		return rep;
	}

	/**
	 * This function is used to have all the nodes of a Priority search tree
	 * @param root the root of the Pst
	 * @return a list containing all {@link Node}s in the tree
	 */
	public ArrayList<Node> listofNodes(Node<Segment> root){
		ArrayList<Node> rep=new ArrayList<>();
		if (root!= null){
			rep.add(root);
			if (root.getLeft()!=null)
				rep.addAll(listofNodes(root.getLeft()));
			if(root.getRight()!=null)
				rep.addAll(listofNodes(root.getRight()));
		}
		return rep;
	}

	/**
	 * This function is used in medianTest to test if a median is respected for a node
	 * @param root The node to verify
	 */
	public void  submedianTest(Node<Segment> root) {
		float median = root.getMedian();
		ArrayList<Segment> sons = listofSonsData(root);//it contains the root too
		List<Segment> sonsleft = sons.subList(0, sons.size() / 2);
		List<Segment> sonsright = sons.subList(sons.size() / 2, sons.size());
		//System.out.println("sons left");
		for (Segment s : sonsleft) {
			//System.out.println(s.toString());
			assertTrue(s.getY1() <= median);
		}
		//System.out.println("Sons right");
		for (Segment s : sonsright) {
			//System.out.println(s.toString());
			assertTrue(s.getY1() >= median);
		}
	}
	/**
	 * Test is passed if the root of the tree is correct (it's the minimum in x)
	 */
	@Test
	public void constructRootTest(){
		Segment seg=new Segment(-42,-42,-42,-42);
		list.add(seg);
		list.sort(Segment::compareTo);
		Pst abr = new Pst(list);
		//the first segment is the min in x
		assertTrue(abr.getRoot().getData().equals(seg));
		//abr.printPst(abr.getRoot(),"");//used to see the Pst in terminal

	}

	/**
	 * Test is passed if the median is correct by definition, it looks for each nodes if the median is correct
	 */
	@Test
	public void medianTest(){
		list.sort(Segment::compareTo);
		Pst abr = new Pst(list);
		Node<Segment> root=abr.getRoot();
		ArrayList<Node> nodes = listofNodes(root);
		for (Node n:nodes) {
			//System.out.println(n.getData().toString()); //to verify that all the nodes are present
			submedianTest(n);
		}
	}

}

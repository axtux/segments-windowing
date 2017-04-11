package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import data.BasicPst;
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
		while (i<10) {
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
	private ArrayList<Segment> listofSonsData(Node<Segment> root){
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
	 * @param root the root of the BasicPst
	 * @return a list containing all {@link Node}s in the tree
	 */
	private ArrayList<Node> listofNodes(Node<Segment> root){
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
	 * Test is passed if the root of the tree is correct (it's the minimum in x)
	 */
	@Test
	public void constructRootTest(){
		Segment seg=new Segment(-42,-42,-42,-42);//the first segment is the min in x
		list.add(seg);
		Pst abrs = new Pst(list);
		BasicPst abr=abrs.getOriginal();
		ArrayList<Node> nodes=listofNodes(abr.getRoot());
		for (Node<Segment> n1:nodes) {
			ArrayList<Node> descendent=listofNodes(n1);
			for (Node<Segment> n2:descendent) {
				assertTrue(Math.min(n1.getData().getX1(),n1.getData().getX2())<=Math.min(n2.getData().getX1(),n2.getData().getX2()));
			}
		}
		//abr.printPst(abr.getRoot(),"");//used to see the BasicPst in terminal

	}

	/**
	 * Test is passed if the median is correct by definition, it looks for each nodes if the median is correct
	 */
	@Test
	public void medianTest(){

		Pst abrs = new Pst(list);
		BasicPst abr=abrs.getOriginal();
		ArrayList<Node> nodes = listofNodes(abr.getRoot());
		//System.out.println(nodes.size());
		for (Node n:nodes) {
			//System.out.println(n.getData().toString()); //to verify that all the nodes are present
			submedianTest(n);
		}
	}

	/**
	 * This function is used in medianTest to test if a median is respected for a node
	 * @param root The node to verify
	 */
	private void  submedianTest(Node<Segment> root) {
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
	 * We test herre the windowing on a bordered window
	 */
	@Test
	public void windowingTest1(){
		ArrayList<Segment> list = new ArrayList<Segment>();
		list.add(new Segment(1,2,0,0));//in vertical
		list.add(new Segment(0,5,1,1));//in vert
		list.add(new Segment(-4,1,2,2));//in vert
		list.add(new Segment(4,15,3,3));//in vert
		list.add(new Segment(1,1,1,2));//in horyzontal
		list.add(new Segment(2,2,0,5));//in hory
		list.add(new Segment(3,3,-4,1));//in hory
		list.add(new Segment(4,4,4,15));//in hory
		list.add(new Segment(-5,-4,4,4));//not in ,x left
		list.add(new Segment(8,9,5,5));//not in ,x right
		list.add(new Segment(0,0,-5,-4));//not in , y down
		list.add(new Segment(5,5,6,8));//not in , y up
		Pst abr = new Pst(list);
		ArrayList<Segment> segs = abr.getWindow(new Segment(0,1,0,1));
		//abr.getOriginal().printPst(abr.getOriginal().getRoot(),"");//used to see the BasicPst in terminal
		System.out.println(segs.size());
		for (Segment s:segs) {
			System.out.println(s.toString());
		}
	}

}

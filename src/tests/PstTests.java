package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import data.BasicPst;
import data.Pst;
import data.PstNode;
import data.Segment;

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

	public ArrayList<Segment> listofSonsData(PstNode root){

		ArrayList<Segment> rep=new ArrayList<Segment>();
		if (root!= null){
			rep.add(root.getSegment());
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
	 * @return a list containing all {@link PstNode}s in the tree
	 */

	public ArrayList<PstNode> listofNodes(PstNode root){

		ArrayList<PstNode> rep=new ArrayList<>();

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
		ArrayList<PstNode> nodes=listofNodes(abr.getRoot());
		for (PstNode n1:nodes) {
			ArrayList<PstNode> descendent=listofNodes(n1);
			for (PstNode n2:descendent) {
				assertTrue(Math.min(n1.getSegment().getX1(),n1.getSegment().getX2())<=Math.min(n2.getSegment().getX1(),n2.getSegment().getX2()));
			}
		}
		//abr.printPst();//used to see the BasicPst in terminal

	}


	/**
	 * This function is used in medianTest to test if a median is respected for a node
	 * @param root The node to verify
	 */

	public void  submedianTest(PstNode root) {
		int median = root.getMedian();
		
		List<Segment> leftSegments = listofSonsData(root.getLeft());
		List<Segment> rightSegments = listofSonsData(root.getRight());
		
		//System.out.println("sons left "+leftSegments.size());
		for (Segment s : leftSegments) {
			//System.out.println(s.toString());
			assertTrue(s.getY1() <= median);
		}
		//System.out.println("Sons right "+rightSegments.size());
		for (Segment s : rightSegments) {
			//System.out.println(s.toString());
			assertTrue(s.getY1() >= median);
		}
	}

	/**
	 * Test is passed if the median is correct by definition, it looks for each nodes if the median is correct
	 */
	@Test
	public void medianTest(){

		Pst abrs = new Pst(list);
		BasicPst abr=abrs.getOriginal();
		ArrayList<PstNode> nodes = listofNodes(abr.getRoot());
		//System.out.println(nodes.size());
		for (PstNode n:nodes) {
			//System.out.println(n.getSegment().toString()); //to verify that all the nodes are present
			submedianTest(n);
		}
	}


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
		ArrayList<Segment> verification=new ArrayList<Segment>();
		verification.add(new Segment(1,2,0,0));//in vertical
		verification.add(new Segment(0,5,1,1));//in vert
		verification.add(new Segment(-4,1,2,2));//in vert
		verification.add(new Segment(4,15,3,3));//in vert
		verification.add(new Segment(1,1,1,2));//in horyzontal
		verification.add(new Segment(2,2,0,5));//in hory
		verification.add(new Segment(3,3,-4,1));//in hory
		verification.add(new Segment(4,4,4,15));//in hory
		Pst abr = new Pst(list);
		ArrayList<Segment> segs = abr.getWindow(new Segment(0,5,0,5));
		//abr.getOriginal().printPst();//used to see the BasicPst in terminal
		//System.out.println(abr.getOriginal().getRoot().toString());
		/*System.out.println(segs.size());
		for (Segment s:segs) {
			System.out.println(s.toString());
		}*/
		for (Segment s :segs) {
			assertTrue(verification.contains(s));
		}
	}

	/**
	 * test if the vertical segment problem is fixed
	 */
	@Test
	public void windowingTest2(){
		list.add(new Segment(2,2,-1,2));
		Pst abr = new Pst(list);
		ArrayList<Segment> segs = abr.getWindow(new Segment(0,5,0,5));
		assertTrue(segs.contains(new Segment(2,2,-1,2)));
	}

	/**
	 * test if the segment throught the window are taken
	 */
	@Test
	public void windowingTest3(){
		list.add(new Segment(20,-20,2,2));
		list.add(new Segment(2,2,20,-20));
		Pst abr = new Pst(list);
		ArrayList<Segment> segs = abr.getWindow(new Segment(0,5,0,5));
		assertTrue(segs.contains(new Segment(20,-20,2,2)));
		assertTrue(segs.contains(new Segment(2,2,20,-20)));
		/*System.out.println(segs.size());
		for (Segment s:segs) {
			System.out.println(s.toString());
		}*/
	}

	@Test
	public void windowingTest4(){
		ArrayList<Segment> segments=new ArrayList<Segment>();
		segments.add(new Segment(-42,0,2,2));//in and horyzontal
		segments.add(new Segment(5,45,1,1));//in and at the border
		segments.add(new Segment(-42,10,0,0));//in the border and beyond
		segments.add(new Segment(2,2,0,2));//in and vertical
		segments.add(new Segment(1,1,-5,2));// in and behind
		segments.add(new Segment(0,0,10,2));// in and beyond
		segments.add(new Segment(3,3,-42,42));//goes throught
		ArrayList<Segment> verification=new ArrayList<>();
		verification.addAll(segments);
		segments.add(new Segment(6,28,2,2));
		segments.add(new Segment(2,2,6,8));
		segments.add(new Segment(2,2,-4,-7));
		segments.add(new Segment(41,43,42,42));
		Pst abr = new Pst(segments);
		ArrayList<Segment> segs = abr.getWindow(new Segment(Integer.MIN_VALUE,5,0,5));
		/*System.out.println(segs.size());
		for (Segment s:segs) {
			System.out.println(s.toString());
		}*/
		for (Segment s:segs) {
			assertTrue(verification.contains(s));
		}
	}

	@Test
	public void windowingTest5(){
		Pst abr = new Pst(list);
		ArrayList<Segment> window1 = abr.getWindow(new Segment(Integer.MIN_VALUE,5,0,5));
		ArrayList<Segment> window2 = abr.getWindow(new Segment(3,Integer.MAX_VALUE,0,5));
		ArrayList<Segment> window3 = abr.getWindow(new Segment(0,5,Integer.MIN_VALUE,5));
		ArrayList<Segment> window4 = abr.getWindow(new Segment(5,0,5,Integer.MAX_VALUE));
		ArrayList<Segment> window5 = abr.getWindow(new Segment(0,5,0,5));
		assertFalse(window1.isEmpty());
		assertFalse(window2.isEmpty());
		assertFalse(window3.isEmpty());
		assertFalse(window4.isEmpty());
		assertFalse(window5.isEmpty());
	}

}

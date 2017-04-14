package tests;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import data.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by marco on 5/04/17.
 */
public class PstTests {

	private Array<Segment> list = new Array<Segment>();

	/**
	 * This initialise a list of Segment and add it to list (variable).
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
	 * @param root The root of the Priority Search Tree
	 * @return An {@link Array} containing all {@link Segment}s in the tree
	 */

	private Array<Segment> getSegments(PstNode root){

		Array<Segment> rep=new Array<Segment>();
		if (root!= null){
			rep.add(root.getSegment());
			if (root.getLeft()!=null)
				rep.addAll(getSegments(root.getLeft()));
			if(root.getRight()!=null)
				rep.addAll(getSegments(root.getRight()));
		}
		return rep;
	}

	/**
	 * This function is used to have all the nodes of a Priority search tree
	 * @param root The root of the Pst
	 * @return An {@link Array} containing all {@link PstNode}s in the tree
	 */

	private Array<PstNode> getNodes(PstNode root){

		Array<PstNode> rep=new Array<>();

		if (root!= null){
			rep.add(root);
			if (root.getLeft()!=null)
				rep.addAll(getNodes(root.getLeft()));
			if(root.getRight()!=null)
				rep.addAll(getNodes(root.getRight()));
		}
		return rep;
	}

	/**
	 * Test is passed if the root of the each sub-tree in the {@link Pst} is correct (it's the minimum in x)
	 */
	@Test
	public void rootTest(){

		Segment seg=new Segment(-42,-42,-42,-42);//the first segment is the min in x
		list.add(seg);
		Pst abrs = new Pst(list);
		BasicPst abr=abrs.getOriginal();
		Array<PstNode> nodes= getNodes(abr.getRoot());

		for (PstNode n1:nodes) {
			Array<PstNode> descendent= getNodes(n1);
			for (PstNode n2:descendent) {
				assertTrue(Math.min(n1.getSegment().getX1(),n1.getSegment().getX2())<=Math.min(n2.getSegment().getX1(),n2.getSegment().getX2()));
			}
		}
		//abr.printPst();//used to see the BasicPst in terminal

	}


	/**
	 * This function is used in medianTest to test if a median is respected for a specific node
	 * @param root The node to verify
	 */

	private void medianTestNode(PstNode root) {

		int median = root.getMedian();
		
		List<Segment> leftSegments = getSegments(root.getLeft());
		List<Segment> rightSegments = getSegments(root.getRight());

		for (Segment s : leftSegments) {
			assertTrue(s.getY1() <= median);
		}
		for (Segment s : rightSegments) {
			assertTrue(s.getY1() >= median);
		}
	}

	/**
	 * Test is passed if the median is correct by definition for each nodes in the {@link Pst}
	 */
	@Test
	public void medianTest(){

		Pst abrs = new Pst(list);
		BasicPst abr=abrs.getOriginal();
		Array<PstNode> nodes = getNodes(abr.getRoot());

		for (PstNode n:nodes) {
			//System.out.println(n.getSegment().toString()); //to verify that all the nodes are present
			medianTestNode(n);
		}
	}

	/**
	 * This test is used to verify all the differents type of segments are taken the windowing in a bordered window ([x;x']X[y;y']).
	 */
	@Test
	public void windowingTest1(){

		Array<Segment> list = new Array<Segment>();
		list.add(new Segment(1,2,0,0));//horizontal segment completely in the window
		list.add(new Segment(0,5,1,1));//horizontal segment that touches the borders of the window
		list.add(new Segment(-4,1,2,2));//horizontal segment not completely in the window, x1 is behind the window in x
		list.add(new Segment(4,15,3,3));//horizontal segment not completely in the window, x2 is beyond the window in x

		list.add(new Segment(1,1,1,2));//vertical segment completely in the window
		list.add(new Segment(2,2,0,5));//vertical segment that touches the borders of the window
		list.add(new Segment(3,3,-4,1));//vertical segment not completely in the window, Y1 is behind the window in y
		list.add(new Segment(4,4,4,15));//vertical segment not completely in the window, y2 is beyond the window in y

		list.add(new Segment(20,-20,2,2));//horizontal segment that cross over the window
		list.add(new Segment(2,2,20,-20));//vertical segment that cross over the window

		Array<Segment> verification=new Array<Segment>(list);

		list.add(new Segment(-5,-4,4,4));//horizontal segment not in the window, x1 and x2 are behind the window in x
		list.add(new Segment(8,9,5,5));//horizontal segment not in the window, x1 and x2 are beyond the window in x
		list.add(new Segment(0,0,-5,-4));//vertical segment not in the window, y1 and y2 are behind the window in y
		list.add(new Segment(5,5,6,8));//vertical segment not in the window, y1 and y2 are beyond the window in y

		Pst abr = new Pst(list);
		Array<Segment> segs = abr.getWindow(new Segment(0,5,0,5));

		assertTrue(segs.size()==verification.size());
		for (Segment s :segs) {
			assertTrue(verification.contains(s));
		}
	}

	/**
	 * This test is passed if all the correct segments are taken by the windowing application on a window not bordered in x (-infinity).
	 * The form of the window is like that : [Integer.MIN_VALUE;x2]X[y1;y2]
	 */
	@Test
	public void windowingTest2(){

		ArrayList<Segment> segments=new ArrayList<Segment>();
		segments.add(new Segment(-42,0,2,2));//horizontal segment completely in the window
		segments.add(new Segment(5,45,1,1));//horizontal segment not completely in the window, x2 is beyond the window in x and x1 is at the border
		segments.add(new Segment(-42,10,0,0));//horizontal segment not completely in the window, x2 is beyond the window in x

		segments.add(new Segment(1,1,1,2));//vertical segment completely in the window
		segments.add(new Segment(2,2,0,5));//vertical segment that touches the borders of the window
		segments.add(new Segment(3,3,-4,1));//vertical segment not completely in the window, Y1 is behind the window in y
		segments.add(new Segment(4,4,4,15));//vertical segment not completely in the window, y2 is beyond the window in y

		segments.add(new Segment(3,3,-42,42));//vertical segment that cross over the window

		ArrayList<Segment> verification=new ArrayList<>();
		verification.addAll(segments);

		segments.add(new Segment(6,28,2,2));//segments that are not in the window
		segments.add(new Segment(2,2,6,8));
		segments.add(new Segment(2,2,-4,-7));
		segments.add(new Segment(41,43,42,42));

		Pst abr = new Pst(segments);
		ArrayList<Segment> segs = abr.getWindow(new Segment(Integer.MIN_VALUE,5,0,5));

		assertTrue(segs.size()==verification.size());
		for (Segment s:segs) {
			assertTrue(verification.contains(s));
		}
	}

	/**
	 * This test is passed if all the correct segments are taken by the windowing application on a window not bordered in x (+infinity).
	 * The form of the window is like that : [x1;Integer.MAX_VALUE]X[y1;y2]
	 */
	@Test
	public void windowingTest3(){

		new Segment(5,0,5,Integer.MAX_VALUE);

		ArrayList<Segment> segments=new ArrayList<>();
		segments.add(new Segment(42,1,2,2));//horizontal segment completely in the window
		segments.add(new Segment(0,-45,1,1));//horizontal segment not completely in the window, x2 is behind the window in x and x1 is at the border
		segments.add(new Segment(-42,10,0,0));//horizontal segment not completely in the window, x1 is behind the window in x

		segments.add(new Segment(1,1,1,2));//vertical segment completely in the window
		segments.add(new Segment(2,2,0,5));//vertical segment that touches the borders of the window
		segments.add(new Segment(3,3,-4,1));//vertical segment not completely in the window, Y1 is behind the window in y
		segments.add(new Segment(4,4,4,15));//vertical segment not completely in the window, y2 is beyond the window in y

		segments.add(new Segment(3,3,-42,42));//vertical segment that cross over the window

		ArrayList<Segment> verification=new ArrayList<>();
		verification.addAll(segments);

		segments.add(new Segment(-6,-28,2,2));//segments that are not in the window
		segments.add(new Segment(2,2,6,8));
		segments.add(new Segment(2,2,-4,-7));
		segments.add(new Segment(-41,-43,42,42));

		Pst abr = new Pst(segments);
		ArrayList<Segment> segs = abr.getWindow(new Segment(0,Integer.MAX_VALUE,0,5));

		assertTrue(segs.size()==verification.size());
		for (Segment s:segs) {
			assertTrue(verification.contains(s));
		}
	}

	/**
	 * This test is passed if all the correct segments are taken by the windowing application on a window not bordered in y (-infinity).
	 * The form of the window is like that : [x1;x2]X[Integer.MIN_VALUE;y2]
	 */
	@Test
	public void windowingTest4(){

		ArrayList<Segment> segments=new ArrayList<>();
		segments.add(new Segment(1,2,0,0));//horizontal segment completely in the window
		segments.add(new Segment(0,5,1,1));//horizontal segment that touches the borders of the window
		segments.add(new Segment(-4,1,2,2));//horizontal segment not completely in the window, x1 is behind the window in x
		segments.add(new Segment(4,15,3,3));//horizontal segment not completely in the window, x2 is beyond the window in x

		segments.add(new Segment(2,2,-41,1));//vertical segment completely in the window
		segments.add(new Segment(0,0,5,25));//vertical segment not completely in the window, y2 is beyond the window in y and y1 is at the border
		segments.add(new Segment(1,1,2,25));//vertical segment not completely in the window, y2 is beyond the window in y

		segments.add(new Segment(-42,42,4,4));//horizontal segment that cross over the window

		ArrayList<Segment> verification=new ArrayList<>();
		verification.addAll(segments);

		segments.add(new Segment(6,28,2,2));//segments that are not in the window
		segments.add(new Segment(-6,-28,2,2));
		segments.add(new Segment(2,2,6,8));
		segments.add(new Segment(41,43,42,42));

		Pst abr = new Pst(segments);
		ArrayList<Segment> segs = abr.getWindow(new Segment(0,5,Integer.MIN_VALUE,5));

		assertTrue(segs.size()==verification.size());
		for (Segment s:segs) {
			assertTrue(verification.contains(s));
		}
	}

	/**
	 * This test is passed if all the correct segments are taken by the windowing application on a window not bordered in y (-infinity).
	 * The form of the window is like that : [x1;x2]X[y1;Integer.MAX_VALUE]
	 */
	@Test
	public void windowingTest5(){

		ArrayList<Segment> segments=new ArrayList<>();
		segments.add(new Segment(1,2,0,0));//horizontal segment completely in the window
		segments.add(new Segment(0,5,1,1));//horizontal segment that touches the borders of the window
		segments.add(new Segment(-4,1,2,2));//horizontal segment not completely in the window, x1 is behind the window in x
		segments.add(new Segment(4,15,3,3));//horizontal segment not completely in the window, x2 is beyond the window in x

		segments.add(new Segment(2,2,41,1));//vertical segment completely in the window
		segments.add(new Segment(0,0,0,-25));//vertical segment not completely in the window, y2 is behind the window in y and y1 is at the border
		segments.add(new Segment(1,1,-42,25));//vertical segment not completely in the window, y1 is behind the window in y

		segments.add(new Segment(-42,42,25,25));//horizontal segment that cross over the window

		ArrayList<Segment> verification=new ArrayList<>();
		verification.addAll(segments);

		segments.add(new Segment(6,28,2,2));//segments that are not in the window
		segments.add(new Segment(-6,-28,2,2));
		segments.add(new Segment(2,2,-6,-8));
		segments.add(new Segment(41,43,-42,-42));

		Pst abr = new Pst(segments);
		ArrayList<Segment> segs = abr.getWindow(new Segment(0,5,0,Integer.MAX_VALUE));

		assertTrue(segs.size()==verification.size());
		for (Segment s:segs) {
			assertTrue(verification.contains(s));
		}
	}

}

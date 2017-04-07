package tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Random;

import data.Node;
import data.Pst;
import data.Segment;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by marco on 5/04/17.
 */
public class PstTests {

	ArrayList<Segment> list = new ArrayList<Segment>();

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
		abr.printPst(abr.getRoot(),"");//used to see the Pst in terminal

	}

	/**
	 * Test is passed if the median is correct by definition
	 */
	@Test
	public void  medianTest(){
		list.sort(Segment::compareTo);
		Pst abr = new Pst(list);
		Node<Segment> temp=abr.getRoot();
		float median=temp.getMedian();
		assertTrue(temp.getLeft().getData().getY1()<=median);
		assertTrue(temp.getRight().getData().getY1()>=median);

	}


}

package tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Random;

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

	@Test
	public void constructPst(){
		list.add(new Segment(-42,-42,-42,-42));
		list.sort(Segment::compareTo);
		Pst abr = new Pst(list);
		//the first segment is the min in x
		assertTrue(new Segment(-42,-42,-42,-42).getX1() == abr.getRoot().getData().getX1());
		//assertTrue(new Segment(-42,-42,-42,-42).getX2() == abr.getRoot().getData().getX2());
		//assertTrue(new Segment(-42,-42,-42,-42).getY1() == abr.getRoot().getData().getY1());
		//assertTrue(new Segment(-42,-42,-42,-42).getY2() == abr.getRoot().getData().getY2());
		//faire méthode printAbr qui va print tout l'abre niveau par niveau comme ca Seg1:x1,x2,y1,y2
	}


}

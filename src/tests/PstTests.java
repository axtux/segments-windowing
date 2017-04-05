package tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Random;

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
			int x1= new Random().nextInt(50);
			int x2= new Random().nextInt(100);
			int y1= new Random().nextInt(200);
			int y2= new Random().nextInt(200);
			list.add(new Segment(x1,x2,y1,y2));
			i++;
		}
	}

	@Test
	public void constructPst(){
		list.add(new Segment(-42,-42,-42,-42));
		list.sort(Segment::compareTo);
		Pst abr = new Pst(list);
		assertEquals(new Segment(-42,-42,-42,-42),abr.getRoot().getData());
	}


}

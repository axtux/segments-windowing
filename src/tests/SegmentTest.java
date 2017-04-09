package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import data.Segment;

public class SegmentTest {
	@Test
	public void sameYsameX() {
		Segment s1 = new Segment(1, 1, 2, 2);
		Segment s2 = new Segment(1, 1, 2, 2);
		assertEquals(s1, s2);
		
	}
	@Test
	public void sameYNotSameX() {
		Segment s1 = new Segment(1, 2, 3, 3);
		Segment s2 = new Segment(2, 1, 3, 3);
		assertEquals(s1, s2);
		
	}
	@Test
	public void notSameYNotSameX() {
		Segment s1 = new Segment(1, 2, 3, 4);
		Segment s2 = new Segment(2, 1, 4, 3);
		assertEquals(s1, s2);
		
	}
}

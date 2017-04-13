package tests;

import java.util.ArrayList;

import org.junit.Test;

import data.Segment;

public class PstHorizontalTests extends PstFileTests {
	public PstHorizontalTests() {
		super("scenes/test_horizontal.txt");
	}
	
	@Test
	public void wholeTest() {
		Segment window = new Segment(Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
		ArrayList<Segment> filtered = scene.filter(window).getSegments();
		assertAllIn(filtered);
	}
	
	@Test
	public void closedTest() {
		Segment window = new Segment(-50, 50,-50, 50);
		ArrayList<Segment> filtered = scene.filter(window).getSegments();
		// line indexes
		int[] indexes = {8, 9, 10, 11, 20, 21};
		
		assertIndexesIn(indexes, filtered);
	}
	
	@Test
	public void leftTest() {
		Segment window = new Segment(Integer.MIN_VALUE, 50,-50, 50);
		ArrayList<Segment> filtered = scene.filter(window).getSegments();
		// line indexes
		int[] indexes = {8, 9, 10, 11, 12, 20, 21};
		
		assertIndexesIn(indexes, filtered);
	}
	@Test
	public void upTest() {
		Segment window = new Segment(-50, 50, -50, Integer.MAX_VALUE);
		ArrayList<Segment> filtered = scene.filter(window).getSegments();
		// line indexes
		int[] indexes = {8, 9, 10, 11, 14, 17, 18, 19, 20, 21};
		
		assertIndexesIn(indexes, filtered);
	}
	@Test
	public void rightTest() {
		Segment window = new Segment(-50, Integer.MAX_VALUE, -50, 50);
		ArrayList<Segment> filtered = scene.filter(window).getSegments();
		// line indexes
		int[] indexes = {8, 9, 10, 11, 13, 20, 21};
		
		assertIndexesIn(indexes, filtered);
	}
	@Test
	public void downTest() {
		Segment window = new Segment(-50, 50, Integer.MIN_VALUE, 50);
		ArrayList<Segment> filtered = scene.filter(window).getSegments();
		// line indexes
		int[] indexes = {4, 5, 6, 7, 8, 9, 10, 11, 20, 21};
		
		assertIndexesIn(indexes, filtered);
	}
}

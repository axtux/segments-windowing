package tests;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import data.Scene;
import data.Segment;

public class PstFileTests {
	protected Scene scene;
	protected ArrayList<Segment> segments;
	
	/**
	 * JUnit requires at least one test method per class.
	 */
	@Test
	public void noTest() {}
	/**
	 * Create scene from file and assert that no error occurred.
	 * @param filename File from which to load scene.
	 */
	protected void setFile(String filename) {
		scene = Scene.getScene(filename);
		Assert.assertNotNull(scene);
		segments = scene.getSegments();
		Assert.assertNotNull(segments);
	}
	/**
	 * Assert that all segments from segments are present in filtered.
	 * @param filtered List of segments.
	 */
	protected void assertAllIn(ArrayList<Segment> filtered) {
		Assert.assertEquals("filtered size", segments.size(), filtered.size());
		
		for(Segment s : segments) {
			Assert.assertTrue(s+" is not in filtered", filtered.contains(s));
		}
	}
	/**
	 * Assert indexes size matches filtered size. Assert all segments line indexes are present in filtered.
	 * @param indexes Line indexes in scene file.
	 * @param filtered List of segments within which to check that indexes are present.
	 */
	protected void assertIndexesIn(int[] indexes, ArrayList<Segment> filtered) {
		// segment indexes start at 0 (-1) and do not include first line (-1)
		for(int i = 0; i < indexes.length; ++i) indexes[i] = indexes[i]-2;
		
		Assert.assertEquals("filtered size", indexes.length, filtered.size());
		
		Segment s;
		for(int i : indexes) {
			s = segments.get(i);
			Assert.assertTrue(s+" is not in filtered", filtered.contains(s));
		}
	}
}

package data;

import java.util.ArrayList;

/**
 * Priority search tree that support any kind of windowing.
 * Windows supported :
 * - [X, X']x[Y, Y'] ;
 * - [-∞, X']x[Y, Y'] ;
 * - [X, +∞]x[Y, Y'] ;
 * - [X, X']x[-∞, Y'] ;
 * - [X, X']x[Y, +∞].
 */
public class Pst {
	BasicPst original;
	BasicPst opposed;
	BasicPst exchanged;
	BasicPst both;
	
	public Pst(ArrayList<Segment> segments) {
		// create 4 arrays
		ArrayList<Segment> original_segments = new ArrayList<Segment>(segments);
		ArrayList<Segment> opposed_segments = new ArrayList<Segment>(segments.size());
		ArrayList<Segment> exchanged_segments = new ArrayList<Segment>(segments.size());
		ArrayList<Segment> both_segments = new ArrayList<Segment>(segments.size());
		
		for(Segment s : segments) {
			opposed_segments.add(s.oppose());
			exchanged_segments.add(s.exchange());
			both_segments.add(s.oppose().exchange());
		}
		
		// sort arrays
		Heap.sortArray(original_segments);
		Heap.sortArray(opposed_segments);
		Heap.sortArray(exchanged_segments);
		Heap.sortArray(both_segments);
		
		original = new BasicPst(original_segments);
		opposed = new BasicPst(opposed_segments);
		exchanged = new BasicPst(exchanged_segments);
		both = new BasicPst(both_segments);
	}
	
	public ArrayList<Segment> getWindow(Segment window) {
		if(window.getMinX() == Integer.MIN_VALUE) {
			// case [-∞, X']x[Y, Y']
			return getLeftWindow(window);
		}
		
		if(window.getMaxX() == Integer.MAX_VALUE) {
			// case [X, +∞]x[Y, Y']
			return getRightWindow(window);
		}
		
		if(window.getMinY() == Integer.MIN_VALUE) {
			// case [X, X']x[-∞, Y']
			return getDownWindow(window);
		}
		
		if(window.getMaxX() == Integer.MAX_VALUE) {
			// case [X, X']x[Y, +∞]
			return getUpWindow(window);
		}
		
		// case [X, X']x[Y, Y']
		return getLeftWindow(window);
	}
	
	public ArrayList<Segment> getLeftWindow(Segment window) {
		// this one is always efficient using windowing
		return original.windowing(window.getWindow());
	}
	
	public ArrayList<Segment> getRightWindow(Segment window) {
		// oppose coordinates to be able to use efficient windowing
		ArrayList<Segment> segments = opposed.windowing(window.oppose().getWindow());
		for(int i = 0; i < segments.size(); ++i) {
			segments.set(i, segments.get(i).oppose());
		}
		return segments;
	}
	
	public ArrayList<Segment> getDownWindow(Segment window) {
		// exchange coordinates to be able to use efficient windowing
		ArrayList<Segment> segments = exchanged.windowing(window.exchange().getWindow());
		for(int i = 0; i < segments.size(); ++i) {
			segments.set(i, segments.get(i).exchange());
		}
		return segments;
	}
	
	public ArrayList<Segment> getUpWindow(Segment window) {
		// oppose and exchange coordinates to be able to use efficient windowing
		ArrayList<Segment> segments = both.windowing(window.oppose().exchange().getWindow());
		for(int i = 0; i < segments.size(); ++i) {
			segments.set(i, segments.get(i).oppose().exchange());
		}
		return segments;
	}
}

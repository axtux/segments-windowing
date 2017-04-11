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
		// TODO implement
		return null;
	}
}

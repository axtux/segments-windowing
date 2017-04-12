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
	BasicPst opposed_exchanged;
	
	public Pst(ArrayList<Segment> segments) {
		// make 4 arrays from segments
		Array<Segment> original_segments = new Array<Segment>(segments);
		Array<Segment> opposed_segments = new Array<Segment>(segments);
		Array<Segment> exchanged_segments = new Array<Segment>(segments);
		Array<Segment> opposed_exchanged_segments = new Array<Segment>(segments);
		
		// make them what they say they are
		opposeArray(opposed_segments);
		exchangeArray(exchanged_segments);
		opposeArray(opposed_exchanged_segments);
		exchangeArray(opposed_exchanged_segments);
		
		// create a BasicPst for each array
		original = new BasicPst(original_segments);
		opposed = new BasicPst(opposed_segments);
		exchanged = new BasicPst(exchanged_segments);
		opposed_exchanged = new BasicPst(opposed_exchanged_segments);
	}
	
	public ArrayList<Segment> getWindow(Segment window) {
		window = window.getWindow();
		System.out.println("selected window "+window);
		
		if(window.getMinX() == Integer.MIN_VALUE) {
			System.out.println("case [-∞, X']x[Y, Y']");
			// case [-∞, X']x[Y, Y']
			return getLeftWindow(window);
		}
		
		if(window.getMaxX() == Integer.MAX_VALUE) {
			System.out.println("case case [X, +∞]x[Y, Y']");
			// case [X, +∞]x[Y, Y']
			return getRightWindow(window);
		}
		
		if(window.getMinY() == Integer.MIN_VALUE) {
			System.out.println("case [X, X']x[-∞, Y']");
			// case [X, X']x[-∞, Y']
			return getDownWindow(window);
		}
		
		if(window.getMaxX() == Integer.MAX_VALUE) {
			System.out.println("case [X, X']x[Y, +∞]");
			// case [X, X']x[Y, +∞]
			return getUpWindow(window);
		}
		
		System.out.println("case [X, X']x[Y, Y']");
		// case [X, X']x[Y, Y']
		return getLeftWindow(window);
	}
	
	public ArrayList<Segment> getLeftWindow(Segment window) {
		// this one is always efficient using windowing
		return original.windowing(window);
	}
	
	public ArrayList<Segment> getRightWindow(Segment window) {
		// oppose coordinates to be able to use efficient windowing
		ArrayList<Segment> segments = opposed.windowing(window.oppose());
		// recover coordinates to original state
		opposeArray(segments);
		return segments;
	}
	
	public ArrayList<Segment> getDownWindow(Segment window) {
		// exchange coordinates to be able to use efficient windowing
		ArrayList<Segment> segments = exchanged.windowing(window.exchange());
		// recover coordinates to original state
		exchangeArray(segments);
		return segments;
	}
	
	public ArrayList<Segment> getUpWindow(Segment window) {
		// oppose and exchange coordinates to be able to use efficient windowing
		ArrayList<Segment> segments = opposed_exchanged.windowing(window.oppose().exchange());
		// recover coordinates to original state
		opposeArray(segments);
		exchangeArray(segments);
		return segments;
	}
	
	private void opposeArray(ArrayList<Segment> segments) {
		for(int i = 0; i < segments.size(); ++i) {
			segments.set(i, segments.get(i).oppose());
		}
	}
	private void exchangeArray(ArrayList<Segment> segments) {
		for(int i = 0; i < segments.size(); ++i) {
			segments.set(i, segments.get(i).exchange());
		}
	}

	public BasicPst getOriginal() {
		return original;
	}
}

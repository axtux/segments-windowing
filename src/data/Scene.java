package data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Scene containing a window and a list of segments.
 */
public class Scene {
	private Segment window, subWindow;
	private ArrayList<Segment> segments;
	private Pst pst;
	/**
	 * Get Scene from file.
	 * @param filename File from which to read Scene.
	 * @return Segments or null on error.
	 */
	public static Scene getScene(String filename) {
		try {
			return new Scene(filename);
		} catch (IOException e) {
			return null;
		} catch (NoSuchElementException e) {
			return null;
		}
	}
	/**
	 * Build Scene from file.
	 * @param filename File from which to read Scene.
	 * @throws IOException In case an error occurs reading file.
	 * @throws NoSuchElementException If input is exhausted, if the next token does not match the Float regular expression, or is out of range.
	 */
	public Scene(String filename) throws IOException, NoSuchElementException {
		if(filename == null) throw new NullPointerException();
		
		Scanner lecteur = new Scanner(new File(filename));
		lecteur.useLocale(Locale.US);//used to detected the representation of the float
		
		// first line contains x x' y y', [x, x']x[y, y'] being window limits
		Segment w = new Segment(
			(int) lecteur.nextFloat(),
			(int) lecteur.nextFloat(),
			(int) lecteur.nextFloat(),
			(int) lecteur.nextFloat()
		);
		if(!setWindow(w)) {
			lecteur.close();
			throw new InputMismatchException("window width/height must be >= 10");
		}
		
		// following line contains x y x' y', each one define a segment (why different order ?)
		segments = new ArrayList<Segment>();
		Segment tmp;
		
		while (lecteur.hasNextFloat()) {
			int x1 = (int) lecteur.nextFloat();
			int y1 = (int) lecteur.nextFloat();
			int x2 = (int) lecteur.nextFloat();
			int y2 = (int) lecteur.nextFloat();
			tmp = new Segment(x1, x2, y1, y2);
			if(!addSegment(tmp)) {
				lecteur.close();
				throw new InputMismatchException("segment must be vertical or horizontal");
			}
		}
		
		lecteur.close();
		
		pst = new Pst(segments);
	}

	private boolean setWindow(Segment window) {
		if(!validWindow(window)) {
			return false;
		}
		
		this.window = window.getWindow();
		return true;
	}
	
	private boolean setSubWindow(Segment subWindow) {
		// restrict sub window to window
		int x1 = Math.max(subWindow.getMinX(), window.getMinX());
		int x2 = Math.min(subWindow.getMaxX(), window.getMaxX());
		int y1 = Math.max(subWindow.getMinY(), window.getMinY());
		int y2 = Math.min(subWindow.getMaxY(), window.getMaxY());
		
		this.subWindow = new Segment(x1, x2, y1, y2);
		return true;
	}
	/**
	 * Check that window {@code X1 < X2}, {@code Y1 < Y2} and minimum width/height is 10.
	 * @param window Window to check.
	 * @return True if window is valid, false otherwise.
	 */
	public static boolean validWindow(Segment window) {
		int width = window.getX2()-window.getX1();
		int height = window.getY2()-window.getY1();
		if(width < 10 || height < 10) {
			return false;
		}
		return true;
	}
	/**
	 * Check that the segment is vertical or horizontal and add it.
	 * @param s Segment to add.
	 * @return True on success, false on error. An error occurs if segment is neither vertical neither horizontal.
	 */
	private boolean addSegment(Segment s) {
		if(s.getX1() != s.getX2() && s.getY1() != s.getY2()) {
			return false;
		}
		
		segments.add(s);
		return true;
	}
	/**
	 * Get window.
	 * @return View window.
	 */
	public Segment getWindow() {
		return window;
	}
	/**
	 * Get sub window.
	 * @return Sub window is used to test windowing.
	 */
	public Segment getSubWindow() {
		return subWindow;
	}
	/**
	 * Get Segments.
	 * @return Segments list.
	 */
	public ArrayList<Segment> getSegments() {
		return segments;
	}
	/**
	 * Filter scene using priority search tree.
	 * @param window Only segments visible on this window will be kept. Window width/height must be {@code >= 10}.
	 * @return Filtered scene or null if window is not valid.
	 */
	public Scene filter(Segment window) {
		if(window == null) return null;
		
		setSubWindow(window);
		segments = pst.getWindow(window);
		return this;
	}
}

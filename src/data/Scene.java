package data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Scene {
	private Segment window;
	private ArrayList<Segment> segments;
	/**
	 * Get Segments from file.
	 * @param filename File from which to read Segments.
	 * @return Segments or null on error.
	 */
	public static Scene getScene(String filename) {
		try {
			return new Scene(filename);
		} catch (IOException e) {
			return null;
		}
	}
	/**
	 * Build Segments from file.
	 * @param filename File from which to read Segments.
	 * @throws IOException In case an error occurs reading file.
	 */
	public Scene(String filename) throws IOException {
		if(filename == null) throw new NullPointerException();
		
		Scanner lecteur = new Scanner(new File(filename));
		lecteur.useLocale(Locale.US);//used to detected the representation of the float
		
		// first line contains x x' y y', [x, x']x[y, y'] being window limits
		window = new Segment(
			(int) lecteur.nextFloat(),
			(int) lecteur.nextFloat(),
			(int) lecteur.nextFloat(),
			(int) lecteur.nextFloat()
		);
		
		// following line contains x y x' y', each one define a segment (why different order ?)
		segments = new ArrayList<Segment>();
		
		while (lecteur.hasNextFloat()) {
			int x1 = (int) lecteur.nextFloat();
			int y1 = (int) lecteur.nextFloat();
			int x2 = (int) lecteur.nextFloat();
			int y2 = (int) lecteur.nextFloat();
			segments.add(new Segment(x1, x2, y1, y2));
		}
		
		lecteur.close();
	}
	
	public Segment getWindow() {
		return window;
	}
	
	public ArrayList<Segment> getSegments() {
		return segments;
	}
	/**
	 * Filter scene using priority search tree.
	 * @param window Only segments visible on this window will be kept.
	 * @return Filtered scene.
	 */
	public Scene filter(Segment window) {
		
		// TODO implement, for now only display window is changed
		this.window = window;
		
		return this;
	}
}

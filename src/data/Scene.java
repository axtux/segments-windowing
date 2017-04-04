package data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Scene {
	private Seg window;
	private ArrayList<Seg> segments;
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
		lecteur.useLocale(Locale.US);
		
		// first line contains x x' y y', [x, x']x[y, y'] being window limits
		window = new Seg(
			(int) lecteur.nextFloat(),
			(int) lecteur.nextFloat(),
			(int) lecteur.nextFloat(),
			(int) lecteur.nextFloat()
		);
		
		// following line contains x y x' y', each one define a segment (why different order ?)
		segments = new ArrayList<Seg>();
		
		while (lecteur.hasNextFloat()) {
			int x1 = (int) lecteur.nextFloat();
			int y1 = (int) lecteur.nextFloat();
			int x2 = (int) lecteur.nextFloat();
			int y2 = (int) lecteur.nextFloat();
			segments.add(new Seg(x1, x2, y1, y2));
		}
		
		lecteur.close();
		
		// TODO sort segments ?
	}
	
	public Seg getWindow() {
		return window;
	}
	
	public ArrayList<Seg> getSegments() {
		return segments;
	}
}

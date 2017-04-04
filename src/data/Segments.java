package data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Segments {
	private Tuple window;
	private ArrayList<Tuple> segments;
	
	public Segments(String filename) throws IOException {
		if(filename == null) throw new NullPointerException();
		
		Scanner lecteur = new Scanner(new File(filename));
		lecteur.useLocale(Locale.US);
		
		// first line contains x x' y y', [x, x']x[y, y'] being window limits
		window = new Tuple(
			(int) lecteur.nextFloat(),
			(int) lecteur.nextFloat(),
			(int) lecteur.nextFloat(),
			(int) lecteur.nextFloat()
		);
		
		// following line contains x y x' y', each one define a segment (why different order ?)
		segments = new ArrayList<Tuple>();
		
		while (lecteur.hasNextFloat()) {
			int x1 = (int) lecteur.nextFloat();
			int y1 = (int) lecteur.nextFloat();
			int x2 = (int) lecteur.nextFloat();
			int y2 = (int) lecteur.nextFloat();
			segments.add(new Tuple(x1, x2, y1, y2));
		}
		
		lecteur.close();
		
		// TODO sort segments ?
	}
	
	public Tuple getWindow() {
		return window;
	}
	
	public ArrayList<Tuple> getSegments() {
		return segments;
	}
}

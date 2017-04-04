package data;

import java.io.IOException;

import graphics.SegmentsWindow;

public class Main {
	public static void main(String[] args) {
		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		
		Segments segments;
		try {
			segments = new Segments("scenes/1000.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		new SegmentsWindow(segments);
	}
}

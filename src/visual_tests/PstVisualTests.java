package visual_tests;

import data.Array;
import data.Scene;
import data.Segment;
import graphics.SceneFrame;

public class PstVisualTests {
	private static Array<Segment> windows;
	
	public static void main(String[] args) {
		visualTest("scenes/test_horizontal.txt");
		visualTest("scenes/test_vertical.txt");
	}
	
	private static void initWindows() {
		if(windows != null) {
			return;
		}
		
		windows = new Array<Segment>();
		
		// all
		windows.add(new Segment(-50, 50, -50, 50));
		// closed
		windows.add(new Segment(-50, 50, -50, 50));
		
		// left
		windows.add(new Segment(Integer.MIN_VALUE, 50, -50, 50));
		// up
		windows.add(new Segment(-50, 50, -50, Integer.MAX_VALUE));
		// right
		windows.add(new Segment(-50, Integer.MAX_VALUE, -50, 50));
		// down
		windows.add(new Segment(-50, 50, Integer.MIN_VALUE, 50));
		
		// up-left
		windows.add(new Segment(Integer.MIN_VALUE, 50, -50, Integer.MAX_VALUE));
		// up-right
		windows.add(new Segment(-50, Integer.MAX_VALUE, -50, Integer.MAX_VALUE));
		// down-right
		windows.add(new Segment(-50, Integer.MAX_VALUE, Integer.MIN_VALUE, 50));
		// down-left
		windows.add(new Segment(Integer.MIN_VALUE, 50, Integer.MIN_VALUE, 50));
		
		// not-left
		windows.add(new Segment(-50, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE));
		// not-up
		windows.add(new Segment(Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, 50));
		// not-right
		windows.add(new Segment(Integer.MIN_VALUE, 50, Integer.MIN_VALUE, Integer.MAX_VALUE));
		// not-down
		windows.add(new Segment(Integer.MIN_VALUE, Integer.MAX_VALUE, -50, Integer.MAX_VALUE));
		
		// all
		windows.add(new Segment(Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE));
	}
	
	public static void visualTest(String sceneFile) {
		Scene scene = Scene.getScene(sceneFile);
		if(scene == null) {
			System.out.println("unable to get scene from file "+sceneFile);
		}
		
		initWindows();
		
		new SceneFrame(scene);
		for(Segment w : windows) {
			new SceneFrame(scene.filter(w));
		}
	}
	
}

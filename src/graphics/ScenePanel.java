package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import data.Point;
import data.Scene;
import data.Segment;

/**
 * Panel drawing segments
 */
public class ScenePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Point origin;
	private int width, height;
	private ArrayList<Segment> relative_segments;
	private double scale;
	private ArrayList<Segment> scaled_segments;
	/**
	 * Build panel from scene.
	 * @param scene Scene with window width/height >= 10
	 */
	public ScenePanel(Scene scene) {
		super();
		if(scene == null) throw new NullPointerException();
		
		setWindow(scene.getWindow());
		setSegments(scene.getSegments());
		setBackground(Color.WHITE);
		if(!setScale(1)) {
			throw new RuntimeException("Window width/height must be >= 10");
		}
	}
	/**
	 * Set window sizes and origin Point
	 * @param window The window limits.
	 */
	private void setWindow(Segment window) {
		if(window == null) {
			throw new NullPointerException();
		}
		
		width = window.getX2()-window.getX1();
		height = window.getY2()-window.getY1();
		origin = new Point(-window.getX1(), -window.getY1());
	}
	/**
	 * Make segments relative to graphics axes and save them
	 * @param segments Segments relative to the origin, x-axis counting from left to right, y-axis counting from bottom to top.
	 */
	private void setSegments(ArrayList<Segment> segments) {
		if(segments == null) {
			throw new NullPointerException();
		}
		
		relative_segments = new ArrayList<Segment>(segments.size());
		// compute segments relative to graphics axis
		for(Segment s : segments) {
			relative_segments.add(relativise(s));
		}
	}
	/**
	 * Get scaling factor.
	 * @return Scaling factor.
	 */
	public double getScale() {
		return scale;
	}
	/**
	 * Set scaling factor.
	 * @param scale Scaling factor should be > 0. 1 is normal, less is zoomed out and more is zoomed in.
	 * @return True on success, false on error. An error occurs if new scaled width or height is < 10 or > {@link Integer.MAX_VALUE}.
	 */
	public boolean setScale(double scale) {
		// add one pixel because axes were not counted
		double scaledWidth = width*scale +1;
		double scaledHeight = height*scale +1;
		
		if(scaledWidth < 10 || scaledHeight < 10) {
			System.out.println("Minimum scaled width/height is 10");
			return false;
		}
		
		if(scaledWidth > Integer.MAX_VALUE || scaledHeight > Integer.MAX_VALUE) {
			// integer overflow
			System.out.println("Maximum scaled width/height is "+Integer.MAX_VALUE);
			return false;
		}
		
		this.scale = scale;
		setPreferredSize(new Dimension((int) scaledWidth, (int) scaledHeight));
		
		// update scaled segments
		scaled_segments = new ArrayList<Segment>(relative_segments.size());
		int x1, y1, x2, y2;
		
		for(Segment s : relative_segments) {
			x1 = (int) (s.getX1()*scale);
			y1 = (int) (s.getY1()*scale);
			x2 = (int) (s.getX2()*scale);
			y2 = (int) (s.getY2()*scale);
			
			scaled_segments.add(new Segment(x1, x2, y1, y2));
		}
		return true;
	}
	/**
	 * Make a segment relative to graphics axes
	 * @param s Segment to make relative to graphics axes
	 * @return Segment relative to graphics axes
	 */
	private Segment relativise(Segment s) {
		// move X to origin
		int x1 = s.getX1()+origin.getX();
		int x2 = s.getX2()+origin.getX();
		// move Y to origin
		int y1 = s.getY1()+origin.getY();
		int y2 = s.getY2()+origin.getY();
		// Graphics Y is counting UPside down, so we reverse Y
		y1 = height-y1;
		y2 = height-y2;
		
		return new Segment(x1, x2, y1, y2);
	}
	/**
	 * Draw segments
	 */
	public void paint(Graphics g) {
		super.paint(g);
		
		Color oldColor = g.getColor();
		g.setColor(Color.BLACK);
		
		for(Segment s : scaled_segments) {
			g.drawLine(s.getX1(), s.getY1(), s.getX2(), s.getY2());
		}
		
		g.setColor(oldColor);
	}
}

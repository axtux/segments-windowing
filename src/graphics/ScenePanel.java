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
	
	public ScenePanel(Scene scene) {
		if(scene == null) throw new NullPointerException();
		
		setWindow(scene.getWindow());
		setSegments(scene.getSegments());
		setBackground(Color.WHITE);
		setScale(1);
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
		setSize(new Dimension(width, height));
		setPreferredSize(new Dimension(width, height));
		origin = new Point(-window.getX1(), -window.getY1());
	}
	/**
	 * Relativise segments and save them
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
	 */
	public void setScale(double scale) {
		if(scale < 0.001) {
			return;
		}
		
		this.scale = scale;
		int scaledWidth = (int) (width*scale);
		int scaledHeight = (int) (height*scale);
		setPreferredSize(new Dimension(scaledWidth, scaledHeight));
		//setSize(new Dimension(scaledWidth, scaledHeight));
		
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
	}
	/**
	 * Relativise a segment to graphics axis
	 * @param s Segment to relativise
	 * @return Relativised tuple
	 */
	private Segment relativise(Segment s) {
		// move X to origin
		int x1 = s.getX1()+origin.getX();
		int x2 = s.getX2()+origin.getX();
		// move Y to origin
		int y1 = s.getY1()+origin.getY();
		int y2 = s.getY2()+origin.getY();
		// Graphics Y is counting UPside down, so we reverse Y
		y1 = getHeight()-y1;
		y2 = getHeight()-y2;
		
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
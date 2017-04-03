import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class JCanvas extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Point origin;
	private ArrayList<Tuple> relative_segments;

	public JCanvas(Tuple window, ArrayList<Tuple> segments) {
		setWindow(window);
		setSegments(segments);
		setBackground(Color.WHITE);
	}
	/**
	 * Set window sizes and origin Point
	 * @param window The window limits.
	 */
	private void setWindow(Tuple window) {
		if(window == null) {
			throw new NullPointerException();
		}
		
		int width = window.getX2()-window.getX1();
		int height = window.getY2()-window.getY1();
		this.setSize(new Dimension(width, height));
		this.setPreferredSize(new Dimension(width, height));
		this.origin = new Point(-window.getX1(), -window.getY1());
		
		System.out.println("window size is "+Integer.toString(width)+"x"+Integer.toString(height));
	}
	/**
	 * Relativise segments and save them
	 * @param segments Segments relative to the origin, x-axis counting from left to right, y-axis counting from bottom to top.
	 */
	private void setSegments(ArrayList<Tuple> segments) {
		if(segments == null) {
			throw new NullPointerException();
		}
		
		relative_segments = new ArrayList<Tuple>(segments.size());
		// compute segments relative to graphics axis
		for(Tuple s : segments) {
			relative_segments.add(relativise(s));
		}
	}
	/**
	 * Relativise a segment to graphics axis
	 * @param s Tuple to relativise
	 * @return Relativised tuple
	 */
	private Tuple relativise(Tuple s) {
		// move X to origin
		int x1 = s.getX1()+origin.getX();
		int x2 = s.getX2()+origin.getX();
		// move Y to origin
		int y1 = s.getY1()+origin.getY();
		int y2 = s.getY2()+origin.getY();
		// Graphics Y is counting UPside down, so we reverse Y
		y1 = getHeight()-y1;
		y2 = getHeight()-y2;
		
		return new Tuple(x1, x2, y1, y2);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Color oldColor = g.getColor();
		g.setColor(Color.BLACK);
		
		for(Tuple s : relative_segments) {
			g.drawLine(s.getX1(), s.getY1(), s.getX2(), s.getY2());
		}
		
		g.setColor(oldColor);
	}
}

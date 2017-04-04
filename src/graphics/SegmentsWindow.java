package graphics;
import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import data.Point;
import data.Tuple;
/**
 * Window to display segments using {@link SegmentsPanel} and enable mouse scrolling.
 */
public class SegmentsWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	private SegmentsWindow self;
	private SegmentsPanel panel;
	
	public SegmentsWindow(Tuple window, ArrayList<Tuple> segments) {
		super("Segments Window");
		self = this;
		panel = new SegmentsPanel(window, segments);
		add(new JScrollPane(panel));
		// center window
		setLocationRelativeTo(null);
		setVisible(true);
		
		activateMouseScroll();
		updateSize();
	}
	
	public void updateSize() {
		// minimize when needed
		setExtendedState(NORMAL);
		// update size
		pack();
	}
	
	private void activateMouseScroll() {
		MouseScroller ms = new MouseScroller();
		panel.addMouseListener(ms);
		panel.addMouseMotionListener(ms);
		panel.addMouseWheelListener(ms);
		ms.mouseReleased(null);
	}
	/**
	 * Mouse listener that enable mouse scrolling.
	 */
	private class MouseScroller extends MouseAdapter {
		private Point origin;
		
		public void mousePressed(MouseEvent e) {
			origin = new Point(e.getX(), e.getY());
			panel.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		}
		
		public void mouseReleased(MouseEvent e) {
			panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
		
		public void mouseDragged(MouseEvent e) {
			if (origin == null) {
				return;
			}
			
			Rectangle view = panel.getVisibleRect();
			view.x += origin.getX() - e.getX();
			view.y += origin.getY() - e.getY();
			
			panel.scrollRectToVisible(view);
		}
		
		public void mouseWheelMoved(MouseWheelEvent e) {
			// prevent default behaviour
			e.consume();
			// adjut scale per 0.1, zoom in on wheel up zoom out on wheel down
			panel.setScale(panel.getScale()-0.1*e.getWheelRotation());
			// adjust frame size and repaint panel
			self.updateSize();
		}
	}
}

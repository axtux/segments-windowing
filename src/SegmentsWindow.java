import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
/**
 * Window to display segments using {@link SegmentsPanel} and enable mouse scrolling.
 */
public class SegmentsWindow {
	private SegmentsPanel panel;
	
	public SegmentsWindow(Tuple window, ArrayList<Tuple> segments) {
		panel = new SegmentsPanel(window, segments);
		JScrollPane scroll = new JScrollPane(panel);
		JFrame frame = new JFrame("Segments Window");
		frame.add(scroll);
		frame.pack();
		// center window
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		activateMouseScroll();
	}
	
	private void activateMouseScroll() {
		MouseScroller ms = new MouseScroller();
		panel.addMouseListener(ms);
		panel.addMouseMotionListener(ms);
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
	};
}

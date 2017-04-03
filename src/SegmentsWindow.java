import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class SegmentsWindow {
	SegmentsPanel panel;
	public SegmentsWindow(Tuple window, ArrayList<Tuple> segments) {
		panel = new SegmentsPanel(window, segments);
		JScrollPane scroll = new JScrollPane(panel);
		JFrame frame = new JFrame("Segments Window");
		frame.getContentPane().add(scroll);
		frame.pack();
		// center window
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		activateScroll();
	}
	
	private void activateScroll() {
		MouseScroller ms = new MouseScroller();
		panel.addMouseListener(ms);
		panel.addMouseMotionListener(ms);
		ms.mouseReleased(null);
	}
	
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
			view.x += origin.x - e.getX();
			view.y += origin.y - e.getY();
			
			panel.scrollRectToVisible(view);
		}
	};
}

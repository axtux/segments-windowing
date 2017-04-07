package graphics;
import java.awt.Cursor;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import data.Point;
import data.Scene;
/**
 * Window to display segments using {@link ScenePanel} and enable mouse scrolling.
 */
public class SceneFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private SceneFrame self;
	private ScenePanel panel;
	
	public SceneFrame(Scene scene) {
		super("Segments Window");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		self = this;
		panel = new ScenePanel(scene);
		
		// container to center panel
		JPanel container = new JPanel(new GridBagLayout());
		container.add(panel);
		
		// scroll panel without wheel
		JScrollPane scroll = new JScrollPane(container);
		scroll.setWheelScrollingEnabled(false);
		add(scroll);
		
		activateMouseScroll();
		
		// center window
		setExtendedState(MAXIMIZED_BOTH);
		// size when user minimizes window
		setSize(500, 500);
		update();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	/**
	 * Update scroll bars and repaint
	 */
	public void update() {
		panel.revalidate();
		panel.repaint();
	}
	
	private void activateMouseScroll() {
		MouseScroller ms = new MouseScroller();
		
		// mouse click and drag only on panel
		panel.addMouseListener(ms);
		panel.addMouseMotionListener(ms);
		
		// wheel on all window
		addMouseWheelListener(ms);
		
		ms.updateCursor();
	}
	
	private static boolean isScrollable(JPanel panel) {
		/* Not working when view is on top
		Rectangle view = panel.getVisibleRect();
		
		if(
			Double.compare(view.getX(), view.getMinX()) == 0
			&& Double.compare(view.getY(), view.getMinY()) == 0
			&& Double.compare(view.getWidth(), view.getMaxX()) == 0
			&& Double.compare(view.getHeight(), view.getMaxY()) == 0
		) {
			return false;
		}
		//*/
		return true;
	}
	/**
	 * Mouse listener that enable mouse scrolling.
	 */
	private class MouseScroller extends MouseAdapter {
		private Point origin;
		
		public void mousePressed(MouseEvent e) {
			origin = new Point(e.getX(), e.getY());
			
			if(isScrollable(panel)) {
				panel.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
				return;
			}
			
			panel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		
		public void mouseReleased(MouseEvent e) {
			if(isScrollable(panel)) {
				panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				return;
			}
			
			panel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
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
			// prevent default behavior
			e.consume();
			
			// backup old view
			Rectangle oldView = panel.getVisibleRect();
			
			// ratio 0.9 or 1.1 depending on rotation, zoom in on wheel up zoom out on wheel down
			double ratio = 1-0.1*e.getWheelRotation();
			panel.setScale(panel.getScale()*ratio);
			
			// Update scroll bars and repaint
			self.update();
			
			Rectangle view = panel.getVisibleRect();
			/* set view back to centered point
			view.x = (int) (oldView.getCenterX()*ratio-view.getWidth()/2);
			view.y = (int) (oldView.getCenterY()*ratio-view.getHeight()/2);
			//*/
			//* set view back to mouse point
			view.x = (int) ((oldView.getX()+e.getX())*ratio-e.getX());
			view.y = (int) ((oldView.getY()+e.getY())*ratio-e.getY());
			//*/
			panel.scrollRectToVisible(view);
			
			updateCursor();
		}
		
		public void updateCursor() {
			mouseReleased(null);
		}
	}
}

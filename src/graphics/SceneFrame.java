package graphics;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import data.Point;
import data.Scene;
/**
 * JFrame to display segments using {@link ScenePanel}, enable mouse scroll and wheel zoom.
 */
public class SceneFrame extends JFrame implements WindowStateListener {
	private static final long serialVersionUID = 1L;
	private SceneFrame self;
	private ScenePanel panel;
	/**
	 * Create frame from scene.
	 * @param scene Scene to display.
	 */
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
		
		addMouseListeners();
		
		// center window
		setExtendedState(MAXIMIZED_BOTH);
		// size when user minimizes window
		addWindowStateListener(this);
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
	
	private void addMouseListeners() {
		MouseScrollWheelZoom ms = new MouseScrollWheelZoom();
		
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
	 * Manages mouse events. Moving pressed mouse will scroll panel and wheel movements will affect zoom level.
	 */
	private class MouseScrollWheelZoom extends MouseAdapter {
		private Point origin;
		/**
		 * Save origin and set pressed cursor.
		 */
		public void mousePressed(MouseEvent e) {
			origin = new Point(e.getX(), e.getY());
			
			if(isScrollable(panel)) {
				panel.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
				return;
			}
			
			panel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		/**
		 * Set released cusor.
		 */
		public void mouseReleased(MouseEvent e) {
			if(isScrollable(panel)) {
				panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				return;
			}
			
			panel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		/**
		 * Scroll panel view with mouse movements.
		 */
		public void mouseDragged(MouseEvent e) {
			if (origin == null) {
				return;
			}
			
			Rectangle view = panel.getVisibleRect();
			view.x += origin.getX() - e.getX();
			view.y += origin.getY() - e.getY();
			
			panel.scrollRectToVisible(view);
		}
		/**
		 * Change zoom level with wheel movements.
		 */
		public void mouseWheelMoved(MouseWheelEvent e) {
			// prevent default behavior
			e.consume();
			
			// backup old view
			Rectangle oldView = panel.getVisibleRect();
			
			// ratio 0.9 or 1.1 depending on rotation, zoom in on wheel up zoom out on wheel down
			double ratio = 1-0.1*e.getWheelRotation();
			if(! panel.setScale(panel.getScale()*ratio) ) {
				// minimum or maximum scale has been reached
				return;
			}
			
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
		/**
		 * Set released cursor.
		 */
		public void updateCursor() {
			mouseReleased(null);
		}
	}
	/**
	 * Set good size on frame minimized
	 */
	public void windowStateChanged(WindowEvent e) {
		// from maximized to minimized
		if(e.getOldState() == MAXIMIZED_BOTH && e.getNewState() == NORMAL) {
			Dimension innerSize = getContentPane().getPreferredSize();
			
			Dimension maxSize = Toolkit.getDefaultToolkit().getScreenSize();
			// set maximum size to 80% of screen size
			maxSize.width *= 0.8;
			maxSize.height *= 0.8;
			
			int width = Math.min((int) innerSize.getWidth(), (int) maxSize.getWidth());
			int height = Math.min((int) innerSize.getHeight(), (int) maxSize.getHeight());
			
			this.setSize(width, height);
		}
	}
}

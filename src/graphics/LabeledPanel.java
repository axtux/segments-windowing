package graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
/**
 * JPanel with a label within border just as HTML field set element.
 */
public class LabeledPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Border labeledBorder;
	/**
	 * Create panel with a 10-pixels margin.
	 * @param label Label of this panel.
	 */
	public LabeledPanel(String label) {
		this(label, 10, true);
	}
	/**
	 * Create panel with a 10-pixels margin (bottom margin depends on parameter).
	 * @param label Label of this panel.
	 * @param bottom_margin If false, bottom margin will be set to 0. If true, no change will be done.
	 */
	public LabeledPanel(String label, boolean bottom_margin) {
		this(label, 10, bottom_margin);
	}
	/**
	 * Create panel with a custom margin size.
	 * @param label Label of this panel.
	 * @param margin_size Size of the margin in pixels.
	 */
	public LabeledPanel(String label, int margin_size) {
		this(label, margin_size, true);
	}
	/**
	 * Create panel with a custom margin size.
	 * @param label Label of this panel.
	 * @param margin_size Size of the margin in pixels.
	 * @param bottom_margin If false, bottom margin will be set to 0. If true, no change will be done.
	 */
	public LabeledPanel(String label, int margin_size, boolean bottom_margin) {
		super();
		labeledBorder = BorderFactory.createTitledBorder(label);
		setMargin(margin_size, bottom_margin);
	}
	/**
	 * Set custom margin.
	 * @param size Size of the margin in pixels.
	 * @param bottom If false, bottom margin will be set to 0. If true, no change will be done.
	 */
	public void setMargin(int size, boolean bottom) {
		Border margin = BorderFactory.createEmptyBorder(size, size, bottom ? size : 0, size);
		Border comp = BorderFactory.createCompoundBorder(margin, labeledBorder);
		setBorder(comp);
	}
}

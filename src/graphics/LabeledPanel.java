package graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class LabeledPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Border labeledBorder;
	
	public LabeledPanel(String label) {
		this(label, 10, true);
	}
	
	public LabeledPanel(String label, boolean bottom_margin) {
		this(label, 10, bottom_margin);
	}
	
	public LabeledPanel(String label, int margin_size) {
		this(label, margin_size, true);
	}
	
	public LabeledPanel(String label, int margin_size, boolean bottom_margin) {
		super();
		labeledBorder = BorderFactory.createTitledBorder(label);
		setMargin(margin_size, bottom_margin);
	}
	
	public void setMargin(int size, boolean bottom) {
		Border margin = BorderFactory.createEmptyBorder(size, size, bottom ? size : 0, size);
		Border comp = BorderFactory.createCompoundBorder(margin, labeledBorder);
		setBorder(comp);
	}
}

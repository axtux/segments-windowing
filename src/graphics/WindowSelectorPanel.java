package graphics;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import data.Segment;
/**
 * JPanel to select a window.
 */
public class WindowSelectorPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JIntegerField[] fields;
	private StatusListener statusListener;
	
	public WindowSelectorPanel(StatusListener statusListener) {
		super();
		this.statusListener = statusListener == null ? new StatusListener(){} : statusListener;
		
		createFields();
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		addAll();
	}
	
	private void createFields() {
		fields = new JIntegerField[4];
		for(int i = 0; i < fields.length; ++i) {
			fields[i] = new JIntegerField(5);
		}
	}
	
	private void addAll() {
		JPanel line = new JPanel();
		line.add(new JLabel("[ X, X' ] x [ Y, Y' ]"));
		add(line);
		
		line = new JPanel();
		line.add(new JLabel("="));
		add(line);
		
		line = new JPanel();
		line.add(new JLabel("["));
		line.add(fields[0]);
		line.add(new JLabel(","));
		line.add(fields[1]);
		line.add(new JLabel("] x ["));
		line.add(fields[2]);
		line.add(new JLabel(","));
		line.add(fields[3]);
		line.add(new JLabel("]"));
		add(line);
		
		line = new JPanel();
		line.add(new JLabel("Enter \"min\" to get -∞ or \"max\" to get +∞."));
		add(line);
	}
	/**
	 * Get selected window.
	 * @return Segment object representing selected window or null if one of the field is empty.
	 */
	public Segment getSelectedWindow() {
		for(JIntegerField field : fields) {
			if(field.getValue() == null) {
				setStatus(false, "One field has empty value.");
				return null;
			}
		}
		
		int x1 = fields[0].getValue().intValue();
		int x2 = fields[1].getValue().intValue();
		int y1 = fields[2].getValue().intValue();
		int y2 = fields[3].getValue().intValue();
		
		return new Segment(x1, x2, y1, y2);
	}
	
	private boolean setStatus(boolean noError, String status) {
		return statusListener.updateStatus(noError, status);
	}
	
}

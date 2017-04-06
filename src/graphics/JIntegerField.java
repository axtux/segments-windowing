package graphics;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;
/**
 * Field for user to enter an integer. Empty field has null value, "min" field has Integer.MIN_VALUE value and "max" field has Integer.MAX_VALUE value.
 */
public class JIntegerField extends JTextField implements KeyListener {
	private static final long serialVersionUID = 1L;
	private Integer value;
	/**
	 * Create field with columns size.
	 * @param columns Size used for JTextField.
	 */
	public JIntegerField(int columns) {
		super(columns);
		addKeyListener(this);
	}
	/**
	 * 
	 * Create field with columns size and defaultValue.
	 * @param columns Size used for JTextField.
	 * @param defaultValue Default value.
	 */
	public JIntegerField(int columns, int defaultValue) {
		this(columns);
		setValue(new Integer(defaultValue));
	}
	/**
	 * Integer value of this field.
	 * @return Integer value or null if field is empty.
	 */
	public Integer getValue() {
		return value;
	}
	/**
	 * Set integer value. Integer.MIN_VALUE will display "min", Integer.MAX_VALUE will display "max" and null will display "" (empty field).
	 * @param newValue
	 */
	public void setValue(Integer newValue) {
		value = newValue;
		
		if(value == null) {
			setText("");
		} else if(value.equals(Integer.MIN_VALUE)) {
			setText("min");
		} else if(value.equals(Integer.MAX_VALUE)) {
			setText("max");
		} else {
			setText(value.toString());
		}
		
		System.out.println("Value is now "+(value == null ? "null" : value.toString()));
	}
	
	/**
	 * Unused KeyListener methods
	 */
	public void keyTyped(KeyEvent e) {}
	/**
	 * Unused KeyListener methods
	 */
	public void keyPressed(KeyEvent e) {}
	/**
	 * Update value when key is released
	 */
	public void keyReleased(KeyEvent e) {
		updateValue();
	}
	/**
	 * Update value. Empty field will set value to null, "min" will set value to Integer.MIN_VALUE and "max" will set value to Integer.MAX_VALUE.
	 */
	private void updateValue() {
		String text = getText();
		
		if(text == null || text.isEmpty()) {
			setValue(null);
			return;
		}
		
		text = text.toLowerCase();
		
		if(text.equals("min")) {
			setValue(Integer.MIN_VALUE);
			return;
		}
		if(text.equals("max")) {
			setValue(Integer.MAX_VALUE);
			return;
		}
		// typing "min" or "max"
		if("min".startsWith(text) || "max".startsWith(text)) {
			value = null;
			return;
		}
		
		try {
			setValue(Integer.valueOf(text));
		} catch (NumberFormatException e) {
			// don't change value
			setValue(value);
		}
	}
	
	
}

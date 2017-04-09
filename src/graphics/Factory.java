package graphics;

import java.awt.Container;
import java.awt.event.ActionListener;

import javax.swing.JButton;
/**
 * Easy interface to create UI elements.
 */
public class Factory {
	/**
	 * Create a JButton.
	 * @param text Text to be displayed on button.
	 * @param action Action to set on the button.
	 * @param actionListener Action listener to add to the button.
	 * @param container Container into which button will be added.
	 * @return Newly created button.
	 */
	public static JButton createButton(String text, String action, ActionListener actionListener, Container container) {
		JButton button = createButton(text, action, actionListener);
		container.add(button);
		return button;
	}
	/**
	 * Create a JButton.
	 * @param text Text to be displayed on button.
	 * @param action Action to set on the button.
	 * @param actionListener Action listener to add to the button.
	 * @return Newly created button.
	 */
	public static JButton createButton(String text, String action, ActionListener actionListener) {
		JButton button = new JButton(text);
		button.setActionCommand(action);
		button.addActionListener(actionListener);
		return button;
	}
}

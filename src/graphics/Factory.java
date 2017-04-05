package graphics;

import java.awt.Container;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Factory {
	public static JButton createButton(String text, String action, ActionListener actionListener, Container container) {
		JButton button = createButton(text, action, actionListener);
		container.add(button);
		return button;
	}
	
	public static JButton createButton(String text, String action, ActionListener actionListener) {
		JButton button = new JButton(text);
		button.setActionCommand(action);
		button.addActionListener(actionListener);
		return button;
	}
}

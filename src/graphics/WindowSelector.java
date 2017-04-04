package graphics;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import data.File;
import data.Segments;

public class WindowSelector extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private String scenesDir;
	
	private JPanel content;
	private JComboBox<String> files;
	private JLabel error;
	
	public static void main(String[] args) {
		new WindowSelector("scenes");
	}
	
	public WindowSelector(String scenesDir) {
		super("Window Selector");
		setScenesDir(scenesDir);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		
		content = new JPanel();
		getContentPane().add(content, BorderLayout.CENTER);
		
		addFileSelector();
		addWindowSelector();
		addButton();
		addError();
		// refresh when error element exists
		refreshFileSelector();
		// center window
		resize();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void resize() {
		pack();
	}
	
	private void setScenesDir(String scenesDir) {
		if(scenesDir == null) throw new NullPointerException();
		this.scenesDir = scenesDir;
	}
	
	private void addFileSelector() {
		JPanel container = new JPanel();
		files = new JComboBox<String>(new String[0]);
		container.add(files);
		content.add(container);
	}
	
	private void refreshFileSelector() {
		ArrayList<String> filenames = File.list(scenesDir, ".txt");
		if(filenames == null) {
			error("Enable to list files into directory "+scenesDir);
			return;
		}
		
		if(filenames.size() == 0) {
			error("No valid files found into directory "+scenesDir);
			return;
		}
		
		files = new JComboBox<String>(filenames.toArray(new String[0]));
		
		
	}
	
	private void addWindowSelector() {
		JPanel container = new JPanel();
		
		content.add(container);
	}
	
	private void addButton() {
		JPanel container = new JPanel();
		
		JButton button = new JButton("Display all segments");
		button.setActionCommand("ACTION_DISPLAY_ALL");
		button.addActionListener(this);
		container.add(button);
		
		content.add(container);
	}
	
	private void addError() {
		JPanel container = new JPanel();
		
		error = new JLabel("Select a file");
		container.add(error);
		
		content.add(container);
	}
	
	public void actionPerformed(ActionEvent e) {
		System.out.println("Action performed : "+e.getActionCommand());
		switch(e.getActionCommand()) {
		case "ACTION_DISPLAY_ALL":
			displayAll();
			return;
		default:
			System.out.println("Action not implemented : "+e.getActionCommand());
		}
	}
	
	private void displayAll() {
		int i = files.getSelectedIndex();
		if(i == -1) {
			error("No file selected");
			return;
		}
		
		String file = files.getItemAt(i);
		Segments segments = Segments.getSegments(file);
		if(segments == null) {
			error("Unable to get segments from file "+file+". Please check readability and/or format.");
			return;
		}
		
		new SegmentsWindow(segments);
	}
	
	private void error(String error) {
		System.out.println("Error : "+error);
		this.error.setText(error);
		resize();
	}
	
}

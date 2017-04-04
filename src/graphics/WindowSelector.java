package graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import data.File;
import data.Scene;

public class WindowSelector extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private String scenesDir;
	
	private JPanel content;
	private JPanel filesContainer;
	private JComboBox<String> files;
	private JLabel error;
	
	public static void main(String[] args) {
		new WindowSelector("scenes");
	}
	
	public WindowSelector(String scenesDir) {
		super("File and Window Selector");
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.X_AXIS));
		add(content);
		
		addFileSelector();
		addWindowSelector();
		addButton();
		addError();
		// refresh when error element exists
		setScenesDir(scenesDir);
		// center window
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void setScenesDir(String scenesDir) {
		if(scenesDir == null) throw new NullPointerException();
		this.scenesDir = scenesDir;
		refreshFileSelector();
	}
	
	private void addFileSelector() {
		JPanel container = new JPanel();
		filesContainer = container;
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
		/*
		files.removeAll();
		for(String filename : filenames) {
			files.addItem(filename);
		}
		//*/
		filesContainer.removeAll();
		files = new JComboBox<String>(filenames.toArray(new String[0]));
		filesContainer.add(files);
		
		JButton button = new JButton("Change directory");
		button.setActionCommand("ACTION_CHANGE_DIRECTORY");
		button.addActionListener(this);
		filesContainer.add(button);
		
		pack();
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
		case "ACTION_CHANGE_DIRECTORY":
			changeDir();
			return;
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
		Scene scene = Scene.getScene(file);
		if(scene == null) {
			error("Unable to get scene from file "+file+". Please check readability and/or format.");
			return;
		}
		
		new SceneWindow(scene);
	}
	
	private void changeDir() {
		JFileChooser fc = new JFileChooser(scenesDir);
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		int r = fc.showOpenDialog(this);
		if (r == JFileChooser.APPROVE_OPTION) {
			setScenesDir(fc.getSelectedFile().getPath());
		}
	}
	
	private void error(String error) {
		System.out.println("Error : "+error);
		this.error.setText(error);
		pack();
	}
	
}

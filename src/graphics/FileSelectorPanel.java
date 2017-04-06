package graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import data.File;

public class FileSelectorPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel boxContainer;
	private JComboBox<String> box;
	private JButton button;
	private String directory;
	private String suffix;
	private StatusListener statusListener;
	
	public FileSelectorPanel(String suffix, StatusListener statusListener) {
		super();
		boxContainer = new JPanel();
		//box = new JComboBox<String>(new String[0]);
		//boxContainer.add(box);
		add(boxContainer);
		button = Factory.createButton("Select directory", "ACTION_CHANGE_DIRECTORY", this, this);
		this.suffix = suffix ==  null ? "" : suffix;
		this.statusListener = statusListener == null ? new StatusListener(){} : statusListener;
		// open application root directory by default
		changeDir(".");
	}
	
	public boolean changeDir(String newDir) {
		directory = newDir;
		boxContainer.removeAll();
		box = null;
		
		ArrayList<String> filenames = File.list(newDir, suffix);
		if(filenames == null) {
			return setStatus(false, "Enable to list files into directory "+newDir);
		}
		
		if(filenames.size() == 0) {
			return setStatus(false, "No valid files found into directory "+newDir);
		}
		
		button.setText("Change directory");
		box = new JComboBox<String>(filenames.toArray(new String[0]));
		boxContainer.add(box);
		return setStatus(true, "Changed directory to "+newDir);
	}
	
	public String getSelectedFile() {
		if(box == null) {
			setStatus(false, "No valid directory chosen");
			return null;
		}
		
		int i = box.getSelectedIndex();
		if(i == -1) {
			setStatus(false, "No file selected");
			return null;
		}
		
		return directory+"/"+box.getItemAt(i);
	}
	
	private void changeDir() {
		JFileChooser fc = new JFileChooser(directory);
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		int r = fc.showOpenDialog(this);
		if (r == JFileChooser.APPROVE_OPTION) {
			changeDir(fc.getSelectedFile().getPath());
		}
	}
	
	private boolean setStatus(boolean noError, String status) {
		return statusListener.updateStatus(noError, status);
	}
	
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "ACTION_CHANGE_DIRECTORY":
			changeDir();
			return;
		default:
			setStatus(false, "Action not implemented : "+e.getActionCommand());
		}
	}
}
package graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import data.File;
/**
 * Display a combo box to select a file within a directory and a button to change directory.
 */
public class FileSelectorPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JLabel dirLabel;
	private JPanel boxContainer;
	private JComboBox<String> box;
	private JButton button;
	private String directory;
	private String suffix;
	private StatusListener statusListener;
	/**
	 * Create a selector.
	 * @param suffix Only files with this suffix will be proposed to the user.
	 * @param statusListener Status listener.
	 */
	public FileSelectorPanel(String suffix, StatusListener statusListener) {
		super();
		this.suffix = suffix ==  null ? "" : suffix;
		this.statusListener = statusListener == null ? new StatusListener(){} : statusListener;
		// add UI components
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		addAll();
		// open application root directory by default
		changeDir(".");
	}
	
	private void addAll() {
		JPanel line = new JPanel();
		line.add(new JLabel("Directory :"));
		dirLabel = new JLabel();
		line.add(dirLabel);
		add(line);
		
		line = new JPanel();
		button = Factory.createButton("Select directory", "ACTION_CHANGE_DIRECTORY", this, line);
		add(line);
		
		boxContainer = new JPanel();
		add(boxContainer);
	}
	/**
	 * Change directory from which files are proposed.
	 * @param newDir New directory.
	 * @return True on success, false on error.
	 * An error occurs if directory is not readable or if no files with suffix are fount into it.
	 */
	public boolean changeDir(String newDir) {
		directory = newDir;
		dirLabel.setText(directory);
		boxContainer.removeAll();
		box = null;
		
		ArrayList<String> filenames = File.list(newDir, suffix);
		if(filenames == null) {
			boxContainer.add(new JLabel("Unable to list files into directory."));
			updateSize();
			return false;
		}
		
		if(filenames.size() == 0) {
			boxContainer.add(new JLabel("No valid file found into directory."));
			updateSize();
			return false;
		}
		
		button.setText("Change directory");
		
		boxContainer.add(new JLabel("Select file :"));
		box = new JComboBox<String>(filenames.toArray(new String[0]));
		boxContainer.add(box);
		return setStatus(true, "Changed directory to "+newDir);
	}
	/**
	 * Get selected file path. Path can be absolute or relative to application root directory.
	 * @return Selected file by user or null if no file has been selected.
	 */
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
	
	private void changeDirDialog() {
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
			changeDirDialog();
			return;
		default:
			setStatus(false, "Action not implemented : "+e.getActionCommand());
		}
	}
	
	private void updateSize() {
		setStatus(true, "updated");
	}
}

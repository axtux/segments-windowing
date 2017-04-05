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
	
	private JPanel filesContainer;
	private JComboBox<String> files;
	private JLabel status;
	
	public static void main(String[] args) {
		new WindowSelector("scenes");
	}
	
	public WindowSelector(String scenesDir) {
		super("Scene file/window selector");
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		
		addFileSelector(false);
		addWindowSelector(false);
		addButton(false);
		addStatus(true);
		// refresh when error element exists
		setScenesDir(scenesDir);
		// center window
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void setScenesDir(String scenesDir) {
		if(scenesDir == null) throw new NullPointerException();
		
		ArrayList<String> filenames = File.list(scenesDir, ".txt");
		if(filenames == null) {
			error("Enable to list files into directory "+scenesDir);
			return;
		}
		
		if(filenames.size() == 0) {
			error("No valid files found into directory "+scenesDir);
			return;
		}
		
		this.scenesDir = scenesDir;
		filesContainer.removeAll();
		files = new JComboBox<String>(filenames.toArray(new String[0]));
		filesContainer.add(files);
		
		JButton button = new JButton("Change directory");
		button.setActionCommand("ACTION_CHANGE_DIRECTORY");
		button.addActionListener(this);
		filesContainer.add(button);
		
		error("Changed directory to "+scenesDir);
		pack();
		
	}
	
	private void addFileSelector(boolean bottom_margin) {
		JPanel container = new LabeledPanel("Scene file selector", bottom_margin);
		filesContainer = container;
		files = new JComboBox<String>(new String[0]);
		container.add(files);
		add(container);
	}
	
	private void addWindowSelector(boolean bottom_margin) {
		JPanel container = new LabeledPanel("Scene window selector", bottom_margin);
		
		container.add(new JLabel("TODO"));
		
		add(container);
	}
	
	private void addButton(boolean bottom_margin) {
		JPanel container = new LabeledPanel("Open scene", bottom_margin);
		
		JButton button = new JButton("whole scene");
		button.setActionCommand("ACTION_OPEN_WHOLE_SCENE");
		button.addActionListener(this);
		container.add(button);
		
		button = new JButton("restricted window");
		button.setActionCommand("ACTION_OPEN_RESTRICTED_WINDOW");
		button.addActionListener(this);
		container.add(button);
		
		add(container);
	}
	
	private void addStatus(boolean bottom_margin) {
		JPanel container = new LabeledPanel("Status", bottom_margin);
		
		status = new JLabel("Select a file");
		container.add(status);
		
		add(container);
	}
	
	public void actionPerformed(ActionEvent e) {
		System.out.println("Action performed : "+e.getActionCommand());
		switch(e.getActionCommand()) {
		case "ACTION_CHANGE_DIRECTORY":
			changeDir();
			return;
		case "ACTION_OPEN_WHOLE_SCENE":
			openScene();
			return;
		default:
			System.out.println("Action not implemented : "+e.getActionCommand());
		}
	}
	
	private void openScene() {
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
		status.setText(error);
		pack();
	}
	
}

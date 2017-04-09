package graphics;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import data.Scene;
import data.Segment;
/**
 * Main frame to select a scene file and a window. User can open whole scene or custom window.
 */
public class SelectorFrame extends JFrame implements ActionListener, StatusListener {
	private static final long serialVersionUID = 1L;
	
	private FileSelectorPanel fileSelector;
	private WindowSelectorPanel windowSelector;
	private JLabel status;
	/**
	 * Create frame.
	 * @param scenesDir Default directory for scene files selection.
	 */
	public SelectorFrame(String scenesDir) {
		super("Scene file/window selector");
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		
		addFileSelector(false);
		addWindowSelector(false);
		addButtons(false);
		addStatus(true);
		// refresh when error element exists
		fileSelector.changeDir(scenesDir);
		// size and center
		resize();
		setVisible(true);
	}
	
	private void addFileSelector(boolean bottom_margin) {
		JPanel container = new LabeledPanel("Scene file selector", bottom_margin);
		fileSelector = new FileSelectorPanel(".txt", this);
		container.add(fileSelector);
		add(container);
	}
	
	private void addWindowSelector(boolean bottom_margin) {
		JPanel container = new LabeledPanel("Scene window selector", bottom_margin);
		
		windowSelector = new WindowSelectorPanel(this);
		container.add(windowSelector);
		
		add(container);
	}
	
	private void addButtons(boolean bottom_margin) {
		JPanel container = new LabeledPanel("Open scene", bottom_margin);
		
		Factory.createButton("Whole scene", "ACTION_OPEN_WHOLE_SCENE", this, container);
		Factory.createButton("Selected window", "ACTION_OPEN_WINDOW", this, container);
		
		add(container);
	}
	
	private void addStatus(boolean bottom_margin) {
		JPanel container = new LabeledPanel("Status", bottom_margin);
		
		status = new JLabel("Select a file");
		container.add(status);
		
		add(container);
	}
	
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "ACTION_OPEN_WHOLE_SCENE":
			openScene();
			return;
		case "ACTION_OPEN_WINDOW":
			openWindow();
			return;
		default:
			updateStatus(false, "Action not implemented : "+e.getActionCommand());
		}
	}
	
	private Scene getScene() {
		String file = fileSelector.getSelectedFile();
		if(file == null) {
			updateStatus(false, "No file selected");
			return null;
		}
		
		Scene scene = Scene.getScene(file);
		if(scene == null) {
			updateStatus(false, "Unable to get scene from file "+file+". Please check readability and/or format.");
			return null;
		}
		
		return scene;
	}
	
	private void openScene() {
		Scene scene = getScene();
		if(scene == null) {
			return;
		}
		
		new SceneFrame(scene);
		updateStatus(true, "Opened whole scene.");
	}
	
	private void openWindow() {
		Scene scene = getScene();
		if(scene == null) {
			return;
		}
		
		Segment window = windowSelector.getSelectedWindow();
		if(window == null || !Scene.validWindow(window)) {
			updateStatus(false, "Selected window is not valid");
			return;
		}
		
		new SceneFrame(scene.filter(window));
		updateStatus(true, "Opened filtered scene.");
	}
	
	public boolean updateStatus(boolean noError, String message) {
		System.out.println((noError ? "Status : " : "Error : ")+message);
		if(status != null) {
			status.setForeground(noError ? Color.BLACK : Color.RED);
			status.setText(message);
		}
		resize();
		return noError;
	}
	
	private void resize() {
		pack();
		setLocationRelativeTo(null);
	}
	
}

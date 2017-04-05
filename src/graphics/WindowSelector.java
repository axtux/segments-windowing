package graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import data.Scene;

public class WindowSelector extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private FileSelectorPanel fileSelector;
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
		fileSelector.changeDir(scenesDir);
		// center window
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void addFileSelector(boolean bottom_margin) {
		JPanel container = new LabeledPanel("Scene file selector", bottom_margin);
		fileSelector = new FileSelectorPanel(".txt");
		container.add(fileSelector);
		add(container);
	}
	
	private void addWindowSelector(boolean bottom_margin) {
		JPanel container = new LabeledPanel("Scene window selector", bottom_margin);
		
		container.add(new JLabel("TODO"));
		
		add(container);
	}
	
	private void addButton(boolean bottom_margin) {
		JPanel container = new LabeledPanel("Open scene", bottom_margin);
		
		Factory.createButton("whole scene", "ACTION_OPEN_WHOLE_SCENE", this, container);
		Factory.createButton("restricted window", "ACTION_OPEN_RESTRICTED_WINDOW", this, container);
		
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
		default:
			System.out.println("Action not implemented : "+e.getActionCommand());
		}
	}
	
	private boolean openScene() {
		String file = fileSelector.getSelectedFile();
		if(file == null) {
			return setStatus(false, "No file selected");
		}
		
		Scene scene = Scene.getScene(file);
		if(scene == null) {
			error("Unable to get scene from file "+file+". Please check readability and/or format.");
			return false;
		}
		
		new SceneWindow(scene);
		return true;
	}
	
	private void error(String error) {
		System.out.println("Error : "+error);
		status.setText(error);
		pack();
	}
	
	private boolean setStatus(boolean noError, String status) {
		status = (noError ? "Status : " : "Error : ")+status;
		//System.out.println(status);
		error(status);
		return noError;
	}
	
}

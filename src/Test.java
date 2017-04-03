import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Test {
	public static void main(String args[]) {
		ArrayList<Tuple> segments = new ArrayList<Tuple>();
		segments.add(new Tuple(0, 100, 0, 100));
		JPanel panel = new JCanvas(new Tuple(-500, 500, -500, 500), segments);
		JScrollPane scroll = new JScrollPane(panel);
		JFrame frame = new JFrame("SDD2 Windowing");
		frame.getContentPane().add(scroll);
		frame.pack();
		frame.setVisible(true);
	}
}

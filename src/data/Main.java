package data;

import java.io.IOException;

import graphics.SceneWindow;

public class Main {
	public static void main(String[] args) {
		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		
		Scene scene;
		try {
			scene = new Scene("scenes/1000.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		new SceneWindow(scene);
	}
}

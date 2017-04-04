package tests;

import java.util.ArrayList;

import org.junit.Test;

import data.File;

public class FileTests {
	@Test
	public void consoleTest() {
		ArrayList<String> files = File.list("scenes");
		System.out.println("Files are "+files.toString());
		
		boolean result = File.putContent("test.txt", "coucou petite perruche !\nC'est moi l'élan");
		System.out.println("File "+(result ? "" : "not ")+"written");
		
		String content = File.getContent("test.txt");
		System.out.println("File content "+content);
	}
}

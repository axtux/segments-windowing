package tests;

import java.util.ArrayList;

import data.File;

public class FileTests {
	public void main(String[] args) {
		ArrayList<String> files = File.list("scenes");
		System.out.println("Files are "+files.toString());
		
		boolean result = File.putContent("test.txt", "coucou petite perruche !\nC'est moi l'élan");
		System.out.println("File "+(result ? "" : "not ")+"written");
		
		String content = File.getContent("test.txt");
		System.out.println("File content "+content);
	}
}

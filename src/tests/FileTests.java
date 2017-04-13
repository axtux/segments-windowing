package tests;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import data.File;

public class FileTests {
	String filename = "test.txt";
	
	@Test
	public void listTest() {
		ArrayList<String> files = File.list(".");
		
		String[] required_files = {"build.xml", "src", "lib"};
		for(String s : required_files) {
			Assert.assertTrue("File "+s+" is not listed", files.contains(s));
		}
	}
	
	@Test
	public void writeReadTest() {
		String content = "coucou petite perruche !\nC'est moi l'élan";
		Assert.assertTrue("Error writing file "+filename, File.putContent(filename, content));
		
		String read_content = File.getContent(filename);
		Assert.assertEquals("Write content does not match read content", content, read_content);
	}
	
	@After
	public void clean() {
		java.io.File test = new java.io.File(filename);
		if(test.isFile()) {
			Assert.assertTrue("clean test file "+filename, test.delete());
		}
	}
}

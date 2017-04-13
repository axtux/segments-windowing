package data;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;
import java.util.List;
/**
 * Easy interface to manage files.
 */
public class File {
	public static String lastError;
	/**
	 * Write content to filename. Any existing file will be overwritten.
	 * @param filename File name to write.
	 * @param content Content of the file.
	 * @return True on success, false on error.
	 */
	public static boolean putContent(String filename, String content) {
		return putContent(filename, content, false);
	}
	/**
	 * Write content to filename.
	 * @param filename File name to write.
	 * @param content Content of the file.
	 * @param append If true, content will be appended at the end of the file. If false, any existing file will be overwritten.
	 * @return True on success, false on error.
	 */
	public static boolean putContent(String filename, String content, boolean append) {
		Path path = FileSystems.getDefault().getPath(filename);
		Array<String> lines = new Array<String>(1);
		lines.add(content);
		
		try {
			if(append) {
				Files.write(path, lines, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
			} else {
				Files.write(path, lines, StandardCharsets.UTF_8);
			}
			
			return true;
		} catch (IOException e) {
			lastError = e.getMessage();
			return false;
		} catch (UnsupportedOperationException e) {
			lastError = e.getMessage();
			return false;
		} catch (SecurityException e) {
			lastError = e.getMessage();
			return false;
		}
		
	}
	/**
	 * Get file content.
	 * @param filename File name to read.
	 * @return File content or null on error.
	 */
	public static String getContent(String filename) {
		Path path = FileSystems.getDefault().getPath(filename);
		
		try {
			List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
			return String.join("\n", lines);
		} catch (IOException e) {
			lastError = e.getMessage();
			return null;
		} catch (SecurityException e) {
			lastError = e.getMessage();
			return null;
		}
		
	}
	/**
	 * List entries into directory.
	 * @param pathname Directory to read.
	 * @return Sorted list of entry names relative to pathname or null on error.
	 */
	public static Array<String> list(String pathname) {
		Path path = FileSystems.getDefault().getPath(pathname);
		Iterator<Path> it;
		
		try {
			it = Files.newDirectoryStream(path).iterator();
		} catch (IOException e) {
			lastError = e.getMessage();
			return null;
		} catch (SecurityException e) {
			lastError = e.getMessage();
			return null;
		}
		
		Array<String> paths = new Array<String>();
		while(it.hasNext()) {
			paths.add(it.next().getFileName().toString());
		}
		
		paths.sort(String::compareTo);
		
		return paths;
	}
	/**
	 * List entries ending with suffix into directory. Comparison is case insensitive.
	 * @param pathname Directory to read.
	 * @param suffix only entries ending with this suffix will be listed.
	 * @return Sorted list of entry names relative to pathname or null on error.
	 */
	public static Array<String> list(String pathname, String suffix) {
		Array<String> paths = list(pathname);
		if(paths == null) {
			return null;
		}
		
		if(suffix == null || suffix.isEmpty()) {
			return paths;
		}
		
		Array<String> filtered = new Array<String>(paths.size());
		suffix = suffix.toLowerCase();
		for(String path : paths) {
			if(path.toLowerCase().endsWith(suffix)) {
				filtered.add(path);
			}
		}
		
		return filtered;
	}
}

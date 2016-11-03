package utils;

import static java.lang.System.out;

/**
 * Utilities for host system information.
 * @author Casey
 * @version 0.1
 */
public class SysUtils {
	
	//constants
	private static final String SEP = System.getProperty("file.separator");
	private static final String PATH = System.getProperty("user.dir");
	
	/**
	 * Returns true if program is being run on any Windows OS.
	 * @return true if OS is Windows
	 */
	public static boolean isWindows() {
		return System.getProperty("os.name").contains("Windows");
	}
	
	/**
	 * Returns path to user's working directory with trailing separator.
	 * @return path
	 */
	public static String getPath() {
		return PATH + SEP;
	}
	
	/**
	 * Returns OS specific directory separator.
	 * @return directory separator
	 */
	public static String getSeparator() {
		return SEP;
	}
	
	public static void exitDoor(String message) {
		
		out.println("\n" + message + "\n");
		System.exit(1);
	}
}

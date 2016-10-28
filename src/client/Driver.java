package client;

import static java.lang.System.out;

import engine.gUI.*;

/**
 * Driver for Freecell card game.
 * @author groovyLlama devteam
 * @version 0.2
 */
public class Driver {
	
	private static final String SEP = System.getProperty("file.separator");
	private static final String PATH = System.getProperty("user.dir");
	
	public static void main(String[] args) {
		
		// TODO remove/comment next line to auto enter gui
		args = "--test".split(" ");
		
		if (args.length > 0) {
			
			String op = args[0];
			switch(op) {
			
			case "-t" :
			case "--test" :
				CLI.prompt();
				out.println("\n~ groovyLlama devteam, fall 2016 ~\n");
				break;
			default:
				break;
			}
		}
		else {
		
			// TODO initialize and run GUI
			FreeGUI.main(args);
		}	
	}
	
	/**
	 * Returns true if program is being run on any Windows OS.
	 * @return true if OS is Windows
	 */
	public static boolean isWindows() {
		
		return System.getProperty("os.name").contains("Windows");
	}
	
	/**
	 * Returns path to user's working directory.
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
}

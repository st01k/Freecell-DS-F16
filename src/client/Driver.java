package client;

import static java.lang.System.out;

/**
 * Driver for Freecell card game.
 * @author groovyLlama devteam
 * @version 0.1
 */
public class Driver {
	
	public static void main(String[] args) {
		
		// TODO remove/comment next line to auto enter gui
		args = "--test".split(" ");
		
		String op = "";
		if (args.length > 0) op = args[0];
		if (op.matches("-t") || op.matches("--test")) {
			
			CLI.prompt();
			out.println("\n~ groovyLlama devteam, fall 2016 ~\n");
		}
		else {
			// TODO initialize and run GUI
		}		
	}
}

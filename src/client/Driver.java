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
		args = "this is here for auto entry into cli".split(" ");
		if (args.length > 0) {
			
			CLI.prompt();
			out.println("\n~ groovyLlama devteam, fall 2016 ~\n");
		}
		else {
			// TODO initialize GUI
		}		
	}
}

package client;

import static java.lang.System.out;

import engine.gUI.*;

/**
 * Driver for Freecell card game.
 * @author groovyLlama devteam
 * @version 0.2
 */
public class Driver {
	
	public static void main(String[] args) {
		
		// TODO remove/comment next line to auto enter gui
		args = "--test".split(" ");
		
		if (args.length > 0) {
			
			String op = args[0];
			switch(op) {
			
			case "-t" 		:
			case "--test" 	:
				CLI.prompt();
				out.println("\n~ groovyLlama devteam, fall 2016 ~\n");
				break;
			default:
				break;
			}
		}
		else {
		
			// TODO initialize and run GUI
			// TODO modify following when main is removed from FreeGUI
			FreeGUI.main(args);
		}	
	}
}

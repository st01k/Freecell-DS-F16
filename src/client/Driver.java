package client;

import static java.lang.System.out;
import client.cli.CLI;

import engine.Engine;

/**
 * Driver for Freecell card game.
 * @author groovyLlama devteam
 * @version 1.0
 */
public class Driver {
	
	/**
	 * Entry point into program.
	 * @param args arguments from command line
	 */
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
		else Engine.start(true);
	}
}

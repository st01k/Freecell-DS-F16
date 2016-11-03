package client.cli;

import static java.lang.System.out;

import java.util.Scanner;

import playingCards.StdCard;
import engine.Engine;
import utils.*;

/**
 * Freecell command-line interface.
 * @author Casey
 * @version 1.1
 */
public class CLI {
	
	// constants
	private static final Scanner scan = new Scanner(System.in);
	private static final String dbgStr = "[debug]";
	private static final int FFSZ = 50;	// form feed size
	
	// class variables
	private static boolean debug = false;
	private static String prompt;

	/**
	 * Main prompt.
	 */
	public static void prompt() {
		
		out.println("<<<< Freecell CLI v0.1 >>>>");
		out.println("Type 'help' for commands.");
		out.println("Type 'exit' any time.");
		out.println();
		
		boolean cont = true;
		do {

			prompt = "freecell> ";
			if (debug) prompt = dbgStr + prompt;
			out.print(prompt);
			String in = scan.nextLine().toLowerCase();
			
			switch(in) {
			
			case ("exit") 	: cont = false;
				break;
			case ("help") 	: printHelp();
				break;
			case ("test") 	: Tester.enter();
				break;
			case ("path")	: out.println(SysUtils.getPath()); 
				break;
			case ("debug") 	: toggleDebug();
				break;
			case ("uni")	: StdCard.toggleUni();
				break;
			case ("gui")	:  
				Engine.start(true); 
				out.println();
				break;
			case ("cli")	:
				game();
				break;
			case ("cls") 	: formFeed();
				break; 
			case ("cred")	: credz();
				break;
			default 		:  
				out.println("Invalid Command.  Type 'help' for a list of commands.");
			}
		} while (cont);
	}
	
	/**
	 * Main prompt.
	 */
	public static void game() {
		
		out.println("\nCell positions are referenced by the top letters.");
		out.println("Pile positions are referenced by the bottom letters.");
		out.println("Cards inserted into cells will fill the next open cell.");
		out.println("Black: Spades & Clubs | Red: Hearts & Diamonds");
		
		Engine.start(false);
	}
	
	/**
	 * Toggle master debug switch in Debugger.
	 */
	private static void toggleDebug() {	
		debug = !debug;
		Debugger.masterToggleDebug();
	}
	
	/**
	 * Prints help.
	 */
	private static void printHelp() {
		
		out.println();
		out.println("Available Commands: ");
		out.println("----------------------------------------------");
		out.println("help\tPrints this menu");
		out.println("test\tRuns unit tests");
		out.println("path\tPrints working directory");
		out.println("debug\tToggles debug mode");
		out.println("uni\tToggles unicode characters");
		out.println("gui\tStarts game in GUI");
		out.println("cli\tStarts game in CLI");
		out.println("cls\tClear screen");
		out.println("cred\tPrints credits");
		out.println("exit\tExits current prompt");
		out.println();
	}
	

	
	/**
	 * Prints a form feed to screen.  Scrolls up specified lines.
	 * Amount is specified with FFSZ constant.
	 */
	private static void formFeed() {
		for (int i = 0; i < FFSZ; i++) out.println();
	}
	
	/**
	 * Prints project credits.
	 */
	private static void credz() {
		
		out.println();
		out.println(".-----.----.-----.-----.--.--.--.--.");
		out.println("|  _  |   _|  _  |  _  |  |  |  |  |");
		out.println("|___  |__| |_____|_____|\\___/|___  |");
		out.println("|_____|                      |_____|");
		out.println(" ___    __");
		out.println("|   |  |  .---.-.--------.---.-.");
		out.println("|.  |  |  |  _  |        |  _  |");
		out.println("|.  |__|__|___._|__|__|__|___._|");
		out.println("|:      |");
		out.println("|::.. . |	DevTeam");
		out.println("`-------'");
		out.println();
		out.println("Casey Murphy 		- Lead Ninja");
		out.println("Brandon Wharton 	- Jedi Master");
		out.println("Ryan Whytsell 		- GUI Wizard");
		out.println("Gordon Finnie 		- Guard Dawg");
		out.println();
	}
}
